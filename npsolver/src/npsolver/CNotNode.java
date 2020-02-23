package npsolver;

public class CNotNode extends gateNode{

	CNotNode(solverNet neto, int[] indices) {
		super(neto, indices);
		// TODO Auto-generated constructor stub
		width = 3;
		boolean[][] states = {{false,false,false},{false,true,true},{true,false,true},{true,true,false}};
		possibleStates = states;
		super.postConstructer();
	}

}
