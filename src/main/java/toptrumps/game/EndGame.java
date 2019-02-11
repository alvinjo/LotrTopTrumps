package toptrumps.game;

import toptrumps.deck.DeckManager;
import toptrumps.player.Player;

import static toptrumps.constants.Constants.*;
import static toptrumps.game.Game.*;

public class EndGame {

    public static void messages(Player thisPlayer){
        if(getActivePlayers().size()==1){ //one player wins
            winLoseMessage(thisPlayer);
        }else if(!roundLimitNotReached()){ //round limit reached
            drawScenario(thisPlayer);
        }else{
            winLoseMessage(thisPlayer); //player out of game
        }
    }

    private static void winLoseMessage(Player thisPlayer){
        if(checkIfActive(thisPlayer)){
            thisPlayer.printOut(WIN_MESSAGE);
        }else{
            thisPlayer.printOut(LOSE_MESSAGE);
        }
    }

    private static void drawScenario(Player thisPlayer){
        thisPlayer.printOut(ROUND_LIMIT_MESSAGE);
        Player winner = DeckManager.getPlayerWithMostCards();
        if(winner != null){
            if(winner.equals(thisPlayer)){
                thisPlayer.printOut(WIN_MESSAGE);
            }else{
                thisPlayer.printOut(DRAW_SCENARIO_LOSE(winner.getUsername()));
            }
        }else{
            thisPlayer.printOut(DRAW_SCENARIO_DRAW);
        }
    }
}
