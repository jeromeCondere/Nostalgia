package utils.box;

import java.util.ArrayList;

import agents.NosAgent;


public class Mailbox extends Box{

protected ArrayList<Inbox>inboxes=new ArrayList<Inbox>();
protected ArrayList<Outbox>outboxes=new ArrayList<Outbox>();

	
	
protected Mailbox(String name, String mailboxName) {
		super(name, mailboxName);
	}
public Mailbox(String name)
{
	super(name,name);//mailboxName=name
}
protected  void addOutbox(String OutboxName)
{
  Outbox outbox=new Outbox(OutboxName, this.mailboxName);
	  outboxes.add(outbox);
}
protected  void addInbox(String InboxName)
{
	
  Inbox inbox=new Inbox(InboxName, this.mailboxName);
  	  inboxes.add(inbox);
}
public void addOutputConnection(String OutBoxName,String InboxName,String mailbox)//connect the outbox to an inbox
{
	//we must specify maibox because two inboxes can have the same name but different mailboxes
	if(mailbox==null)
		return;
	
   Outbox outbox=new Outbox(OutBoxName,this.mailboxName);
   if(!outboxes.contains(outbox))
   {
	   this.addOutbox(OutBoxName);
   }
      int index=  outboxes.indexOf(outbox);
	 outboxes.get(index).addInbox(new Inbox(InboxName,mailbox));
	 //outbox>(inbox,mailbox)
}
public void addOutputConnection(String OutBoxName,String InboxName,NosAgent agent)//connect the outbox to an inbox
{
	//we must specify maibox because two inboxes can have the same name but different mailboxes
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

public void addInputConnection(String InboxName,String OutboxName,String mailbox)//connect the inbox to an outbox
{
	
	if(mailbox==null)
		return;
	Inbox inbox=new Inbox(InboxName,this.mailboxName);
	if(!inboxes.contains(inbox))
		this.addInbox(InboxName);
	
	int index=inboxes.indexOf(inbox);
	inboxes.get(index).addOutbox(new Outbox(OutboxName,mailbox));
	//inbox<(outbox,mailbox)
}
public ArrayList<Inbox> getInboxes()
{
	return this.inboxes;
}
public ArrayList<Outbox> getOutboxes()
{
	return this.outboxes;
}

}
