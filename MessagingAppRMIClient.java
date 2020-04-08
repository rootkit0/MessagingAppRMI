import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

//Cliente
public class MessagingAppRMIClient extends UnicastRemoteObject implements CallbacksListener {
	//Init variables
	private static final long serialVersionUID = 1;
	//Constructor
	public MessagingAppRMIClient() throws RemoteException { };
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
			//Client username and status
			String client_username = null;
			Boolean client_status = false;
			//Listener for server callbacks
			MessagingAppRMIClient listener = new MessagingAppRMIClient();
			while(scan.hasNextLine()) {
				String command = scan.nextLine();
                StringTokenizer st = new StringTokenizer(command);
				while(st.hasMoreTokens()) {
					String command_type = st.nextToken();
					//Treat cases depending on command type
					if(command_type == "Login") {
						try {
							String user_login = st.nextToken();
							String pass_login = st.nextToken();
							if(!client_status) {
								if(servicioMsg.login(user_login, pass_login, listener)) {
									client_username = user_login;
									client_status = true;
								}
								else {
									System.out.println("Error! Usuario o contraseña incorrectos!");
								}
							}
							else {
								System.out.println("Error! Ya estas conectado con el usuario: " + client_username);
							}
						}
						catch(Exception e) {
							System.out.println("Sintaxis incorrecta! Uso: Login <username> <password>");
						}
					}
					else if(command_type == "Logout") {
						try {
							if(client_status) {
								if(servicioMsg.logout(client_username)) {
									client_username = "";
									client_status = false;
									System.out.println("Te has desconectado");
								}
								else {
									System.out.println("Error! No se ha podido desconectar al usuario: " + client_username);
								}
							}
							else {
								System.out.println("Error! No estas conectado en el sistema!");
							}
						}
						catch(Exception e) {
							System.out.println("Sintaxis incorrecta! Uso: Logout");
						}
					}
					else if(command_type == "NewUser") {
						try {
							String user_newUser = st.nextToken();
							String pass_newUser = st.nextToken();
							String group_newUser = "";
							if(servicioMsg.newUser(user_newUser, pass_newUser)) {
								System.out.println("Usuario creado con exito!");
							}
							else {
								System.out.println("Error! El usuario ya existe, escoja otro username!");
							}
							if(st.hasMoreTokens()) {
								group_newUser = st.nextToken();
								if(servicioMsg.joinGroup(user_newUser, group_newUser)) {
									System.out.println("Te has subscrito al grupo " + group_newUser);
								}
								else {
									System.out.println("Error! El grupo no existe!");
								}
							}
						}
						catch(Exception e) {
							System.out.println("Sintaxis incorrecta! Uso: NewUser <username> <password> <group>");
						}
					}
					else if(command_type == "SendMsg") {
						try {
							String user_sendMsg = st.nextToken();
							String text_sendMsg = st.nextToken();
							if(servicioMsg.sendMsgUser(client_username, user_sendMsg, text_sendMsg)) {
								System.out.println("Mensaje enviado correctamente!");
							}
							else {
								System.out.println("Error! El usuario esta desconectado o no existe!");
							}
						}
						catch(Exception e) {
							System.out.println("Sintaxis incorrecta! Uso: SendMsg <usuario> <mensaje>");
						}
					}
					else if(command_type == "NewGroup") {
						try {
							String group_newGroup = st.nextToken();
							if(servicioMsg.newGroup(group_newGroup)) {
								System.out.println("Grupo creado con exito!");
							}
							else {
								System.out.println("Error! El grupo ya existe, escoja otro groupname");
							}
						}
						catch(Exception e) {
							System.out.println("Sintaxis incorrecta! Uso: NewGroup <groupname>");
						}
					}
					else if(command_type == "JoinGroup") {
						try {
							String group_joinGroup = st.nextToken();
							if(servicioMsg.joinGroup(client_username, group_joinGroup)) {
								System.out.println("Te has subscrito al grupo " + group_joinGroup);
							}
							else {
								System.out.println("Error! El grupo no existe!");
							}
						}
						catch(Exception e) {
							System.out.println("Sintaxis incorrecta! Uso: JoinGroup <groupname>");
						}
					}
					else if(command_type == "Exit") {
						System.gc();
						System.exit(0);
					}
					else {
						System.out.println("Introduce un comando valido!");
					}
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

	//Callback methods
	public void userConnected(String username) throws RemoteException {
		System.out.println("El usuario " + username + " se ha conectado.");
	}

	public void userDisconnected(String username) throws RemoteException {
		System.out.println("El usuario " + username + " se ha desconectado.");
	};

    public void groupCreated(String group) throws RemoteException {
		System.out.println("Se ha creado el grupo " + group);
		System.out.println("Para unirte al grupo usa el comando: JoinGroup " + group);
	};

    public void sendUserMessage(Message msg) throws RemoteException {
		System.out.println("Mensaje de " + msg.sender + ": " + msg.text);
	};

    public void sendGroupMessage(Message msg) throws RemoteException {
		System.out.println("Mensaje del grupo " + msg.receiver + " enviado por " + msg.sender + ": " + msg.text);
	};
}
