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
    private Game game;
    private Scanner in;
    private PrintWriter out;
    private String username;
    private Battle battle;

    public Player(Game game, Socket socket){
        this.game = game;

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

        while(nobodyHasWonYet() && stillInTheGame()){
            game.makeBarrierChanges();
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
        roundStartMessages(actingPlayer);

        if(actingPlayer.equals(this)){
            cardAttribSelection();
        }
    }

    private boolean stillInTheGame(){
        return game.checkIfActive(this);
    }

    private boolean nobodyHasWonYet(){
        return game.getNumOfActivePlayers() != 1;
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
        battle = Battle.getInstance(game);
        Player winner = battle.getWinnerOrDraw(attributeAndCondition);

        boolean someoneWon = winner != null;
        Battle.displayBattleResultTable(attributeAndCondition, game);
        if(someoneWon){
            battle.transferCards(winner);
            winner.sendCardToBack();
            game.displayWinnerOfRound(winner);
            game.removeLosers();
            game.incrementWhosTurnIsIt();
        }else{
            battle.addCardsToPile();
            game.displayDrawResult();
            game.removeLosers();
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
