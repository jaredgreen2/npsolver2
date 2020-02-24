package npsolver;

import java.util.ArrayList;

public class tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<int[]> samples;//should be specified here
		int[] providedSample1 = {0, 8,  7,  0, 0, 0, 0, 2,  1,
5,  4,  0, 9,  0, 0, 8,  0, 0,
0, 0, 0, 0, 0, 3,  0, 0, 0,
0, 9,  0, 0, 1,  0, 0, 0, 0,
0, 0, 2,  0, 0, 8,  0, 6,  0,
1,  0, 0, 0, 0, 6,  0, 0, 0,
6,  0, 0, 0, 0, 0, 0, 4,  0,
3,  0, 0, 0, 0, 0, 0, 0, 0,
4,  0, 0, 0, 0, 0, 2,  8,  9};
		samples.add(providedSample1);
		int[] providedSample2 = {11, 0, 0, 0, 0, 9  0, 0, 3,  6,  0, 0, 4,  0, 15, 0,
0, 0, 0, 7,  0, 0, 13, 2,  0, 8,  0, 16, 0, 0, 0, 12,
0, 0, 13, 15, 0, 8,  3,  0, 0, 0, 0, 14, 7,  5,  6,  0,
0, 0, 0, 0, 6,  0, 0, 14, 0, 0, 7,  0, 1,  0, 0, 8,
0, 1,  0, 3,  5,  0, 0, 0, 0, 0, 9,  10, 0, 0, 0, 0,
6,  0, 4,  8,  0, 12, 0, 0, 0, 3,  0, 0, 0, 1,  14, 0,
0, 0, 0, 10, 0, 16, 0, 0, 11, 0, 0, 0, 8,  0, 0, 7,
0, 0, 16, 0, 0, 13, 9,  0, 0, 0, 0, 0, 2,  0, 4,  0,
3,  0, 1,  0, 0, 0, 4,  6,  0, 0, 0, 8,  0, 0, 0, 0,
0, 9,  0, 0, 0, 2,  0, 0, 0, 14, 3,  0, 0, 11, 0, 0,
0, 0, 0, 0, 15, 0, 12, 3,  0, 16, 2,  0, 10, 0, 0, 4,
14, 16, 0, 0, 0, 0, 0, 8,  7,  1,  0, 4,  5,  0, 0, 2,
4,  3,  0, 1,  8,  7,  0, 0, 0, 10, 0, 0, 0, 0, 0, 13,
0, 0, 6,  0, 0, 3,  2,  0, 0, 0, 14, 0, 12, 10, 11, 0,
0, 0, 10, 9,  16, 0, 0, 0, 0, 0, 1,  0, 0, 0, 7,  5,
16, 0, 14, 0, 0, 11, 10, 0, 0, 0, 0, 3,  0, 0, 0, 1};
samples.add(providedSample2);
		for(int i=0;i<32;i++)
		{
			//with "size" between 2 and 5 inclusive, or 4x4 < puzzle < 25x25 square, and some missing values, not enforcing rules
			int[] arr = samples.get(i);
			int size = (int) Math.pow(samples.get(i).length, 1.0/4.0);
			//actually, this should draw from a predetermined set of puzzles, each exemplifying a different test case
			//namely,(can be completely solved with heuristics), (resorts to brute force at least once in the old algorithm with a single solution),
			//(has multiple solutions), and (has no solutions), using apparent worst cases for each algorithm
			Sudoku sPuzzle = new Sudoku(size,arr);
			//output the puzzle as is
			System.out.print(sPuzzle.toString());
			//solve it with the old and new method, compare their runtimes
			//start a timer here
			long start = System.nanoTime();
			solverNet snet = new solverNet();
			Sudoku sPuzzleSolution = (Sudoku) snet.solve(sPuzzle);
			long time1 = System.nanoTime() - start;
			//stop the first timer here. record time, start a new timer
			start = System.nanoTime();
			sPuzzle.normalSolve(false);
			long time2 = System.nanoTime() - start;
			//stop second timer
			//output the solution and stats
			System.out.print(sPuzzleSolution.toString());
			System.out.println("time with new solver" + time1);
			System.out.println("time with old solver" + time2);
		}
		//pool the stats together in a scatter plot

	}

}
