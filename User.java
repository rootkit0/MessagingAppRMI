import java.util.*;

public class User {
    //Init variables
    public String username;
    public String password;
    public Boolean status;
    private ArrayList<Message> userMessages = new ArrayList<Message>();
    private ArrayList<Group> userGroups = new ArrayList<Group>();
    
    //Constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.status = false;
    }
}
