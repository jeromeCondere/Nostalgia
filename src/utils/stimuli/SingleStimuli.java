package utils.stimuli;

import jade.lang.acl.ACLMessage;
import utils.box.Inbox;

public class SingleStimuli {
private Inbox inbox;
private ACLMessage message;
public SingleStimuli(Inbox inbox,ACLMessage message)
{
	this.inbox=inbox;
	this.message=message;
}
public SingleStimuli(Inbox inbox)
{
	this.inbox=inbox;
}
public void clear()
{
	this.message=null;
}
public boolean isFilled()
{
	return message!=null;
}
public void setStimuli(ACLMessage message)
{
	this.message=message;
}
public boolean equals(Object obj)
{
    // Vérification de l'égalité des références
    if (obj==this) {
        return true;
    }
    else if(obj instanceof Inbox)
    {
    	Inbox inboxObj=(Inbox)obj;
    	return this.inbox.getMailboxName().equals(inboxObj.getMailboxName()) && inbox.getName().equals(inboxObj.getName()); 
    }
    
    
    
    
	return false;
}
}
