package model_runner;

import jade.lang.acl.ACLMessage;





public class net1 extends NetlogoRunner implements Communicate {

	
	public net1(String path,int width,int heigth)
	{
		super(path);
		this.setHeigth(heigth);
		this.setWidth(width);
			
	}
	
    @Override
	public ACLMessage sendOutboxMessage(String user, String OutboxMessage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void TreatInboxMessage(ACLMessage message) {
		// TODO Auto-generated method stub
		
	}

}
