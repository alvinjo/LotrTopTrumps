package toptrumps;

import toptrumps.deck.Card;
import toptrumps.deck.DeckBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class Game {

    private static List<Card> deck;
    private int playersConnected;
    private static CyclicBarrier barrier;
    private List<Player> playerList;
    private boolean cardsDealt;
    private int whosTurnIsIt;


    public Game(int numOfPlayers){   //for selecting num of players.

    }


    public Game(){
        deck = DeckBuilder.getDeck();
        playersConnected = 0;
        barrier = new CyclicBarrier(2);
        playersConnected = 0;
        playerList = new ArrayList<Player>();
        cardsDealt = false;
        whosTurnIsIt = 0;
    }

    public List<Player> getPlayerList(){
        return playerList;
    }

    public void addPlayerToList(Player player){
        playerList.add(player);
    }

    public static List<Card> getDeck() {
        return deck;
    }

    public static CyclicBarrier getBarrier() {
        return barrier;
    }

    public int getPlayersConnected() {
        return playersConnected;
    }

    public void displayWinnerOfRound(Player winner){
        for (Player p : playerList) {
            if(p.equals(winner)){
                p.printOut("\nYou won! The cards have been added to your deck.");
            }else{
                p.printOut("\n" + winner.getUsername() + " won! You lost a card!");
            }
        }
    }

    public void displayDrawResult(){
        String drawMessage = "One or more players tied! Everyone's top card is added to the card pile.";
        playerList.forEach(p -> p.printOut(drawMessage));
    }


    public synchronized void dealCards(){
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


    private int[] calcDistribution(int parts){
        int cards = deck.size();
        int[] distribution = new int[parts];

        for (int i = 0; i < distribution.length; i++) {
            distribution[i] = cards/parts;
            cards -= cards/parts;
            parts--;
        }
        return distribution;
    }

    public boolean isCardsDealt() {
        return cardsDealt;
    }

    public Player getWhosTurnIsIt() {
        return playerList.get(whosTurnIsIt);
    }

    public void turnFinished(){
        whosTurnIsIt = (whosTurnIsIt+1==playerList.size()) ? 0 : whosTurnIsIt+1;
    }
}
