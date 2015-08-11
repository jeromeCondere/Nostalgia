package agents.Netlogo;

import jade.lang.acl.ACLMessage;
import agents.NosAgent;

public abstract class ComputeAgent extends NosAgent {
	public abstract void compute(ACLMessage messageOrder);
	public abstract void compute(Object [] o);
}
