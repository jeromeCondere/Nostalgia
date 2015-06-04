

import jade.core.Agent;

/**
   This example shows a minimal agent that just prints "Hello World!" 
   and then terminates.
   @author Giovanni Caire - TILAB
 */
public class HelloWorldAgent extends Agent {

  protected void setup() {
  	System.out.println("Hello World! name is "+getLocalName());
   	// Make this agent terminate
  	doDelete();
  } 
  
}