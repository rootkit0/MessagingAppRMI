import java.rmi.*;
import java.util.*;

//Cliente
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
			//Get input from keyboard
            Scanner scan = new Scanner(System.in);
            //Exception handler
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    scan.close();
                }
			});
			//Client status
			String client_username = "";
			Boolean client_status = false;
			while(true) {
				String command = scan.nextLine();
                StringTokenizer st = new StringTokenizer(command);
				String command_type = st.nextToken();
				//Treat cases depending on command type
                switch(command_type) {
                    case "Login":
                        String user_login = st.nextToken();
						String pass_login = st.nextToken();
						if(servicioMsg.login(user_login, pass_login)) {
							client_username = user_login;
							client_status = true;
						}
						else {
							System.out.println("Usuario o contrasena incorrectos");
						}
                        break;
					case "Logout":
						
                        break;
                    case "NewUser":
                        String user_newUser = st.nextToken();
						String pass_newUser = st.nextToken();
						servicioMsg.newUser(user_newUser, pass_newUser);
                        if(st.hasMoreTokens()) {
							String group_newUser = st.nextToken();
                        }
                        break;
					case "SendMsg":
						String text_sendMsg = "";
						//Enviar mensaje a un grupo
                        if(st.nextToken() == "-g") {
							String group_sendMsg = st.nextToken();
							text_sendMsg = st.nextToken();
							
						}
						//Enviar mensaje a un usuario
						else {
							String user_sendMsg = st.nextToken();
							text_sendMsg = st.nextToken();
						}
						break;
					case "GetMsg":
						break;
                    case "NewGroup":
                        String group_newGroup = st.nextToken();
                        break;
                    case "JoinGroup":
                        String group_joinGroup = st.nextToken();
                        break;
                    case "Exit":

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
