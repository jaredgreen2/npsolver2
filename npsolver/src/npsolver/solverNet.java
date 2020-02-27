package npsolver;

import java.util.ArrayList;

public class solverNet {
	ArrayList<bitNode> bits;
	ArrayList<gateNode> gates;
	int mostChanges = 0;
	int[] nextBits;
	int[] nextGates;
	int gateLines;
	public ProblemFormat solve(ProblemFormat puzzle)
	{
		puzzle.tryHeuristics(false);
		boolean started = false;
		double N = puzzle.nmax();
		double Ndelta = N/2;
		boolean consistent;
		boolean stable;
		boolean lastWasConsistent = false;
		ProblemFormat lastSolution = puzzle;
		do
		{
			if(!started)
			{
				puzzle.toNet(this);
				nextGates = new int[gates.size()];
				for(int i=0;i<gates.size();i++)
				{
					nextGates[i] = 1;
				}
			}
			puzzle.constrain(this,N);
			do
			{
				consistent = true;
				stable = true;
				nextBits = new int[bits.size()];
				for(int i=0;i<bits.size();i++)
				{
					nextBits[i] = -1;
				}
				for(int i=0;nextGates[i]!= -1;i++)
				{
					gateNode gate = gates.get(nextGates[i]);
					double[] oldWeights = gate.weights;
					gate.propagate();
					if(gate.weights!=oldWeights)
					{
						stable = false;
					}
					for(int j=0;j<gate.width;j++)
					{
						if(gate.bits[j]!=bits.get(gate.indexList[j]))
						{
							consistent = false;
						}
					}
				}
				nextGates = new int[gates.size()];
				for(int i=0;i<gates.size();i++)
				{
					nextGates[i] = -1;
				}
				for(int i=0;nextBits[i]!= -1;i++)
				{
					bitNode bit1 = bits.get(nextBits[i]);
					double[] oldWeights = bit1.weights;
					bit1.propagate();
					if(bit1.weights!=oldWeights)
					{
						stable = false;
					}
					if(bit1.numChanges>mostChanges)
					{
						mostChanges = bit1.numChanges;
					}
				}
			}while(!consistent&& !stable &&(mostChanges<gateLines*2 + 1));
			if(!consistent)
			{
				//if the puzzle has a number to be minimized, nQuantum returns the least amount by which problemformat.number() can change
				if(Ndelta<puzzle.nQuantum())
				{
					System.out.print("no solution");
					return lastSolution;
				}else
				{
					N+=Ndelta;
					Ndelta = Ndelta/2;
					if(lastWasConsistent)
					{
						N -= Ndelta;
					}
					lastWasConsistent = false;
				}
			}else
			{
				lastSolution = puzzle.toSolution(this);
				if(Ndelta<puzzle.nQuantum())
				{
					return lastSolution;
				}else
				{
					N = Math.min(N-Ndelta, Math.floor(puzzle.number(this)/puzzle.nQuantum())*puzzle.nQuantum());
				}
				lastWasConsistent = true;
			}
		}while(true);
	}

	void addGate(gateNode gate,boolean complex)
	{
		if(!complex)   //this block is implemented by complexGate.insert
		{
			int priorSize = bits.size();
			for(int i=0;i<gate.width;i++)
			{
				if(gate.indexList[i]<0)
				{
					gate.indexList[i] = priorSize - gate.indexList[i] - 1;   //this prevents links to existing bitnodes from being shifted. due to this, unused bits are added last
				}
			}
		}
		gates.add(gate);
		gateLines+=gate.width;
	}

	//should this function be here? this class has no other setters because it is tightly integrated with ProblemFormat, meaning bits is public
	public void addBit(bitNode bit)
	{
		bits.add(bit);
	}
	public boolean getGateBit(int i, int j) {
		// TODO Auto-generated method stub
		gateNode gate = gates.get(i);
		return gate.bits[j];
	}

	public double[] getGateWeight(int i, int j,boolean equal) {
		// TODO Auto-generated method stub
		gateNode gate = gates.get(i);
		if(equal)
		{
			return gate.weights[2*j];
		}
		return gate.weights[2*j + 1];
	}

	public boolean getBitsBit(int i) {
		// TODO Auto-generated method stub
		bitNode bit = bits.get(i);
		return bit.bit;
	}

	public double[] getBitWeight(int i) {
		// TODO Auto-generated method stub
		bitNode bit = bits.get(i);
		return bit.weights;
	}

	public void changeNextGates(int[] indexList) {
		// TODO Auto-generated method stub
		for(int i=0;i<indexList.length;i+=2)
		{
			for(int j=0;j<gates.size();j++)
			{
				if(nextGates[j]==indexList[i])
				{
					j = gates.size();//escape this loop
				}
				if(nextGates[j]==-1)
				{
					nextGates[j] = indexList[i];
				}
			}
		}
	}

	public void changeNextBits(int[] indexList) {
		// TODO Auto-generated method stub
		for(int i=0;i<indexList.length;i++)
		{
			for(int j=0;j<bits.size();j++)
			{
				if(nextBits[j]==indexList[i])
				{
					j = bits.size();//escape this loop
				}
				if(nextBits[j]==-1)
				{
					nextBits[j] = indexList[i];
				}
			}
		}
	}

}
