package model_runner;
import model_runner.NetlogoRunner;

import org.nlogo.lite.InterfaceComponent;
import org.nlogo.api.CompilerException;


public class NetlogoRunner extends GraphicModelRunner {

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
		comp= new InterfaceComponent(frame);
	}
	public NetlogoRunner(String path,String argv[])
	{
		super(path,argv);
		comp= new InterfaceComponent(frame);
	}
	public NetlogoRunner()
	{
	super();
	comp= new InterfaceComponent(frame);
	}
	
	public NetlogoRunner(String path,int width,int heigth)
	{
		this(path);
		this.setHeigth(heigth);
		this.setWidth(width);
		
		
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

	
	public void endModel()
	{
		    if(this.closeAtEnd==true)
			frame.dispose();
	}
}




