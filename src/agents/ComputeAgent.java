package agents;

import jade.lang.acl.ACLMessage;

public abstract class ComputeAgent extends NosAgent {
	public abstract void compute(ACLMessage messageOrder);
	public abstract void compute(Object [] o);
}
