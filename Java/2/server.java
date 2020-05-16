
import java.io.*;
import java.util.*;
import java.net.*;

public class server {

    static Vector<ClientHandler> ar = new Vector<>();

    // counter for clients
    static int i = 0;

    public static void main(String[] args) throws IOException {
        // server is listening on port 1234
        ServerSocket ss = new ServerSocket(1234);

        Socket s;

        // running infinite loop for getting
        // client request
        while (true) {
            // Accept the incoming request
            s = ss.accept();

            System.out.println("New client request received : " + s);

            // obtain input and output streams
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());

            System.out.println("Creating a new handler for this client...");

            // Create a new handler object for handling this request.
            ClientHandler mtch = new ClientHandler(s, "client " + i, dis, dos);

            // Create a new Thread with this object.
            Thread t = new Thread(mtch);

            System.out.println("Adding this client to active client list");

            // add this client to active clients list
            ar.add(mtch);

            // start the thread.
            t.start();

            // increment i for new client.
            // i is used for naming only, and can be replaced
            // by any naming scheme
            i++;

        }
    }
}

// ClientHandler class
class ClientHandler implements Runnable {
    Scanner scn = new Scanner(System.in);
    private String name;
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;
    boolean isloggedin;
    int talkingTo = -1;
    String nickName = "null";

    // constructor
    public ClientHandler(Socket s, String name, DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
        this.name = name;
        this.s = s;
        this.isloggedin = true;
    }

    public void change(int i) {
        talkingTo = i;
    }

    @Override
    public void run() {

        String received;
        String changeID;
        try {
            dos.writeUTF("Your ID : " + this.name + "\nEnter a nickname");
            this.nickName = dis.readUTF();
            dos.writeUTF("Waiting for to choose nickName");
            for(int i=0; i<2; ) {
                i=2;
                for(ClientHandler ch : server.ar)
                    if(ch.nickName.equals("null"))
                        i=0;
            }
            dos.writeUTF("Done\nEnter number ID of client you want to talk to :");
            for(ClientHandler ch : server.ar) {
                if(ch.name != this.name)
                    dos.writeUTF(ch.nickName + " : " + ch.name);
            }
            changeID = dis.readUTF();
            System.out.print(changeID);
            change(Integer.valueOf(changeID)); 
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
            

        while (true) {
            try {
                // receive the string
                received = dis.readUTF();

                System.out.println(received);

                if (received.equals("logout")) {
                    this.isloggedin = false;
                    this.s.close();
                    break;
                }
                if(received.equals("!ch")) {
                    dos.writeUTF("Enter number ID of client you want to talk to :");
                    for(ClientHandler ch : server.ar) {
                        if(ch.name != this.name)
                            dos.writeUTF(ch.name + " ");
                    }
                    changeID = dis.readUTF();
                    System.out.print(changeID);
                    change(Integer.valueOf(changeID));
                    continue;
                }

                for (ClientHandler mc : server.ar) 
				{ 
					// if the recipient is found, write on its 
					// output stream 
					if (mc.name.equals("client " + Integer.toString(this.talkingTo)) && mc.isloggedin==true) 
					{ 
						mc.dos.writeUTF(this.nickName + " : " + received); 
						break; 
					} 
				} 
			} catch (IOException e) { 
				
                e.printStackTrace(); 
                break;
			} 
			
		} 
		try
		{ 
			// closing resources 
			this.dis.close(); 
			this.dos.close(); 
			
		}catch(IOException e){ 
			e.printStackTrace(); 
		} 
	} 
} 
