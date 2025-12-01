package rubikscube;

import java.util.ArrayList;
import java.util.List;

public class Phase1Solver {

   
    private static final int MAX_DEPTH = 20;

    
    private final List<Integer> path = new ArrayList<>();

  
    public String solve(CubieCube cube) {
        path.clear();

        int eo = cube.getEO();
        int co = cube.getCO();
        int sl = cube.getUDSlice();

        int h = Phase1Prune1D.heuristic(eo, co, sl);

       
        for (int depth = h; depth <= MAX_DEPTH; depth++) {
            if (search(eo, co, sl, depth, -1)) {
                return solutionString();
            }
        }
        return "No Phase 1 solution up to depth " + MAX_DEPTH;
    }

   
    public String solveFromSticker(RubiksCube sticker) throws IncorrectFormatException {
        CubieCube cc = sticker.toCubie();
        return solve(cc);
    }

   final int solvedSliceIndex = 425; 
     
    private boolean search(int eo, int co, int sl, int depth, int lastMove) {
        int h = Phase1Prune1D.heuristic(eo, co, sl);
        if (h > depth) {
            return false;
        }

        if (depth == 0) {
          
            return eo == 0 && co == 0 && sl == solvedSliceIndex;
        }

        for (int move = 0; move < Phase1Tables.N_MOVES; move++) {

           
            if (lastMove != -1 && face(move) == face(lastMove)) continue;

            int eo2 = Phase1Tables.EO_MOVE[move][eo];
            int co2 = Phase1Tables.CO_MOVE[move][co];
            int sl2 = Phase1Tables.SLICE_MOVE[move][sl];

            if (search(eo2, co2, sl2, depth - 1, move)) {
               
                path.add(move);
                return true;
            }
        }
        return false;
    }

   
    private int face(int move) {
        if (move < 0) return -1;
        return move / 3;
    }

    private String solutionString() {
        StringBuilder sb = new StringBuilder();
        for (int i = path.size() - 1; i >= 0; i--) {
            int m = path.get(i);
            sb.append(Phase1Tables.MOVES[m]);
            if (i > 0) sb.append(' ');
        }
        return sb.toString();
    }

 
    public List<String> getMoveList() {
        List<String> res = new ArrayList<>();
        for (int i = path.size() - 1; i >= 0; i--) {
            res.add(Phase1Tables.MOVES[path.get(i)]);
        }
        return res;
    }
}
