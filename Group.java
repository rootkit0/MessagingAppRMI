import java.util.*;

public class Group {
    //Init variables
    public String group;
    private ArrayList<Message> groupMessages = new ArrayList<Message>();

    //Constructor
    public Group(String group) {
        this.group = group;
    }

    public void addMessage(Message msg) {
        this.groupMessages.add(msg);
    }

    public ArrayList<Message> getMessages() {
        return this.groupMessages;
    }
}
