import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by pontu on 2016-05-20.
 */
public class Server implements Runnable{
    private Socket socket;

    public Server (Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            //Catch user input
            Scanner localInScanner = new Scanner(System.in);

            //Catch the input from the socket with the scanner
            Scanner inScanner = new Scanner(socket.getInputStream());

            //Catch the output from the client
            PrintWriter outWriter = new PrintWriter(socket.getOutputStream());

            boolean running = true;

            while(running){
               /* String localInput = localInScanner.nextLine();

                if(localInput.equals("quit")){
                    running = false;
                }*/

                if(inScanner.hasNext()){
                    //The input from any user to the server
                    String input = inScanner.nextLine();

                    //Tell clients that someone said something
                    System.out.println("Someone said: " + input);

                    //Message to the sender
                    outWriter.println("You said: " + input);

                    //Clear the output stream
                    outWriter.flush();
                }
            }

        }catch(IOException e){
            System.out.println("Ooops! Could not read or write for this reason: ");
            e.printStackTrace();
        }
    }
}
