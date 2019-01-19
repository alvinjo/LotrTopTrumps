package toptrumps;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static toptrumps.constants.Constants.SERVER_PORT;

public class GameServer {

    private static Game game = new Game();

    //TODO: Refactor, check if method should be static

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(SERVER_PORT);
            System.out.println("Game running on port " + SERVER_PORT);

            while(true){
                Socket socket = server.accept();
                System.out.println("Player connected");
                Player player = new Player(game, socket);
                new Thread(player).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
