import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class MessagingAppRMIServant extends UnicastRemoteObject implements MessagingAppRMI {
	//Init variables
	private static final long serialVersionUID = 1;
	private ArrayList<User> users_db = new ArrayList<User>();
	private ArrayList<Group> groups_db = new ArrayList<Group>();

	//Constructor
	public MessagingAppRMIServant() throws RemoteException { }
	
	//Remote methods implementation
	public boolean login(String username, String password, CallbacksListener listener) throws RemoteException {
		Iterator<User> users_itr = users_db.iterator();
		int index = 0;
		while(users_itr.hasNext()) {
			User nextUser = users_itr.next();
			if(nextUser.username.equals(username) && nextUser.password.equals(password)) {
				//Set user status and listener
				nextUser.setStatus(true);
				nextUser.setListener(listener);
				users_db.set(index, nextUser);
				//Notify the login to all users
				notifyLogin(username);
				return true;
			}
			++index;
		}
		return false;
	}

    public boolean logout(String username) throws RemoteException {
		Iterator<User> users_itr = users_db.iterator();
		int index = 0;
		while(users_itr.hasNext()) {
			User nextUser = users_itr.next();
			if(nextUser.username.equals(username)) {
				//Check if the user is online
				if(nextUser.getStatus() == true) {
					//Set user status and listener
					nextUser.setStatus(false);
					nextUser.removeListener();
					users_db.set(index, nextUser);
					return true;
				}
			}
			++index;
		}
		return false;
	}

    public void newUser(String username, String password) throws RemoteException {
		User new_user = new User(username, password);
		//Check if the user exists
		if(getUser(username) == null) {
			//Add the user to users database
			this.users_db.add(new_user);
		}
		else {
			//User already exists
			System.out.println("User already exists, choose another username.");
		}
	}

	public void sendMsgUser(String sender, String receiver, String message) throws RemoteException {
		Message new_message = new Message(sender, receiver, message);
		User aux_user = getUser(receiver);
		//Check if the receiver exists
		if(aux_user != null) {
			//Check if the receiver is online
			if(aux_user.getStatus() == true) {
				//Send the message
			}
			else {
				System.out.println("User is offline, the message could not be sent");
			}
		}
		else {
			System.out.println("User doesn't exist, the message could not be sent");
		}	
	}

	public void sendMsgGroup(String sender, String group, String message) throws RemoteException {
		Message new_message = new Message(sender, group, message);
		Group aux_group = getGroup(group);
		//Check if group exists
		if(aux_group != null) {
			//Send the message
		}
		else {
			System.out.println("Group doesn't exist, the message could not be sent");
		}
	}

    public void newGroup(String group) throws RemoteException {
		Group new_group = new Group(group);
		//Check if the group exist
		if(getGroup(group) != null) {
			//Add the group to the groups database
			this.groups_db.add(new_group);
		}
		else {
			//Group already exists
			System.out.println("Group already exists, choose another group name.");
		}
	}

    public void joinGroup(String username, String group) throws RemoteException {
		//Check if the user or group exist
		User aux_user = getUser(username);
		Group aux_group = getGroup(group);
		if(aux_user != null && aux_group != null) {
			//Check if user is online
			if(aux_user.getStatus() == true) {
				aux_user.joinGroup(aux_group);
			}
			else {
				System.out.println("User is offline, couldn't join the group.");
			}
		}
		else {
			System.out.println("User or group does not exist.");
		}
	}

	public void exit(String username) throws RemoteException {
	
	}

	public User getUser(String username) {
		Iterator<User> users_itr = users_db.iterator();
		while(users_itr.hasNext()) {
			User nextUsr = users_itr.next();
			//Return user if found
			if(nextUsr.username.equals(username)) {
				return nextUsr;
			}
		}
		//Return null if user not found
		return null;
	}

	public Group getGroup(String group) {
		Iterator<Group> groups_itr = groups_db.iterator();
		while(groups_itr.hasNext()) {
			Group nextGroup = groups_itr.next();
			//Return group if found
			if(nextGroup.group.equals(group)) {
				return nextGroup;
			}
		}
		//Return null if user not found
		return null;
	}

	//Callback methods
	public void notifyLogin(String username) {
		Iterator<User> users_itr = users_db.iterator();
		int index = 0;
		while(users_itr.hasNext()) {
			User nextUsr = users_itr.next();
			try{
				nextUsr.userListener.userConnected(username);
			}
			catch (RemoteException re) {
				nextUsr.removeListener();
				users_db.set(index, nextUsr);
			}
			++index;
		}
	}
}
