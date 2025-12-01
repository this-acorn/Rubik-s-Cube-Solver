package rubikscube;

public class Phase1Tables {

    // EO: 0..2047, CO: 0..2186
    public static final int N_EO = 2048;
    public static final int N_CO = 2187;

    // 18 moves
    public static final String[] MOVES = {
        "U","U2","U'",
        "R","R2","R'",
        "F","F2","F'",
        "D","D2","D'",
        "L","L2","L'",
        "B","B2","B'"
    };

    public static final int N_MOVES = MOVES.length;

    
    public static int[][] EO_MOVE = new int[N_MOVES][N_EO];


    public static int[][] CO_MOVE = new int[N_MOVES][N_CO];

    public static final int N_SLICE = 495;
    public static int[][] SLICE_MOVE = new int[N_MOVES][N_SLICE];


    static {
        initEoMove();
        initCoMove();
         initSliceMove();
        System.out.println("Phase1Tables: EO / CO / Slice initialized.");
    }

    private static void initEoMove() {
        CubieCube cube = new CubieCube();

        for (int e = 0; e < N_EO; e++) {
            
            cube = new CubieCube();       
            cube.setEOFromIndex(e);

            for (int m = 0; m < N_MOVES; m++) {
                CubieCube c2 = new CubieCube();
             
                System.arraycopy(cube.cp, 0, c2.cp, 0, 8);
                System.arraycopy(cube.co, 0, c2.co, 0, 8);
                System.arraycopy(cube.ep, 0, c2.ep, 0, 12);
                System.arraycopy(cube.eo, 0, c2.eo, 0, 12);

                c2.applyMove(MOVES[m]);
                EO_MOVE[m][e] = c2.getEO();
            }
        }
    }
    private static void initSliceMove() {
        CubieCube cube = new CubieCube();

        for (int s = 0; s < N_SLICE; s++) {
            cube = new CubieCube();
            cube.setUDSlice(s);

            for (int m = 0; m < N_MOVES; m++) {
                CubieCube c2 = new CubieCube();
                System.arraycopy(cube.cp, 0, c2.cp, 0, 8);
                System.arraycopy(cube.co, 0, c2.co, 0, 8);
                System.arraycopy(cube.ep, 0, c2.ep, 0, 12);
                System.arraycopy(cube.eo, 0, c2.eo, 0, 12);

                c2.applyMove(MOVES[m]);
                SLICE_MOVE[m][s] = c2.getUDSlice();
            }
        }
    }

    private static void initCoMove() {
        CubieCube cube = new CubieCube();

        for (int c = 0; c < N_CO; c++) {
            cube = new CubieCube();
            cube.setCOFromIndex(c);

            for (int m = 0; m < N_MOVES; m++) {
                CubieCube c2 = new CubieCube();
                System.arraycopy(cube.cp, 0, c2.cp, 0, 8);
                System.arraycopy(cube.co, 0, c2.co, 0, 8);
                System.arraycopy(cube.ep, 0, c2.ep, 0, 12);
                System.arraycopy(cube.eo, 0, c2.eo, 0, 12);

                c2.applyMove(MOVES[m]);
                CO_MOVE[m][c] = c2.getCO();
            }
        }
    }
}
