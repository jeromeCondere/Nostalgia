package exemples.NetlogoExchange;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nlogo.agent.AgentSet;
import org.nlogo.agent.Turtle;
import org.nlogo.agent.World;
import org.nlogo.api.Agent;

import utils.Netlogo.NetlogoJson;
import utils.Netlogo.NetlogoTurtle;

import jade.lang.acl.ACLMessage;
import model_runner.communicate.Netlogo.NetlogoCommunicate;
import model_runner.netlogo.NetlogoRunner;

public class NetlogoExchangeRunner extends NetlogoRunner implements
		NetlogoCommunicate {

	public NetlogoExchangeRunner(String path,int width,int heigth)
	{
		super(path);
		this.setHeigth(heigth);
		this.setWidth(width);	
	}
	
	@Override
	public ACLMessage sendOutboxMessage(String user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void TreatInboxMessage(ACLMessage message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void treatInput(String inbox, ACLMessage message) {
		
		
		float eps=0.02f;
		float epsilon =1f;
		epsilon+=eps;
		ArrayList<NetlogoTurtle> tl;
		if(inbox.equals("in2"))
		{
			//in by left
			try {
				tl = NetlogoJson.getTurtles(message.getContent());
				inLeft(epsilon,tl);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(inbox.equals("in1"))
		{
			//in by right
			try {
				tl = NetlogoJson.getTurtles(message.getContent());
				inRight(epsilon,tl);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

	}

	@Override
	public String getOutput(String outbox, String user) {
		// TODO Auto-generated method stub
		ArrayList <NetlogoTurtle>turtles;
		float epsilon =1f;
		if(outbox.equals("out1"))
		{
			turtles = RightBorder(epsilon);
			
			if(!turtles.isEmpty())
			return NetlogoJson.setNetlogoMessage(turtles, null, "eh");
		//return "yolo1";
		}
		else if (outbox.equals("out2"))
		{
			turtles = LeftBorder(epsilon);
			if(!turtles.isEmpty())
				return NetlogoJson.setNetlogoMessage(turtles, null, "eh");
		}
		
		
		return null;
	}

	//remove the turtles that has passed the right border
	protected  ArrayList<NetlogoTurtle> RightBorder(float eps)
	{
		ArrayList<Long> index_turtles = new ArrayList<Long>();
		ArrayList<NetlogoTurtle> turtles = new ArrayList<NetlogoTurtle>();
		AgentSet walkers = (AgentSet)report ("turtles with [xcor >  (max-pxcor - "+eps+") ]");
		for(int i=0;i<walkers.count();i++)
		{
			Turtle item=(Turtle) walkers.agent(i);
			NetlogoTurtle turtle=new NetlogoTurtle(item);
			index_turtles.add(item.id);
			turtles.add(turtle);
		}
		removeTurtles(index_turtles);

		return turtles;
		
	}
	
	//remove the turtles that has passed the left border
	protected  ArrayList<NetlogoTurtle> LeftBorder(float eps)
	{
		ArrayList<Long> index_turtles =new ArrayList<Long>();
		ArrayList<NetlogoTurtle> turtles=new ArrayList<NetlogoTurtle>();
		AgentSet walkers = (AgentSet)report ("turtles with [xcor  < "+eps+" ]");
		for(int i=0;i<walkers.count();i++)
		{
			Turtle item=(Turtle) walkers.agent(i);
			NetlogoTurtle turtle=new NetlogoTurtle(item);
			index_turtles.add(item.id);
			turtles.add(turtle);
		}
		removeTurtles(index_turtles);
		
		return turtles;
		
	}

	//remove the turtles that has passed the top border
	protected  ArrayList<NetlogoTurtle> UpBorder(float eps)
	{
		ArrayList<Long> index_turtles =new ArrayList<Long>();
		ArrayList<NetlogoTurtle> turtles=new ArrayList<NetlogoTurtle>();
		AgentSet walkers = (AgentSet)report ("turtles with [ycor >  (max-pycor - "+eps+") ]");
		for(int i=0;i<walkers.count();i++)
		{
			Turtle item=(Turtle) walkers.agent(i);
			NetlogoTurtle turtle=new NetlogoTurtle(item);
			index_turtles.add(item.id);
			turtles.add(turtle);
		}
		removeTurtles(index_turtles);

		return turtles;
		
	}
	
	//remove the turtles that has passed the bottom border
	protected  ArrayList<NetlogoTurtle> DownBorder(float eps)
	{
		ArrayList<Long> index_turtles =new ArrayList<Long>();
		ArrayList<NetlogoTurtle> turtles=new ArrayList<NetlogoTurtle>();
		AgentSet walkers = (AgentSet)report ("turtles with [ycor  < "+eps+" ]");
		for(int i=0;i<walkers.count();i++)
		{
			Turtle item=(Turtle) walkers.agent(i);
			NetlogoTurtle turtle=new NetlogoTurtle(item);
			index_turtles.add(item.id);
			turtles.add(turtle);
		}
		removeTurtles(index_turtles);

		return turtles;
		
	}

	//process turtles that comes from the right
	protected void inRight(float eps,ArrayList<NetlogoTurtle> tl)
	{
		String cmd="";
		for(int i=0;i<tl.size();i++)
		{
			NetlogoTurtle turtle=tl.get(i);
			cmd+="create-turtles 1 [ ";
			cmd+="set size "+turtle.getSize()+"\n";
			cmd+="setxy ( max-pxcor - "+eps+" ) "+turtle.getY()+"\n";
			cmd+="set heading "+ turtle.getOrientation()+ "\n";
			cmd+=" ] \n";
		}
		this.NetlogoCmd(cmd);
	}
	
	//process turtles that comes from the left
	protected void inLeft(float eps,ArrayList<NetlogoTurtle>tl)
	{
		String cmd="";
		for(int i=0;i<tl.size();i++)
		{
			NetlogoTurtle turtle=tl.get(i);
			cmd+="create-turtles 1 [ ";
			cmd+="set size "+turtle.getSize()+"\n";
			cmd+="setxy ( min-pxcor + "+eps+" ) "+turtle.getY()+"\n";
			cmd+="set heading "+ turtle.getOrientation()+ "\n";
			cmd+=" ] \n";
		}
		this.NetlogoCmd(cmd);
	}
	
	//process turtles that comes from the top
	protected void inUp(float eps,ArrayList<NetlogoTurtle>tl)
	{
		String cmd="";
		for(int i=0;i<tl.size();i++)
		{
			NetlogoTurtle turtle=tl.get(i);
			cmd+="create-turtles 1 [ ";
			cmd+="set size "+turtle.getSize()+"\n";
			cmd+="setxy "+turtle.getX()+" ( max-pycor - "+eps +" )\n";
			cmd+="set heading "+ turtle.getOrientation()+ "\n";
			cmd+=" ] \n";
		}
		this.NetlogoCmd(cmd);
	}
	
	//process turtles that comes from the bottom
	protected void inDown(float eps,ArrayList<NetlogoTurtle>tl)
	{
		String cmd="";
		for(int i=0;i<tl.size();i++)
		{
			NetlogoTurtle turtle=tl.get(i);
			cmd+="create-turtles 1 [ ";
			cmd+="set size "+turtle.getSize()+"\n";
			cmd+="setxy "+turtle.getX()+" ( min-pycor + "+eps +")\n";
			cmd+="set heading "+ turtle.getOrientation()+ "\n";
			cmd+=" ] \n";
		}
		this.NetlogoCmd(cmd);
	}

	
	protected void removeTurtles(ArrayList<Long> indexes)
	{
		for(int i=0;i<indexes.size();i++)
		{
			Long index =indexes.get(i);
			this.NetlogoCmd("ask turtle "+index+" [ die ] ");
			
		}
	}


}
