package toptrumps;

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

        while(game.getPlayerList().size()!=1) {
            checkTurn();
            waitForOtherPlayers();
        }

        game.endGameMessage(this);

    }

    private boolean login(){
        username = Input.login(in, out);
        game.addPlayerToList(this);
        return true;
    }

    private void displayCards(){
        deck.forEach(out::println);
    }

    private void checkTurn(){
        Player actingPlayer = game.getWhosTurnIsIt();
        roundStartMessages();

        if(actingPlayer.equals(this)){
            cardAttribSelection();
        }
    }


    public void roundStartMessages(){
        Player actingPlayer = game.getWhosTurnIsIt();
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
        battle = Battle.getInstance(game);
        Player winner = battle.getWinnerOrDraw(attributeAndCondition);

        boolean someoneWon = winner != null;
        Battle.displayBattleResultTable(attributeAndCondition, game);
        if(someoneWon){
            battle.transferCards(winner);
            winner.sendCardToBack();
            game.displayWinnerOfRound(winner);
            game.removeLosers();
            game.turnFinished();
        }else{
            battle.addCardsToPile();
            game.displayDrawResult();
            game.removeLosers();
        }

    }




    public void sendCardToBack(){
        deck.add(deck.get(0));
        deck.remove(0);
    }



    public void printOut(String s){
        out.println(s);
    }

    private void waitForOtherPlayers(){
        try{
            game.getBarrier().await();
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
        deck.add(card);
    }

    @Override
    public String toString(){
        return "Username: " + username;
    }
}
