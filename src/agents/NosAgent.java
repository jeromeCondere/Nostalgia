package agents;

import java.util.ArrayList;

import utils.SubBox;

import jade.core.Agent;

public abstract class NosAgent extends Agent {
	protected ArrayList<SubBox> Inboxes=new ArrayList<SubBox>();
	protected ArrayList<SubBox> Outboxes=new ArrayList<SubBox>();
	protected NosAgent innerAgent;
	//two outboxes can't have the same name and the same user
	//two inboxes  can't have the same name and the same user
 	public void broadCast()
	{
		
	}
	public void addReceiver(String user,String Outbox)
	{
		
		//add agents that can receive our message and the box in which the message will be sent
		//this >message>(user,outbox)
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
		//add agents that can send us their message and the box in which the message will be received
		//(this,Inbox)<message<user
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
    
	public ArrayList<SubBox> getInboxes()
	{
		return Inboxes;
	}
	public ArrayList<SubBox> getOutBoxes()
	{
		return Outboxes;
	}
	protected void Copy(NosAgent nos)
	{
		this.Inboxes=nos.Inboxes;
		this.Outboxes=nos.Outboxes;
		this.innerAgent=nos.innerAgent;
	}
	
}
