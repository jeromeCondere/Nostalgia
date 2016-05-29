package agents;

import java.util.ArrayList;

import agents.boxAgents.MailBoxAgent;

import utils.box.SubBox;
import utils.communication.message.ACLNosMessage;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public abstract class NosAgent extends Agent {
	protected MailBoxAgent mailboxAgent=null;
	//two outboxes can't have the same name and the same user
	//two inboxes  can't have the same name and the same user
	protected String name=null;
	
	public String getAgentName()
	{
		return this.name;
	}
	public void addInputConnection(String InboxName,String OutboxName,NosAgent agent )
	{
		mailboxAgent.addInputConnection(InboxName, OutboxName, agent);
	}
	public void addOutputConnection(String OutBoxName,String InboxName,NosAgent agent)
	{
		mailboxAgent.addOutputConnection(OutBoxName, InboxName, agent);
	}
	protected void Copy(NosAgent nos)
	{
		
		this.mailboxAgent=nos.mailboxAgent;
	}
	public void addOutputConnection(String OutBoxName,String InboxName,String name)
	{
		this.mailboxAgent.addOutputConnection(OutBoxName, InboxName, name);
	}
	public void addInputConnection(String InboxName,String OutboxName,String name)
	{
		
		this.mailboxAgent.addInputConnection(InboxName, OutboxName, name);
	}
	public String getMailBoxName()
	{
		if(this.mailboxAgent==null)
			return null;
		if(this.mailboxAgent.getMailbox()==null)
			return null;
		return this.mailboxAgent.getMailbox().getMailboxName();
	}

	
}
