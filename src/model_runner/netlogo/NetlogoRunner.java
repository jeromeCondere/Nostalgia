package model_runner.netlogo;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

import model_runner.graphic.GraphicModelRunner;
import model_runner.netlogo.NetlogoRunner;

import org.nlogo.lite.InterfaceComponent;
import org.nlogo.api.CompilerException;

import utils.Netlogo.NetlogoTurtle;
import utils.box.Inbox;
import utils.box.Outbox;


public class NetlogoRunner extends GraphicModelRunner implements Cloneable  {

	protected javax.swing.JFrame frame = new javax.swing.JFrame();
	protected InterfaceComponent comp; 
	protected int maxTicks=2000;//default maxTicks
	//protected int tick=0;
	protected boolean closeAtEnd=false;
	
	
	/**
	 * @param args
	 */
	public NetlogoRunner(String path)
	{
		super(path);
		
	}
	public NetlogoRunner(String path,String argv[])
	{
		super(path,argv);
		
	}
	public NetlogoRunner()
	{
	super();
	
	}
	
	public NetlogoRunner(String path,int width,int heigth)
	{
		this(path);
		this.setHeigth(heigth);
		this.setWidth(width);
		
		
	}
	public Object clone()
	{
		NetlogoRunner clonned=new NetlogoRunner(this.getPath(),this.getWidth(),this.getHeigth());
		clonned.setMaxTicks(this.maxTicks);
		clonned.setName(this.name);
		clonned.setRedim(this.redim);
		clonned.setPosX(this.x);
		clonned.setPosY(this.y);
		clonned.setRedim(this.redim);
		return clonned;
		
	}
	public void NetlogoCmd(String cmd)
	{
		try {
			comp.command (cmd);
		}
		catch (CompilerException e) {
			e.printStackTrace ();
		}
	}
	public int getMaxTicks()
	{
		return this.maxTicks;
	}
	public void setMaxTicks(int maxTicks)
	{
		if(maxTicks>0)
		  this.maxTicks=maxTicks;
	}
	
	public void go()
	{
		NetlogoCmd("go");
		
	}
	public void setup()
	{
		NetlogoCmd("setup");
	}
	public Object report(String source)
	{
		try {
			return comp.report(source);
		} catch (CompilerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public void run() {
		// TODO Auto-generated method stub
		frame = new javax.swing.JFrame();
		comp= new InterfaceComponent(frame);
		try{
		 java.awt.EventQueue.invokeAndWait(
				    new Runnable() {
				      public void run() {
				    	frame.setTitle(name);
				        frame.setSize(width,heigth);
				        frame.add(comp);
				        frame.setVisible(true);
				        if(redim==false)//set redim
				        {
				        	frame.setResizable(false);
				        }
				        try {
				          comp.open(path);
				        }
				        catch(Exception ex) {
				          ex.printStackTrace();
				        }}});
				      
				    }
				    catch(Exception ex) 
				    {
				      ex.printStackTrace();
				    }
		
				  }

	//public getTurtles
	
	public void setCloseAtEnd(boolean closeAtEnd)
	{
		this.closeAtEnd=closeAtEnd;
	}
	public boolean getCloseAtEnd()
	{
		return this.closeAtEnd;
	}
	public void endModel()
	{
		    if(this.closeAtEnd==true)
			frame.dispose();
	}
	public static ArrayList<NetlogoTurtle>getTurtles(String content) throws Exception
	{
		/*
		 * get all turtles from message coded in json
		 * the message structure is
		 * {
		 *   "turtles":[...],
		 *   "meta-data":[..],//data that does not concern turtles
		 *   "comments":"..."
		 * 
		 * }
		 */
		
		try{
			ArrayList<NetlogoTurtle> turtles=new ArrayList<NetlogoTurtle>();
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(content);
			JSONArray turtlesJson=(JSONArray) jsonObject.get("turtles");
			for(int i=0;i<turtlesJson.size();i++)
			{
				JSONObject turtlesJson_i=(JSONObject) turtlesJson.get(i);
				NetlogoTurtle turtle_i=new NetlogoTurtle();
				float xpos=((Number)turtlesJson_i.get("x")).floatValue();
				float ypos=((Number)turtlesJson_i.get("y")).floatValue();
				turtle_i.setX(xpos);
				turtle_i.setY(ypos);
				float orientation=((Number)turtlesJson_i.get("orientation")).floatValue();
				turtle_i.setOrientation(orientation);
				if(turtlesJson_i.containsKey("breed"))
				{
					turtle_i.setBreed((String)turtlesJson_i.get("breed"));
				}
				if(turtlesJson_i.containsKey("z"))
				{
					float zpos=((Number)turtlesJson_i.get("z")).floatValue();
					turtle_i.setZ(zpos);
				}
				if(turtlesJson_i.containsKey("color"))
				{
					JSONObject colorJson=(JSONObject) turtlesJson_i.get("color");
					int r=((Number) colorJson.get("r")).intValue();
					int g=((Number) colorJson.get("g")).intValue();
					int b=((Number) colorJson.get("b")).intValue();
					turtle_i.setColor(r, g, b);
					
					
				}
				if(turtlesJson_i.containsKey("shape"))
				{
					turtle_i.setBreed((String)turtlesJson_i.get("shape"));
				}
					turtles.add(turtle_i);
			}
			
			return turtles;
		}
		
		/*catch(JSONException j)
		{
			
		}
		*/
		catch(Exception e)
		{
			throw e;
			
		}
		
	}
	public static JSONObject TurtleToJson( NetlogoTurtle turtle)
	{
		if(turtle==null)
		return null;
		
		JSONObject turtleJson=new JSONObject();
		turtleJson.put("x", turtle.getX());
		turtleJson.put("y", turtle.getY());
		turtleJson.put("z", turtle.getZ());
		
		int r=turtle.getColor().getRed();
		int g=turtle.getColor().getGreen();
		int b=turtle.getColor().getBlue();
		JSONObject color=new JSONObject();
		color.put("r", r);
		color.put("g", g);
		color.put("b", b);
		turtleJson.put("color",color);
		turtleJson.put("breed",turtle.getBreed());
		turtleJson.put("shape",turtle.getShape());
		turtleJson.put("orientation", turtle.getOrientation());
		
		return turtleJson;
	}
	public static JSONObject TurtleToJsonCompressed( NetlogoTurtle turtle)
	{
		if(turtle==null)
		return null;
		
		JSONObject turtleJson=new JSONObject();
		turtleJson.put("x", turtle.getX());
		turtleJson.put("y", turtle.getY());
		if(turtle.getZ()!=0)
		turtleJson.put("z", turtle.getZ());
		
		int r=turtle.getColor().getRed();
		int g=turtle.getColor().getGreen();
		int b=turtle.getColor().getBlue();
		JSONObject color=new JSONObject();
		if(r!=255 && g!=255 && b!=255)
		{
		color.put("r", r);
		color.put("g", g);
		color.put("b", b);
		turtleJson.put("color",color);
		}
		if(!turtle.getBreed().equals("default"))
		turtleJson.put("breed",turtle.getBreed());
		
		if(!turtle.getShape().equals("default"))
		turtleJson.put("shape",turtle.getShape());
		
		turtleJson.put("orientation", turtle.getOrientation());
		
		return turtleJson;
	}
	public static JSONArray TurtlesToJson( ArrayList<NetlogoTurtle> turtles)
	{
		if(turtles==null || turtles.size()==0)
		{
			return null;
		}
		JSONArray turtlesJson=new JSONArray();
		for(int i=0;i<turtles.size();i++)
		{
			JSONObject turtleJson=TurtleToJson(turtles.get(i));
			turtlesJson.add(turtleJson);
		}
		
		return turtlesJson;
	}
	public static JSONArray TurtlesToJsonCompressed( ArrayList<NetlogoTurtle> turtles)
	{
		if(turtles==null || turtles.size()==0)
		{
			return new JSONArray(); //empty list
		}
		JSONArray turtlesJson=new JSONArray();
		for(int i=0;i<turtles.size();i++)
		{
			JSONObject turtleJson=TurtleToJsonCompressed(turtles.get(i));
			turtlesJson.add(turtleJson);
		}
		
		return turtlesJson;
	}
	public static JSONObject InboxToJson(Inbox inbox)
	{
		if(inbox==null)
			return null;
		
		JSONObject InboxJson= new JSONObject();
		InboxJson.put("name", inbox.getName());
		InboxJson.put("mailboxName", inbox.getMailboxName());
		InboxJson.put("user", inbox.getOwnerName());
		
		return InboxJson;
	}
	public static JSONArray InboxesToJson(ArrayList<Inbox>inboxes)
	{
		if(inboxes==null)
			return null;
		JSONArray inboxesJson=new JSONArray();
		for(int i=0;i<inboxes.size();i++)
		{
			Inbox inbox=inboxes.get(i);
			inboxesJson.add(InboxToJson(inbox));
		}
		return inboxesJson;
	}
	public static JSONObject OutboxToJson(Outbox outbox)
	{
		if(outbox==null)
			return null;
		
		JSONObject outboxJson= new JSONObject();
		outboxJson.put("name", outbox.getName());
		outboxJson.put("mailboxName", outbox.getMailboxName());
		//outboxJson.put("user",outbox.getOwnerName());
		
		return outboxJson;
	}
    protected String setNetlogoMessage(ArrayList<NetlogoTurtle> turtles,JSONObject metadata,String comments)
    {
    	JSONArray turtlesJson=TurtlesToJson(turtles);
    	JSONObject messageJson= new JSONObject();
    	
    	messageJson.put("turtles",turtlesJson);
    	messageJson.put("metadata",metadata);
    	messageJson.put("comments",comments);
    	return messageJson.toString();
    }

}




