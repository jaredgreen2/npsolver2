package npsolver;

public abstract class ProblemFormat {
	int size;
	public abstract void toNet(solverNet solverNet);

	public abstract void constrain(solverNet net,double n);//empty if there is nothing to minimize

	public abstract ProblemFormat toSolution(solverNet solverNet);

	public abstract double nQuantum();//returns 1 if there is nothing to minimize

	public abstract double number(solverNet solverNet);

	public abstract double nmax();//returns 0 if there is nothing to minimize

	public abstract void tryHeuristics(boolean bruteStep); //here would go those heuristics that are known to advance the solution in all cases,
                                         // and which can be applied in less time than the new method would take to do the same thing
	
	public abstract boolean normalSolve(boolean laterStep);  //this would contain the previous algorithm for solving the problem
}
