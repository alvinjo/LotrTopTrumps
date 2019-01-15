package toptrumps;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Service {

    private Game game;
    private Scanner in;
    private PrintWriter out;

    public Service(Game game, Socket socket){
        this.game = game;

        try{
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
