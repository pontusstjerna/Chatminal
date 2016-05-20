import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by pontu on 2016-05-20 with help from http://www.dreamincode.net/forums/topic/262304-simple-client-and-server-chat-program/.
 */
public class Server {
    public static void main(String[] args){
        try{
            final int PORT = 1337;
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Server is up! Waiting for clients...");

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
