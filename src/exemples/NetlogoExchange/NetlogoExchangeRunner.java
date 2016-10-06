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
		// TODO Auto-generated method stub
		Pattern p = Pattern.compile("^in.*");
		Matcher m = p.matcher(inbox);
		//if the name of the inbox starts with in
		if(m.matches())
		{
			
			try {
				float eps=0.02f;
				ArrayList<NetlogoTurtle> tl;
				tl = NetlogoJson.getTurtles(message.getContent());
				String cmd="";
				for(int i=0;i<tl.size();i++)
				{
					NetlogoTurtle turtle=tl.get(i);
					cmd+="create-turtles 1 [ ";
					cmd+="set size 3 ";
					cmd+="setxy "+(turtle.getX()+eps)+" random-pycor ";
					cmd+=" ] \n";
				}
				this.NetlogoCmd(cmd);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return;
			}
			
		}

	}

	@Override
	public String getOutput(String outbox, String user) {
		// TODO Auto-generated method stub
		if(outbox.equals("out1"))
		{
			ArrayList <NetlogoTurtle>turtles = RightBorder(15);
			
			if(!turtles.isEmpty())
			return NetlogoJson.setNetlogoMessage(turtles, null, "eh");
		//return "yolo1";
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
		if(turtles.size() > 0)
		{
			System.out.println("zidnzioe");
		}

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
		if(turtles.size() > 0)
		{
			System.out.println("zidnzioe");
		}
		else {
			System.out.println("noooo");
		}
		return turtles;
		
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
