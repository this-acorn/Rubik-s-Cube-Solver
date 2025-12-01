package rubikscube;

import java.io.*;
import java.util.*;

public class RubiksCube {

	static final int U=0, D=1, F=2, B=3, L=4, R=5;
	
	final char[][][] face=new char[6][3][3];
	

    /*   OOO
    *    OOO
    *    OOO
    * GGGWWWBBBYYY
    * GGGWWWBBBYYY
    * GGGWWWBBBYYY
    *    RRR
    *    RRR
    *    RRR
    *    
    */

  


    public RubiksCube() {
    	fill(face[U], 'O'); 
		fill(face[D], 'R'); 
		fill(face[F], 'W'); 
		fill(face[B], 'Y'); 
		fill(face[L], 'G');
		fill(face[R], 'B'); 
    
    }



    private static void fill(char[][] arr, char c) {
    	for(int i=0; i<3; i++) {
    		Arrays.fill(arr[i], c);
    	}
    }
   
    public RubiksCube(String fileName) throws IOException, IncorrectFormatException {
    	this();
    	List<String>  lines=new ArrayList<>();
    	try(BufferedReader br=new BufferedReader(new FileReader(fileName))){
    		for(int i=0; i<9; i++) {
    			String s=br.readLine();
    			if(s==null) throw new IncorrectFormatException("there's no value");
    			lines.add(s);
    		}
    		if (br.readLine()!=null) throw new IncorrectFormatException("Too many lines");
    	}
    	fileToCube(lines);
    }

    //    private void fileToCube(List<String> c) throws IncorrectFormatException {
       
    //     for (int i = 0; i < 3; i++) {
    //         String s = c.get(i);
    //         for (int j = 0; j < 3; j++) face[U][i][j] = s.charAt(j + 3);
    //     }
       
    //     for (int i = 0; i < 3; i++) {
    //         String s = c.get(i + 3);
    //         for (int j = 0; j < 3; j++) face[L][i][j] = s.charAt(j);       
    //         for (int j = 0; j < 3; j++) face[F][i][j] = s.charAt(j + 3);   
    //         for (int j = 0; j < 3; j++) face[R][i][j] = s.charAt(j + 6);  
    //         for (int j = 0; j < 3; j++) face[B][i][j] = s.charAt(j + 9);   
    //     }
        
    //     for (int i = 0; i < 3; i++) {
    //         String s = c.get(i + 6);
    //         for (int j = 0; j < 3; j++) face[D][i][j] = s.charAt(j + 3);
    //     }
    // }




    public void fileToCube(List<String> c) throws IncorrectFormatException {

    // ------------ U (lines 0~2, cols 3~5) ------------
    for (int i = 0; i < 3; i++) {
        String s = c.get(i);
        face[U][i][0] = s.charAt(3);
        face[U][i][1] = s.charAt(4);
        face[U][i][2] = s.charAt(5);
    }

    // ------------ L F R B (lines 3~5) ------------
    for (int i = 0; i < 3; i++) {
        String s = c.get(i + 3);

        // L = cols 0~2
        face[L][i][0] = s.charAt(0);
        face[L][i][1] = s.charAt(1);
        face[L][i][2] = s.charAt(2);

        // F = cols 3~5
        face[F][i][0] = s.charAt(3);
        face[F][i][1] = s.charAt(4);
        face[F][i][2] = s.charAt(5);

        // R = cols 6~8
        face[R][i][0] = s.charAt(6);
        face[R][i][1] = s.charAt(7);
        face[R][i][2] = s.charAt(8);

        // B = cols 9~11
        face[B][i][0] = s.charAt(9);
        face[B][i][1] = s.charAt(10);
        face[B][i][2] = s.charAt(11);
    }

    // ------------ D (lines 6~8, cols 3~5) ------------
    for (int i = 0; i < 3; i++) {
        String s = c.get(i + 6);
        face[D][i][0] = s.charAt(3);
        face[D][i][1] = s.charAt(4);
        face[D][i][2] = s.charAt(5);
    }
}




















   // =========================================================
    //  RubiksCube (facelets)  -->  CubieCube (cp/co/ep/eo)
    // =========================================================

    // solved 상태 기준 corner 색 (U/D, R/L, F/B 순서)
    // index: 0:URF, 1:UFL, 2:ULB, 3:UBR, 4:DFR, 5:DLF, 6:DBL, 7:DRB
    private static final char[][] CORNER_COLORS = {
        {'O','B','W'}, // URF
        {'O','G','W'}, // UFL
        {'O','G','Y'}, // ULB
        {'O','B','Y'}, // UBR
        {'R','B','W'}, // DFR
        {'R','G','W'}, // DLF
        {'R','G','Y'}, // DBL
        {'R','B','Y'}  // DRB
    };

    // solved 상태 기준 edge 색 (첫번째, 두번째 스티커 순서)
    // index: 0:UR, 1:UF, 2:UL, 3:UB, 4:FR, 5:FL, 6:BL, 7:BR,
    //        8:DR, 9:DF, 10:DL, 11:DB
    private static final char[][] EDGE_COLORS = {
        {'O','B'}, // UR
        {'O','W'}, // UF
        {'O','G'}, // UL
        {'O','Y'}, // UB
        {'W','B'}, // FR
        {'W','G'}, // FL
        {'Y','G'}, // BL
        {'Y','B'}, // BR
        {'R','B'}, // DR
        {'R','W'}, // DF
        {'R','G'}, // DL
        {'R','Y'}  // DB
    };
 
    /*   OOO
    *    OOO
    *    OOO
    * GGGWWWBBBYYY
    * GGGWWWBBBYYY
    * GGGWWWBBBYYY
    *    RRR
    *    RRR
    *    RRR
    *    
    */































    public CubieCube toCubie() throws IncorrectFormatException {
        CubieCube cc = new CubieCube();

        // -------------------------
        // 1) corners: cp[], co[]
        // -------------------------
        for (int pos = 0; pos < 8; pos++) {
            char[] c = new char[3];

            switch (pos) {
                case 0: // URF: (U,R,F)  U W G
                //0:URF, 1:UFL, 2:ULB, 3:UBR, 4:DFR, 5:DLF, 6:DBL, 7:DRB
                    c[0] = face[U][2][2];
                    c[1] = face[R][0][0];
                    c[2] = face[F][0][2];

                    break;
                case 1: // UFL: (U,L,F)
                    c[0] = face[U][2][0];
                    c[1] = face[L][0][2];
                    c[2] = face[F][0][0];
                    break;
                case 2: // ULB: (U,L,B)
                    c[0] = face[U][0][0];
                    c[1] = face[L][0][0];
                    c[2] = face[B][0][2];
                    break;
                case 3: // UBR: (U,R,B)
                    c[0] = face[U][0][2];
                    c[1] = face[R][0][2];
                    c[2] = face[B][0][0];
                    break;
                case 4: // DFR: (D,R,F)
                    c[0] = face[D][0][2];
                    c[1] = face[R][2][0];
                    c[2] = face[F][2][2];
                    break;
                case 5: // DLF: (D,L,F)
                    c[0] = face[D][0][0];
                    c[1] = face[L][2][2];
                    c[2] = face[F][2][0];
                    break;
                case 6: // DBL: (D,L,B)
                    c[0] = face[D][2][0];
                    c[1] = face[L][2][0];
                    c[2] = face[B][2][2];
                    break;
                case 7: // DRB: (D,R,B)
                    c[0] = face[D][2][2];
                    c[1] = face[R][2][2];
                    c[2] = face[B][2][0];
                    break;
                    
            }
                          System.out.println("Corner " + pos + " = " + c[0] + " " + c[1] + " " + c[2]);

            boolean found = false;
            // 어떤 corner 조각인지 찾기
            for (int cubie = 0; cubie < 8 && !found; cubie++) {
                char[] ref = CORNER_COLORS[cubie];
                // orientation 0,1,2에 대해 cyclic rotation 검사
                for (int ori = 0; ori < 3 && !found; ori++) {
                    boolean match = true;
                    for (int i = 0; i < 3; i++) {
                        if (c[i] != ref[(i + ori) % 3]) {
                            match = false;
                            break;
                        }
                    }
                    if (match) {
                        cc.cp[pos] = cubie;
                        cc.co[pos] = ori;  // ori = 0,1,2 (twist)
                        found = true;
                    }
                }
            }
            if (!found) {
                throw new IncorrectFormatException("Invalid corner colors at position " + pos);
            }
        }




    
        // -------------------------
        // 2) edges: ep[], eo[]
        // -------------------------
        for (int pos = 0; pos < 12; pos++) {
            char a = 0, b = 0;

            switch (pos) {
                case 0: // UR: (U,R)
                    a = face[U][1][2];
                    b = face[R][0][1];
                    break;
                case 1: // UF: (U,F)
                    a = face[U][2][1];
                    b = face[F][0][1];
                    break;
                case 2: // UL: (U,L)
                    a = face[U][1][0];
                    b = face[L][0][1];
                    break;
                case 3: // UB: (U,B)
                    a = face[U][0][1];
                    b = face[B][0][1];
                    break;
                case 4: // FR: (F,R)
                    a = face[F][1][2];
                    b = face[R][1][0];
                    break;
                case 5: // FL: (F,L)
                    a = face[F][1][0];
                    b = face[L][1][2];
                    break;
                case 6: // BL: (B,L)
                    a = face[B][1][0]; //Y
                    b = face[L][1][2];//  G
                    break;
                case 7: // BR: (B,R)
                    a = face[B][1][2];
                    b = face[R][1][0]; //
                    break;
                case 8: // DR: (D,R)
                    a = face[D][1][2];
                    b = face[R][2][1];
                    break;
                case 9: // DF: (D,F)
                    a = face[D][0][1];
                    b = face[F][2][1];
                    break;
                case 10: // DL: (D,L)
                    a = face[D][1][0];
                    b = face[L][2][1];
                    break;
                case 11: // DB: (D,B)
                    a = face[D][2][1];
                    b = face[B][2][1];
                    break;
            }



            boolean found = false;
            for (int cubie = 0; cubie < 12 && !found; cubie++) {
                char[] ref = EDGE_COLORS[cubie];
                if (a == ref[0] && b == ref[1]) {
                    cc.ep[pos] = cubie;
                    cc.eo[pos] = 0;  // not flipped
                    found = true;
                } else if (a == ref[1] && b == ref[0]) {
                    cc.ep[pos] = cubie;
                    cc.eo[pos] = 1;  // flipped
                    found = true;
                }
            }
            if (!found) {
                throw new IncorrectFormatException("Invalid edge colors at position " + pos);
            }
        }

        return cc;
    }





    
    // -------------------------------------------------------
    // Public: applyMove(String)
    // -------------------------------------------------------
    public void applyMove(String m) {
        switch(m) {
            case "U":  turnU(); break;
            case "U2": turnU(); turnU(); break;
            case "U'": turnU(); turnU(); turnU(); break;

            case "R":  turnR(); break;
            case "R2": turnR(); turnR(); break;
            case "R'": turnR(); turnR(); turnR(); break;

            case "F":  turnF(); break;
            case "F2": turnF(); turnF(); break;
            case "F'": turnF(); turnF(); turnF(); break;

            case "D":  turnD(); break;
            case "D2": turnD(); turnD(); break;
            case "D'": turnD(); turnD(); turnD(); break;

            case "L":  turnL(); break;
            case "L2": turnL(); turnL(); break;
            case "L'": turnL(); turnL(); turnL(); break;

            case "B":  turnB(); break;
            case "B2": turnB(); turnB(); break;
            case "B'": turnB(); turnB(); turnB(); break;

            default:
                throw new IllegalArgumentException("Invalid move: " + m);
        }
    }

    // -------------------------------------------------------
    // Face rotations (sticker-level)
    // -------------------------------------------------------

    private void rotateFaceCW(char[][] f) {
        char[][] tmp = new char[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tmp[j][2 - i] = f[i][j];
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                f[i][j] = tmp[i][j];
            }
        }
    }

    // ----------------- U MOVE -----------------
private void turnU() {
    rotateFaceCW(face[U]);

    char[] tmp = face[F][0].clone();
     face[F][0] = face[R][0].clone();  // R → F
    face[R][0] = face[B][0].clone();  // B → R
    face[B][0] = face[L][0].clone();  // L → B
    face[L][0] = tmp;                 // F → L

}

    // ----------------- D MOVE -----------------
private void turnD() {
    rotateFaceCW(face[D]);

    char[] tmp = face[F][2].clone();
    face[F][2] = face[R][2].clone();
    face[R][2] = face[B][2].clone();
    face[B][2] = face[L][2].clone();
    face[L][2] = tmp;
}
















public void printURF() {
    System.out.println(
        "URF corner colors = " +
        face[U][2][2] + " " +  // U 면
        face[R][0][0] + " " +  // R 면
        face[F][0][2]          // F 면
    );
}











private void turnR() {
    rotateFaceCW(face[R]);

    char t0 = face[U][0][2];
    char t1 = face[U][1][2];
    char t2 = face[U][2][2];

    face[U][0][2] = face[F][0][2];
    face[U][1][2] = face[F][1][2];
    face[U][2][2] = face[F][2][2];

    face[F][0][2] = face[D][0][2];
    face[F][1][2] = face[D][1][2];
    face[F][2][2] = face[D][2][2];

    face[D][0][2] = face[B][2][0];
    face[D][1][2] = face[B][1][0];
    face[D][2][2] = face[B][0][0];

    face[B][2][0] = t0;
    face[B][1][0] = t1;
    face[B][0][0] = t2;
}
private void turnL() {
    rotateFaceCW(face[L]);

    char t0 = face[U][0][0];
    char t1 = face[U][1][0];
    char t2 = face[U][2][0];

    face[U][0][0] = face[B][2][2];
    face[U][1][0] = face[B][1][2];
    face[U][2][0] = face[B][0][2];

    face[B][2][2] = face[D][0][0];
    face[B][1][2] = face[D][1][0];
    face[B][0][2] = face[D][2][0];

    face[D][0][0] = face[F][0][0];
    face[D][1][0] = face[F][1][0];
    face[D][2][0] = face[F][2][0];

    face[F][0][0] = t0;
    face[F][1][0] = t1;
    face[F][2][0] = t2;
}


    // ----------------- F MOVE -----------------
    private void turnF() {
        rotateFaceCW(face[F]);

        char[] tmp = {
                face[U][2][0], face[U][2][1], face[U][2][2]
        };

        face[U][2][0] = face[L][2][2];
        face[U][2][1] = face[L][1][2];
        face[U][2][2] = face[L][0][2];

        face[L][2][2] = face[D][0][2];
        face[L][1][2] = face[D][0][1];
        face[L][0][2] = face[D][0][0];

        face[D][0][2] = face[R][0][0];
        face[D][0][1] = face[R][1][0];
        face[D][0][0] = face[R][2][0];

        face[R][0][0] = tmp[0];
        face[R][1][0] = tmp[1];
        face[R][2][0] = tmp[2];
    }

    // ----------------- B MOVE -----------------
    private void turnB() {
        rotateFaceCW(face[B]);

        char[] tmp = {
                face[U][0][0], face[U][0][1], face[U][0][2]
        };

        face[U][0][0] = face[R][0][2];
        face[U][0][1] = face[R][1][2];
        face[U][0][2] = face[R][2][2];

        face[R][0][2] = face[D][2][2];
        face[R][1][2] = face[D][2][1];
        face[R][2][2] = face[D][2][0];

        face[D][2][2] = face[L][2][0];
        face[D][2][1] = face[L][1][0];
        face[D][2][0] = face[L][0][0];

        face[L][2][0] = tmp[0];
        face[L][1][0] = tmp[1];
        face[L][0][0] = tmp[2];
    }
}






  

    
    


	
