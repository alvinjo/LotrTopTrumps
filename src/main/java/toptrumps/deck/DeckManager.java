package toptrumps.deck;

import toptrumps.game.Game;
import toptrumps.player.Player;

import java.util.Collections;
import java.util.List;

public class DeckManager {

    private static boolean cardsDealt = false;
    private static List<Card> deck;

    public static synchronized void dealCards(List<Player> playerList){
        deck = DeckBuilder.getDeck();
        if(!cardsDealt){
            int parts = playerList.size();
            int[] cardDistribution = calcDistribution(parts);

            Collections.shuffle(deck);
            for (int i = 0; i < parts; i++) {
                playerList.get(i).setDeck(deck.subList(0, cardDistribution[i]));
                deck = deck.subList(cardDistribution[i], deck.size());
            }
        }
        cardsDealt = true;
    }

    private static int[] calcDistribution(int parts){
        int cards = deck.size();
        int[] distribution = new int[parts];

        for (int i = 0; i < distribution.length; i++) {
            distribution[i] = cards/parts;
            cards -= cards/parts;
            parts--;
        }
        return distribution;
    }

    public static Player getPlayerWithMostCards(){
        List<Player> players = Game.getActivePlayers();
        players.sort((p1, p2) -> {
            if(p1.getDeck().size() == p2.getDeck().size()){
                return 0;
            }
            return (p1.getDeck().size() > p2.getDeck().size()) ? -1 : 1;
        });

        if(players.get(0).getDeck().size() == players.get(1).getDeck().size()){
            return null;
        }else{
            return players.get(0);
        }
    }
}
