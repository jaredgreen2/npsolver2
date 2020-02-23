package npsolver;

import java.util.ArrayList;

public class tester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<int[]> samples;//should be specified here
		for(int i=0;i<32;i++)
		{
			//with "size" between 2 and 5 inclusive, or 4x4 < puzzle < 25x25 square, and some missing values, not enforcing rules
			int[] arr = samples.get(i);
			int size = (int) Math.pow(samples.get(i).length, 1/4);
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
