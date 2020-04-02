import java.rmi.*;

public interface MessagingAppRMI extends java.rmi.Remote {
	//Remote methods
	public boolean login(String username, String password) throws RemoteException;
    public void logout(String username) throws RemoteException;
    public void newUser(String username, String password) throws RemoteException;
	public void sendMsgUser(String sender, String receiver, String message) throws RemoteException;
	public void sendMsgGroup(String sender, String group, String message) throws RemoteException;
    public void getMsg(String username) throws RemoteException;
    public void newGroup(String group) throws RemoteException;
	public void joinGroup(String username, String group) throws RemoteException;
	public void exit(String username) throws RemoteException;
}
