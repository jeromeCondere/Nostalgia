package agents;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import model_runner.Neural.NeuralRunner;


import agents.boxAgents.Neural.NeuralMailboxAgent;

import utils.Netlogo.NetlogoJson;
import utils.Neural.NeuralData;
import utils.box.Inbox;
import utils.box.NeuralInbox;
import utils.box.Outbox;

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
 protected NeuralRunner runner;
 public NeuralAgent(String name)
 {
	 this.name=name;
	 this.mailboxAgent=new NeuralMailboxAgent(this);
 }
 public NeuralAgent(String name,NeuralRunner runner)
 {
	 this(name);
	 this.runner=runner;
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
		     this.treatFromMailbox(message);
			}
			else 
			{
				//message comes from a stimuli generator
				
			}
		}
		
	}
	protected void treatFromMailbox(ACLMessage message)
	{
		NeuralAgent agent=((NeuralAgent)this.myAgent);
		ArrayList<NeuralData> neuralDatum=getNeuralDataFromMessage(message);
		NeuralData dataSum=agent.runner.sum(neuralDatum);
		NeuralData dataToSend=agent.runner.transfert(dataSum, bias);
		//we always send something
		
		//we send to all the inboxes of the outboxes
		ArrayList<Outbox>outboxes=agent.mailboxAgent.getMailbox().getOutboxes();
		for(int i=0;i<outboxes.size();i++)
		{
			
			ArrayList<Inbox> inboxes_of_external_mailbox=outboxes.get(i).getInBoxes();
			for(int j=0;j<inboxes_of_external_mailbox.size();j++)
			{
				NeuralInbox inbox_j=(NeuralInbox)inboxes_of_external_mailbox.get(j);
				ACLMessage messageToSend=makeMessage(outboxes.get(i),inbox_j,dataToSend);
				send(messageToSend);
			}
		}
			
	}
	protected ACLMessage makeMessage(Outbox outbox,NeuralInbox inbox,NeuralData dataToSend)
	{
		ArrayList<Inbox>inboxes=new ArrayList<Inbox>();
		 inboxes.add(inbox);
		JSONObject jsonContent=new JSONObject();
		JSONObject ports=new JSONObject();
		 ports.put("out", NetlogoJson.OutboxToJson(outbox));
		 ports.put("in", NetlogoJson.InboxesToJson(inboxes));
		 
		 JSONObject singleStimuliJson=new JSONObject();
		 singleStimuliJson.put("value", dataToSend.getValue());
		 singleStimuliJson.put("content",dataToSend.getContent());
		 
		 jsonContent.put("ports", ports);
		 jsonContent.put("single stimuli",singleStimuliJson);
		 
		 ACLMessage message=new ACLMessage(ACLMessage.INFORM);
		 message.addReceiver(mailboxAgent.getAID());
		 message.setOntology("inter-Neural-Communicate");
		 message.setLanguage("json");
		 message.setContent(jsonContent.toString());
		 
		return message;
	}
	protected ArrayList<NeuralData> getNeuralDataFromMessage(ACLMessage message)
	{
		String content=message.getContent();
		try{
			ArrayList<NeuralData> datum=new ArrayList<NeuralData>();
			JSONParser jsonParser = new JSONParser();
			JSONObject object =(JSONObject) jsonParser.parse(content);
			JSONArray stimuliArray=(JSONArray) object.get("stimuli");
			for(int i=0;i<stimuliArray.size();i++)
			{
				JSONObject stimuliObj=(JSONObject) stimuliArray.get(i);
				JSONObject singleStimuli=(JSONObject) stimuliObj.get("single stimuli");
				JSONObject inboxJson=(JSONObject) stimuliObj.get("in");
				
				String inboxName=(String) inboxJson.get("name");
				float weight= ((Number)inboxJson.get("weight")).floatValue();
				float value= ((Number)singleStimuli.get("value")).floatValue();
				String singleStimuliContent=(String) singleStimuli.get("content");
				NeuralData data=new NeuralData(inboxName,singleStimuliContent,value,weight);
				datum.add(data);
			}
			return datum;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
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
		
		mailboxAgent.doDelete();
		this.myAgent.doDelete();
	}
	
	
}
}
