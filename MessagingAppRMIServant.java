import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

// Implementacion Servidor
public class MessagingAppRMIServant extends UnicastRemoteObject implements MessagingAppRMI {
	//Init variables
	private static final long serialVersionUID = 1;
	private ArrayList<User> users_db = new ArrayList<User>();
	private ArrayList<Group> groups_db = new ArrayList<Group>();

	//Constructor
	public MessagingAppRMIServant() throws RemoteException { }

	//Remote methods implementation
	public boolean login(String username, String password) throws RemoteException {
		Iterator<User> users_itr = users_db.iterator();
		while(users_itr.hasNext()) {
			User nextUsr = users_itr.next();
			if(nextUsr.username == username && nextUsr.password == password) {
				nextUsr.status = true;
				return true;
			}
		}
		return false;
	}

    public void logout(String username) throws RemoteException {

	}

    public void newUser(String username, String password) throws RemoteException {

	}

	public void sendMsgUser(String sender, String receiver, String message) throws RemoteException {

	}

	public void sendMsgGroup(String sender, String group, String message) throws RemoteException {

	}

    public void getMsg(String username) throws RemoteException {

	}

    public void newGroup(String group) throws RemoteException {

	}

    public void joinGroup(String username, String group) throws RemoteException {
		
	}

	public void exit(String username) throws RemoteException {

	}
}
