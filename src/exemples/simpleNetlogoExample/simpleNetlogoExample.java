package exemples.simpleNetlogoExample;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import agents.Netlogo.NetlogoAgent;

public class simpleNetlogoExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		jade.core.Runtime runt= jade.core.Runtime.instance();
		  Profile myProfile = new ProfileImpl();
		  ContainerController myContainer = runt.createMainContainer(myProfile);
		  
		NelogoRunnerSimple runner=new NelogoRunnerSimple("walk_1.nlogo",400,400);
		NetlogoAgent agt1=new NetlogoAgent(runner,"netlogo_simple");
		agt1.setFps(30);
		try {
			AgentController rma = myContainer.createNewAgent("rma", "jade.tools.rma.rma", null);
			AgentController dummy=myContainer.createNewAgent("dummy", "jade.tools.DummyAgent.DummyAgent",null);
			AgentController netlogo_simple = myContainer.acceptNewAgent("net1", agt1);
			rma.start();
			netlogo_simple.start();
			
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
