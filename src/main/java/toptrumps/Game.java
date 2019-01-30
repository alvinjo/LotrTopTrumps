package toptrumps;

import toptrumps.deck.Card;
import toptrumps.deck.DeckBuilder;

import java.util.*;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.Stream;

public class Game {

    private static List<Card> deck;
    private int playersConnected;
    private static CyclicBarrier barrier;
    private List<Player> playerList;
    private boolean[] activePlayers;
    private boolean cardsDealt;
    private int whosTurnIsIt;
    private int numOfPlayers;

    public Game(int numOfPlayers){
        this.numOfPlayers = numOfPlayers;
        deck = DeckBuilder.getDeck();
        playersConnected = 0;
        barrier = new CyclicBarrier(numOfPlayers);
        playersConnected = 0;
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
                System.out.println("someone removed");
                activePlayers[i] = false;
//                reduceBarrier();
            }
        }
        System.out.println(Arrays.toString(activePlayers));
    }

    public void incrementWhosTurnIsIt(){
        System.out.println("## method call");
        System.out.println("whosturn: " + whosTurnIsIt);
        if(whosTurnIsIt + 1 == activePlayers.length){
            System.out.println("endoflist");
            for (int i = 0; i < activePlayers.length; i++) {
                if(activePlayers[i]){
                    System.out.println("found active");
                    whosTurnIsIt = i;
                    System.out.println(whosTurnIsIt);
                    break;
                }
            }
        }else{
            System.out.println("middleoflist");
            for (int i = whosTurnIsIt+1; i < activePlayers.length; i++) {
                if(activePlayers[i]){
                    System.out.println("foundactive");
                    whosTurnIsIt = i;
                    System.out.println(whosTurnIsIt);
                    break;
                }else{
                    System.out.println("middle but did not find. loop from beginning");
                    for (int j = 0; j < activePlayers.length; j++) {
                        if(activePlayers[j]){
                            System.out.println("found activ");
                            whosTurnIsIt = j;
                            break;
                        }
                    }
                }
            }
        }
        System.out.println("whosturn: " + whosTurnIsIt);
    }


    public void endGameMessage(Player p){ //TODO: needed?
//        List<Player> players = new ArrayList<>(playerList);
//        if(p.equals(players.get(0))){
//            p.printOut("\n\r##### You win the game! #####");
//        }else{
//            p.printOut("\n\r##### You ran out of cards! #####");
//        }
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


    public List<Player> getPlayerList(){
        return playerList;
    }

    public void addPlayerToList(Player player){
        playerList.add(player);
    }

    public static CyclicBarrier getBarrier() {
        return barrier;
    }

    private void reduceBarrier(){
        numOfPlayers--;
        barrier = new CyclicBarrier(numOfPlayers);
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
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

    public boolean checkIfActive(int i){
        return activePlayers[i];
    }

}
