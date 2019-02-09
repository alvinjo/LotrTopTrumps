package toptrumps;

import toptrumps.deck.DeckBuilder;
import toptrumps.deck.DeckManager;

import java.util.*;
import java.util.concurrent.CyclicBarrier;

import static toptrumps.constants.Constants.*;

public class Game {

    private static CyclicBarrier barrier;
    private static List<Player> playerList;
    private static boolean[] activePlayers;
    private static int whosTurnIsIt;
    private static int newBarrierSize;
    private static boolean barrierChanged = false;
    private static int roundNumber;

    public Game(int numOfPlayers){
        DeckBuilder.getDeck();
        barrier = new CyclicBarrier(numOfPlayers);
        playerList = new ArrayList<>();
        activePlayers = new boolean[numOfPlayers];
        initActivePlayers();
        whosTurnIsIt = 0;
        roundNumber = 0;
    }

    public static void dealCards(){
        DeckManager.dealCards(playerList);
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
                p.printOut(ROUND_WIN_MESSAGE);
            }else{
                p.printOut("\n\r" + winner.getUsername() + " won! You lost a card!");
            }
        }
    }

    public static void displayDrawResult(){
        List<Player> activePlayers = getActivePlayers();
        activePlayers.forEach(p -> p.printOut(ROUND_DRAW_MESSAGE));
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
        if(getActivePlayers().size() > 1 && getRoundNumber() == ROUND_LIMIT){
            drawMessage(p);
          }else if (checkIfActive(p)){
            p.printOut(WIN_MESSAGE);
        }else{
            p.printOut(LOSE_MESSAGE);
        }
    }

    private static void drawMessage(Player p){
        p.printOut(ROUND_LIMIT_MESSAGE);
        Player winner = DeckManager.getPlayerWithMostCards();
        if(winner != null){
            if(winner == p){
                p.printOut(WIN_MESSAGE);
            }
            p.printOut("\n\r##### " + winner.getUsername() + " won #####");
        }else{
            p.printOut(DRAW_MESSAGE);
        }
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