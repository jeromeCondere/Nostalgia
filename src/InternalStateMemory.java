import java.util.ArrayList;


public class InternalStateMemory {

	int memorySize;
	public ArrayList<Integer> states;
	public InternalStateMemory(int bufferSize)
	{
		if(bufferSize<1)
		   {
			this.memorySize=3;//the agent can store only 3 states
		   }
		else
		{
			this.memorySize=bufferSize;
		}
		states=new ArrayList<Integer>();
		
	}
	public InternalStateMemory()
	{
		this(3);
	}
	/*
	public void changeMemorySize(int memorySize)
	{
		if(memorySize<1)
			return;
		this.memorySize=memorySize;
		this.states.
	}
	*/
	public void addState(int state)
	{
		if(states.size()<memorySize)
			{
			this.states.add(0,state);//the most recent state is at the begining
			}
		else //if(states.size()==memorySize)
		  {
			int lastIndex=this.states.size()-1;
			this.states.remove(lastIndex);
			this.states.add(state);
		  }	
	}
	public int pop()
	{
		int stateRes=states.get(0);
		states.remove(0);
		return stateRes;
	}
	public int peek()
	{
		return states.get(0);
	}
	public int get(int index)
	{
		return states.get(index);
	}

	
}
