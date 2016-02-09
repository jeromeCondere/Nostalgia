package UnitTest;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import utils.box.Inbox;
import utils.box.Outbox;
import utils.box.Mailbox;

public class MailboxTest {

	@Test
	public void TestaddInbox()
	{
		Mailbox alanMailbox=new Mailbox("Albox");
		Inbox inAlbox=new Inbox("inAlbox", "mailbox");
		alanMailbox.getInboxes().add(inAlbox);

		if(alanMailbox.getInboxes().isEmpty())
		{
			fail("error the mailbox is empty");
		}
		String inboxName=alanMailbox.getInboxes().get(0).getName();
		String mailboxName=alanMailbox.getInboxes().get(0).getMailboxName();
		assertEquals("error on Inbox name",inboxName,"inAlbox");
		assertEquals("error on mailbox name of inbox",mailboxName,"mailbox");
	}
	@Test
	public void TestaddOutbox()
	{
		Mailbox alanMailbox=new Mailbox("Albox");
		Outbox outAlbox=new Outbox("outAlbox", "mailbox");
		alanMailbox.getOutboxes().add(outAlbox);

		if(alanMailbox.getOutboxes().isEmpty())
		{
			fail("error the mailbox is empty");
		}
		String outboxName=alanMailbox.getOutboxes().get(0).getName();
		String mailboxName=alanMailbox.getOutboxes().get(0).getMailboxName();
		assertEquals("error on Outbox name",outboxName,"outAlbox");
		assertEquals("error on mailbox name of Outbox",mailboxName,"mailbox");
	}
	
	
	@Test
	public void TestaddInputConnection()
	{
		
		Mailbox alanMailbox=new Mailbox("Albox");
		alanMailbox.addInputConnection("inAlbox", "OutboxJen", "mailboxJen");
		
		if(alanMailbox.getInboxes().isEmpty())
		{
			fail("error the mailbox is empty");
		}
		String inboxName=alanMailbox.getInboxes().get(0).getName();
		String mailboxName=alanMailbox.getInboxes().get(0).getMailboxName();
		assertEquals("error on Inbox name",inboxName,"inAlbox");
		assertEquals("error on mailbox name",mailboxName,alanMailbox.getMailboxName());
	    ArrayList<Outbox> outboxes_of_inAlbox=alanMailbox.getInboxes().get(0).getOutBoxes();
		String outboxName=outboxes_of_inAlbox.get(0).getName();
		String mailboxName_of_outbox=outboxes_of_inAlbox.get(0).getMailboxName();
		assertEquals("error on Outbox name",outboxName,"OutboxJen");
		assertEquals("error on mailbox name for the outbox",mailboxName_of_outbox,"mailboxJen");
	
	}
	@Test
	public void TestaddOutputConnection()
	{
		
		Mailbox alanMailbox=new Mailbox("Albox");
		alanMailbox.addOutputConnection("outAlbox", "inboxJen", "mailboxJen");
		
		if(alanMailbox.getOutboxes().isEmpty())
		{
			fail("error the mailbox is empty");
		}
		String outboxName=alanMailbox.getOutboxes().get(0).getName();
		String mailboxName=alanMailbox.getOutboxes().get(0).getMailboxName();
		assertEquals("error on Outbox name",outboxName,"outAlbox");
		assertEquals("error on mailbox name",mailboxName,alanMailbox.getMailboxName());
	    ArrayList<Inbox> inboxes_of_inAlbox=alanMailbox.getOutboxes().get(0).getInBoxes();
		String inboxName=inboxes_of_inAlbox.get(0).getName();
		String mailboxName_of_outbox=inboxes_of_inAlbox.get(0).getMailboxName();
		assertEquals("error on Outbox name",inboxName,"inboxJen");
		assertEquals("error on mailbox name for the inbox",mailboxName_of_outbox,"mailboxJen");
	
	}
	
	
	
	

}
