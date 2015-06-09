import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import model_runner.NetlogoRunner;
import model_runner.net1;

import org.nlogo.app.App;
import org.nlogo.headless.HeadlessWorkspace;
import org.nlogo.lite.InterfaceComponent;

import agents.NetlogoAgent;
import agents.NosAgent;
import agents.talk;
public class Main {
  public static void main(String[] argv) {
	  jade.core.Runtime runt= jade.core.Runtime.instance();
	  Profile myProfile = new ProfileImpl();
	  ContainerController myContainer = runt.createMainContainer(myProfile);
	
	  
		try {
			
			net1 nety=new net1("walk_1.nlogo",400,400);
			net1 netz=new net1("walk_1.nlogo",400,400);
			nety.set_i(88);
			netz.set_i(25);
			NetlogoAgent agt1=new NetlogoAgent(nety);
			NetlogoAgent agt2=new NetlogoAgent(netz);
		    HelloWorldAgent yo=new HelloWorldAgent();
		    agt1.addReceiver("net2", "yolo_in");//agt1>(net2,yolo_in)
		    agt2.addSender("net1", "yolo_in");//agt2<(net1,yolo_in)
		    
			
		    AgentController rma = myContainer.createNewAgent("rma", "jade.tools.rma.rma", null);
		    AgentController net1 = myContainer.acceptNewAgent("net1", agt1);	   
		    AgentController net2 = myContainer.acceptNewAgent("net2", agt2);
		    rma.start();
		    net1.start();
		    net2.start();
		    
		    
		} catch(StaleProxyException e) {
		    e.printStackTrace();
		}
    }
}
  
