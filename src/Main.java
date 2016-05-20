import sun.security.x509.IPAddressName;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by pontu on 2016-05-20 with help from http://www.dreamincode.net/forums/topic/262304-simple-client-and-server-chat-program/.
 */
public class Main {
    public static void main(String[] args){
        if(args.length > 0){
            if(args[0].equals("server")){
                if(args.length > 1){ //Start server on given port
                        new Main().runServer(Integer.valueOf(args[1]));
                }else{ //Run server on standard port
                    new Main().runServer(1550);
                }
            }else{ //Start client on given address
                new Main().runClient(args[0], Integer.valueOf(args[1]));
            }
        }else{ //Start client on standard address
            new Main().runClient("localhost", 1550);
        }
    }

    private void runClient(String ip, int port){
        try{
            Socket socket = new Socket(ip, port);
            System.out.println("Connected to " + ip + "...");

            Client client = new Client(socket);

            Thread t = new Thread(client);
            t.start();

        }catch(IOException e){
            System.out.println("Oops! Failed to connect to " + ip);
            e.printStackTrace();
        }
    }

    private void runServer(int port){
        try{
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server up on port " + port + "! Waiting for clients...");

            while(true){
                //Accept any incoming clients trying to connect
                Socket s = server.accept();

                System.out.println("Aha! Someone connected with address " + s.getLocalAddress());
                Client chat = new Client(s);
                Thread t = new Thread(chat);
                t.start();

            }
        }catch(IOException e){
            System.out.println("Oops! Something went wrong... ");
            e.printStackTrace();
        }
    }
}
