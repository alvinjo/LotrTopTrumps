package toptrumps;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static toptrumps.constants.Constants.SERVER_PORT;

public class GameServer {

    private static Game game = new Game(3);

    //TODO: Refactor, check if method should be static. check if classes should be instances or util
    //TODO: Remove wandering TODO comments. Remove comments. Remove print statements.

    //TODO: decrement cyclic barrier after player loses
    //TODO:     players are waiting at cyclic barrier when its 3, while current player has decremented it
    //TODO:     how do you change the cyclic barrier while threads are waiting?
    //TODO:     currently, players are not removed. they continue to hit the waiting method while active players
    //TODO:         keep playing. out of play players do not see the endgame message

    //TODO: at a draw, if the current player has no more cards they are out and cannot repeat a turn
    //TODO: Check if endgame works with more than two players
    //TODO: Display who won to all players? Keep a untouched player list to continue displaying messages?
    //TODO: What happens if everyone draws? Display the draw to all players?
    //TODO: Move game methods from Player class to Game class. Put methods in correct classes
    //TODO: Game should end after certain number of rounds if no one has won. Player with most cards wins.


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
