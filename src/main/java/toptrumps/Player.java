package toptrumps;

import toptrumps.constants.Enums;
import toptrumps.deck.Card;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;

public class Player implements Runnable {

    private List<Card> deck;
    private Game game;
    private Scanner in;
    private PrintWriter out;
    private String username;
    private Battle battle;

    public Player(Game game, Socket socket){
        this.game = game;

        System.out.println("thread created");

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
        game.dealCards();
        displayCards();

        //for n times
        // or until no more cards
        // players removed from playerList when deck is empty

        for (int i = 0; i < 10; i++) {
            checkTurn();
            waitForOtherPlayers();
        }


    }

    private void checkTurn(){
        Player actingPlayer = game.getWhosTurnIsIt();
        if(actingPlayer.equals(this)){
            myTurn();
            displayCurrentCard();
            cardAttribSelection();
        }else{
            out.println("\nNot my turn");
            out.println(deck.size() + " cards");
            displayCurrentCard();
            notMyTurn(actingPlayer.getUsername());
        }
    }

    public void howManyCards(){
        int numOfCards = deck.size();
        out.println(numOfCards + " cards");
    }

    private void myTurn(){
        out.println("\nMy turn");
        out.println(deck.size() + " cards");
    }

    public void notMyTurn(String actingPlayer){
        out.println("\n" + actingPlayer + " is making a move");
    }


    private void cardAttribSelection(){
        getAttribInput();
    }


    private void displayCurrentCard(){
        out.println("\n" + deck.get(0));
    }

    private void getAttribInput(){
        String[] attributeAndCondition = Input.attribInput(in, out);
        battle(attributeAndCondition);
    }

    private void battle(String[] attributeAndCondition){
        battle = Battle.getInstance(game);
        Player winner = battle.getWinnerOrDraw(attributeAndCondition);

        boolean someoneWon = winner != null;
        if(someoneWon){
            game.displayWinnerOfRound(winner);
            game.turnFinished();
        }else{
            game.displayDrawResult();
            displayCurrentCard();
            cardAttribSelection();
        }
    }


    private void waitingForPlayerSelect(){
        out.println("Another player is making their move...");
    }

    private void displayCards(){
        deck.forEach(out::println);
    }

    public void sendCardToBack(){
        deck.add(deck.get(0));
        deck.remove(0);
    }

    private boolean login(){
        username = Input.login(in, out);
        game.addPlayerToList(this);
        return true;
    }

    private void waitForOtherPlayers(){
        try{
            game.getBarrier().await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }


    public void printOut(String s){
        out.println(s);
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }

    public void addCard(Card card){
        deck.add(card);
    }

    @Override
    public String toString(){
        return "Username: " + username;
    }
}
