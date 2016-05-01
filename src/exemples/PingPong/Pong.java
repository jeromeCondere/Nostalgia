package exemples.PingPong;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;

public class Pong extends Agent {
	protected void setup() {
	  	
	   	// Make this agent terminate
		SequentialBehaviour behavior = new SequentialBehaviour();
		behavior.addSubBehaviour(new TickerBehaviour(this, 2000){

			@Override
			protected void onTick() {
				// TODO Auto-generated method stub
				ACLMessage msg = receive();
				if(msg==null)
					return;
				ACLMessage answer = msg.createReply();
				msg.setContent("pong !!");
				System.out.println("Pong");
				send(answer);
			}
			
		});
		this.addBehaviour(behavior);
	  }
}
