package utils.Netlogo;

import java.awt.Color;

public class NetlogoTurtle {
protected String breed="default";
protected float x=0.0f;
protected float y=0.0f;
protected float z=0.0f;
protected Color color=new Color(255,255,255);
protected float orientation=0.0f;
public NetlogoTurtle()
{
	
}
public NetlogoTurtle(float x,float y,Color c)//2D basic constructor
{
	
	this.x=x;
	this.y=y;
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

}
