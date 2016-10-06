package exemples.NetlogoExchange;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import agents.Netlogo.NetlogoAgent;
import exemples.simpleNetlogoExample.NelogoRunnerSimple;

public class NetlogoExchange {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		jade.core.Runtime runt= jade.core.Runtime.instance();
		Profile myProfile = new ProfileImpl();
		ContainerController myContainer = runt.createMainContainer(myProfile);
		  
		NetlogoExchangeRunner runner1=new NetlogoExchangeRunner("walk_1.nlogo",400,400);
		NetlogoExchangeRunner runner2=new NetlogoExchangeRunner("walk_1.nlogo",400,400);
		runner1.setName("R1");
		runner2.setName("R2");
		NetlogoAgent agt1=new NetlogoAgent(runner1,"netlogo_simple1");
		NetlogoAgent agt2=new NetlogoAgent(runner2,"netlogo_simple2");

		
		agt1.setFps(40);
		agt2.setFps(1);
		
		agt1.addOutputConnection("out1","in2",agt2);//agt1(out1)> agt2 (in2)
		agt2.addInputConnection("in2","out1", agt1);//agt2(in2) < agt1(out1)
		// agt1 ----> agt2
		
		
	    agt2.addOutputConnection("out2","in1",agt1);//agt2(out2)> agt1(in1)
	    agt1.addInputConnection("in1","out2", agt2);//agt1(in1) < agt2(out2)
	    // agt2 ------> agt1
	    
		
		try {
			AgentController rma = myContainer.createNewAgent("rma", "jade.tools.rma.rma", null);
			AgentController dummy=myContainer.createNewAgent("dummy", "jade.tools.DummyAgent.DummyAgent",null);
			AgentController netlogo_agt1 = myContainer.acceptNewAgent("netlogo_simple1", agt1);
			AgentController netlogo_agt2 = myContainer.acceptNewAgent("netlogo_simple2", agt2);
			rma.start();
			netlogo_agt1.start();
			netlogo_agt2.start();
			
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
