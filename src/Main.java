import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import model_runner.NetlogoRunner;

import org.nlogo.app.App;
import org.nlogo.headless.HeadlessWorkspace;
import org.nlogo.lite.InterfaceComponent;

import agents.NetlogoAgent;
public class Main {
  public static void main(String[] argv) {
	  jade.core.Runtime runt= jade.core.Runtime.instance();
	  Profile myProfile = new ProfileImpl();
	  
	  //myProfile.setParameter(Profile.MAIN_HOST, "localhost");
	  //myProfile.setParameter(Profile.MAIN_PORT, "1099");
	  
	  ContainerController myContainer = runt.createMainContainer(myProfile);

	// .. load a container into the above variable ..
	
	
	  
		try {
			NetlogoAgent a=new NetlogoAgent("walk_1.nlogo",600,600);
		    
			Object o[]={a};
			
		    AgentController rma = myContainer.createNewAgent("rma", "jade.tools.rma.rma", null);
		    AgentController net2 = myContainer.createNewAgent("net", "agents.NetlogoAgent", o);
		    
		    rma.start();
		    net2.start();
		    
		    
		} catch(StaleProxyException e) {
		    e.printStackTrace();
		}
    }
}
  
