import java.util.*;
import java.io.*;
import java.net.*;

public class server {
    static Vector<clientResolver> clients = new Vector<clientResolver>();
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8080);
        Socket s;
        int i = 0;
        
        print("Server initialised.....");
        while(true) {
            s = ss.accept();
            DataInputStream din = new DataInputStream(s.getInputStream());
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            
            print("New Client received" + s);
            print("Resolving Client ... ");

            clientResolver client = new clientResolver(i, s, din, dout);

            clients.add(client);
            Thread thread = new Thread(client);
            thread.start();
            i++;
            
        }
    }

    public static void print(String s) {
        System.out.println(s);
    }
}

class clientResolver implements Runnable {
    String name;
    DataInputStream din;
    DataOutputStream dout;
    Socket s;
    
    public clientResolver(int i, Socket s, DataInputStream din, DataOutputStream dout) {
        this.s = s;
        this.din = din;
        this.dout = dout;
        this.name = "User#" + i;
        try {
            this.dout.writeUTF(this.name);
            
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    public void run() {
        String msg;
        while(true) {
            try {
                msg = (String) din.readUTF();
                server.print("------------\n" + msg);

                StringTokenizer st = new StringTokenizer(msg, ":"); 
                String recipient = st.nextToken();
                server.print("Recipient : " + recipient);
                String MsgToSend = st.nextToken();
                server.print("Msg : " + MsgToSend);
 

                for(clientResolver client : server.clients){
                    if(!client.name.equals(recipient)){
                        server.print("Client : " + client.name + "\n-----------");
                        client.dout.writeUTF(this.name + ":" + MsgToSend);
                    }
                }
                break;
                
            } catch (Exception e) {
                //TODO: handle exception
            }
        }
        
        try {
            this.din.close();
            this.dout.close();
        } catch (IOException e) {
            //TODO: handle exception
        }
    }
}