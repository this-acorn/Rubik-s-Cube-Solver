# Rubik's Cube Solver

A 3×3 Rubik's Cube solver written in Java, implementing **Kociemba's two-phase algorithm**. Given a scrambled cube, it finds a sequence of face turns that returns the cube to its solved state.

## Overview

The solver reads a scrambled cube from a text file (described as a flat sticker layout), converts it into an internal *cubie-level* model, and searches for a solution in two phases. Each phase uses **iterative-deepening A\* (IDA\*)** search guided by **pruning-table heuristics**, with move transitions and pruning distances precomputed up front for fast lookup.

The final output is a normalized sequence of basic face turns (`U R F D L B`).

## Algorithm

The solve is split into Kociemba's two phases, each operating on a reduced coordinate space:

**Phase 1** — reduce the cube to the `<U, D, R2, L2, F2, B2>` subgroup (G1) by fixing:
- Edge Orientation (EO) — 2,048 states
- Corner Orientation (CO) — 2,187 states
- UD-Slice position — 495 states

**Phase 2** — solve the cube within G1 using only moves that preserve the subgroup.

### Key data structures

This project was a study in using the right data structure for the job:

- **Move tables** (`int[move][coordinate]`) — precomputed transition tables so applying a move is a single array lookup instead of a full cube simulation.
- **Pruning tables** (`byte[coordinate]`) — pattern databases storing the minimum number of moves to solve each sub-coordinate. Built with a **breadth-first search (queue-based) flood fill** from the solved state.
- **Admissible heuristic** — the per-state lower bound is `max(EO, CO, Slice)` distances, which keeps IDA\* optimal while pruning the search tree aggressively.
- **Cubie model** (`CubieCube`) — encodes corner/edge permutation and orientation, decoupling the search logic from the raw sticker representation.

## Project structure

```
src/rubikscube/
├── Solver.java          # Entry point: read → toCubie → Phase1 → Phase2 → output
├── RubiksCube.java      # Sticker-level model + file parsing
├── CubieCube.java       # Cubie-level model (permutation + orientation, coordinates)
├── Phase1Solver.java    # IDA* search for Phase 1
├── Phase1Tables.java    # Phase 1 move-transition tables
├── Phase1Prune1D.java   # Phase 1 pruning (pattern) tables via BFS
├── Phase2Solver.java    # IDA* search for Phase 2
├── Phase2Tables.java    # Phase 2 move-transition tables
├── Phase2Prune1D.java   # Phase 2 pruning tables
└── IncorrectFormatException.java
testcases/               # 40 scrambles with expected solutions
```

## Input format

A 9-line text file describing the unfolded cube. The U (up) and D (down) faces are indented by 3 spaces; the middle band lists the **L, F, R, B** faces left to right:

```
   OOO
   OOO
   OOO
GGGWWWBBBYYY
GGGWWWBBBYYY
GGGWWWBBBYYY
   RRR
   RRR
   RRR
```

Each letter is a sticker color. The example above is the solved cube.

## Usage

Compile and run from the `src` directory:

```bash
javac rubikscube/*.java
java rubikscube.Solver <input_file> <output_file>
```

Example:

```bash
java rubikscube.Solver ../testcases/scramble01.txt ../testcases/sol01.txt
```

The output file contains the solution as a string of basic moves, e.g.:

```
URUUURRR
```

## Testing

The `testcases/` directory contains 40 scrambles (`scramble01.txt`–`scramble40.txt`) used to validate that each generated solution actually solves the corresponding cube.

---

*Originally developed as a project for SFU CMPT 225 (Data Structures & Algorithms).*
