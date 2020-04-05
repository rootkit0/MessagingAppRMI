import java.rmi.*;

public interface CallbacksListener extends java.rmi.Remote {
    public void userConnected(String username) throws RemoteException;
}
