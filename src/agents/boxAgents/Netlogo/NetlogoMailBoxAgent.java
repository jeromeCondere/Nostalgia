package agents.boxAgents.Netlogo;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import agents.NosAgent;
import agents.Netlogo.NetlogoAgent;
import agents.boxAgents.MailBoxAgent;

import model_runner.communicate.Communicate;
import utils.Netlogo.NetlogoJson;
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
		this.setMailbox(mailbox_);
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
		
		if(message.getOntology().equals("inter-Netlogo-Communicate"))
		{
		message.clearAllReceiver();
		message.setDefaultEnvelope();
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject=null;
		try {
			jsonObject = (JSONObject) jsonParser.parse(message.getContent());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Inbox>inboxes=NetlogoJson.getInboxesboxJson(jsonObject);
		if(inboxes==null)
			System.out.println("null inboxes");
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
		if(message.getOntology().equals("inter-Netlogo-Communicate"))//the message comes from a netlogo
	     {
	    	 
	    	 
	    	 ArrayList<Inbox> inboxes=this.getMailbox().getInboxes();
	    	 JSONParser jsonParser = new JSONParser();
	 		JSONObject jsonObject=null;
	    		 try {
	    				jsonObject = (JSONObject) jsonParser.parse(message.getContent());
	    			} catch (ParseException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
	    	 
	    	 ArrayList<Inbox> message_inboxes=NetlogoJson.getInboxesboxJson(jsonObject);
	    	 Outbox message_Outbox=NetlogoJson.getOutboxJson(jsonObject);
	    	 String originalData=null;
	    	 
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
