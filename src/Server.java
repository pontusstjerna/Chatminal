import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by pontu on 2016-05-20.
 */
public class Server implements Broadcaster{
    private int port;
    private boolean running;
    private List<ClientThread> clients;

    public Server (int port){
        this.port = port;
        clients = new ArrayList<>();
    }


    public void start() {
        running = true;

        try{
            ServerSocket serverSocket = new ServerSocket(port);

            System.out.println("Server is up on IP " + serverSocket.getInetAddress() + " and port " + port + "!");
            System.out.println("Waiting for clients... ");
            while(running){

                Socket socket = serverSocket.accept();

                ClientThread client = new ClientThread(socket, this);
                clients.add(client);

                if(running){
                    Thread t = new Thread(client);
                    t.start();
                }
            }
        }catch(IOException e){
            System.out.println("Unable to start the server!");
        }
    }

    @Override
    public synchronized void broadcast(String output) {

        //Write to server
        System.out.println(output);

        //Broadcast
        for(ClientThread c : clients){
            c.output(output);
        }
    }

    @Override
    public void remove(Object o) {
        clients.remove(o);
    }
}
