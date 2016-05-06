package utils.communication.message;

import java.util.Iterator;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonUtils {
	public JsonUtils()
	{
		
	}
	
	/*
	 * we use this method whenever we have to encapsulate a message in another
	 * message with the JSON language
	 */
	public static JSONObject TransformMessageToJson(ACLMessage message)
	{
		if(message ==null)
			return null;
		
		String content = message.getContent();
		String conversation_id=message.getConversationId();
		String in_reply_to=message.getInReplyTo();
		String language=message.getLanguage();
		String encoding=message.getEncoding();
		String ontology=message.getOntology();
		String protocol=message.getProtocol();
		AID sender_aid=message.getSender();
		String sender=null;
		if(sender_aid!=null)
		  sender=sender_aid.getName();
		
		int performative=message.getPerformative();
		Iterator receiver_iterator=message.getAllReceiver();
		JSONArray receiversJSON=new JSONArray();
		while(receiver_iterator.hasNext())
		{
			AID aid = (AID) receiver_iterator.next();
			JSONObject receiver_json =new JSONObject();
			receiver_json.put("name", aid.getName());
			receiversJSON.add(receiver_json);
		}
		JSONObject jsonMessage = new JSONObject();
		jsonMessage.put("content", content);
		jsonMessage.put("conversation_id", conversation_id);
		jsonMessage.put("ontology", ontology);
		jsonMessage.put("in_reply_to", in_reply_to);
		jsonMessage.put("language", language);
		jsonMessage.put("encoding", encoding);
		jsonMessage.put("protocol", protocol);
		jsonMessage.put("sender", sender);
		jsonMessage.put("performative", performative);
		jsonMessage.put("receivers",receiversJSON);
		
		return jsonMessage;
	}
	
	public static ACLMessage transformJsonToMessage(JSONObject jsonMessage)
	{
		if(jsonMessage==null)
		{
			return null;
		}
		String content = (String) jsonMessage.get("content");
		String conversation_id=(String) jsonMessage.get("conversation_id");
		String in_reply_to= (String) jsonMessage.get("in_reply_to");
		String language=(String) jsonMessage.get("in_reply_to");
		String encoding=(String) jsonMessage.get("encoding");
		String ontology=(String) jsonMessage.get("ontology");
		String protocol=(String) jsonMessage.get("protocol");
		
		String sender_str = (String) jsonMessage.get("sender");
		AID sender_aid=new AID (sender_str,true);
		
		ACLMessage message = null;
		if(((Number) jsonMessage.get("performative"))!=null)
			{
			int performative = ((Number) jsonMessage.get("performative")).intValue();
			message = new ACLMessage(performative);
			}
		else
		{
			message = new ACLMessage();
		}
		message.setContent(content);
		message.setConversationId(conversation_id);
		message.setInReplyTo(in_reply_to);
		message.setLanguage(language);
		message.setEncoding(encoding);
		message.setOntology(ontology);
		message.setProtocol(protocol);
		message.setSender(sender_aid);
		
		
		return message;
	}
	
}
