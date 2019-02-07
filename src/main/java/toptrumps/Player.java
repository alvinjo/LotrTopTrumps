package toptrumps;

import toptrumps.deck.Card;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;

public class Player implements Runnable {

    private List<Card> deck;
    private Scanner in;
    private PrintWriter out;
    private String username;
    private Battle battle;

    public Player(Socket socket){
        try{
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void run() {
        login();
        waitForOtherPlayers();
        Game.dealCards();
        displayCards();

        while(nobodyHasWonYet() && stillInTheGame()){
            Game.makeBarrierChanges();
            checkTurn();
            waitForOtherPlayers();
        }
        Game.endGameMessage(this);
    }

    private void login(){
        username = Input.login(in, out);
        Game.addPlayerToList(this);
    }

    private void displayCards(){
        deck.forEach(out::println);
    }

    private void checkTurn(){
        Player actingPlayer = Game.getWhosTurnIsIt();
        roundStartMessages(actingPlayer);

        if(actingPlayer.equals(this)){
            cardAttribSelection();
        }
    }

    private boolean stillInTheGame(){
        return Game.checkIfActive(this);
    }

    private boolean nobodyHasWonYet(){
        return Game.getNumOfActivePlayers() != 1;
    }

    private void roundStartMessages(Player actingPlayer){
        if(actingPlayer.equals(this)){
            myTurn();
        }else{
            notMyTurn(actingPlayer.getUsername());
        }
        howManyCards();
        displayCurrentCard();
    }

    private void myTurn(){
        out.println("\nMy turn");
    }

    private void notMyTurn(String actingPlayer){
        out.println("\n" + actingPlayer + " is making a move");
    }

    private void howManyCards(){
        out.println("You have " + deck.size() + " card/s");
    }

    private void displayCurrentCard(){
        out.println("\n" + deck.get(0));
    }

    private void cardAttribSelection(){
        String[] attributeAndCondition = Input.attribInput(in, out);
        battle(attributeAndCondition);
    }



    private void battle(String[] attributeAndCondition){
        battle = Battle.getInstance();
        Player winner = battle.getWinnerOrDraw(attributeAndCondition);

        boolean someoneWon = winner != null;
        Battle.displayBattleResultTable(attributeAndCondition);
        if(someoneWon){
            battle.transferCards(winner);
            winner.sendCardToBack();
            Game.displayWinnerOfRound(winner);
            Game.removeLosers();
            Game.incrementWhosTurnIsIt();
        }else{
            battle.addCardsToPile();
            Game.displayDrawResult();
            Game.removeLosers();
            if(!Game.checkIfActive(this)){
                Game.incrementWhosTurnIsIt();
            }
        }
    }

    private void sendCardToBack(){
        deck.add(deck.get(0));
        deck.remove(0);
    }

    public void printOut(String s){
        out.println(s);
    }

    private void waitForOtherPlayers(){
        try{
            Game.getBarrier().await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public void addCard(Card card){
        List<Card> newDeck = new ArrayList<>(deck);
        newDeck.add(card);
        setDeck(newDeck);
    }

    @Override
    public String toString(){
        return "Username: " + username;
    }
}
