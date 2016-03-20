package utils.communication.message.Netlogo;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import NosException.CheckMessageException;
import jade.lang.acl.ACLMessage;
import utils.Netlogo.NetlogoJson;
import utils.communication.message.MessageChecker;

public class NetlogoInputMessageChecker implements MessageChecker {

	@Override
	public Object check(ACLMessage message,Object [] params) throws CheckMessageException {
		JSONObject jsonObject=null;
		JSONParser jsonParser = (JSONParser) params[0];
		try {
  			jsonObject = (JSONObject) jsonParser.parse(message.getContent());
  			
  		} catch (ParseException e) {
  			// TODO Auto-generated catch block
  			throw new CheckMessageException("Json parse error.");
  		}
		return jsonObject;//code for normal Json
	}

}
