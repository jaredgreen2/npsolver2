package npsolver;

public class xandNode extends gateNode{

	xandNode(solverNet neto, int[] indices) {
		super(neto, indices);
		// TODO Auto-generated constructor stub
		width = 4;
		boolean[][] states = {{false,false,false,false},{false,true,true,false},{true,false,true,false},{true,true,false,true}};
		possibleStates = states;
		super.postConstructer();
	}

}
