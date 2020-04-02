import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

// Implementacion Servidor
public class MessagingAppRMIServant extends UnicastRemoteObject implements MessagingAppRMI {
	//Init variables
	private static final long serialVersionUID = 1;
	//Constructor
	public MessagingAppRMIServant() throws RemoteException {

	}
	//Remote methods implementation
	public void login() throws RemoteException {

	};
    public void logout() throws RemoteException {

	};
    public void newUser() throws RemoteException {

	};
	public void sendMsgUser() throws RemoteException {

	};
	public void sendMsgGroup() throws RemoteException {

	};
    public void getMsg() throws RemoteException {

	};
    public void newGroup() throws RemoteException {

	};
    public void joinGroup() throws RemoteException {
		
	};
}
