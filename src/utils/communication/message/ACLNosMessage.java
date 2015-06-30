package utils.communication.message;


import java.util.EmptyStackException;
import java.util.Stack;

import jade.lang.acl.ACLMessage;

public class ACLNosMessage extends ACLMessage {
//ACLNosMessage constants start from 40
Stack<String> forwardStack=null;
public void pushForwarder(String forwarder)
{
	if(forwardStack==null)
		forwardStack=new Stack<String>();
	forwardStack.push(forwarder);
}
public String popForwarder() throws EmptyStackException
{
	try{
	return forwardStack.pop();
	}
	catch(EmptyStackException e)
	{
		throw e;
	}
}
//there is only one forwarder
public boolean isUniqueForwarder()
{
	return forwardStack.size()==1;
}
public boolean isForwardStackEmpty()
{
	return forwardStack.empty();
}
public void clearForwardStack()
{
	forwardStack.clear();
}


}
