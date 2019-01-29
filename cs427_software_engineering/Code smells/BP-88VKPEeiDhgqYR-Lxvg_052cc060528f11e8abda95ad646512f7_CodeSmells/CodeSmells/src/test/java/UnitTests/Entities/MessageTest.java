package UnitTests.Entities;

import entities.Message;
import entities.MessageType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MessageTest {

    @Test
    public void getMessageTest() {
        String msg = "something";
        Message m = new Message(MessageType.CHECKIN, msg);
        assertEquals(msg, m.getContent());

    }

    @Test
    public void getTypeTest() {
        Message m = new Message(MessageType.CHECKIN, "dummy");
        assertEquals(MessageType.CHECKIN, m.getType());
    }
}
