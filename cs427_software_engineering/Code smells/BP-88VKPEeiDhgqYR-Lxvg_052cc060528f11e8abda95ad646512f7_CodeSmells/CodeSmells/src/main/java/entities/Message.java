package entities;

public class Message {

    private MessageType type;
    private String content;

    public Message(MessageType type, String msg) {
        this.type = type;
        this.content = msg;
    }

    public MessageType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

}
