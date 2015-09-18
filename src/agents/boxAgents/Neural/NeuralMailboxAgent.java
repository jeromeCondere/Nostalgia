package agents.boxAgents.Neural;


import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import utils.Netlogo.NetlogoJson;
import utils.Neural.NeuralJson;
import utils.box.Inbox;
import utils.box.Mailbox;
import utils.box.NeuralMailbox;
import utils.stimuli.StimuliPool;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import agents.NeuralAgent;
import agents.Netlogo.NetlogoAgent;
import agents.boxAgents.MailBoxAgent;
import agents.boxAgents.Netlogo.NetlogoMailBoxAgent;


public class NeuralMailboxAgent extends MailBoxAgent {
	protected String ownername=null;
	protected StimuliPool stimuliPool=null;
	protected int fps=120;
	public NeuralMailboxAgent(NeuralAgent owner)
	{
		super(owner);
		ownername=owner.getAgentName();
		String mailboxname="neuralmailbox_"+ownername;
		Mailbox mailbox_=new NeuralMailbox(mailboxname);
		mailbox_.setOwnerName(ownername);
		this.mailbox=mailbox_;
		stimuliPool=new StimuliPool(mailbox.getInboxes());
	}
	
	public int getFps() {
		return fps;
	}
	public void setFps(int fps) {
		this.fps = fps;
	}
	protected void setup()
	{
		
	    this.addBehaviour(new MainLoop(this,1000/fps));
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
	protected void StimuliMergeAndSend()
	{
		/*
		 * merge all messages from stimuli pool int one and send to the owner of the mailbox
		 */
		ACLMessage message=new ACLMessage(ACLMessage.INFORM);
		AID aid_receiver=new AID(this.ownername,AID.ISLOCALNAME);
		message.addReceiver(aid_receiver);
		String content= NeuralJson.setMessageInJson(mailbox.getInboxes(),stimuliPool,null);
		message.setOntology("inter-Neural-Communicate");
		message.setLanguage("json");
		message.setContent(content);
		
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
			StimuliMergeAndSend();
			//we clear the stimuli pool
			this.stimuliPool.clear();
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
			((NeuralMailboxAgent) myAgent).process();
		}
		
	}
	
}
