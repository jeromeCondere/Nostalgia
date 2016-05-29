package agents.forward;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONObject;

import utils.communication.message.ForwardStrategy;
import utils.communication.message.JsonUtils;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import agents.NosAgent;


public class ForwarderAgent extends NosAgent {

	//contains conv_id and the message to forward in case there is an agreement
	private HashMap<String,ACLMessage> messagesToForward=new HashMap<String,ACLMessage>();
	private ArrayList<String> conversation_to_process = new ArrayList<String>();
	private ForwardPolicy forwardPolicy;
	
	public void forwardStrategy(ACLMessage message)
	{
		String ontology = message.getOntology();
		String content = message.getContent();
		String protocol = message.getProtocol();
		
		JSONObject objJSON = new JSONObject();
		JSONObject messageJSON = new JSONObject();
		messageJSON.put("protocol", protocol);
		messageJSON.put("ontology", ontology);
		messageJSON.put("content", content);
		
		JSONObject forwardJSON = new JSONObject();
		forwardJSON.put("strategy", "classic");
		forwardJSON.put("depth", new Integer(2));
		objJSON.put("message", messageJSON);
	}
	/*
	 * there are two way to request
	 * 1- ask for permission to request and then depending
	 *    on the answer, send the message or not
	 *    
	 * 2- send the message immediately with a forwarding structure
	 *    without waiting for a answer.
	 *   
	 *   the first way take more time
	 *   
	 * 
	 */
	
	//request
	public void forwardRequest(AID aid, ForwardStrategy strategy,ACLMessage messageToForward)
	{
		//mode 2
		//open conversation for forward request
		//save the message in the memory
		
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		SecureRandom random = new SecureRandom();
		String str_random = new BigInteger(130, random).toString(32);
		String conversation_id = "conversation_forward_"+str_random;
		message.setConversationId(conversation_id);
	    JSONObject object = new JSONObject();
	    object.put("type", "forward");
	    object.put("strategy", strategy.toJson());
		message.setContent(object.toJSONString());
		
		messagesToForward.put(conversation_id, messageToForward);
		send(message);
	}
	public void treatAnswerRequest(ACLMessage answer)
	{
		String conversation_id = answer.getConversationId();
		//if there is an agreement on the conversation we send the message with the
		//informations to forward
		if(answer.getPerformative() == ACLMessage.AGREE && messagesToForward.containsKey(conversation_id))
		{
			ACLMessage message = answer.createReply();
			message.setPerformative(ACLMessage.PROPAGATE);//inform for forwarding
			ACLMessage messageToSend = messagesToForward.get(conversation_id);
			JSONObject object =new JSONObject();
			JSONObject messageToSendJson =JsonUtils.TransformMessageToJson(messageToSend);
			object.put("type", "forward");
		    object.put("message",messageToSendJson.toString());
		    message.setContent(object.toString());
		    
		    send(message);
		    
		}
	}
	public void ProcessRequest(ACLMessage request)
	{
		if(forwardPolicy.matchPolicy(request))
		{
			//if the request contains the message to forward already we forward the message
			//according to the forwardStrategy
			
		}
	}
	
	
}
