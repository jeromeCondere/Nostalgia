package utils;

import java.util.ArrayList;

public class SubBox {

	private String name;
	private ArrayList<String>owners;
	public SubBox(String name,ArrayList<String>owners)
	{
		this.owners=owners;
		this.name=name;
	}
	public SubBox(String name)
	{
		this.owners=new ArrayList<String>();
		this.name=name;
	}
	public SubBox(String name,String owner)
	{
		this(name);
		owners.add(owner);
	}
	public void addOwner(String owner)
	{
		if(owner==null)
			return;
		if(!owner.contains(owner))//we check if the owner doesn't exist
		{
			this.addOwner(owner);
		}
	}
	public void removeOwner(String owner)
	{
	  owners.remove(owner);
	}
	public String getName()
	{
		return name;
	}
	public ArrayList<String> getOwners()
	{
		return owners;
	}
	public boolean equals(Object obj) {
	    // Vérification de l'égalité des références
	    if (obj==this) 
	    {
	        return true;
	    }
	 
	    // Vérification du type du paramètre
	    if (obj instanceof SubBox) {
	 
	        // Vérification des valeurs des attributs
	    	SubBox other = (SubBox) obj;
	 
	        return  other.name.equals(name) && other.owners.equals(owners);
	    }
	 
	    return false;
	}
	
	
}
