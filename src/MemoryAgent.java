import java.util.ArrayList;

import jade.core.Agent;

/*
short term memory agent
*/
public abstract class MemoryAgent extends Agent {
public ArrayList<Integer> states;
/*states(0) contains the less recent state registered
 * states(memorySize -1) contains the most recent
 */
int memorySize;
public MemoryAgent(int bufferSize)
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

public MemoryAgent()
{
	this(3);
}



	
}
