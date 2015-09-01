package agents;

import java.util.ArrayList;

import utils.box.Inbox;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public abstract class NeuronalAgent extends NosAgent
{
 protected abstract ACLMessage sum(ArrayList<ACLMessage> message);
 protected  ACLMessage transfer(ACLMessage message)
 {
	 return message;
 }
 public void setWeight(Inbox inbox,float weigth)
 {
	 
 }
 
}
