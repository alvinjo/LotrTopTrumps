package toptrumps;

import toptrumps.deck.Card;
import toptrumps.deck.DeckBuilder;

import java.util.*;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.Stream;

public class Game {

    private static List<Card> deck;
    private static CyclicBarrier barrier;
    private static List<Player> playerList;
    private static boolean[] activePlayers;
    private static boolean cardsDealt;
    private static int whosTurnIsIt;
    private static int newBarrierSize;
    private static boolean barrierChanged = false;
    private static int roundNumber;

    public Game(int numOfPlayers){
        deck = DeckBuilder.getDeck();
        barrier = new CyclicBarrier(numOfPlayers);
        playerList = new ArrayList<>();
        activePlayers = new boolean[numOfPlayers];
        initActivePlayers();
        cardsDealt = false;
        whosTurnIsIt = 0;
        roundNumber = 0;
    }

    private static void initActivePlayers(){
        for (int i = 0; i < activePlayers.length; i++) {
            activePlayers[i] = true;
        }
    }


    public static void displayWinnerOfRound(Player winner){
        List<Player> activePlayers = getActivePlayers();
        for (Player p : activePlayers) {
            if(p.equals(winner)){
                p.printOut("\nYou won! The cards have been added to your deck.");
            }else{
                p.printOut("\n" + winner.getUsername() + " won! You lost a card!");
            }
        }
    }

    public static void displayDrawResult(){
        List<Player> activePlayers = getActivePlayers();
        String drawMessage = "\nOne or more players tied! Everyone's top card is added to the card pile.";
        activePlayers.forEach(p -> p.printOut(drawMessage));
    }


    public static Player getWhosTurnIsIt() {
        return playerList.get(whosTurnIsIt);
    }


    public static void removeLosers(){
        List<Player> players = new ArrayList<>(playerList);
        for (int i = 0; i < players.size(); i++) {
            if(players.get(i).getDeck().size()==0){
                activePlayers[i] = false;
                barrierChanged = true;
            }
        }
        newBarrierSize = (int) getNumOfActivePlayers();
    }

    public static void incrementWhosTurnIsIt(){
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


    public static void endGameMessage(Player p){
        if(getActivePlayers().size() > 1){
            if(getPlayerWithMostCards() != null){
              p.printOut("\n\r##### It was a draw! #####");
            }
        }else if (checkIfActive(p)){
            p.printOut("\n\r##### You win the game! #####");
        }else{
            p.printOut("\n\r##### You ran out of cards! #####");
        }
    }

    private static Player getPlayerWithMostCards(){
        List<Player> players = getActivePlayers();
        players.sort((p1, p2) -> {
            if(p1.getDeck().size() == p2.getDeck().size()){
                return 0;
            }
            return (p1.getDeck().size() > p2.getDeck().size()) ? 1 : -1;
        });

        if(players.get(0).getDeck().size() == players.get(1).getDeck().size()){
            return null;
        }else{
            return players.get(0);
        }
    }

    public static synchronized void dealCards(){
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


    public static void addPlayerToList(Player player){
        playerList.add(player);
    }

    public static CyclicBarrier getBarrier() {
        return barrier;
    }

    public static synchronized void makeBarrierChanges(){
        if(barrierChanged){
            barrier = new CyclicBarrier(newBarrierSize);
        }
        barrierChanged = false;
    }

    public static List<Player> getActivePlayers(){
        List<Player> active = new ArrayList<>();
        for (int i = 0; i < activePlayers.length; i++) {
            if(activePlayers[i]){
                active.add(playerList.get(i));
            }
        }
        return active;
    }

    public static long getNumOfActivePlayers(){
        int count = 0;
        for (boolean activePlayer : activePlayers) {
            if (activePlayer) {
                count++;
            }
        }
        return count;
    }

    public static boolean checkIfActive(Player player) {
        for (int i = 0; i < playerList.size(); i++) {
            if(playerList.get(i).equals(player)){
                return activePlayers[i];
            }
        }
        return false;
    }

    public static int getRoundNumber(){
        return roundNumber;
    }

    public static void incrementRoundNumber(){
        roundNumber++;
    }

}
