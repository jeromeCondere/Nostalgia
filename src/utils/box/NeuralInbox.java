package utils.box;

public class NeuralInbox extends Inbox {

	private float weight=1;
	public NeuralInbox(String name, String mailboxName) {
		super(name, mailboxName);
		
	}
	
	public NeuralInbox(String name, String mailboxName,float weigth) {
		super(name, mailboxName);
		this.weight=weigth;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weigth) {
		 this.weight=weigth;
	}
	
}
 