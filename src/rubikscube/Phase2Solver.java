package rubikscube;

import java.util.ArrayList;
import java.util.List;

public class Phase2Solver {

    
    private static final int MAX_DEPTH = 18;

    private final List<Integer> path = new ArrayList<>();

    public String solve(CubieCube cube) {
        path.clear();

  
        int cp = cube.getCPIndex();
        int ude = cube.getUDEdgeIndex();
        int slp = cube.getSlicePermIndex();

        int h = Phase2Prune1D.heuristic(cp, ude, slp);

        for (int depth = h; depth <= MAX_DEPTH; depth++) {
            if (search(cp, ude, slp, depth, -1)) {
                return solutionString();
            }
        }
        return "No Phase 2 solution up to depth " + MAX_DEPTH;
    }

    private boolean search(int cp, int ude, int slp, int depth, int lastMove) {
        int h = Phase2Prune1D.heuristic(cp, ude, slp);
        if (h > depth) return false;

        if (depth == 0) {
            
            // cp index = 0, ude index = 0, slice perm = 0 이면 solved
            return (cp == 0 && ude == 0 && slp == 0);
        }

        for (int move = 0; move < Phase2Tables.N_MOVES; move++) {

            if (lastMove != -1 && face(move) == face(lastMove)) continue;

            int cp2  = Phase2Tables.CP_MOVE[move][cp];
            int ude2 = Phase2Tables.UDE_MOVE[move][ude];
            int slp2 = Phase2Tables.SLICEP_MOVE[move][slp];

            if (search(cp2, ude2, slp2, depth - 1, move)) {
                path.add(move);
                return true;
            }
        }

        return false;
    }

    // move index -> face index (0:U,1:D,2:R,3:L,4:F,5:B)
    private int face(int move) {
        switch (move) {
            case 0: // U
            case 1: // U2
            case 2: // U'
                return 0;
            case 3: // D
            case 4: // D2
            case 5: // D'
                return 1;
            case 6: // R2
                return 2;
            case 7: // L2
                return 3;
            case 8: // F2
                return 4;
            case 9: // B2
                return 5;
            default:
                return -1;
        }
    }

    private String solutionString() {
        StringBuilder sb = new StringBuilder();
        for (int i = path.size() - 1; i >= 0; i--) {
            int m = path.get(i);
            sb.append(Phase2Tables.MOVES[m]);
            if (i > 0) sb.append(' ');
        }
        return sb.toString();
    }

    public List<String> getMoveList() {
        List<String> res = new ArrayList<>();
        for (int i = path.size() - 1; i >= 0; i--) {
            res.add(Phase2Tables.MOVES[path.get(i)]);
        }
        return res;
    }
}
