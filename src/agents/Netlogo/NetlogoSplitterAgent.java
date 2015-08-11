package agents.Netlogo;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.Iterator;

import model_runner.netlogo.NetlogoRunner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import utils.communication.message.ACLNetlogoMessage;

import agents.SplitterAgent;

public class NetlogoSplitterAgent extends SplitterAgent {

	protected int turtleLimit=0;
	public NetlogoSplitterAgent(int turtleLimit)
	{
		this.turtleLimit=turtleLimit;
	}
	@Override
	public ArrayList<ACLMessage> split(ACLMessage message) {
		// TODO Auto-generated method stub
		if(message instanceof ACLNetlogoMessage && message.getOntology().equals("inter-Netlogo-Communicate"))
		{
		if(turtleLimit<=0)
		return null;
		ArrayList<ACLMessage> messages= new ArrayList<ACLMessage>();
		String content=message.getContent();
		JSONParser jsonParser = new JSONParser();
		try {
			JSONObject jsonObject = (JSONObject) jsonParser.parse(content);
			JSONArray turtlesJson=(JSONArray) jsonObject.get("turtles");
			if(turtleLimit>turtlesJson.size())
			{
				return null;
			}
			int indexturtlesSplit=0;
			ArrayList<JSONArray> turtlesSplit=new ArrayList<JSONArray>();
			for(int i=0;i<turtlesJson.size();i+=turtleLimit)
			{
				JSONArray turtles=new JSONArray();
				for(int j=i;j<turtlesJson.size() && j<i+turtleLimit;j++)
				{
					JSONObject turtleJson=(JSONObject) turtlesJson.get(i);
					turtles.add(turtleJson);
				}
				turtlesSplit.add(turtles);
			}
			for(int i=0;i<turtlesSplit.size();i++)
			{
				ACLMessage splitMessage=new ACLMessage();
				splitMessage.setPerformative(ACLMessage.INFORM);
				Iterator<AID> it=message.getAllReceiver();
				while(it.hasNext())
				splitMessage.addReceiver(it.next());
				JSONObject messageSplitJson= new JSONObject();
		    	messageSplitJson.put("turtles",turtlesSplit.get(i));
		    	
		    	messageSplitJson.put("metadata",null);
		    	messageSplitJson.put("comments",null);
		    	
		    	
				String contentSplit=messageSplitJson.toString();
				messages.add(splitMessage);
			}
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		}
		return null;
	}

}
