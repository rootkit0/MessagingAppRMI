import java.rmi.*;

public interface MessagingAppRMI extends java.rmi.Remote {
	//Remote methods
	public void login() throws RemoteException;
    public void logout() throws RemoteException;
    public void newUser() throws RemoteException;
	public void sendMsgUser() throws RemoteException;
	public void sendMsgGroup() throws RemoteException;
    public void getMsg() throws RemoteException;
    public void newGroup() throws RemoteException;
    public void joinGroup() throws RemoteException;
}
