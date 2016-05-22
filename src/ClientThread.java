import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Pontus on 2016-05-22.
 */
public class ClientThread implements Runnable {

    private Socket socket;
    private Scanner input;
    private PrintWriter output;
    private Broadcaster server;

    private String userName;

    public ClientThread(Socket socket, Broadcaster server){
        this.socket = socket;
        this.server = server;

        connectInputs();
    }

    @Override
    public void run() {
        boolean running = true;

        while(running){
            String inp = input.nextLine();

            if(inp.equals("quit")){
                System.out.println(userName + " disconnected.");
                running = false;
            }else{
                server.broadcast(userName + ": " + inp);
            }
        }
        server.remove(this);
        output.close();
        input.close();
        try{
            socket.close();
        }catch(IOException e){
            System.out.println("Unable to close socket!");
        }
    }

    private void connectInputs(){
        try{
            output = new PrintWriter(socket.getOutputStream());
            input = new Scanner(socket.getInputStream());

            userName = input.nextLine();
            System.out.println(userName + " successfully connected to server!");
        }catch(IOException e){
            System.out.println("Client unable to connect!");
        }
    }

    public void output(String output){
        this.output.println(output);
        this.output.flush();
    }

    @Override
    public String toString(){
        return userName;
    }
}
