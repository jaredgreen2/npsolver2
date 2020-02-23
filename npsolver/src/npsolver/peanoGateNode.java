package npsolver;

import java.util.Arrays;

public class peanoGateNode extends gateNode {

	peanoGateNode(solverNet neto, int[] indices, int  max) {
		super(neto, indices);
		// TODO Auto-generated constructor stub
		width = max;
		boolean[] stateGen = new boolean[max];
		boolean[][] states = new boolean[max][max];
		for(int i=0;i<max;i++)
		{
			Arrays.fill(stateGen, false);
			stateGen[i] = true;
			states[i] = stateGen;
		}
		possibleStates = states;
		super.postConstructer();
	}

}
