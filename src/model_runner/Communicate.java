package model_runner;

import jade.lang.acl.ACLMessage;



public interface Communicate {

	ACLMessage sendOutboxMessage(String user,String Outbox);//send output data
	void TreatInboxMessage(ACLMessage message);//treat output data
	}
