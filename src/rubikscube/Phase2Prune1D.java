package rubikscube;

import java.util.Arrays;

public class Phase2Prune1D {

    // 8! = 40320
    public static final int N_CP   = 40320;
    public static final int N_UDE  = 40320;
    // 4! = 24
    public static final int N_SLICEP = 24;

    public static int[] CP_PRUNE   = new int[N_CP];
    public static int[] UDE_PRUNE  = new int[N_UDE];
    public static int[] SLICEP_PRUNE = new int[N_SLICEP];

    static {
        System.out.println("Building Phase 2 1D prune tables...");
        buildCPPrune();
        buildUDEPrune();
        buildSlicePPrune();
        System.out.println("Phase 2 1D prune tables ready.");
    }

    private static void buildCPPrune() {
        Arrays.fill(CP_PRUNE, -1);
        int[] queue = new int[N_CP];
        int head = 0, tail = 0;

        CP_PRUNE[0] = 0;      // solved CP index = 0
        queue[tail++] = 0;

        while (head < tail) {
            int idx = queue[head++];
            int d = CP_PRUNE[idx];

            for (int m = 0; m < Phase2Tables.N_MOVES; m++) {
                int nxt = Phase2Tables.CP_MOVE[m][idx];
                if (CP_PRUNE[nxt] == -1) {
                    CP_PRUNE[nxt] = d + 1;
                    queue[tail++] = nxt;
                }
            }
        }
    }

    private static void buildUDEPrune() {
        Arrays.fill(UDE_PRUNE, -1);
        int[] queue = new int[N_UDE];
        int head = 0, tail = 0;

        UDE_PRUNE[0] = 0;   // solved U/D edge permutation index = 0
        queue[tail++] = 0;

        while (head < tail) {
            int idx = queue[head++];
            int d = UDE_PRUNE[idx];

            for (int m = 0; m < Phase2Tables.N_MOVES; m++) {
                int nxt = Phase2Tables.UDE_MOVE[m][idx];
                if (UDE_PRUNE[nxt] == -1) {
                    UDE_PRUNE[nxt] = d + 1;
                    queue[tail++] = nxt;
                }
            }
        }
    }

    private static void buildSlicePPrune() {
        Arrays.fill(SLICEP_PRUNE, -1);
        int[] queue = new int[N_SLICEP];
        int head = 0, tail = 0;

        SLICEP_PRUNE[0] = 0;  // solved slice permutation index = 0
        queue[tail++] = 0;

        while (head < tail) {
            int idx = queue[head++];
            int d = SLICEP_PRUNE[idx];

            for (int m = 0; m < Phase2Tables.N_MOVES; m++) {
                int nxt = Phase2Tables.SLICEP_MOVE[m][idx];
                if (SLICEP_PRUNE[nxt] == -1) {
                    SLICEP_PRUNE[nxt] = d + 1;
                    queue[tail++] = nxt;
                }
            }
        }
    }

    // h2(a,b,c) = max of the three 1D distances
    public static int heuristic(int cpIdx, int udeIdx, int slicePIdx) {
        int h1 = CP_PRUNE[cpIdx];
        int h2 = UDE_PRUNE[udeIdx];
        int h3 = SLICEP_PRUNE[slicePIdx];
        int h = h1;
        if (h2 > h) h = h2;
        if (h3 > h) h = h3;
        return h;
    }
}
