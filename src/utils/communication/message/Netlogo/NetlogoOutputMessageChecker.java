package utils.communication.message.Netlogo;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import jade.lang.acl.ACLMessage;
import NosException.CheckMessageException;
import utils.Netlogo.NetlogoJson;
import utils.box.Inbox;
import utils.box.Outbox;
import utils.communication.message.MessageChecker;

public class NetlogoOutputMessageChecker implements MessageChecker {

	@Override
	public Object check(ACLMessage message,Object [] params) throws CheckMessageException {
		// TODO Auto-generated method stub
		
		Outbox outbox=(Outbox) params[0];
		ArrayList<Inbox>inboxes=(ArrayList<Inbox>) params[1];
		String content=(String) params[2];
		JSONParser jsonParser = (JSONParser) params[3];
		try{
			 JSONObject jsonObject = (JSONObject) jsonParser.parse(content);
			 JSONObject ports=new JSONObject();
			 ports.put("out", NetlogoJson.OutboxToJson(outbox));
			 ports.put("in", NetlogoJson.InboxesToJson(inboxes));
			 jsonObject.put("ports", ports);					 
			 message.setContent(jsonObject.toString());
			
		 }
		 catch( Exception e)
		 {
			 //the result is not regular Json
			 JSONObject contentJson= new JSONObject();
			 JSONObject metadataJson=new JSONObject();
			 metadataJson.put("log", "original object was not json");
			 metadataJson.put("original content",content);
			 contentJson.put("metadata", metadataJson);
			 JSONObject ports=new JSONObject();
			 ports.put("out", NetlogoJson.OutboxToJson(outbox));
			 ports.put("in", NetlogoJson.InboxesToJson(inboxes));
			 contentJson.put("ports", ports);
			 message.setContent(contentJson.toString());
		 }
		 return null;
	}

}
