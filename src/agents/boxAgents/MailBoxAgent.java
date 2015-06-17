package agents.boxAgents;

import java.util.ArrayList;

import agents.NosAgent;

import utils.box.Mailbox;
import utils.box.SubBox;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class MailBoxAgent extends Agent {
	
	private Mailbox mailbox; 
	protected NosAgent owner=null;//the agent owner of the MailBox
	protected String user=null;
	public MailBoxAgent(NosAgent owner)
	{
		this.owner=owner;
	}
	public MailBoxAgent(String owner)
	{
		this.user=owner;
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
		String mailboxname="mailbox_"+agent.getLocalName();
		System.out.println("input");
		getMailbox().addInputConnection(InboxName, OutboxName,mailboxname);
		
	}
	public void addOutputConnection(String OutBoxName,String InboxName,NosAgent agent)
	{
		System.out.println("output");
		String mailboxname="mailbox_"+agent.getLocalName();
		getMailbox().addOutputConnection(OutBoxName, InboxName, mailboxname);
		
	}
	public void addOutputConnection(String OutBoxName,String InboxName,String name)
	{
		System.out.println("output");
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
