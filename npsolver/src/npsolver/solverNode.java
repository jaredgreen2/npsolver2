package npsolver;

public abstract class solverNode {
	public double [] weights;
	abstract void weight(boolean init);
	abstract void propagate();
}
