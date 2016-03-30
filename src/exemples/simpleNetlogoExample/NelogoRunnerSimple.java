package exemples.simpleNetlogoExample;

import java.util.ArrayList;

import org.nlogo.agent.AgentSet;
import org.nlogo.agent.Turtle;

import utils.Netlogo.NetlogoJson;
import utils.Netlogo.NetlogoTurtle;
import jade.lang.acl.ACLMessage;
import model_runner.communicate.Netlogo.NetlogoCommunicate;
import model_runner.netlogo.NetlogoRunner;

public class NelogoRunnerSimple extends NetlogoRunner implements
		NetlogoCommunicate {

	public NelogoRunnerSimple(String path,int width,int heigth)
	{
		super(path);
		this.setHeigth(heigth);
		this.setWidth(width);
		
	}
	
	@Override
	public void TreatInboxMessage(ACLMessage message) {
		// TODO Auto-generated method stub
	}
	@Override
	public ACLMessage sendOutboxMessage(String user) {
		// TODO Auto-generated method stub
		
		return null;
	}
	@Override
	public void treatInput(String inbox, ACLMessage message) {
		// TODO Auto-generated method stub
	}
	@Override
	public String getOutput(String outbox, String user) 
	{
		return null;
		// TODO Auto-generated method stub
	}

}
