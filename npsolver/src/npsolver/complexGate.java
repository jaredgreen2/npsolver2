package npsolver;

import java.util.ArrayList;

public abstract class complexGate {
	ArrayList<gateNode> gates;
	ArrayList<bitNode> bits;
	abstract void add(solverNet net,int[] indexList);
	
	//all the add functions call this last
	void insert(solverNet net)
	{
		int gatesPriorSize = net.gates.size();
		gateNode gate;
		for(int i=0;i<gates.size();i++)
		{
			gate = gates.get(i);
			for(int j=0;j<gate.width;j++)
			{
				if(gate.indexList[j] < 0)
				{
					gate.indexList[j] = gatesPriorSize - gate.indexList[j] - 1;  //this prevents links to existing bitnodes from being shifted
				}
			}
			net.addGate(gate,true);
		}
		
	}
}
