import java.rmi.*;
import java.util.*;

public interface CallbacksListener extends java.rmi.Remote {
    public void userConnected(String username) throws RemoteException;
    public void userDisconnected(String username) throws RemoteException;
    public void groupCreated(String group) throws RemoteException;
    public void sendUserMessage(String sender, String msg, Date time) throws RemoteException;
    public void sendGroupMessage(String group, String msg, Date time) throws RemoteException;
}
