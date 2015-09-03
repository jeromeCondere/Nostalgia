package agents.boxAgents.Neural;


import utils.box.Mailbox;
import utils.stimuli.StimuliPool;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
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
		Mailbox mailbox_=new Mailbox(mailboxname);
		mailbox_.setOwnerName(ownername);
		this.setMailbox(mailbox_);
	}
	protected void processMessageFromOwner(ACLMessage message)
	{
		
	}
	protected void processMessageFromOutside(ACLMessage message)
	{
		
	}
	
	protected boolean isAllPortsfilled()
	{
		
		
		
		return false;
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
}
