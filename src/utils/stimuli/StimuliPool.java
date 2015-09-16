package utils.stimuli;

import jade.lang.acl.ACLMessage;

import java.util.ArrayList;

import utils.box.Inbox;

public class StimuliPool {
private ArrayList<SingleStimuli> pool=null;
public StimuliPool()
{
	pool=new ArrayList<SingleStimuli>();
}
public StimuliPool(ArrayList<Inbox>inboxes)
{
	if(inboxes==null || inboxes.size()==0)
		return;
	for(int i=0;i<inboxes.size();i++)
	{
		Inbox inbox=inboxes.get(i);
		pool.add(new SingleStimuli(inbox,null));
	}
}
public ArrayList<SingleStimuli> getPool()
{
	return pool;
}
public ArrayList<ACLMessage> getMessages()
{
	ArrayList<ACLMessage> messages=new ArrayList<ACLMessage>();
	for(SingleStimuli s: pool)
	{
		messages.add(s.getMessage());
	}
	return messages;
}
public boolean isFilled()
{
	for(int i=0;i<pool.size();i++)
	{
		SingleStimuli stimuli=pool.get(i);
		if(stimuli.isFilled()==false)
			return false;	
	}
	return true;
}
public ArrayList<Inbox> getInboxesNotFilled()
{
	if(isFilled())
		return null;
	
	ArrayList<Inbox> result=new ArrayList<Inbox>();
	for(SingleStimuli s:pool)
	{
		if(!s.isFilled())
			result.add(s.getInbox());
	}
	return result;
}
public void setStimuliMessage(Inbox inbox,ACLMessage message)
{
	int index=pool.indexOf(inbox);
	if(index!=-1)
	{
		pool.get(index).setStimuli(message);
	}
}
}
