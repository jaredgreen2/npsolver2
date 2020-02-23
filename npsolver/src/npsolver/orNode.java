package npsolver;

import java.util.ArrayList;

public class orNode extends complexGate{

	orNode()
	{
		
	}
	@Override
	void add(solverNet net, int[] indexArray) {
		// TODO Auto-generated method stub
		super.bits = new ArrayList<bitNode>();
		bits.add(new bitNode(false, net,2));
		bits.add(new bitNode(false,net,2));
		bits.add(new bitNode(false,net,1));
		bitNode bit1 = new bitNode(true,net,2);
		bit1.bit = false;
		bits.add(bit1);
		gates = new ArrayList<gateNode>();
		int[] indices1 = {indexArray[1],indexArray[2],-1,-2};
		gates.add(new xandNode(net,indices1));
		int[] indices2 = {-1,-3,-4};
		gates.add(new CNotNode(net,indices2));
		int[] indices3 = {-2,-4,indexArray[3]};
		gates.add(new CNotNode(net,indices3));

		super.insert(net);
	}
	
}
