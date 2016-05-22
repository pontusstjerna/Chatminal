import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by pontu on 2016-05-20 with help from http://www.dreamincode.net/forums/topic/262304-simple-client-and-server-chat-program/.
 */
public class Chatminal {

    private static final int STD_PORT = 1550;
    private static final String STD_IPv4 = "localhost";
    private static final String SERV_COMMAND = "server";

    public static void main(String[] args) {
        switch (args.length) {
            case 3: //java <username> <ip> <port>
                new Chatminal().runClient(args[0], args[1], Integer.valueOf(args[2]));
                break;
            case 2:
                if (args[0].equals(SERV_COMMAND)) { //java server port
                    new Server(Integer.valueOf(args[1])).start();
                } else { //java <username> <ip>
                    new Chatminal().runClient(args[0], args[1], STD_PORT);
                }
                break;
            case 1:
                if (args[0].equals(SERV_COMMAND)) {
                    new Server(STD_PORT).start();
                } else { //java <username>
                    new Chatminal().runClient(args[0], STD_IPv4, STD_PORT);
                }
                break;
            case 0:
                new Chatminal().runClient("Anonymous", STD_IPv4, STD_PORT);
                break;
            default:
                System.out.println("Oops! Too many arguments! Enter <java Chatminal help> for a list of commands.");
        }
    }

    public void runClient(String userName, String ip, int port){
        try{
            Socket socket = new Socket(ip, port);
            System.out.println("Connected to " + ip + "...");

            Client client = new Client(socket, userName);

            Thread t = new Thread(client);
            t.start();

        }catch(IOException e){
            System.out.println("Oops! Failed to connect to " + ip);
        }
    }
}
