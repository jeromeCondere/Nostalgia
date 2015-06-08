package agents;
import java.util.ArrayList;

import model_runner.Communicate;
import model_runner.NetlogoRunner;
import model_runner.net1;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class NetlogoAgent extends NosAgent
{
    protected  int fps=90;//default max frame rate
	protected NetlogoRunner net;
	public NetlogoAgent()
	{
		net=new net1("random_walk_1.nlogo",700,700);
	}
	public NetlogoAgent(String path,int width,int heigth)
	{
		net=new NetlogoRunner(path,width,heigth);
	}
	public void setFps(int fps)
	{
		if(fps>0)
		  this.fps=fps;
	}
    public void setNetlogoRuner(NetlogoRunner runner)
	{
		this.net=runner;
	}
	public static Object[] getNetParams(String path,int width,int heigth)
	{
		 Object t[]={path,width,heigth};
		return t;
	}
	protected void Copy(NetlogoAgent netAgent)
	{
		//copy the references of an object into our object
		this.fps=netAgent.fps;
		this.net=netAgent.net;
		super.Copy(netAgent);
	}
	protected void setup() 
    {

		Object args[]=this.getArguments();
		if(args==null || args.length==0)
		{
			net=new net1("random_walk_1.nlogo",700,700);
			return;
		}
		else if (args.length==3)
		{
			
			String path=args[0].toString();
			int width=Integer.parseInt(args[1].toString());
			int heigth=Integer.parseInt(args[2].toString());
			net=new NetlogoRunner(path,width,heigth);
		}
		else if(args.length==1)
		{
			if(args[0] instanceof NetlogoAgent)
			{
			    NetlogoAgent agent=	(NetlogoAgent)args[0];
			    this.Copy(agent);
			}
		}
	
	  	
	   	long period=1000/fps;
		SequentialBehaviour SequentialExec = new SequentialBehaviour();
		SequentialExec.addSubBehaviour(new Start(this));
		SequentialExec.addSubBehaviour(new MainLoop(this,period));
		SequentialExec.addSubBehaviour(new End(this));
	  	this.addBehaviour(SequentialExec);
	  } 
	
	protected NetlogoRunner getRunner()
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
			if(net instanceof Communicate)
			{
			ACLMessage message =receive();
			if(message!=null)
			{
				
				
				while(message!=null){
					message=receive();
					/*
					 * the acl message  will be in json for communication inter netlogo
					 */
					((Communicate) net).TreatInboxMessage(message);
				   }
				
			}
			}
			
			net.go();
			if(net instanceof Communicate)
			{
				for(int i=0;i<Outboxes.size();i++)
				{
					ArrayList<String>dests=Outboxes.get(i).getOwners();
					for(int j=0;j<dests.size();j++)
					{
						//we send our message to all dest
						ACLMessage message=((Communicate) net).sendOutboxMessage(dests.get(j),Outboxes.get(i).getName());
						//treatOutbox -> send output
						send(message);
					}
				}
			}
			
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
