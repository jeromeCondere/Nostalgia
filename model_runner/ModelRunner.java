package model_runner;
import org.nlogo.app.App;


public abstract class ModelRunner implements Runnable {

	protected String argv[];
	protected String path;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public ModelRunner(String path)
	{
		this.path=path;
		this.argv=null;
	}
	public ModelRunner(String path,String argv[])
	{
		this.argv=argv;
		this.path=path;
	}
	public ModelRunner()
	{
		
	}
	public String getPath()
	{
		return path;
	}

}
