package npsolver;

public class notNode extends gateNode{

	notNode(solverNet neto, int[] indices) {
		super(neto, indices);
		// TODO Auto-generated constructor stub
		width = 2;
		boolean[][] states = {{false,true},{true,false}};
		possibleStates = states;
		super.postConstructer();
	}

}
