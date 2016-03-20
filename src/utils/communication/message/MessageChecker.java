package utils.communication.message;

import NosException.CheckMessageException;
import jade.lang.acl.ACLMessage;

public interface MessageChecker 
{
	//check if a message received is valid according to a standard of reception
	public Object check(ACLMessage message,Object [] params) throws CheckMessageException;
}
