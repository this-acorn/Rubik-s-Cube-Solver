package rubikscube;

import java.util.*;

public class Phase1Prune1D {

    public static final int N_EO = Phase1Tables.N_EO;      // 2048
    public static final int N_CO = Phase1Tables.N_CO;      // 2187
    public static final int N_SL = Phase1Tables.N_SLICE;   // 495

    // prune tables: 최소 move 수 저장
    public static byte[] pruneEO  = new byte[N_EO];
    public static byte[] pruneCO  = new byte[N_CO];
    public static byte[] pruneSL  = new byte[N_SL];

    static {
        System.out.println("Building 1D prune tables...");
        buildEO();  
        buildCO();
        buildSlice();
        System.out.println("1D Prune Tables Ready.");
    }

    // ---------------------------
    // EO Prune (2048 states)
    // ---------------------------
    private static void buildEO() {
        Arrays.fill(pruneEO, (byte)-1);
        Queue<Integer> q = new ArrayDeque<>();

        pruneEO[0] = 0;
        q.add(0);

        while (!q.isEmpty()) {
            int eo = q.poll();
            byte d = pruneEO[eo];

            for (int m = 0; m < Phase1Tables.N_MOVES; m++) {
                int next = Phase1Tables.EO_MOVE[m][eo];

                if (pruneEO[next] == -1) {
                    pruneEO[next] = (byte)(d + 1);
                    q.add(next);
                }
            }
        }
    }

    // ---------------------------
    // CO Prune (2187 states)
    // ---------------------------
    private static void buildCO() {
        Arrays.fill(pruneCO, (byte)-1);
        Queue<Integer> q = new ArrayDeque<>();

        pruneCO[0] = 0;
        q.add(0);

        while (!q.isEmpty()) {
            int co = q.poll();
            byte d = pruneCO[co];

            for (int m = 0; m < Phase1Tables.N_MOVES; m++) {
                int next = Phase1Tables.CO_MOVE[m][co];

                if (pruneCO[next] == -1) {
                    pruneCO[next] = (byte)(d + 1);
                    q.add(next);
                }
            }
        }
    }

    // ---------------------------
    // Slice Prune (495 states)
    // ---------------------------
    private static void buildSlice() {
        Arrays.fill(pruneSL, (byte)-1);
        Queue<Integer> q = new ArrayDeque<>();

        CubieCube solved = new CubieCube();
        int start = solved.getUDSlice();  // correct start index for solved state

        pruneSL[start] = 0;
        q.add(start);

        while (!q.isEmpty()) {
            int sl = q.poll();
            byte d = pruneSL[sl];

            for (int m = 0; m < Phase1Tables.N_MOVES; m++) {
                int next = Phase1Tables.SLICE_MOVE[m][sl];
                if (pruneSL[next] == -1) {
                    pruneSL[next] = (byte)(d + 1);
                    q.add(next);
                }
            }
        }
    }


    // heuristic = max of EO/CO/Slice
    public static int heuristic(int eo, int co, int sl) {
        return Math.max(pruneEO[eo],
               Math.max(pruneCO[co], pruneSL[sl]));
    }
}
