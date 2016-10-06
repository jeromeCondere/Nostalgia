package model_runner.communicate;

import jade.lang.acl.ACLMessage;




public interface Communicate {

	ACLMessage sendOutboxMessage(String user);//send output data
	void TreatInboxMessage(ACLMessage message);//treat input data
	}
