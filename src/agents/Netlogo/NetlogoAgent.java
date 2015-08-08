package agents.Netlogo;
import java.util.ArrayList;
import java.util.Iterator;

import agents.NosAgent;
import agents.boxAgents.Netlogo.NetlogoMailBoxAgent;

import utils.box.Inbox;
import utils.box.Outbox;
import utils.box.SubBox;
import utils.communication.message.ACLNetlogoMessage;

import model_runner.communicate.Communicate;
import model_runner.communicate.Netlogo.NetlogoCommunicate;
import model_runner.netlogo.NetlogoRunner;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class NetlogoAgent extends NosAgent
{
    protected  int fps=1;//default max frame rate
	protected NetlogoRunner net;
	public NetlogoAgent()
	{
		this.mailboxAgent=new NetlogoMailBoxAgent(this);
	}
	public NetlogoAgent(String path,int width,int heigth)
	{
		this();
		net=new NetlogoRunner(path,width,heigth);
	}
	
	public NetlogoAgent(NetlogoRunner runner)
	{
		this();
		net=runner;
	}
	public NetlogoAgent(NetlogoRunner runner,String name)
	{
		this.name=name;
		net=runner;
		this.mailboxAgent=new NetlogoMailBoxAgent(this);
		
	}
	
	
	public void setFps(int fps)
	{
		if(fps>0)
		  this.fps=fps;
	}
    public void setNetlogoRuner(NetlogoRunner runner)
	{
		this.net=runner;
	}

	protected void Copy(NetlogoAgent netAgent)
	{
		//copy the references of an object into our object
		this.fps=netAgent.fps;
		this.net=netAgent.net;
		super.Copy(netAgent);
	}
	
	
	protected void setup() 
    {

		//System.out.println("setup "+this.getLocalName());
		//this.mailboxAgent=new NetlogoMailBoxAgent(this);
		try {
			AgentController ac= this.getContainerController().acceptNewAgent(mailboxAgent.getMailbox().getMailboxName(), mailboxAgent);
			//System.out.println("start agent controller");
			ac.start();
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Object args[]=this.getArguments();
		if(args!=null) 
		{
		 if (args.length==3)
		{
			
			String path=args[0].toString();
			int width=Integer.parseInt(args[1].toString());
			int heigth=Integer.parseInt(args[2].toString());
			net=new NetlogoRunner(path,width,heigth);
		}
		else if(args.length==1)
		{
			if(args[0] instanceof NetlogoAgent)
			{
			    NetlogoAgent agent=	(NetlogoAgent)args[0];
			    this.Copy(agent);//this<-agent
			}
		}
		}
	
		
	   	long period=1000/fps;
		SequentialBehaviour SequentialExec = new SequentialBehaviour();
		SequentialExec.addSubBehaviour(new Start(this));
		SequentialExec.addSubBehaviour(new MainLoop(this,period));
		SequentialExec.addSubBehaviour(new End(this));
	  	this.addBehaviour(SequentialExec);
	  	
	  } 
	
	protected NetlogoRunner getRunner()
	{
		return net;
	}
	
	protected class MainLoop extends TickerBehaviour
	{
        double ticks=1;
		public MainLoop(Agent a, long period) {
			
			super(a, period);
			this.setFixedPeriod(false);//we allow fps changes

			
		}
      protected void treatNetlogoCommunicateMessage(ACLNetlogoMessage message)
      {
    	  NetlogoCommunicate netCom=(NetlogoCommunicate) net;
    	  ArrayList<Inbox> inboxes=message.getInboxes();
    	  for(int i=0;i<inboxes.size();i++)
    	  {
    		  netCom.treatInput(inboxes.get(i).getName(), message);
    	  }
      }
      protected void sendNetlogoCommunicateMessage()
      {

			NetlogoAgent agent = (NetlogoAgent)myAgent;
			NetlogoCommunicate netCom=(NetlogoCommunicate)net;
			
			//we send our message in the boxes of the users
			if(agent.mailboxAgent==null || agent.mailboxAgent.getMailbox()==null)
				return;
			ArrayList<Outbox>outboxes=agent.mailboxAgent.getMailbox().getOutboxes();
			for(int i=0;i<outboxes.size();i++)
			{
				
				ArrayList<Inbox> inboxes_of_external_mailbox=outboxes.get(i).getInBoxes();
				for(int j=0;j<inboxes_of_external_mailbox.size();j++)
				{
					ACLMessage message=null;
					Inbox inbox_j=inboxes_of_external_mailbox.get(j);
					String mailbox=inbox_j.getMailboxName();
					String user=inbox_j.getOwnerName();
					String result=netCom.getOutput(outboxes.get(i).getName(), user);
					//we send our message to all dest						
					//treatOutbox -> send output
					 if(result!=null && !result.equals(""))
					 {
						 message=new ACLNetlogoMessage();
						 message.setPerformative(ACLMessage.INFORM);
						 String own_mailbox=agent.getMailBoxName();
						 AID own_mailbox_aid=new AID(own_mailbox,AID.ISLOCALNAME);
						 AID receiver_aid=new AID(mailbox,AID.ISLOCALNAME);
						 
						 
						
						 message.addReceiver(own_mailbox_aid);
						 ArrayList<Inbox>message_inboxes=new ArrayList<Inbox>();
						 ArrayList<Outbox>message_outboxes=new ArrayList<Outbox>();
						 message_inboxes.add(inbox_j);
						 ((ACLNetlogoMessage) message).setInboxes(message_inboxes);
						 ((ACLNetlogoMessage) message).setOutbox(outboxes.get(i));
						 message.setContent(result);
						 message.setOntology("inter-Netlogo-Communicate");
						 message.setLanguage("Json");
						 send(message);
					 }
				}
			}
		
      }
		@Override
		protected void onTick() {
			// TODO Auto-generated method stub
			if(ticks>=net.getMaxTicks())
				stop();
			ticks=(double) net.report("ticks");//get ticks
			if(net instanceof NetlogoCommunicate)
			{
			ACLMessage message =receive();
			if(message!=null && message.getSender().equals(mailboxAgent.getAID()))
			{
				if(message instanceof ACLNetlogoMessage)
				{
					ACLNetlogoMessage netlogoMessage=(ACLNetlogoMessage)message;
					this.treatNetlogoCommunicateMessage(netlogoMessage);
				}
				else
				{
					((NetlogoCommunicate)net).TreatInboxMessage(message);
				}		
			}
			}
			
			net.go();
			if(net instanceof NetlogoCommunicate)
			{
				this.sendNetlogoCommunicateMessage();
			}
			
		}
		

		
		
	}
	
	protected class Start extends OneShotBehaviour
	{

		public Start(Agent a)
		{
			this.myAgent=a;
		}
		public void onStart()
		{
			//System.out.println("weeeerk");
		}
		@Override
		public void action() {
			// TODO Auto-generated method stub
			
			System.out.println("Starting "+this.myAgent.getLocalName());
			System.out.println(net.getName()+" has started ");
			net.run();
			net.setup();
			myAgent.doWait(100);
		}

		
	}
	protected class End	extends OneShotBehaviour
	{
		public End(Agent a)
		{
			this.myAgent=a;
		}

		@Override
		public void action() {
			// TODO Auto-generated method stub
			net.endModel();
			System.out.println(net.getName() +" has stopped ");
			this.myAgent.doDelete();
			mailboxAgent.doDelete();
		}
		
		
	}
		
	
}
