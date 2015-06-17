package utils.box;

import java.util.ArrayList;


public class Outbox extends Box{
	private ArrayList<Inbox> inboxes=null;
	public Outbox(String name, String mailboxName) {
		super(name, mailboxName);
		// TODO Auto-generated constructor stub
	}
	public ArrayList<Inbox> getInBoxes()
	{
		return this.inboxes;
	}
	public void addOutputConnection(String InboxName,String mailbox)//connect the outbox to an inbox
	{
		this.addInbox(new Inbox(InboxName,mailbox));
	}
	protected void addInbox(Inbox inbox)
	{
		if(inbox==null)
			return;
		if(inboxes==null)
			inboxes=new ArrayList<Inbox>();
		//if the inbox doesn't exist yet we add it
		if(!inboxes.contains(inbox))
			inboxes.add(inbox);
	}
	public boolean equals(Object obj) {
	    // Vérification de l'égalité des références
	    if (obj==this) {
	        return true;
	    }
	    else if(obj instanceof Outbox)
	    {
	    	Outbox outbox=(Outbox)obj;
	    	return outbox.mailboxName.equals(this.mailboxName) && outbox.name.equals(this.name);
	    }
	    
	    
	    
		return false;
	}
}
