package utils.box;

public class Box {
	protected String name;
	protected String mailboxName;
	protected String ownername;
	public String getName()
	{
		return name;
	}
	public String getMailboxName()
	{
		return mailboxName;
	}
	public Box(String name,String mailboxName)
	{
		this.mailboxName=mailboxName;
		this.name=name;
		this.ownername=null;
	}
	public Box(String name)
	{
		this.name=name;
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
