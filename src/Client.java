import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by pontu on 2016-05-20.
 */
public class Client implements Runnable{

    Socket socket;

    public Client (Socket socket){
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

            while(true){
                String localInput = localInScanner.nextLine();

                outWriter.println(localInput);
                outWriter.flush();

                if(inScanner.hasNext()){
                    //The input from any user to the server
                    String input = inScanner.nextLine();

                    //Tell clients that some said something
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
