import java.util.*;
import java.io.*;
import java.net.*;

public class client {
    public static void main(String[] args) throws UnknownHostException, IOException {
        int port = 8080;
        String name;
        Scanner read = new Scanner(System.in);
        InetAddress ip = InetAddress.getByName("localhost");
        Socket s = new Socket(ip, port);
        DataInputStream din = new DataInputStream(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());

        name = din.readUTF();
        print("Your Default Name : " + name);

        Thread sendmsg = new Thread(new Runnable(){
        
            @Override
            public void run() {
                print("Thread sendmsg");
                while(true) {
                    String msg = read.nextLine();
                    try {
                        dout.writeUTF(name + ":" + msg);
                        print(name + ":" + msg);
                    } catch (Exception e) {
                        //TODO: handle exception
                    }
                }
            }
        });

        Thread readmsg = new Thread(new Runnable(){
        
            @Override
            public void run() {

                while(true) {
                    try {
                        print("getting msg");
                        String msg1 = din.readUTF();
                         System.out.print(msg1);
                    } catch (Exception e) {
                     //TODO: handle exception
                    }
                }
            }
        });

        sendmsg.start();
        readmsg.start();
    }

    static void print(String s) {
        System.out.println(s);
    }
}