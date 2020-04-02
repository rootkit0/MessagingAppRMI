import java.rmi.*;
import java.util.*;

public class MessagingAppRMIClient {
	public static void main(String args[]) {
		System.out.println("Iniciando el cliente");
		try {
			// Comprobar si se ha especificado la direccion del servicio de registros
			String registry = "localhost";
			if (args.length >=1)
				registry = args[0];
			// Formatear la url del registro
			String registro ="rmi://" + registry + "/MessagingAppRMI";
			// Buscar el servicio en el registro.
			Remote servicioRemoto = Naming.lookup(registro);
			// Convertir a un interfaz
			MessagingAppRMI servicioMsg = (MessagingAppRMI) servicioRemoto;
			//Get keyboard input
            Scanner scan = new Scanner(System.in);
            //Exception handler
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    scan.close();
                }
			});
			while(true) {
                String command = scan.nextLine();
                StringTokenizer st = new StringTokenizer(command);
                String command_type = st.nextToken();
                switch(command_type) {
                    case "login":
                        String user_login = st.nextToken();
                        String pass_login = st.nextToken();
                        break;
                    case "logout":
                        break;
                    case "newUser":
                        String user_newUser = st.nextToken();
                        String pass_newUser = st.nextToken();
                        String group_newUser = "";
                        if(st.hasMoreTokens()) {
                            group_newUser = st.nextToken();
                        }
                        break;
                    case "sendMsg":
                        String user_sendMsg = "";
                        String group_sendMsg = "";
                        if(st.nextToken() == "-g") {
                            group_sendMsg = st.nextToken();
						}
						else {
							user_sendMsg = st.nextToken();
						}
                        String text_sendMsg = st.nextToken();
                        break;
                    case "getMsg":
                        break;
                    case "newGroup":
                        String group_newGroup = st.nextToken();
                        break;
                    case "joinGroup":
                        String group_joinGroup = st.nextToken();
                        break;
                    case "exit":

                        break;
                }
            }
		}
		catch (NotBoundException nbe) {
			System.err.println("No existe el servicio en el registro!");
		}
		catch (RemoteException re) {
			System.err.println("Error Remoto - " + re);
		}
		catch (Exception e) {
			System.err.println("Error - " + e);
		}		
	}
}
