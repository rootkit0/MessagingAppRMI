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
	public boolean login(String username, String password) throws RemoteException {
		Iterator<User> users_itr = users_db.iterator();
		int index = 0;
		while(users_itr.hasNext()) {
			++index;
			User nextUser = users_itr.next();
			if(nextUser.username == username && nextUser.password == password) {
				nextUser.setStatus(true);
				users_db.set(index, nextUser);
				return true;
			}
		}
		return false;
	}

    public boolean logout(String username) throws RemoteException {
		User aux_user = getUser(username);
		//Check if the user exists
		if(aux_user != null) {
			//Check if the user is online
			if(aux_user.getStatus() ==  true) {
				aux_user.setStatus(false);
				return true;
			}
			else {
				System.out.println("User is already offline");
			}
		}
		else {
			System.out.println("User does not exist");
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
				aux_user.addMessage(new_message);
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
			aux_group.addMessage(new_message);
		}
		else {
			System.out.println("Group doesn't exist, the message could not be sent");
		}
	}

    public void getMsg(String username) throws RemoteException {
		ArrayList<Message> userMessages = new ArrayList<Message>();
		ArrayList<Group> userGroups = new ArrayList<Group>();
		User aux_user = getUser(username);
		//Check if the user exists
		if(aux_user != null) {
			//Check if the user is online
			if(aux_user.getStatus() == true) {
				//Get all user messages
				userMessages.addAll(aux_user.getMessages());
				//Get all the groups where user subscribed
				userGroups.addAll(aux_user.getGroups());
				Iterator<Group> groups_itr = userGroups.iterator();
				//Iterate through the groups
				while(groups_itr.hasNext()) {
					Group nextGroup = groups_itr.next();
					//Add all the messages of each group
					userMessages.addAll(nextGroup.getMessages());
				}
			}
			else {
				System.out.println("User is offline, couldn't get the messages");
			}
		}
		else {
			System.out.println("User does not exist, couldn't get the messages");
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
			if(nextUsr.username == username) {
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
			if(nextGroup.group == group) {
				return nextGroup;
			}
		}
		//Return null if user not found
		return null;
	}
}
