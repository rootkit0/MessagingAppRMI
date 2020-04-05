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
                        break;
					case "Logout":
						if(servicioMsg.logout(client_username)) {
							client_username = "";
							client_status = false;
						}
                        break;
                    case "NewUser":
                        String user_newUser = st.nextToken();
						String pass_newUser = st.nextToken();
						String group_newUser = "";
						servicioMsg.newUser(user_newUser, pass_newUser);
                        if(st.hasMoreTokens()) {
							group_newUser = st.nextToken();
							servicioMsg.joinGroup(user_newUser, group_newUser);
						}
                        break;
					case "SendMsg":
						String text_sendMsg = "";
						//Enviar mensaje a un grupo
                        if(st.nextToken() == "-g") {
							String group_sendMsg = st.nextToken();
							text_sendMsg = st.nextToken();
							servicioMsg.sendMsgGroup(client_username, group_sendMsg, text_sendMsg);
						}
						//Enviar mensaje a un usuario
						else {
							String user_sendMsg = st.nextToken();
							text_sendMsg = st.nextToken();
							servicioMsg.sendMsgUser(client_username, user_sendMsg, text_sendMsg);
						}
						break;
					case "GetMsg":
						break;
                    case "NewGroup":
						String group_newGroup = st.nextToken();
						servicioMsg.newGroup(group_newGroup);
                        break;
                    case "JoinGroup":
						String group_joinGroup = st.nextToken();
						servicioMsg.joinGroup(client_username, group_joinGroup);
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
