package agents.forward;
import jade.lang.acl.ACLMessage;


public abstract class ForwardPolicy {
public abstract boolean matchPolicy(ACLMessage request);
}
