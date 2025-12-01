package rubikscube;

public class CubieCube {
    // corner permutation & orientation
    // corners 0..7 in this order:
    // 0: URF, 1: UFL, 2: ULB, 3: UBR, 4: DFR, 5: DLF, 6: DBL, 7: DRB
    public int[] cp = new int[8];   // which corner is at position i
    public int[] co = new int[8];   // orientation of that corner (0,1,2)

    // edge permutation & orientation
    // edges 0..11 in this order:
    // 0: UR, 1: UF, 2: UL, 3: UB,
    // 4: FR, 5: FL, 6: BL, 7: BR,
    // 8: DR, 9: DF, 10: DL, 11: DB
    public int[] ep = new int[12];  // which edge is at position i
    public int[] eo = new int[12];  // orientation of that edge (0,1)

    public CubieCube() {
              //solved state
        for (int i = 0; i < 8; i++) {
            cp[i] = i;
            co[i] = 0;
        }
        for (int i = 0; i < 12; i++) {
            ep[i] = i;
            eo[i] = 0;
        }
    }

    public CubieCube(CubieCube other) {
        this.cp = other.cp.clone();
        this.co = other.co.clone();
        this.ep = other.ep.clone();
        this.eo = other.eo.clone();
    }


    public void moveU() { 
        
        int[] oldCp = cp.clone();
        int[] oldCo = co.clone();
        int[] oldEp = ep.clone();
        int[] oldEo = eo.clone();

          // Corner permutation cycle (0 3 2 1)
        cp[0] = oldCp[3];
        cp[1] = oldCp[0];
        cp[2] = oldCp[1];
        cp[3] = oldCp[2];

        // Corner orientation: no change
        co[0] = oldCo[3];
        co[1] = oldCo[0];
        co[2] = oldCo[1];
        co[3] = oldCo[2];

        // Edge permutation cycle (0 3 2 1)
        ep[0] = oldEp[3];
        ep[1] = oldEp[0];
        ep[2] = oldEp[1];
        ep[3] = oldEp[2];


    // 0: UR, 1: UF, 2: UL, 3: UB,
    // 4: FR, 5: FL, 6: BL, 7: BR,
    // 8: DR, 9: DF, 10: DL, 11: DB

        // Edge orientation: no change
        eo[0] = oldEo[3];
        eo[1] = oldEo[0];
        eo[2] = oldEo[1];
        eo[3] = oldEo[2];
    }

    public void moveR() {
        int[] oldCp = cp.clone();
        int[] oldCo = co.clone();
        int[] oldEp = ep.clone();
        int[] oldEo = eo.clone();

        // Corner permutation (0 4 7 3)
        cp[0] = oldCp[4];
        cp[4] = oldCp[7];
        cp[7] = oldCp[3];
        cp[3] = oldCp[0];

         // 0: URF, 1: UFL, 2: ULB, 3: UBR, 4: DFR, 5: DLF, 6: DBL, 7: DRB

        // Corner orientation deltas
        // twist +2 → (co + 2) % 3
        // twist +1 → (co + 1) % 3
        co[0] = (oldCo[4] + 2) % 3;
        co[4] = (oldCo[7] + 1) % 3;
        co[7] = (oldCo[3] + 2) % 3;
        co[3] = (oldCo[0] + 1) % 3;

        // Edge permutation (4 7 11 8)
        ep[4] = oldEp[7];
        ep[7] = oldEp[11];
        ep[11] = oldEp[8];
        ep[8] = oldEp[4];

        // Edge orientation: no change on R face
        eo[4] = oldEo[7];
        eo[7] = oldEo[11];
        eo[11] = oldEo[8];
        eo[8] = oldEo[4];
    }


    public void moveF() {
        int[] oldCp = cp.clone();
        int[] oldCo = co.clone();
        int[] oldEp = ep.clone();
        int[] oldEo = eo.clone();

        // Corner permutation (0 1 5 4)
        cp[0] = oldCp[1];
        cp[1] = oldCp[5];
        cp[5] = oldCp[4];
        cp[4] = oldCp[0];

        // Corner orientation deltas (+1, +2, +1, +2)
        co[0] = (oldCo[1] + 1) % 3;
        co[1] = (oldCo[5] + 2) % 3;
        co[5] = (oldCo[4] + 1) % 3;
        co[4] = (oldCo[0] + 2) % 3;

        // Edge permutation (1 5 9 4)
        ep[1] = oldEp[5];
        ep[5] = oldEp[9];
        ep[9] = oldEp[4];
        ep[4] = oldEp[1];

        // Edge orientation flips (eo ^= 1)
        eo[1] = oldEo[5] ^ 1;
        eo[5] = oldEo[9] ^ 1;
        eo[9] = oldEo[4] ^ 1;
        eo[4] = oldEo[1] ^ 1;
    }

    public void moveD() {
        int[] oldCp = cp.clone();
        int[] oldCo = co.clone();
        int[] oldEp = ep.clone();
        int[] oldEo = eo.clone();

        // Corner permutation cycle (4 5 6 7)
        cp[4] = oldCp[5];
        cp[5] = oldCp[6];
        cp[6] = oldCp[7];
        cp[7] = oldCp[4];

        // Orientation unchanged
        co[4] = oldCo[5];
        co[5] = oldCo[6];
        co[6] = oldCo[7];
        co[7] = oldCo[4];

        // Edge permutation cycle (8 9 10 11)
        ep[8]  = oldEp[9];
        ep[9]  = oldEp[10];
        ep[10] = oldEp[11];
        ep[11] = oldEp[8];

        // Edge orientation unchanged
        eo[8]  = oldEo[9];
        eo[9]  = oldEo[10];
        eo[10] = oldEo[11];
        eo[11] = oldEo[8];
    }


    public void moveL() {
        int[] oldCp = cp.clone();
        int[] oldCo = co.clone();
        int[] oldEp = ep.clone();
        int[] oldEo = eo.clone();

        // Corner cycle (1 2 6 5)
        cp[1] = oldCp[2];
        cp[2] = oldCp[6];
        cp[6] = oldCp[5];
        cp[5] = oldCp[1];

        // Corner orientation deltas (+2, +1, +2, +1)
        co[1] = (oldCo[2] + 2) % 3;
        co[2] = (oldCo[6] + 1) % 3;
        co[6] = (oldCo[5] + 2) % 3;
        co[5] = (oldCo[1] + 1) % 3;

        // Edge cycle (2 6 10 5)
        ep[2]  = oldEp[6];
        ep[6]  = oldEp[10];
        ep[10] = oldEp[5];
        ep[5]  = oldEp[2];

        // Edge orientation unchanged
        eo[2]  = oldEo[6];
        eo[6]  = oldEo[10];
        eo[10] = oldEo[5];
        eo[5]  = oldEo[2];
    }


    public void moveB() {
        int[] oldCp = cp.clone();
        int[] oldCo = co.clone();
        int[] oldEp = ep.clone();
        int[] oldEo = eo.clone();

        // Corner cycle (3 2 6 7)
        cp[3] = oldCp[2];
        cp[2] = oldCp[6];
        cp[6] = oldCp[7];
        cp[7] = oldCp[3];

        // Corner orientation deltas (+1, +2, +1, +2)
        co[3] = (oldCo[2] + 1) % 3;
        co[2] = (oldCo[6] + 2) % 3;
        co[6] = (oldCo[7] + 1) % 3;
        co[7] = (oldCo[3] + 2) % 3;

        // Edge cycle (3 6 11 7)
        ep[3]  = oldEp[6];
        ep[6]  = oldEp[11];
        ep[11] = oldEp[7];
        ep[7]  = oldEp[3];

        // Edge flip (eo ^= 1)
        eo[3]  = oldEo[6] ^ 1;
        eo[6]  = oldEo[11] ^ 1;
        eo[11] = oldEo[7] ^ 1;
        eo[7]  = oldEo[3] ^ 1;
    }


    public void moveU2() {
        moveU();
        moveU();
    }

    public void moveUp() {
        moveU();
        moveU();
        moveU();
    }

    public void moveR2() {
        moveR();
        moveR();
    }

    public void moveRp() { // R'
        moveR();
        moveR();
        moveR();
    }

    public void moveF2() {
        moveF();
        moveF();
    }

    public void moveFp() { // F'
        moveF();
        moveF();
        moveF();
    }

    public void moveD2() {
        moveD();
        moveD();
    }

    public void moveDp() { // D'
        moveD();
        moveD();
        moveD();
    }


    public void moveL2() {
        moveL();
        moveL();
    }

    public void moveLp() { // L'
        moveL();
        moveL();
        moveL();
    }


    public void moveB2() {
        moveB();
        moveB();
    }

    public void moveBp() { // B'
        moveB();
        moveB();
        moveB();
    }



    public void applyMove(String m) {

        switch(m) {
            case "U":  moveU();  break;
            case "U2": moveU2(); break;
            case "U'": moveUp(); break;

            case "R":  moveR();  break;
            case "R2": moveR2(); break;
            case "R'": moveRp(); break;

            case "F":  moveF();  break;
            case "F2": moveF2(); break;
            case "F'": moveFp(); break;

            case "D":  moveD();  break;
            case "D2": moveD2(); break;
            case "D'": moveDp(); break;

            case "L":  moveL();  break;
            case "L2": moveL2(); break;
            case "L'": moveLp(); break;

            case "B":  moveB();  break;
            case "B2": moveB2(); break;
            case "B'": moveBp(); break;

            default:
                throw new IllegalArgumentException("Invalid move: " + m);
        }
    }


//-----------------------------------------------coordinate 

    public int getEO() {
        int idx = 0;
        for (int i = 0; i < 11; i++) {
            idx |= (eo[i] << i);
        }
        return idx;  // 0~2047
    }


    public int getCO() {
        int idx = 0;
        for (int i = 0; i < 7; i++) {
            idx = idx * 3 + co[i];
        }
        return idx;  // 0 ~ 2186
    }


    // 0..494 : combination index for middle-layer edges (4,5,6,7)
    public int getUDSlice() {
        int idx = 0;
        int r = 4;

        for (int i = 0; i < 12; i++) {
            if (ep[i] >= 4 && ep[i] <= 7) {
                r--;
            } else {
                if (r > 0) {
                    idx += C(11 - i, r - 1);
                }
            }
            if (r == 0) break;
        }
        return idx;
    }







    // set ep[] so that the 4 middle-layer edges (4,5,6,7) are placed
    // according to the given combination index (0..494)
    public void setUDSlice(int idx) {
        // start from identity permutation
        for (int i = 0; i < 12; i++) {
            ep[i] = i;
        }

        boolean[] isSlicePos = new boolean[12];
        int r = 4;

        for (int i = 0; i < 12; i++) {
            if (r == 0) break;

            int c = C(11 - i, r - 1);
            if (idx >= c) {
                idx -= c;
                // this position is not a slice edge
                isSlicePos[i] = false;
            } else {
                // this position is a slice edge
                isSlicePos[i] = true;
                r--;
            }
        }

        int sliceEdge = 4; // FR, FL, BL, BR in your EDGE_COLORS
        for (int i = 0; i < 12; i++) {
            if (isSlicePos[i]) {
                ep[i] = sliceEdge++;
            }
        }
    }

  







        // index(0..2047)로 eo[] 세팅
    public void setEOFromIndex(int idx) {
            int sum = 0;
            for (int i = 0; i < 11; i++) {
                eo[i] = idx & 1;   // 하위 비트
                idx >>= 1;
                sum ^= eo[i];
            }
            // 마지막 edge는 parity로 결정 (전체 xor = 0)
            eo[11] = sum;
        }

    // index(0..2186)로 co[] 세팅
    public void setCOFromIndex(int idx) {
        int sum = 0;
        for (int i = 6; i >= 0; i--) {
            co[i] = idx % 3;
            idx /= 3;
            sum += co[i];
        }
        co[7] = (3 - (sum % 3)) % 3;  // 전체 합 ≡ 0 (mod 3)
    }


    // nCr helper
    private static int C(int n, int r) {
        if (r < 0 || r > n) return 0;
        if (r == 0 || r == n) return 1;
        int res = 1;
        for (int i = 1; i <= r; i++) {
            res = res * (n - r + i) / i;
        }
        return res;
    }




   //PHASE 2 COORDINATE


private static final int[] FACT = {
    1, 1, 2, 6, 24, 120, 720, 5040, 40320
};

// permutation → index (0..n!-1)
private static int permToIndex(int[] p, int n) {
    int idx = 0;
    for (int i = 0; i < n; i++) {
        int smaller = 0;
        for (int j = i + 1; j < n; j++) {
            if (p[j] < p[i]) smaller++;
        }
        idx += smaller * FACT[n - 1 - i];
    }
    return idx;
}

// index → permutation
    private static void indexToPerm(int idx, int n, int[] out) {
        boolean[] used = new boolean[n];
        for (int i = 0; i < n; i++) {
            int f = FACT[n - 1 - i];
            int d = idx / f;
            idx %= f;

            int k = 0;
            for (int v = 0; v < n; v++) {
                if (!used[v]) {
                    if (k == d) {
                        out[i] = v;
                        used[v] = true;
                        break;
                    }
                    k++;
                }
            }
        }
    }

                // 0..40319
    public int getCPIndex() {
        return permToIndex(cp, 8);
    }

    public void setCPFromIndex(int idx) {
        int[] p = new int[8];
        indexToPerm(idx, 8, p);
        for (int i = 0; i < 8; i++) {
            cp[i] = p[i];
        }
    }

    // U/D edge positions & corresponding edge IDs
    private static final int[] UDE_POS   = {0, 1, 2, 3, 8, 9, 10, 11};
    private static final int[] UDE_EDGES = {0, 1, 2, 3, 8, 9, 10, 11};

    private static int udEdgeIdToIdx(int edgeId) {
        for (int i = 0; i < 8; i++) {
            if (UDE_EDGES[i] == edgeId) return i;
        }
        return -1; // Not reachable in G1
    }

    // return value: 0 .. 8! - 1
    public int getUDEdgeIndex() {
        int[] perm = new int[8];
        for (int i = 0; i < 8; i++) {
            int eid = ep[UDE_POS[i]];
            perm[i] = udEdgeIdToIdx(eid);  // compress to 0..7
        }
        return permToIndex(perm, 8);
    }

    public void setUDEdgeFromIndex(int idx) {
        int[] perm = new int[8];
        indexToPerm(idx, 8, perm);
        for (int i = 0; i < 8; i++) {
            ep[UDE_POS[i]] = UDE_EDGES[perm[i]];
        }
    }


   
// 4! = 24 → 0..23
    public int getSlicePermIndex() {
        int[] perm = new int[4];
        for (int i = 0; i < 4; i++) {
            perm[i] = ep[4 + i] - 4;   // edgeID 4..7 → 0..3
        }
        return permToIndex(perm, 4);
    }

    public void setSlicePermFromIndex(int idx) {
        int[] perm = new int[4];
        indexToPerm(idx, 4, perm);
        for (int i = 0; i < 4; i++) {
            ep[4 + i] = 4 + perm[i];
        }
    }




}
