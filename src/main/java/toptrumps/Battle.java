package toptrumps;

import toptrumps.deck.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Battle {

    private List<Card> cardPile;
    private List<Player> playerList;
    private static Battle battle;

    private Battle(Game game){
        cardPile = new ArrayList<>();
//        playerList = game.getPlayerList();
        playerList = new CopyOnWriteArrayList<>(game.getPlayerList());
        System.out.println("game.playerlist: " + game.getPlayerList().toString());
    }

    public static Battle getInstance(Game game){
        if(battle != null){
            return battle;
        }else{
            battle = new Battle(game);
            return battle;
        }

    }

    private synchronized void transferCards(Player winner){
        List<Card> newDeck;
        for (int i = 0; i < playerList.size(); i++) {
            Player loser = playerList.get(i);
            if(!playerList.get(i).equals(winner)){
                newDeck = new ArrayList<>(loser.getDeck());

                winner.addCard(loser.getDeck().get(0));
                newDeck.remove(0);
                loser.setDeck(newDeck);
            }
        }
        playerWinsCardPile(winner);
    }

    private List<Card> getPlayerDeck(Player player){
        List<Card> newDeck = new ArrayList<>(player.getDeck());
        return newDeck;
    }

    private void playerWinsCardPile(Player winner){
        for (Card c : cardPile) {
            winner.addCard(c);
        }
        cardPile.clear();
    }

    private synchronized void addCardsToPile(){
        List<Card> newCardPile = new ArrayList<>(cardPile);
        for (Iterator<Player> pIterator = playerList.iterator(); pIterator.hasNext();) {
            newCardPile.add(pIterator.next().getDeck().get(0));
        }
        removeAllTopCards();
        setCardPile(newCardPile);
        displayCardPile();
    }

    private void removeAllTopCards(){
        for(Iterator<Player> pIterator = playerList.iterator(); pIterator.hasNext();){
//            pIterator.next().getDeck().remove(0);
            Player player = pIterator.next();
            List<Card> newDeck = new ArrayList<>(player.getDeck().subList(1, player.getDeck().size()));
            player.setDeck(newDeck);
        }
    }


    private void displayCardPile(){
        System.out.println("cardpile: " + cardPile.toString());
    }


    public Player getWinnerOrDraw(String[] attributeAndCondition){
        System.out.println("plist size: " + playerList.size());
        Player winner = playerList.get(0);
        for (int i = 1; i < playerList.size(); i++) {
            int winnersStat = getValueOfAttribute(attributeAndCondition[0], winner);
            int enemyStat = getValueOfAttribute(attributeAndCondition[0], playerList.get(i));
            System.out.print("#wcard " + winner.getDeck().get(0).getName());
            System.out.println(" #ecard " + playerList.get(i).getDeck().get(0).getName());
            System.out.println("##wstat " + winnersStat + " ##estat " + enemyStat);
            if(winnersStat == enemyStat){
                System.out.println("its a draw");
                addCardsToPile();
                return null;
            }else if(highestWinsOrLowestWins(attributeAndCondition[1], winnersStat, enemyStat)){
//                System.out.println("hlwins " + attributeAndCondition[0] + attributeAndCondition[1]  );
                winner = playerList.get(i);
            }
        }
        transferCards(winner);
        winner.sendCardToBack();
        return winner;
    }


    private static boolean highestWinsOrLowestWins(String condition, int winnersStat, int enemyStat){
        return checkConditionHigh(condition) ? winnersStat < enemyStat : winnersStat > enemyStat;
    }

    private static boolean checkConditionHigh(String condition){
        switch(condition){
            case "h":
            case "high":
                return true;
            case "l":
            case "low":
                return false;
            default:
                return true;
        }
    }


    private static int getValueOfAttribute(String attrib, Player player){
        int value;
        switch(attrib){
            case "rs":
                value = getResistanceStat(player);
                break;
            case "a":
                value = getAgeStat(player);
                break;
            case "rl":
                value = getResilienceStat(player);
                break;
            case "f":
                value = getFerocityStat(player);
                break;
            case "m":
                value = getMagicStat(player);
                break;
            case "h":
                value = getHeightStat(player);
                break;
             default:
                 value = -1;                        // #####DO SOMETHING ELSE HERE
                 break;
        }
        return value;
    }

    private static int getResilienceStat(Player player){
        return player.getDeck().get(0).getResilience();
    }
    private static int getAgeStat(Player player){
        return player.getDeck().get(0).getAge();
    }
    private static int getResistanceStat(Player player){
        return player.getDeck().get(0).getResistance();
    }
    private static int getFerocityStat(Player player){
        return player.getDeck().get(0).getFerocity();
    }
    private static int getMagicStat(Player player){
        return player.getDeck().get(0).getMagic();
    }
    private static int getHeightStat(Player player){
        return player.getDeck().get(0).getHeight();
    }

    private void setCardPile(List<Card> cardPile){
        this.cardPile = cardPile;
    }

}
