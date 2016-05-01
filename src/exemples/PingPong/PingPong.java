package exemples.PingPong;
import agents.Netlogo.NetlogoAgent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class PingPong {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		jade.core.Runtime runt= jade.core.Runtime.instance();
		Profile myProfile = new ProfileImpl();
		ContainerController myContainer = runt.createMainContainer(myProfile);
try {

		    
		    AgentController rma = myContainer.createNewAgent("rma", "jade.tools.rma.rma", null);
		    AgentController dummy=myContainer.createNewAgent("dummy", "jade.tools.DummyAgent.DummyAgent",null);
		    AgentController Ping =myContainer.acceptNewAgent("PING", new Ping());
		    AgentController Pong =myContainer.acceptNewAgent("PONG", new Pong());
		    
		    Pong.start();
		    Ping.start();
		    rma.start();
		    
		    
		    
		    
		    
		    
		    
		} catch(StaleProxyException e) {
		    e.printStackTrace();
		}
	}

}
