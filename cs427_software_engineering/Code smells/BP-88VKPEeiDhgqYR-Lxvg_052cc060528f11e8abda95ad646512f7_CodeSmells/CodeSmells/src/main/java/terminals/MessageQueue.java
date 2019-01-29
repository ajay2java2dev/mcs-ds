package terminals;

import entities.Message;
import entities.Queue;

public class MessageQueue extends Queue<Message> {

    private final static int messageQueueSize = 10;

    MessageQueue() {
        super(messageQueueSize);
    }


}
