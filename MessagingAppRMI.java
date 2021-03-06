import java.rmi.*;

public interface MessagingAppRMI extends java.rmi.Remote {
	//Remote methods
	//Sistema abierto, misma estructura que la version del foro del campus
	public boolean login(String username, String password, CallbacksListener listener) throws RemoteException;
    public boolean logout(String username) throws RemoteException;
	public boolean newUser(String username, String password) throws RemoteException;
	public boolean sendMsgUser(String sender, String receiver, String message) throws RemoteException;
	public boolean sendMsgGroup(String sender, String group, String message) throws RemoteException;	
	public boolean newGroup(String group) throws RemoteException;
	public boolean joinGroup(String username, String group) throws RemoteException;
}
