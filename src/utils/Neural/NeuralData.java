package utils.Neural;

public class NeuralData {

	private String content;
	private float value;
	private String inboxName;
	private float weight;
	public NeuralData(String inboxName,String content,float value,float weight)
	{
		this.content=content;
		this.inboxName=inboxName;
		this.value=value;
		this.weight=weight;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public String getInboxName() {
		return inboxName;
	}
	public void setInboxName(String inboxName) {
		this.inboxName = inboxName;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	
}
