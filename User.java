import java.util.*;

public class User {
    //Init variables
    public String username;
    public String password;
    private Boolean status;
    private ArrayList<Message> userMessages = new ArrayList<Message>();
    private ArrayList<Group> userGroups = new ArrayList<Group>();
    
    //Constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.status = false;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void addMessage(Message msg) {
        this.userMessages.add(msg);
    }

    public void joinGroup(Group group) {
        this.userGroups.add(group);
    }   
}
