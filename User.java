import java.rmi.RemoteException;
import java.util.*;

public class User {
    //Init variables
    public String username;
    public String password;
    private Boolean status;
    private ArrayList<Group> userGroups = new ArrayList<Group>();
    //Callback
    public CallbacksListener userListener;

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

    public void joinGroup(Group group) {
        this.userGroups.add(group);
    }

    public boolean checkIfBelongsGroup(String group) {
        Iterator<Group> group_itr = userGroups.iterator();
        while(group_itr.hasNext()) {
            if(group_itr.next().group.equals(group)) {
                return true;
            }
        }
        return false;
    }

    public void setListener(CallbacksListener listener) {
        this.userListener = listener;
    }

    public void removeListener() {
        this.userListener = null;
    }
}
