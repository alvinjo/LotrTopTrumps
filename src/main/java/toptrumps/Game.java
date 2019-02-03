package toptrumps;

import toptrumps.deck.Card;
import toptrumps.deck.DeckBuilder;

import java.util.*;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.Stream;

public class Game {

    private static List<Card> deck;
    private static CyclicBarrier barrier;
    private List<Player> playerList;
    private boolean[] activePlayers;
    private boolean cardsDealt;
    private int whosTurnIsIt;
    private int newBarrierSize;
    private boolean barrierChanged = false;

    public Game(int numOfPlayers){
        deck = DeckBuilder.getDeck();
        barrier = new CyclicBarrier(numOfPlayers);
        playerList = new ArrayList<>();
        activePlayers = new boolean[numOfPlayers];
        initActivePlayers();
        cardsDealt = false;
        whosTurnIsIt = 0;
    }

    private void initActivePlayers(){
        for (int i = 0; i < activePlayers.length; i++) {
            activePlayers[i] = true;
        }
    }


    public void displayWinnerOfRound(Player winner){
        List<Player> activePlayers = getActivePlayers();
        for (Player p : activePlayers) {
            if(p.equals(winner)){
                p.printOut("\nYou won! The cards have been added to your deck.");
            }else{
                p.printOut("\n" + winner.getUsername() + " won! You lost a card!");
            }
        }
    }

    public void displayDrawResult(){
        List<Player> activePlayers = getActivePlayers();
        String drawMessage = "\nOne or more players tied! Everyone's top card is added to the card pile.";
        activePlayers.forEach(p -> p.printOut(drawMessage));
    }


    public Player getWhosTurnIsIt() {
        return playerList.get(whosTurnIsIt);
    }


    public void removeLosers(){
        List<Player> players = new ArrayList<>(playerList);
        for (int i = 0; i < players.size(); i++) {
            if(players.get(i).getDeck().size()==0){
                activePlayers[i] = false;
                barrierChanged = true;
            }
        }
        newBarrierSize = (int) getNumOfActivePlayers();
    }

    public void incrementWhosTurnIsIt(){
        if(whosTurnIsIt + 1 == activePlayers.length){
            for (int i = 0; i < activePlayers.length; i++) {
                if(activePlayers[i]){
                    whosTurnIsIt = i;
                    break;
                }
            }
        }else{
            for (int i = whosTurnIsIt+1; i < activePlayers.length; i++) {
                if(activePlayers[i]){
                    whosTurnIsIt = i;
                    break;
                }else{
                    for (int j = 0; j < activePlayers.length; j++) {
                        if(activePlayers[j]){
                            whosTurnIsIt = j;
                            break;
                        }
                    }
                }
            }
        }
    }


    public void endGameMessage(Player p){ //TODO: needed?
        if (checkIfActive(p)){
            p.printOut("\n\r##### You win the game! #####");
        }else{
            p.printOut("\n\r##### You ran out of cards! #####");
        }
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


    public void addPlayerToList(Player player){
        playerList.add(player);
    }

    public static CyclicBarrier getBarrier() {
        return barrier;
    }

    public synchronized void makeBarrierChanges(){
        if(barrierChanged){
            barrier = new CyclicBarrier(newBarrierSize);
        }
        barrierChanged = false;
    }

    public List<Player> getActivePlayers(){
        List<Player> active = new ArrayList<>();
        for (int i = 0; i < activePlayers.length; i++) {
            if(activePlayers[i]){
                active.add(playerList.get(i));
            }
        }
        return active;
    }

    public long getNumOfActivePlayers(){
        int count = 0;
        for (boolean activePlayer : activePlayers) {
            if (activePlayer) {
                count++;
            }
        }
        return count;
    }

    public boolean checkIfActive(Player player) {
        for (int i = 0; i < playerList.size(); i++) {
            if(playerList.get(i).equals(player)){
                return activePlayers[i];
            }
        }
        return false;
    }

}
