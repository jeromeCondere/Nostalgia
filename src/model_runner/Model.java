package model_runner;
import org.nlogo.app.App;


public abstract class Model implements Runnable {

	protected String argv[];
	protected String path;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public Model(String path)
	{
		this.path=path;
		this.argv=null;
	}
	public Model(String path,String argv[])
	{
		this.argv=argv;
		this.path=path;
	}
	public Model()
	{
		
	}
	public String getPath()
	{
		return path;
	}

}
