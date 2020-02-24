package npsolver;

import java.util.ArrayList;

public class Sudoku extends ProblemFormat {

	int[][] cells;
	ArrayList<ArrayList<int[]>> heuristicStack;
	Sudoku(int size,int[] numberList)
	{
		super.size = size;
		cells = new int[(int) Math.pow(size,2)][(int) Math.pow(size,2)];
		for(int i=0;i<((int) Math.pow(size,4));i++)
		{
			cells[i/((int) Math.pow(size,2))][i%((int) Math.pow(size,2))] = numberList[i];
		}
	}
	
	@Override
	public void tryHeuristics(boolean bruteStep) {//for now only the obvious heuristics are implemented
		// TODO Auto-generated method stub
		boolean exhausted;
		if(bruteStep)
		{
			heuristicStack.add(new ArrayList<int[]>());
		}
		do
		{
			exhausted = true;
			for(int i=0;i<((int) Math.pow(size,2));i++)
			{
				for(int j=0;j<((int) Math.pow(size,2));j++)
				{
					int numPossible = 0;
					int firstNumber = 0;
					int lastNumber = 0;
					for(int k=1;k<=((int) Math.pow(size,2));k++)
					{
						if(possible(i,j,k))
						{
							numPossible++;
							if(numPossible==1)
							{
								firstNumber = k;
							}
							lastNumber = k;
						}
					}
					if(numPossible==1)
					{
						exhausted = false;
						cells[i][j] = firstNumber;
						if(bruteStep)
						{
							//add to list
							int[] coords = {i,j};
							heuristicStack.get(heuristicStack.size()-1).add(coords);
						}
					}
					if(numPossible==2)
					{
						//do all candidate elimination heuristics
						if(eliminateCandidates(i,j,firstNumber))
						{
							cells[i][j] = lastNumber;
							exhausted = false;
						}
						if(eliminateCandidates(i,j,lastNumber))
						{
							cells[i][j] = firstNumber;
							exhausted = false;
						}
					}
				}
			}
			for(int i=0;i<((int) Math.pow(size,2));i++)
			{
				
				for(int k=1;k<=((int) Math.pow(size,2));k++)
				{
					int numInRow = 0;
					int firstj = -1;
					for(int j=0;j<((int) Math.pow(size,2));i++)
					{
						if(cells[i][j]==0)
						{
							if(possible(i,j,k))
							{
								numInRow++;
								if(numInRow==1)
								{
									firstj = j;
								}
							}
						}
					}
					if(numInRow==1)
					{
						cells[i][firstj] = k;
						if(bruteStep)
						{
							//add to list
							int[] coords = {i,firstj};
							heuristicStack.get(heuristicStack.size()-1).add(coords);
						}
						exhausted = false;
					}
				}
			}
			for(int j=0;j<((int) Math.pow(size,2));j++)
			{
				
				for(int k=1;k<=((int) Math.pow(size,2));k++)
				{
					int numInColumn = 0;
					int firsti = -1;
					for(int i=0;i<((int) Math.pow(size,2));i++)
					{
						if(cells[i][j]==0)
						{
							if(possible(i,j,k))
							{
								numInColumn++;
								if(numInColumn==1)
								{
									firsti = i;
								}
							}
						}
					}
					if(numInColumn==1)
					{
						cells[firsti][j] = k;
						if(bruteStep)
						{
							//add to list
							int[] coords = {firsti,j};
							heuristicStack.get(heuristicStack.size()-1).add(coords);
						}
						exhausted = false;
					}
				}
			}
			for(int l=0;l<((int) Math.pow(size,2));l+=size)
			{
				for(int m=0;m<((int) Math.pow(size,2));m+=size)
				{
					for(int k=1;k<=((int) Math.pow(size,2));k++)
					{
						int numInSquareGroup = 0;
						int firsti = -1;
						int firstj = -1;
						for(int i=l;i<(l+size);i++)
						{
							for(int j=m;j<(m+size);j++)
							{
								if(cells[i][j]==0)
								{
									if(possible(i,j,k))
									{
										numInSquareGroup++;
										if(numInSquareGroup==1)
										{
											firsti = i;
											firstj = j;
										}
									}
								}
							}
						}
						if(numInSquareGroup==1)
						{
							cells[firsti][firstj] = k;
							if(bruteStep)
							{
								//add to list
								int[] coords = {firsti,firstj};
								heuristicStack.get(heuristicStack.size()-1).add(coords);
							}
							exhausted = false;
						}
					}
				}
			}
			//should include BUG,xy chains,SK loops, and forcing chains
			//other heuristics, such as riddle of sho only work in a small minority of cases, so they are not included here
		}while(!exhausted);
		
	}
	
	private boolean eliminateCandidates(int i, int j, int k) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public boolean normalSolve(boolean laterStep) { //this comes up with the same solution as the new method. can that be proven?
		// TODO Auto-generated method stub
		tryHeuristics(laterStep);
		//step brute force
		for(int i=0;i<((int) Math.pow(size,2));i++)
		{
			for(int j=0;j<((int) Math.pow(size,2));j++)
			{
				if(cells[i][j] == 0)
				{
					do
					{
						cells[i][j] = cells[i][j] +1;
						if(possible(i,j,cells[i][j]))
						{
							if(normalSolve(true))
							{
								return true;
							}else
							{
								//invalidate last tryHeuristics has already been done, so do nothing
							}
						}
						if(cells[i][j]>((int) Math.pow(size,2)))
						{
							//invalidate last tryHeuristics
							ArrayList<int[]> heuristicFrame = heuristicStack.get(heuristicStack.size()-1);
							for(int l=0;l<(heuristicFrame.size());l++)
							{
								int[] coords = heuristicFrame.get(l);
								cells[coords[0]][coords[1]] = 0;
							}
							heuristicStack.remove(heuristicStack.size()-1);
							cells[i][j] = 0;
							return false;
						}
					}while(true);
				}
			}
		}
		return true;
	}
	
	private boolean possible(int i, int j, int k) {
		// TODO Auto-generated method stub
		for(int l=0;l<((int) Math.pow(size,2));l++)
		{
			if(l!=i)
			{
				if(cells[l][j]==k)
				{
					return false;
				}
			}
		}
		for(int l=0;l<((int) Math.pow(size,2));l++)
		{
			if(l!=j)
			{
				if(cells[i][l]==k)
				{
					return false;
				}
			}
		}
		for(int l=(Math.floorDiv(i, size) * size);l<((Math.floorDiv(i, size)+1) * size);l++)
		{
			for(int m=(Math.floorDiv(j, size) * size);m<((Math.floorDiv(j, size)+1) * size);m++)
			{
				if(l!=i && m!=j)
				{
					if(cells[i][j]==k)
					{
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public void toNet(solverNet net) {
		// TODO Auto-generated method stub
		
		//bits for cell values
		for(int i=0;i<((int) Math.pow(size,2));i++)
		{
			for(int j=0;j<((int) Math.pow(size,2));j++)
			{
				for(int k=1;k<=((int) Math.pow(size,2));k++)
				{
					if(cells[i][j]==k)
					{
						net.addBit(new bitNode(true,net,(int) Math.pow(size,2) - 1 + 2*((int) Math.pow(size,2)) - 2*size + 1));
					}else if(cells[i][j]>0)
					{
						net.addBit(new bitNode(true,net,(int) Math.pow(size,2) - 1 + 2*((int) Math.pow(size,2)) - 2*size + 1));
					}else
					{
						net.addBit(new bitNode(false,net,(int) Math.pow(size,2) - 1 + 2*((int) Math.pow(size,2)) - 2*size + 1));
					}
				}
			}
		}
		//constant bits for rule 1, all cells must have a value: OR((c,1),...(c,(int) Math.pow(size,2)))
		for(int i=0;i<((int) Math.pow(size,4));i++)
		{
			net.addBit(new bitNode(true,net,1));
		}
		//variable bits for rule 1
		for(int i=0;i<((int) Math.pow(size,4)*((int) Math.pow(size,2) - 1));i++)
		{
			net.addBit(new bitNode(false,net,2));
		}
		//constant bit for rule 2, 3, and 4: precluding rows, columns, and sectors
		bitNode bit =  new bitNode(true,net,(int) Math.pow(size,6) *((int) Math.pow(size,2) - 1 + 2*((int) Math.pow(size,2)) - 2*size));
		bit.bit = false;
		net.addBit(bit);
		//gates for rule 1
		for(int i=0;i<((int) Math.pow(size,2));i++)
		{
			for(int j=0;j<((int) Math.pow(size,2));j++)
			{
				if(cells[i][j]==0)
				{
					orNode node = new orNode();
					int[] array1 = {i*((int) Math.pow(size,4))+j*(int) Math.pow(size,2),i*((int) Math.pow(size,2))+j*((int) Math.pow(size,2))+1,((int) Math.pow(size,6))+(int) Math.pow(size,4) +i*((int) Math.pow(size,2))*(((int) Math.pow(size,2))-1)+j*((int) Math.pow(size,2) - 1)};
					node.add(net, array1);
					for(int k=2;k<((int) Math.pow(size,2) - 1);k++)
					{
						orNode node2 = new orNode();
						int[] array2 = {i*((int) Math.pow(size,4))+j*(int) Math.pow(size,2) + k,((int) Math.pow(size,6))+(int) Math.pow(size,4)+i*((int) Math.pow(size,2))*(((int) Math.pow(size,2))-1)+j*((int) Math.pow(size,2) - 1) + k-1,((int) Math.pow(size,6))+(int) Math.pow(size,4)+i*((int) Math.pow(size,2))*(((int) Math.pow(size,2))-1)+j*((int) Math.pow(size,2) - 1) + k};
						node2.add(net, array2);
					}
				}
			}
		}
		//gates for rule 2
		int constantIndex = net.bits.size() - 1;
		for(int i=0;i<((int) Math.pow(size,2));i++)
		{
			for(int j=0;j<((int) Math.pow(size,2));j++)
			{
				for(int k=0;k<((int) Math.pow(size,2));k++)
				{
					for(int l=i+1;l<((int) Math.pow(size,2));l++)
					{
						int[] array = {(int) Math.pow(size,4)*i + j*(int) Math.pow(size,2) + k,(int) Math.pow(size,4)*l + j*(int) Math.pow(size,2) + k,-1,constantIndex};
						xandNode node = new xandNode(net,array);
						net.addGate(node, false);
						net.addBit(new bitNode(false,net,1));
					}
					for(int l=j+1;l<((int) Math.pow(size,2));l++)
					{
						int[] array = {(int) Math.pow(size,4)*i + j*(int) Math.pow(size,2) + k,(int) Math.pow(size,4)*i + l*(int) Math.pow(size,2) + k,-1,constantIndex};
						xandNode node = new xandNode(net,array);
						net.addGate(node, false);
						net.addBit(new bitNode(false,net,1));
					}
					for(int l=(Math.floorDiv(i, size)*size);l<(Math.floorDiv(i, size)*(size+1));l++)
					{
						for(int m=(Math.floorDiv(j, size)*size);m<(Math.floorDiv(j, size)*(size+1));m++)
						{
							if(l>i||m>j)
							{
								int[] array = {(int) Math.pow(size,4)*i + j*(int) Math.pow(size,2) + k,(int) Math.pow(size,4)*l + m*(int) Math.pow(size,2) + k,-1,constantIndex};
								xandNode node = new xandNode(net,array);
								net.addGate(node, false);
								net.addBit(new bitNode(false,net,1));
							}
						}
					}
				}
			}
		}
	}


	@Override
	public ProblemFormat toSolution(solverNet net) {
		// TODO Auto-generated method stub
		
		int index = 0;
		boolean v;
		int[] list = new int[(int) Math.pow(size,4)];
		for(int i=0;i<((int) Math.pow(size,4));i++)
		{
			v=false;
			for(int k=1;k<=((int) Math.pow(size,2));k++)
			{
				if(net.getBitsBit(index))
				{
					list[i] = k;
					v = true;
				}
			}
			if(!v)
			{
				list[i] = 0;
			}
		}
		return new Sudoku(size,list);
	}
	public String toString()
	{
		String str = new String();
		for(int i=0;i<((int) Math.pow(size,2));i++)
		{
			for(int j=0;j<((int) Math.pow(size,2));j++)
			{
				str = str.concat(Integer.toString(cells[i][j]) + " ");
			}
			str = str.concat("\n\n");
		}
		return str;
	}
	//as there is not a number to minimize, the following needs no core functionality
	@Override
	public void constrain(solverNet net, double n) {
		// TODO Auto-generated method stub
		return;     //do nothing
	}

	@Override
	public double nQuantum() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public double number(solverNet solverNet) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double nmax() {
		// TODO Auto-generated method stub
		return 0;
	}

}
