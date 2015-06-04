package model_runner;

import org.nlogo.api.CompilerException;
import org.nlogo.lite.InterfaceComponent;

public class NetlogoRunner extends GraphicModelRunner {

	protected javax.swing.JFrame frame = new javax.swing.JFrame();
	protected InterfaceComponent comp; 
	protected int maxTicks=4000;
	protected int tick=0;
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
	public void run() {
		// TODO Auto-generated method stub
		try{
		 java.awt.EventQueue.invokeAndWait(
				    new Runnable() {
				      public void run() {
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

}




