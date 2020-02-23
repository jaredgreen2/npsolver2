package npsolver;

import java.util.ArrayList;

public class cswapNode extends complexGate{

	@Override
	void add(solverNet net,int[] indexArray) {
		// TODO Auto-generated method stub
		super.bits = new ArrayList<bitNode>();
		bits.add(new bitNode(false,net,2));
		bits.add(new bitNode(false,net,3));
		bits.add(new bitNode(false,net,1));
		gates = new ArrayList<gateNode>();
		int[] indices1 = {indexArray[2],indexArray[3],-1};
		gates.add(new CNotNode(net,indices1));
		int[] indices2 = {indexArray[1],-1,-3,-2};
		gates.add(new xandNode(net,indices2));
		int[] indices3 = {indexArray[2],-2,indexArray[4]};
		gates.add(new CNotNode(net,indices3));
		int[] indices4 = {indexArray[3],-2,indexArray[5]};
		gates.add(new CNotNode(net,indices4));

		super.insert(net);
	}


	
}
