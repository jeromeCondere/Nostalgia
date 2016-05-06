package UnitTest;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

import org.json.simple.JSONObject;
import org.junit.Test;

import utils.communication.message.JsonUtils;
import static org.junit.Assert.*;

public class JsonUtilsTest {
	@Test
	public void TestCreateJsonFromMessage()
	{
		String content = "content";
		String conversation_id="c_id";
		String in_reply_to= "irl";
		String language="C";
		String encoding="encoding";
		String ontology="e";
		String protocol="protocol";
		
		ACLMessage message =new ACLMessage(ACLMessage.CANCEL);
		message.setContent(content);
		message.setConversationId(conversation_id);
		message.setInReplyTo(in_reply_to);
		message.setLanguage(language);
		message.setEncoding(encoding);
		message.setOntology(ontology);
		message.setProtocol(protocol);
		
		//test with all fields
		JSONObject jsonMessage = JsonUtils.TransformMessageToJson(message);
		assertEquals("error on content",content,jsonMessage.get("content"));
		assertEquals("error on conversation_id",conversation_id,jsonMessage.get("conversation_id"));
		assertEquals("error on in_reply_to",in_reply_to,jsonMessage.get("in_reply_to"));
		assertEquals("error on language",language,jsonMessage.get("language"));
		assertEquals("error on encoding",encoding,jsonMessage.get("encoding"));
		assertEquals("error on ontology",ontology,jsonMessage.get("ontology"));
		assertEquals("error on protocol",protocol,jsonMessage.get("protocol"));
		int performativeJson = ((Number) jsonMessage.get("performative")).intValue();
		//ACLMessage default = NOT UNDERSTOOD
		assertEquals("error on performative",ACLMessage.CANCEL,performativeJson);
		
		//test with missing fields
		/*
		 * if you don't specify field it return null
		 */
		ACLMessage message2 =new ACLMessage(ACLMessage.CONFIRM);
		message2.setContent(content);
		message2.setConversationId(conversation_id);
		message2.setInReplyTo(in_reply_to);
		message2.setLanguage(language);
		message2.setEncoding(encoding);
		
		JSONObject jsonMessage2 = JsonUtils.TransformMessageToJson(message2);
		assertEquals("error on content",content,jsonMessage2.get("content"));
		assertEquals("error on conversation_id",conversation_id,jsonMessage2.get("conversation_id"));
		assertEquals("error on in_reply_to",in_reply_to,jsonMessage2.get("in_reply_to"));
		assertEquals("error on language",language,jsonMessage2.get("language"));
		assertEquals("error on encoding",encoding,jsonMessage2.get("encoding"));
		assertEquals("error on ontology",null,jsonMessage2.get("ontology"));
		assertEquals("error on protocol",null,jsonMessage2.get("protocol"));

		int performativeJson2 = ((Number) jsonMessage2.get("performative")).intValue();
		assertEquals("error on performative",ACLMessage.CONFIRM,performativeJson2);
		
		
	}
	@Test
	public void TestCreateMessageFromJson()
	{
		String content = "content";
		String conversation_id="c_id";
		String in_reply_to= "irl";
		String language="C";
		String encoding="encoding";
		String ontology="e";
		String protocol="protocol";
		
		ACLMessage message =new ACLMessage(ACLMessage.CANCEL);
		message.setContent(content);
		message.setConversationId(conversation_id);
		message.setInReplyTo(in_reply_to);
		message.setLanguage(language);
		message.setEncoding(encoding);
		message.setOntology(ontology);
		message.setProtocol(protocol);
		
		JSONObject jsonMessage = JsonUtils.TransformMessageToJson(message);
		ACLMessage messageBis = JsonUtils.transformJsonToMessage(jsonMessage);
		
		if(messageBis.equals(message))
		{
			fail("the two messages are not the same");
		}
		
	}
}
