package model_runner;

public class GraphicModelRunner extends Model implements GraphicRunner {

	protected int width=400;
	protected int heigth=400;
	protected float x=0;
	protected float y=0;
	protected boolean redim=true;
	protected String name="Netlogo";
	public GraphicModelRunner(String path)
	{
		super(path);
		
	}
	public GraphicModelRunner(String path,String argv[])
	{
		super(path,argv);
		
	}
	public GraphicModelRunner()
	{
	super();	
	}
	public String getPath()
	{
		return path;
	}
	@Override
	public void setWidth(int width) {
		// TODO Auto-generated method stub
      this.width=width;
	}

	@Override
	public void setHeigth(int heigth) {
		// TODO Auto-generated method stub
        this.heigth=heigth;
	}

	@Override
	public void setPosX(float X) {
		// TODO Auto-generated method stub
      this.x=X;
	}

	@Override
	public void setPosY(float Y) {
		// TODO Auto-generated method stub
		this.y=Y;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return this.width;
	}

	@Override
	public int getHeigth() {
		// TODO Auto-generated method stub
		return this.heigth;
	}

	@Override
	public void setRedim(boolean redim) {
		// TODO Auto-generated method stub
        this.redim=redim;
	}
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name=name;
	}


}
