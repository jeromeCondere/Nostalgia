package utils.Netlogo;

import java.awt.Color;

import org.nlogo.agent.Turtle;

public class NetlogoTurtle {
protected String breed="default";
protected String shape="default";
public String getShape() {
	return shape;
}
public void setShape(String shape) {
	this.shape = shape;
}
protected float x=0.0f;
protected float y=0.0f;
protected float z=0.0f;
protected Color color=new Color(255,255,255);
protected float orientation=0.0f;
protected float size=1f;

public NetlogoTurtle()
{
	
}
public NetlogoTurtle(Turtle t)
{
	x=(float) t.xcor();
	y=(float) t.ycor();
	orientation= (float) t.heading();
	size=(float) t.size();
	shape=t.shape();
	color=org.nlogo.api.Color.getColor(t.color());
	if(!t.getBreed().printName().equals("TURTLES"))
	breed=t.getBreed().printName();
}
public NetlogoTurtle(float x,float y,Color c)//2D basic constructor
{
	
	this.x=x;
	this.y=y;
	this.color=c;
}
public NetlogoTurtle(double x,double y,Color c)//2D basic constructor
{
	
	this.x=(float) x;
	this.y=(float) y;
	this.color=c;
}
public String getBreed() {
	return breed;
}
public void setBreed(String breed) {
	this.breed = breed;
}
public float getX() {
	return x;
}
public void setX(float x) {
	this.x = x;
}
public float getY() {
	return y;
}
public void setY(float y) {
	this.y = y;
}
public float getZ() {
	return z;
}
public void setZ(float z) {
	this.z = z;
}
public Color getColor() {
	return color;
}
public void setColor(Color color) {
	this.color = color;
}
public void setColor(int r,int g, int b) {
	this.color=new Color(r,g,b);
}

public float getOrientation() {
	return orientation;
}
public void setOrientation(float orientation) {
	this.orientation = orientation;
}
public float getSize() {
	return size;
}
public void setSize(float size) {
	this.size = size;
}

}
