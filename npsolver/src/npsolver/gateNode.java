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
		weights = new double[width*2];
		if(init)
		{
			for(int i=0;i<width;i+=2)
			{
				if(bits[i/2]==net.getBitsBit(indexList[i/2]))
				{
					weights[i] = net.getBitWeight(indexList[i/2])[0];
					weights[i+1] = net.getBitWeight(indexList[i/2])[1];
				}else
				{
					weights[i] = net.getBitWeight(indexList[i/2])[1];
					weights[i+1] = net.getBitWeight(indexList[i/2])[0];
				}

			}
		}
		for(int i=0;i<width;i+=2)
		{
			for(int j=0;j<possibleStates.length;j++)
			{
				if(possibleStates[j][i/2]==bits[i/2])
				{
					weights[i] = weights[i] + net.getBitWeight(indexList[i/2])[0];
					weights[i+1] = weights[i+1] + net.getBitWeight(indexList[i/2])[1];
				}else
				{
					weights[i] = weights[i] + net.getBitWeight(indexList[i/2])[1];
					weights[i+1] = weights[i+1] + net.getBitWeight(indexList[i/2])[0];
				}
			}
		}
	}

	@Override
	void propagate() {
		// TODO Auto-generated method stub
		boolean[] oldBits = bits;
		double force = 0;
		double maxForce = 0;
		int maxIndex = 0;
		for(int i=0;i<possibleStates.length;i++)
		{
			force = 0;
			for(int j=0;j<width;j++)
			{
				if(possibleStates[i][j] == bits[j])
				{
					force += weights[2*j] - weights[2*j + 1];
				}else
				{
					force += weights[2*j + 1] - weights[2*j];
				}
			}
			if(force>maxForce)
			{
				maxIndex = i;
			}
		}
		bits = possibleStates[maxIndex];
		if(bits!=oldBits)
		{
			weight(false);
			net.changeNextBits(indexList);
		}
	}

}
