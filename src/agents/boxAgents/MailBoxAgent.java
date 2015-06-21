package agents.boxAgents;

import java.util.ArrayList;

import agents.NosAgent;

import utils.box.Mailbox;
import utils.box.SubBox;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class MailBoxAgent extends Agent {
	
	private Mailbox mailbox; 
	protected Agent owner;
 
 	public MailBoxAgent(Agent owner)
 	{
 		this.owner=owner;
 	}
	public MailBoxAgent()
	{
	}
	protected void setup()
	{
	}
	public void broadCast(ACLMessage message)
	{
		
	}
	public void addInputConnection(String InboxName,String OutboxName,NosAgent agent )
	{
		String mailboxname=agent.getMailBoxName();
		//System.out.println("input");
		getMailbox().addInputConnection(InboxName, OutboxName,mailboxname);
		
	}
	public void addOutputConnection(String OutBoxName,String InboxName,NosAgent agent)
	{
		//System.out.println("output");
		String mailboxname=agent.getMailBoxName();
		getMailbox().addOutputConnection(OutBoxName, InboxName, mailboxname);
		
	}
	public void addOutputConnection(String OutBoxName,String InboxName,String name)
	{
		//System.out.println("output");
		String mailboxname="mailbox_"+name;
		getMailbox().addOutputConnection(OutBoxName, InboxName, mailboxname);
	}
	public void addInputConnection(String InboxName,String OutboxName,String name)
	{
		System.out.println("input");
		String mailboxname="mailbox_"+name;		
		getMailbox().addInputConnection(InboxName, OutboxName,mailboxname);
	}
	public Mailbox getMailbox() {
		return mailbox;
	}
	public void setMailbox(Mailbox mailbox) {
		this.mailbox = mailbox;
	}

}
