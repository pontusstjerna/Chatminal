import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Pontus on 2016-05-22.
 */
public class ClientListener implements Runnable{
    private Socket socket;

    public ClientListener(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {


    }
}
