package utils.Neural;

import jade.lang.acl.ACLMessage;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import utils.box.Inbox;
import utils.box.NeuralInbox;
import utils.box.Outbox;
import utils.stimuli.SingleStimuli;
import utils.stimuli.StimuliPool;

public class NeuralJson 
{
	public static Outbox getOutboxJson(JSONObject contentJson)//*dup
	{
		JSONObject ports=(JSONObject) contentJson.get("ports");
		JSONObject outboxJson=(JSONObject) ports.get("out");
		String name=(String) outboxJson.get("name");
		String mailboxname=(String) outboxJson.get("mailboxName");
		Outbox outbox=new Outbox(name,mailboxname);
		return outbox;
	}
	public static JSONObject getSingleStimuliJson(String content)
	{
		try{
			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(content);
			JSONObject stimuliObject= (JSONObject) jsonObject.get("single stimuli");
			return stimuliObject;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public static JSONObject NeuralInboxToJson(Inbox inbox)
	{
		if(inbox==null)
			return null;
		
		JSONObject InboxJson= new JSONObject();
		InboxJson.put("name", inbox.getName());
		InboxJson.put("mailboxName", inbox.getMailboxName());
		InboxJson.put("user", inbox.getOwnerName());
		InboxJson.put("weight", ((NeuralInbox)inbox).getWeight());
		
		return InboxJson;
	}
	public static JSONArray StimuliToJson(StimuliPool stimuliPool)
	{
		
		JSONArray stimuliArrayJson=new JSONArray();
		ArrayList<SingleStimuli> pool=stimuliPool.getPool();
		for(SingleStimuli single:pool)
		{
			JSONObject stimuliObject=getSingleStimuliJson(single.getMessage().getContent());
			JSONObject object =new JSONObject();
			JSONObject NeuralInboxobject=NeuralInboxToJson(single.getInbox());
			object.put("single stimuli", stimuliObject);
			object.put("in", NeuralInboxobject);
			stimuliArrayJson.add(object);
		}
		return  stimuliArrayJson;
	}
	public static String setMessageJson()
	{
		return null;
	}
}
