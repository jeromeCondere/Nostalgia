import jade.core.Agent;
import jade.lang.acl.ACLMessage;


public abstract class PerceptionAgent extends Agent
{
  
  public abstract Situation percept(ACLMessage message);// from a message the agent give a situation
  public abstract Situation infere();//infere on environnement in order to get a situation
	
}
