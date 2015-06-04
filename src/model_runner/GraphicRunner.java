package model_runner;

public interface GraphicRunner {

	public void setWidth(int width);
	public void setHeigth(int heigth);
	public void setPosX(float X);//up_left_x
	public void setPosY(float Y);//up_left_y
	public int getWidth();
	public int getHeigth();
	public void setRedim(boolean redim);
	public void setName(String name);
}
