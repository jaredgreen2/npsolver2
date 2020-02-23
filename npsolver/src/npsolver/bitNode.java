package npsolver;

public class bitNode extends solverNode{
	boolean bit;
	boolean constant;
	solverNet net;
	int[] indexList;//even entries point to gates, the odd entries following them point to lines from those gates
	int numChanges = 0;
	int numGates = 0;
	bitNode(boolean constanto, solverNet neto,int connectedGates)
	{
		constant = constanto;
		net = neto;
		indexList = new int[connectedGates*2];//the requirement to anticipate fanout should not be
		bit = true;
		weight(true);
	}
	@Override
	void weight(boolean init) {
		// TODO Auto-generated method stub
		double w;
		if(init)
		{
			if(constant)
			{
				w = 1;
			}else
			{
				w = 0;
			}
			double[] w2 = {w};
			weights = w2;
		}else
		{
			w=0;
			double totalWeight = 0;
			for(int i=0;i<indexList.length;i+=2)
			{
				if(net.getGateBit(i,i+1)==bit)
				{
					w += net.getGateWeight(i,i+1);
				}
				totalWeight += net.getGateWeight(i,i+1);
			}
			w = w/totalWeight;
			double[] w2 = {w};
			weights = w2;
		}
	}
	@Override
	void propagate() {
		// TODO Auto-generated method stub
		weight(false);
		if(!constant)
		{
			if(weights[0] < 0.5)
			{
				bit = !bit;
				numChanges++;
				double[] w2 = {1-weights[0]};
				weights = w2;
				net.changeNextGates(indexList);
			}
		}
		
	}
	public void addGateIndex(int i, int j) {
		// TODO Auto-generated method stub
		indexList[numGates*2] = i;
		indexList[1 + numGates*2] = j;
	}
}
