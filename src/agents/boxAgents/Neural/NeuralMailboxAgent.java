package agents.boxAgents.Neural;


import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import utils.Netlogo.NetlogoJson;
import utils.box.Inbox;
import utils.box.Mailbox;
import utils.box.NeuralMailbox;
import utils.stimuli.StimuliPool;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import agents.Netlogo.NetlogoAgent;
import agents.boxAgents.MailBoxAgent;

public class NeuralMailboxAgent extends MailBoxAgent {
	protected String ownername=null;
	protected StimuliPool stimuliPool=null;
	public NeuralMailboxAgent(NetlogoAgent owner)
	{
		super(owner);
		ownername=owner.getAgentName();
		String mailboxname="neuralmailbox_"+ownername;
		Mailbox mailbox_=new NeuralMailbox(mailboxname);
		mailbox_.setOwnerName(ownername);
		this.mailbox=mailbox_;
		stimuliPool=new StimuliPool(mailbox.getInboxes());
	}
	protected void processMessageFromOwner(ACLMessage message)
	{
		if(message.getOntology().equals("inter-Neural-Communicate"))
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
	
	}
	
	private void fillStimuliPool(ArrayList<Inbox>inboxesNotFilled)
	{
		for(Inbox inbox : inboxesNotFilled)
		{
			MessageTemplate hasInbox=new MessageTemplate(new TemplateInboxExpression(inbox));
			MessageTemplate hasGoodOntology=MessageTemplate.MatchOntology("inter-Neural-Communicate");
			MessageTemplate template=MessageTemplate.and(hasInbox, hasGoodOntology);
			ACLMessage message=receive(template);
			if(message!=null)
			stimuliPool.setStimuliMessage(inbox, message);
		}
	}
	protected boolean isAllPortsfilled()
	{	
		return stimuliPool.isFilled();
	}
	protected void StimuliMerge()
	{
		/*
		 * merge all messages from stimuli pool and send 
		 */
		ArrayList<ACLMessage> messages=stimuliPool.getMessages();
	}
	protected void process()
	{
		AID owner_aid=new AID(ownername,AID.ISLOCALNAME);
		AID []aids={owner_aid};
		MessageTemplate isOwner=MessageTemplate.MatchReceiver(aids);
		ACLMessage messageFromOwner=receive(isOwner);
		if(messageFromOwner!=null)
		{
			processMessageFromOwner(messageFromOwner);
		}
		if(!isAllPortsfilled())	
		{
			/*
			 * if a ports is not filled we look for the messages  in 
			 * the message queue
			 * can fill the ports concerned
			 */
			ArrayList<Inbox>inboxesNotFilled=stimuliPool.getInboxesNotFilled();
			fillStimuliPool(inboxesNotFilled);
		}
		else
		{
			
			/*
			 * we merge the message of the stimili pool and we send it 
			 * to the owner
			 */
		}
		
	}
}
