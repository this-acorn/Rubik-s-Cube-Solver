package rubikscube;

public class Phase2Tables {

    // G1 moves only
    public static final String[] MOVES = {
            "U", "U2", "U'",
            "D", "D2", "D'",
            "R2", "L2", "F2", "B2"
    };
    public static final int N_MOVES = MOVES.length;

    // Tables
    public static int[][] CP_MOVE;
    public static int[][] UDE_MOVE;
    public static int[][] SLICEP_MOVE;

    // ======================
    // Initialization
    // ======================
    static {
        System.out.println("Building Phase 2 move tables...");
        buildMoveTables();
        System.out.println("Phase 2 move tables ready.");
    }

    private static void buildMoveTables() {

        CP_MOVE = new int[N_MOVES][40320];       // 8! = 40320
        UDE_MOVE = new int[N_MOVES][40320];      // UD edges also 8! = 40320
        SLICEP_MOVE = new int[N_MOVES][24];      // 4! = 24

        // For each move
        for (int m = 0; m < N_MOVES; m++) {
            String mv = MOVES[m];

            // ---- CP (40320 states) ----
            for (int idx = 0; idx < 40320; idx++) {
                CubieCube cc = new CubieCube();
                cc.setCPFromIndex(idx);

                cc.applyMove(mv);

                CP_MOVE[m][idx] = cc.getCPIndex();
            }

            // ---- UD Edges (40320 states) ----
            for (int idx = 0; idx < 40320; idx++) {
                CubieCube cc = new CubieCube();
                cc.setUDEdgeFromIndex(idx);

                cc.applyMove(mv);

                UDE_MOVE[m][idx] = cc.getUDEdgeIndex();
            }

            // ---- Slice edges (24 states) ----
            for (int idx = 0; idx < 24; idx++) {
                CubieCube cc = new CubieCube();
                cc.setSlicePermFromIndex(idx);

                cc.applyMove(mv);

                SLICEP_MOVE[m][idx] = cc.getSlicePermIndex();
            }
        }
    }
}
