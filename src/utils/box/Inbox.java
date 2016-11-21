package utils.box;

import java.util.ArrayList;


public class Inbox extends Box{
private ArrayList<Outbox> outboxes=null;
public Inbox(String name, String mailboxName) {
		super(name, mailboxName);
		// TODO Auto-gnated constructor stub
	}
public ArrayList<Outbox> getOutBoxes()
{
	return this.outboxes;
}
public void addInputConnection(String OutboxName,String mailbox)//connect the inbox to an outbox
{
	
	this.addOutbox(new Outbox(OutboxName,mailbox));
}
protected void addOutbox(Outbox outbox)
{
	if(outbox==null)
		return;
	if(outboxes==null)
		{
		outboxes=new ArrayList<Outbox>();
		}
	//if the outbox doesn't exist yet we add it
	if(!outboxes.contains(outbox))
		outboxes.add(outbox);
}

public boolean equals(Object obj)
{
    // Vérification de l'égalité des références
    if (obj==this) {
        return true;
    }
    else if(obj instanceof Inbox)
    {
    	Inbox inbox=(Inbox)obj;
    	return inbox.mailboxName.equals(this.mailboxName) && inbox.name.equals(this.name);
    }
    
    
    
	return false;
}


}
