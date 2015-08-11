package agents;

import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
/*
 * the purpose of this class is to
 * split message that are too heavy for transportation
 * 
 */
public abstract class SplitterAgent extends NosAgent
{
	public SplitterAgent()
	{
	  
	}
	public abstract ArrayList<ACLMessage> split(ACLMessage message);
}
