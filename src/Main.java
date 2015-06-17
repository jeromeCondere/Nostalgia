import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import model_runner.netlogo.NetlogoRunner;

import org.nlogo.app.App;
import org.nlogo.headless.HeadlessWorkspace;
import org.nlogo.lite.InterfaceComponent;

import agents.NosAgent;
import agents.talk;
import agents.Netlogo.NetlogoAgent;
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
			NetlogoAgent agt1=new NetlogoAgent(nety,"net1");
			NetlogoAgent agt2=new NetlogoAgent(netz,"net2");
		    HelloWorldAgent yo=new HelloWorldAgent();

		    agt1.addOutputConnection("out1","in2", "net2");//agt1>(net2,yolo_in)
		    agt2.addInputConnection("in2","out1", "net1");//agt2<(net1,yolo_in)
			
		    AgentController rma = myContainer.createNewAgent("rma", "jade.tools.rma.rma", null);
		    AgentController net1 = myContainer.acceptNewAgent("net1", agt1);	   
		    AgentController net2 = myContainer.acceptNewAgent("net2", agt2);
		    AgentController dummy=myContainer.createNewAgent("dummy", "jade.tools.DummyAgent.DummyAgent",null);
		    
		    
		    
		    rma.start();
		    net1.start();
		    net2.start();
		    
		    System.out.println("fiin");
		    
		    
		    
		    
		    
		} catch(StaleProxyException e) {
		    e.printStackTrace();
		}
    }
}
  
