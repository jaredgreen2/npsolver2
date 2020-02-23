package npsolver;

public class gateNode extends solverNode{
	int width;
	boolean [] bits;
	boolean [][] possibleStates;
	solverNet net;
	int[] indexList;
	gateNode(solverNet neto,int[] indices)
	{
		net = neto;
		indexList = indices;
	}
	void postConstructer()
	{
		bits = new boolean[width];
		bitNode bit;
		for(int i=0;i<width;i++)
		{
			bit = net.bits.get(indexList[i]);
			bit.addGateIndex(net.gates.size(),i);
			bits[i] = net.getBitsBit(indexList[i]);
			
		}
		weight(true);
	}
	@Override
	void weight(boolean init) {
		// TODO Auto-generated method stub
		weights = new double[width];
		for(int i=0;i<width;i++)
		{
			weights[i] = net.getBitWeight(i);
		}
	}

	@Override
	void propagate() {
		// TODO Auto-generated method stub
		boolean[] oldBits = bits;
		double maxWeight = 0;
		double w;
		int maxIndex = 0;
		boolean constantConflict;
		for(int i=0;i<possibleStates.length;i++)
		{
			constantConflict = false;
			for(int j=0;j<width;j++)
			{
				if(net.bits.get(indexList[j]).constant&&(net.getBitsBit(indexList[j])!=possibleStates[i][j]))
				{
					constantConflict = true;
				}
			}
			if(!constantConflict)
			{
				w = 0;
				for(int j=0;j<width;j++)
				{
					if(net.getBitsBit(indexList[j])==possibleStates[i][j])
					{
						w+= weights[j];
					}else
					{
						w += 1-weights[j];
					}
				}
				if(w>maxWeight)
				{
					maxIndex = i;
				}
			}
		}
		bits = possibleStates[maxIndex];
		if(bits!=oldBits)
		{
			net.changeNextBits(indexList);
		}
	}
	
}
