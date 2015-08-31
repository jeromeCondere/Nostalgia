package utils.box;

import agents.NosAgent;

public class NeuralMailbox extends Mailbox {

	public NeuralMailbox(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	protected  void addOutbox(String OutboxName)
	{
		NeuralOutbox outbox=new NeuralOutbox(OutboxName, this.mailboxName);
		  outboxes.add(outbox);
	}
	protected  void addInbox(String InboxName)
	{
		
		NeuralInbox inbox=new NeuralInbox(InboxName, this.mailboxName);
	  	  inboxes.add(inbox);
	}
/*	protected  void addInbox(String InboxName,float weight)
	{
		NeuralInbox inbox=new NeuralInbox(InboxName, this.mailboxName, weight);
	  	  inboxes.add(inbox);
	}
	*/
	public void setInboxWeight(Inbox inbox,float weight)
	{
		int index=this.inboxes.indexOf(inbox);
		if(index!=-1)
		{
			NeuralInbox neural_inbox=(NeuralInbox) this.inboxes.get(index);
			neural_inbox.setWeight(weight);
		}
	}
	
	public void addOutputConnection(String OutBoxName,String InboxName,NosAgent agent)//connect the outbox to an inbox
	{
		//we must specify mailbox because two inboxes can have the same name but different mailboxes
		if(agent==null)
			return;
		
	   Outbox outbox=new Outbox(OutBoxName,this.mailboxName);
	   if(!outboxes.contains(outbox))
	   {
		   this.addOutbox(OutBoxName);
	   }
	     int index=  outboxes.indexOf(outbox);
	     Inbox inbox=new Inbox(InboxName,agent.getMailBoxName());
	     inbox.setOwnerName(agent.getAgentName());//we specify the owner of the inbox
		 outboxes.get(index).addInbox(inbox);
		 //outbox>(inbox,mailbox)
	}

	public void addInputConnection(String InboxName,String OutboxName,NosAgent agent)//connect the inbox to an outbox
	{
		
		if(agent==null)
			return;
		Inbox inbox=new Inbox(InboxName,this.mailboxName);
		if(!inboxes.contains(inbox))
			this.addInbox(InboxName);
		
		int index=inboxes.indexOf(inbox);
		Outbox outbox=new Outbox(OutboxName,agent.getMailBoxName());
		outbox.setOwnerName(agent.getAgentName());
		inboxes.get(index).addOutbox(outbox);
		//inbox<(outbox,mailbox)
	}

}
