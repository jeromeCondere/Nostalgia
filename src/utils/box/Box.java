package utils.box;

public class Box {
	protected String name;
	protected String mailboxName;
	protected String ownername;
	public String getName()
	{
		return name;
	}
	public String getMaiboxName()
	{
		return mailboxName;
	}
	public Box(String name,String mailboxName)
	{
		this.mailboxName=mailboxName;
		this.name=name;
		this.ownername=null;
	}
	public String getOwnerName()
	{
		return this.ownername;
	}
	public void setOwnerName(String ownername)
	{
		this.ownername=ownername;
	}
	
}
