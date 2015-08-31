package utils.box;

public class NeuronnalMailbox extends Mailbox {

	public NeuronnalMailbox(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	protected  void addOutbox(String OutboxName)
	{
		NeuronalOutbox outbox=new NeuronalOutbox(OutboxName, this.mailboxName);
		  outboxes.add(outbox);
	}
}
