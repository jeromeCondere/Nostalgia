package model_runner.communicate.Netlogo;

import model_runner.communicate.Communicate;
import jade.lang.acl.ACLMessage;

public interface NetlogoCommunicate extends Communicate {

	public void treatInput(String inbox,ACLMessage message);
	public String getOutput(String outbox,String user);
	
}
