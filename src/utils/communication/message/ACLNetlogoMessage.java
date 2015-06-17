package utils.communication.message;

import java.util.ArrayList;

import utils.box.Inbox;
import utils.box.Outbox;

import jade.lang.acl.ACLMessage;

public class ACLNetlogoMessage extends ACLMessage {
	protected ArrayList<Inbox> inboxes=new ArrayList<Inbox>();
	protected Outbox outbox;
	//1 outbox several inboxes
	public ACLNetlogoMessage()
	{
		super();
	}
	public void setInboxes(ArrayList<Inbox>Inboxes)
	{
		this.inboxes=Inboxes;
	}
	public void setOutbox(Outbox outbox)
	{
		this.outbox=outbox;
	}
	public Outbox getOutbox()
	{
		return this.outbox;
	}
	public ArrayList<Inbox> getInboxes()
	{
		return this.inboxes;
	}
	
	

}
