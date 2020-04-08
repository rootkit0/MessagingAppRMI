import java.rmi.*;

public interface CallbacksListener extends java.rmi.Remote {
    public void userConnected(String username) throws RemoteException;
    public void groupCreated(String group) throws RemoteException;
    public void sendUserMessage(Message msg) throws RemoteException;
    public void sendGroupMessage(Message msg) throws RemoteException;
}
