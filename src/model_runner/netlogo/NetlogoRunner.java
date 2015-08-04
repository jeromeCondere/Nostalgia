package model_runner.netlogo;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

import model_runner.graphic.GraphicModelRunner;
import model_runner.netlogo.NetlogoRunner;

import org.nlogo.lite.InterfaceComponent;
import org.nlogo.api.CompilerException;

import utils.Netlogo.NetlogoTurtle;


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
	public static ArrayList<NetlogoTurtle>getTurtles(String content)
	{
		/*
		 * get all turtles from message coded in json
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
				
				turtle_i.setX((float)turtlesJson_i.get("x"));
				turtle_i.setY((float)turtlesJson_i.get("y"));
				turtle_i.setOrientation((float)turtlesJson_i.get("orientation"));
				if(turtlesJson_i.containsKey("breed"))
				{
					turtle_i.setBreed((String)turtlesJson_i.get("breed"));
				}
				if(turtlesJson_i.containsKey("z"))
				{
					turtle_i.setZ((float)turtlesJson_i.get("z"));
				}
				if(turtlesJson_i.containsKey("color"))
				{
					JSONObject colorJson=(JSONObject) turtlesJson_i.get("color");
					int r=(int) colorJson.get("r");
					int g=(int) colorJson.get("g");
					int b=(int) colorJson.get("b");
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
			e.printStackTrace();
			
		}
		return null;
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
		turtleJson.put("shape",turtle.getBreed());
		turtleJson.put("orientation", turtle.getOrientation());
		
		return turtleJson;
	}
	
}




