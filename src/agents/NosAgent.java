package agents;

import java.util.ArrayList;

import utils.SubBox;

import jade.core.Agent;

public abstract class NosAgent extends Agent {
	protected ArrayList<SubBox> Inboxes=new ArrayList<SubBox>();
	protected ArrayList<SubBox> Outboxes=new ArrayList<SubBox>();
	//two outboxes can't have the same name
	//two inboxes  can't have the same name
	public void broadCast()
	{
		
	}
	public void addReceiver(String user,String Outbox)
	{
		
		//add agents that can receive our message and the box in which the message will be sent
		if(user==null||Outbox==null)
			return;
		for(int i=0;i<Outboxes.size();i++)
		{
			if(Outboxes.get(i).getName().equals(Outbox))//we found outbox in outboxes
			  {	
				Outboxes.get(i).addOwner(user);
				return;
			  }		
		}
		//we create the Outbox
		Outboxes.add(new SubBox(Outbox,user));
	}
	public void addSender(String user,String Inbox)
	{
		//add agents that we can send our message and the box in which the message will be received
		if(user==null||Inbox==null)
			return;
		for(int i=0;i<Inboxes.size();i++)
		{
			if(Inboxes.get(i).getName().equals(Inbox))//we found inbox in inboxes
			  {	
				Inboxes.get(i).addOwner(user);
				return;
			  }		
		}
		//we create the Inbox
		Inboxes.add(new SubBox(Inbox,user));
	}
    
}
