import java.util.Date;

public class Message {
    //Init variables
    public String sender;
    public String receiver;
    public String text;
    public Date time;
    
    //Constructor
    public Message(String sender, String receiver, String text) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.time = new Date();
    }
}
