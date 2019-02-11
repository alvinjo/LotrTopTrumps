package toptrumps.game;

import toptrumps.player.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static toptrumps.constants.Constants.SERVER_PORT;

public class GameServer {

    private static Game game = new Game(4);

    //TODO: Refactor, check if method should be static. check if classes should be instances or util
    //TODO: Remove wandering TODO comments. Remove comments. Remove print statements.

    //TODO: Display who won to all players? Keep a untouched player list to continue displaying messages?
    //TODO: Move game methods from Player class to Game class. Put methods in correct classes
    //TODO: Game should end after certain number of rounds if no one has won. Player with most cards wins.
    //TODO: At a draw scenario with no winners, show table with players and deck sizes


    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(SERVER_PORT);
            System.out.println("Game running on port " + SERVER_PORT);

            while(true){
                Socket socket = server.accept();
                System.out.println("Player connected");
                Player player = new Player(socket);
                new Thread(player).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
