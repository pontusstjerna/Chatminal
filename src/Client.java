import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by pontu on 2016-05-20.
 */
public class Client implements Runnable{

    Socket socket;
    String userName;

    public Client (Socket socket, String userName){
        this.socket = socket;
        this.userName = userName;
    }

    @Override
    public void run() {
        try{
            //Catch user input
            Scanner localInScanner = new Scanner(System.in);

            //Catch the output from the client
            PrintWriter outWriter = new PrintWriter(socket.getOutputStream());

            //First, send the username
            outWriter.println(userName);
            outWriter.flush();

            //Start a listener thread
            new Thread(){
                @Override
                public void run(){
                    try{
                        //Catch the input from the socket with the scanner
                        Scanner inScanner = new Scanner(socket.getInputStream());

                        boolean running = true;

                        while(running){
                            if(inScanner.hasNext()){

                                //The input from the server to the client
                                String input = inScanner.nextLine();
                                if(input.substring(userName.length()).equals("quit")){
                                    running = false;
                                }

                                //Print the input received, if it's not your own message
                                if(!input.substring(0, userName.length()).equals(userName)){
                                    System.out.println(input);
                                }
                            }
                        }
                    }catch(IOException e){
                        System.out.println("Unable to read any input from server!");
                    }
                }
            }.start();

            boolean running = true;

            //Read inputs from local user
            while(running){
               // System.out.print(userName + ": ");

                String localInput = localInScanner.nextLine();

                if(localInput.equals("quit")){
                    running = false;
                }

                outWriter.println(localInput);
                outWriter.flush();
            }

        }catch(IOException e){
            System.out.println("Ooops! Could not read or write for this reason: ");
            e.printStackTrace();
        }
    }
}
