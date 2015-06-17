

import model_runner.communicate.Netlogo.NetlogoCommunicate;
import model_runner.netlogo.NetlogoRunner;
import jade.core.AID;
import jade.lang.acl.ACLMessage;







public class net1 extends NetlogoRunner implements NetlogoCommunicate {

	private int i=0;
	public net1(String path,int width,int heigth)
	{
		super(path);
		this.setHeigth(heigth);
		this.setWidth(width);
		
	}
	public void set_i(int i)
	{
		this.i=i;
	}

	@Override
	public void TreatInboxMessage(ACLMessage message) {
		// TODO Auto-generated method stub
		if(message.getContent().contains("yolo_in"))
			{
			System.out.println(message.getContent());
			System.out.println("---------  "+i+" ----------");
			i++;
			}
		
	}
	@Override
	public ACLMessage sendOutboxMessage(String user) {
		// TODO Auto-generated method stub
		
		return null;
	}
	@Override
	public void treatInput(String inbox, ACLMessage message) {
		// TODO Auto-generated method stub
		if(inbox.equals("in2"))
			System.out.println(message.getContent());
		
	}
	@Override
	public String getOutput(String outbox, String user) 
	{
		// TODO Auto-generated method stub
		if(outbox.equals("out1"))
		return "yolo";
		return null;
	}
	

}
