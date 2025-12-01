package rubikscube;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.util.*;

public class Solver {

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("File names are not specified");
            System.out.println("usage: java " +
                    MethodHandles.lookup().lookupClass().getName() +
                    " input_file output_file");
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];

        try {

            // ======================================================
            // 1) RubiksCube 
            // ======================================================
            RubiksCube rc = new RubiksCube(inputFile);

            // CubieCube 변환
            CubieCube cc = rc.toCubie();

            // ======================================================
            // 2) Phase 1 
            // ======================================================
            Phase1Solver p1 = new Phase1Solver();
            String s1 = p1.solve(cc);
            List<String> L1 = p1.getMoveList();

            // Phase1 결과 cc에 적용
            for (String m : L1) {
                cc.applyMove(m);
            }

            // ======================================================
            // 3) Phase 2 
            // ======================================================
            Phase2Solver p2 = new Phase2Solver();
            String s2 = p2.solve(cc);
            List<String> L2 = p2.getMoveList();

            // ======================================================
            // 4) 2phase together
            // ======================================================
            List<String> full = new ArrayList<>();
            full.addAll(L1);
            full.addAll(L2);

            // ======================================================
            // 5) primitive movement(U,R,F,D,L,B only) 
            // ======================================================
            List<String> norm = normalize(full);

            StringBuilder sb = new StringBuilder();
            for (String m : norm) sb.append(m);

            // ======================================================
            // 6) output 
            // ======================================================
            try (PrintWriter pw = new PrintWriter(new FileWriter(outputFile))) {
                pw.println(sb.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ======================================================
    // movement normalizer: U2 → UU, R' → RRR, F2 → FF, ...
    // ======================================================
    private static List<String> normalize(List<String> moves) {
        List<String> res = new ArrayList<>();

        for (String m : moves) {
            switch (m) {
                case "U": res.add("U"); break;
                case "U2": res.add("U"); res.add("U"); break;
                case "U'": res.add("U"); res.add("U"); res.add("U"); break;

                case "R": res.add("R"); break;
                case "R2": res.add("R"); res.add("R"); break;
                case "R'": res.add("R"); res.add("R"); res.add("R"); break;

                case "F": res.add("F"); break;
                case "F2": res.add("F"); res.add("F"); break;
                case "F'": res.add("F"); res.add("F"); res.add("F"); break;

                case "D": res.add("D"); break;
                case "D2": res.add("D"); res.add("D"); break;
                case "D'": res.add("D"); res.add("D"); res.add("D"); break;

                case "L": res.add("L"); break;
                case "L2": res.add("L"); res.add("L"); break;
                case "L'": res.add("L"); res.add("L"); res.add("L"); break;

                case "B": res.add("B"); break;
                case "B2": res.add("B"); res.add("B"); break;
                case "B'": res.add("B"); res.add("B"); res.add("B"); break;

                default:
                    throw new IllegalStateException("Unknown move: " + m);
            }
        }

        return res;
    }
}
