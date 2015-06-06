package agents;
import model_runner.NetlogoRunner;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.TickerBehaviour;
public class NetlogoAgent extends Agent 
{
    protected  int fps=90;//default max frame rate
	protected NetlogoRunner net;
	public NetlogoAgent()
	{
		net=new NetlogoRunner("random_walk_1.nlogo",700,700);
	}
	public NetlogoAgent(String path,int width,int heigth)
	{
		net=new NetlogoRunner(path,width,heigth);
	}
	protected void setup() {
	  	
	   	long period=1000/fps;
		SequentialBehaviour SequentialExec = new SequentialBehaviour();
		SequentialExec.addSubBehaviour(new Start(this));
		SequentialExec.addSubBehaviour(new MainLoop(this,period));
		SequentialExec.addSubBehaviour(new End(this));
	  	this.addBehaviour(SequentialExec);
	  } 
	public NetlogoRunner getRunner()
	{
		return net;
	}
	
	protected class MainLoop extends TickerBehaviour
	{
        double ticks=1;
		public MainLoop(Agent a, long period) {
			
			super(a, period);
			this.setFixedPeriod(false);//we allow fps changes

			
		}

		@Override
		protected void onTick() {
			// TODO Auto-generated method stub
			if(ticks>=net.getMaxTicks())
				stop();
			ticks=(double) net.report("ticks");//get ticks
			net.go();
		}
		

		
		
	}
	
	protected class Start extends OneShotBehaviour
	{

		public Start(Agent a)
		{
			this.myAgent=a;
		}
		@Override
		public void action() {
			// TODO Auto-generated method stub
			System.out.println("Starting "+this.myAgent.getLocalName());
			System.out.println(net.getName()+" has started ");
			net.run();
			net.setup();
			myAgent.doWait(100);
		}

		
	}
	protected class End	extends OneShotBehaviour
	{
		public End(Agent a)
		{
			this.myAgent=a;
		}

		@Override
		public void action() {
			// TODO Auto-generated method stub
			net.endModel();
			System.out.println(net.getName() +" has stopped ");
			this.myAgent.doDelete();
		}
		
		
	}
		
	
}
