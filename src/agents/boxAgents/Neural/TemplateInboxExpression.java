package agents.boxAgents.Neural;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import utils.box.Inbox;
import utils.box.Outbox;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate.MatchExpression;

public class TemplateInboxExpression implements MatchExpression {

	private Inbox inbox;
	public TemplateInboxExpression(Inbox inbox)
	{
		this.inbox=inbox;
	}
	@Override
	public boolean match(ACLMessage message) {
		
		JSONParser jsonParser = new JSONParser();
 		JSONObject jsonObject=null;
    		 try {
    				jsonObject = (JSONObject) jsonParser.parse(message.getContent());
    				
    				JSONObject ports=(JSONObject) jsonObject.get("ports");
    				JSONArray messageInboxesJson=(JSONArray) ports.get("in");
    				
    				
    				JSONObject messageOutboxJson=(JSONObject)ports.get("out");
    				String OutboxName=(String) messageOutboxJson.get("name");
    				String OutboxMailboxName=(String) messageOutboxJson.get("mailboxName");
    				Outbox outboxMessage=new Outbox(OutboxName,OutboxMailboxName);
    			    
    				for(int i=0;i<messageInboxesJson.size();i++)
    				{
    					JSONObject inboxJson=(JSONObject) messageInboxesJson.get(i);
    					String name=(String) inboxJson.get("name");
    					String mailboxname=(String) inboxJson.get("mailboxName");
    					String user=(String)inboxJson.get("user");
    					Inbox inboxTocompare=new Inbox(name,mailboxname);
    					
    					//the inbox found is the one we are looking for
    					ArrayList<Outbox> outboxes=inbox.getOutBoxes();
    					if(inbox.equals(inboxTocompare) && outboxes.contains(outboxMessage))
    						return true;
    				}

    			} catch (ParseException e) {
    				// TODO Auto-generated catch block
    				return false;
    			}
		return false;
	}

}
