package rubikscube;

public class TestMain {

    public static void main(String[] args) throws Exception {

        System.out.println("===== TEST: toCubie() CORNER PRINT =====");

        RubiksCube rc = new RubiksCube(); // solved cube
        CubieCube cc = rc.toCubie();

        printCorners(rc);

        System.out.println();
        System.out.println("===== TEST: simple moves (R U R') =====");

        rc.applyMove("R");
        rc.applyMove("U");
        rc.applyMove("R'");

        printCorners(rc);

        System.out.println();
        System.out.println("===== TEST: Phase1 solve =====");

        Phase1Solver solver = new Phase1Solver();
        String sol = solver.solveFromSticker(rc);

        System.out.println("Phase1 result:");
        System.out.println(sol);
    }

    private static void printCorners(RubiksCube rc) throws Exception {

        System.out.println("--- Sticker → Cubie corner check ---");
        CubieCube cc = rc.toCubie();

        // corner index → name
        String[] cornerName = {
            "URF","UFL","ULB","UBR","DFR","DLF","DBL","DRB"
        };

        for (int pos = 0; pos < 8; pos++) {

            char[] c = new char[3];

            switch (pos) {
                case 0: // URF
                    c[0] = rc.face[RubiksCube.U][2][2];
                    c[1] = rc.face[RubiksCube.R][0][0];
                    c[2] = rc.face[RubiksCube.F][0][2];
                    break;
                case 1: // UFL
                    c[0] = rc.face[RubiksCube.U][2][0];
                    c[1] = rc.face[RubiksCube.L][0][2];
                    c[2] = rc.face[RubiksCube.F][0][0];
                    break;
                case 2: // ULB
                    c[0] = rc.face[RubiksCube.U][0][0];
                    c[1] = rc.face[RubiksCube.L][0][0];
                    c[2] = rc.face[RubiksCube.B][0][2];
                    break;
                case 3: // UBR
                    c[0] = rc.face[RubiksCube.U][0][2];
                    c[1] = rc.face[RubiksCube.R][0][2];
                    c[2] = rc.face[RubiksCube.B][0][0];
                    break;
                case 4: // DFR
                    c[0] = rc.face[RubiksCube.D][0][2];
                    c[1] = rc.face[RubiksCube.R][2][0];
                    c[2] = rc.face[RubiksCube.F][2][2];
                    break;
                case 5: // DLF
                    c[0] = rc.face[RubiksCube.D][0][0];
                    c[1] = rc.face[RubiksCube.L][2][2];
                    c[2] = rc.face[RubiksCube.F][2][0];
                    break;
                case 6: // DBL
                    c[0] = rc.face[RubiksCube.D][2][0];
                    c[1] = rc.face[RubiksCube.L][2][0];
                    c[2] = rc.face[RubiksCube.B][2][2];
                    break;
                case 7: // DRB
                    c[0] = rc.face[RubiksCube.D][2][2];
                    c[1] = rc.face[RubiksCube.R][2][2];
                    c[2] = rc.face[RubiksCube.B][2][0];
                    break;
            }

            System.out.println(cornerName[pos] + " = "
                    + c[0] + " " + c[1] + " " + c[2]
                    + "   | Cubie index: " + cc.cp[pos]
                    + " ori: " + cc.co[pos]);
        }
    }
}
