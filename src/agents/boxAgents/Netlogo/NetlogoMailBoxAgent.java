package agents.boxAgents.Netlogo;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.Iterator;

import agents.NosAgent;
import agents.Netlogo.NetlogoAgent;
import agents.boxAgents.MailBoxAgent;

import model_runner.communicate.Communicate;
import utils.box.Inbox;
import utils.box.Mailbox;
import utils.box.Outbox;
import utils.box.SubBox;
import utils.communication.message.ACLNetlogoMessage;

public class NetlogoMailBoxAgent extends MailBoxAgent {
	protected String ownername=null;

	public NetlogoMailBoxAgent(NetlogoAgent owner) 
	{
		super(owner);
		ownername=owner.getAgentName();
		String mailboxname="mailbox_"+ownername;
		Mailbox mailbox_=new Mailbox(mailboxname);
		mailbox_.setOwnerName(ownername);
		this.setMailbox(new Mailbox(mailboxname));
	}
	protected void setup()
	{
		
		if(owner instanceof NetlogoAgent)
		{
			int fps=45;
			fps*=3;
		    this.addBehaviour(new MainLoop(this,1000/fps));
		
		}
		else
		{
			int fps=140;
			this.addBehaviour(new MainLoop(this,1000/fps));
		}
		
	}

	protected void process()
	{
		ACLMessage message =receive();
		if(message!=null)
		{  
		
			AID owner_aid=new AID(ownername,AID.ISLOCALNAME);
			   if(message.getSender().equals(owner_aid))//if the sender is the owner of the mailBox we follow
			   {
				processMessageFromOwner(message);
	           }
			   else //the message comes from outside
			   {  
				   processMessageFromOutside(message);
			   }
			
		}
	}
	protected void processMessageFromOwner(ACLMessage message)
	{
		
		if(message instanceof ACLNetlogoMessage)
		{
		message.clearAllReceiver();
		message.setDefaultEnvelope();
		ArrayList<Inbox>inboxes=((ACLNetlogoMessage)message).getInboxes();
		for(int i=0;i<inboxes.size();i++)
		{
			AID aid_receiver=new AID(inboxes.get(i).getMailboxName(),AID.ISLOCALNAME);
			message.addReceiver(aid_receiver);
		}
		message.setSender(this.getAID());
		send(message);
		}
	}
	protected void processMessageFromOutside(ACLMessage message)
	{
		if(message instanceof ACLNetlogoMessage)//the message comes from a netlogo
	     {
	    	 ACLNetlogoMessage netlogoMessage=(ACLNetlogoMessage) message;
	    	 
	    	 ArrayList<Inbox> inboxes=this.getMailbox().getInboxes();
	    	 ArrayList<Inbox> message_inboxes=netlogoMessage.getInboxes();
	    	 Outbox message_Outbox=netlogoMessage.getOutbox();
	    	 //we check if one of our inboxes is connected to the outbox of the message
	    	 for(int i=0;i<message_inboxes.size();i++)
	    	 {
	    		 Inbox inbox_i=message_inboxes.get(i);
	    		 int index=inboxes.indexOf(inbox_i);
	    		 if(index!=-1)//if the inbox of the message is in our mailbox
	    		 {
	    			ArrayList<Outbox> outboxes=inboxes.get(index).getOutBoxes();
	    			//we check if the outbox of the message is connected to the inbox
	    			if(outboxes.contains(message_Outbox))
	    			{
	    				message.clearAllReceiver();
	    				AID aid_receiver=new AID(this.ownername,AID.ISLOCALNAME);
	    				message.addReceiver(aid_receiver);
	    				message.setSender(this.getAID());
	    				send(message);
	    				return;
	    			}
	    		 }
	    	 }
	     }
	     else
	     {
	    	 message.clearAllReceiver();
	    	 message.addReceiver(owner.getAID());
	    	 // to do : add special condition to follow the message
	    	 send(message);
	     }
	}
	protected class MainLoop extends TickerBehaviour
	{

		public MainLoop(Agent a, long period) {
			super(a, period);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void onTick() {
			// TODO Auto-generated method stub
			((NetlogoMailBoxAgent) myAgent).process();
		}
		
	}

}
