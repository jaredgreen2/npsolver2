package npsolver;

import java.util.ArrayList;

public class fullAdderGate extends complexGate {
	
	@Override
	void add(solverNet net, int[] indexArray) {
		// TODO Auto-generated method stub
		super.bits = new ArrayList<bitNode>();
		bits.add(new bitNode(false,net,2));
		bits.add(new bitNode(false,net,2));
		bits.add(new bitNode(false,net,1));
		bitNode bit1 = new bitNode(false,net,1);
		bits.add(bit1);
		gates = new ArrayList<gateNode>();
		int[] indices1 = {indexArray[1],indexArray[2],-1,-2};
		gates.add(new xandNode(net,indices1));
		int[] indices2 = {indexArray[3],-1,indexArray[4],-3};
		gates.add(new xandNode(net,indices2));
		int[] indices3 = {-2,-4,indexArray[5]};
		gates.add(new CNotNode(net,indices3));

		super.insert(net);
	}

}
