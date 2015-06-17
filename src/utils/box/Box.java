package utils.box;

public class Box {
	protected String name;
	protected String mailboxName;
	protected String username;
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
	}
}
