package model_runner.netlogo;
import model_runner.graphic.GraphicModelRunner;
import model_runner.netlogo.NetlogoRunner;

import org.nlogo.lite.InterfaceComponent;
import org.nlogo.api.CompilerException;


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
	
}




