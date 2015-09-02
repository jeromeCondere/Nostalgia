package agents;

import java.util.ArrayList;

import utils.box.Inbox;
import utils.box.NeuralInbox;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public abstract class NeuralAgent extends NosAgent
{
 protected float bias=0;

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
 
}
