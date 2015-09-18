package agents;

import java.util.ArrayList;


import agents.boxAgents.Neural.NeuralMailboxAgent;

import utils.box.Inbox;
import utils.box.NeuralInbox;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public abstract class NeuralAgent extends NosAgent
{
 protected float bias=0;
 protected int fps=30;

 public NeuralAgent(String name)
 {
	 this.name=name;
	 this.mailboxAgent=new NeuralMailboxAgent(this);
 }
 
protected abstract ACLMessage sum(ArrayList<ACLMessage> message);

 protected  ACLMessage transfer(ACLMessage message)
 {
	 return message;
 }
 public void setWeight(Inbox inbox,float weight)
 {
	 ArrayList<Inbox> inboxes=this.mailboxAgent.getMailbox().getInboxes();
	 int index=inboxes.indexOf(inbox);
	 if(index!=-1)
	 {
		if(inboxes.get(index) instanceof NeuralInbox)
		{
			((NeuralInbox)inboxes.get(index)).setWeight(weight);
		}
	 }
 }
 public void setWeight(String name,float weight)
 {
	 NeuralInbox inbox=new NeuralInbox(name,this.mailboxAgent.getMailbox().getMailboxName());
	 setWeight(inbox, weight);
 }
 public float getBias() {
	return bias;
}
public void setBias(float bias) {
	this.bias = bias;
}
protected void setup()
{
	try {
		AgentController ac= this.getContainerController().acceptNewAgent(mailboxAgent.getMailbox().getMailboxName(), mailboxAgent);
		//System.out.println("start agent controller");
		ac.start();
	} catch (StaleProxyException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	long period=1000/fps;
	SequentialBehaviour SequentialExec = new SequentialBehaviour();
	SequentialExec.addSubBehaviour(new Start(this));
	SequentialExec.addSubBehaviour(new MainLoop(this,period));
	SequentialExec.addSubBehaviour(new End(this));
  	this.addBehaviour(SequentialExec);
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
	
		ACLMessage message=this.myAgent.receive();
		if(message==null)
			return;
		
		if(message.getPerformative()==ACLMessage.INFORM && message.getOntology().equals("inter-Neural-Communicate"))
		{
			if(message.getSender().equals(mailboxAgent.getAID()))
			{
				
			}
		}
		
	}
	protected void treatFromMailbox(ACLMessage message)
	{
		
	}
	
}
protected class Start extends OneShotBehaviour
{

	public Start(Agent a)
	{
		this.myAgent=a;
	}
	
	@Override
	public void action() {
		// TODO Auto-generated method stub
		
		System.out.println("Starting "+this.myAgent.getLocalName());
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
		
		System.out.println(this.myAgent.getLocalName()+" is ending ");
		this.myAgent.doDelete();
		mailboxAgent.doDelete();
	}
	
	
}
}
