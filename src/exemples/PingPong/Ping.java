package exemples.PingPong;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class Ping extends Agent {

	protected void setup() 
	{
	  	
	   	// Make this agent terminate
		SequentialBehaviour behavior = new SequentialBehaviour();
		behavior.addSubBehaviour(new OneShotBehaviour(this) {
			public void action() {
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.setContent("Ping");
			msg.setConversationId("conv");
			msg.addReceiver(new AID("PONG",AID.ISLOCALNAME));
			send(msg);
			}
			} );
		behavior.addSubBehaviour(new TickerBehaviour(this, 2000){

			@Override
			protected void onTick() {
				// TODO Auto-generated method stub
				ACLMessage msg = receive();
				if(msg==null)
					return;
				ACLMessage answer = msg.createReply();
				msg.setContent("ping !!");
				System.out.println("Ping");
				send(answer);
			}
			
		});
		
		this.addBehaviour(behavior);
	  }

}
