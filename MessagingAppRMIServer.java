import java.rmi.*;
import java.rmi.server.*;

// Servidor
public class MessagingAppRMIServer {
	public static void main(String args[]) {
		System.out.println("Iniciando el servidor");
		//Init security server and set policy file
		System.setProperty("java.security.policy", "./server.policy");
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			// Cargar el servicio.
			MessagingAppRMIServant servicioMsg = new MessagingAppRMIServant();
			// Imprimir la ubicacion del servicio.
			RemoteRef location = servicioMsg.getRef();
			System.out.println(location.remoteToString());
			// Crear la URL del registro.
			String registro ="rmi://" + "localhost" + "/MessagingAppRMI";
			// Registrar el servicio
			Naming.rebind(registro, servicioMsg);
		}
		catch (RemoteException re) {
			System.err.println("Remote Error - " + re);
		}
		catch (Exception e) {
			System.err.println("Error - " + e);
		}
	}
}
