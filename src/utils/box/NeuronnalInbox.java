package utils.box;

public class NeuronnalInbox extends Inbox {

	private float weigth=1;
	public NeuronnalInbox(String name, String mailboxName) {
		super(name, mailboxName);
		
	}
	
	public NeuronnalInbox(String name, String mailboxName,float weigth) {
		super(name, mailboxName);
		this.weigth=weigth;
	}
	public float getWeigth() {
		return weigth;
	}
	
}
 