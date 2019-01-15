package toptrumps;

import toptrumps.deck.Card;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Battle {

    private List<Card> cardPile;
    private List<Player> playerList;

    public Battle(Game game){
        cardPile = new ArrayList<>();
        playerList = game.getPlayerList();
    }

    private synchronized void transferCards(Player winner){
//        for (Player player : playerList) {
//            if (!player.equals(winner)) {
//                winner.addCard(player.getDeck().get(0));
//                player.getDeck().remove(0);
//            }
//        }

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
    }

    private void addCardsToPile(){
        for (Player p: playerList) {
            cardPile.add(p.getDeck().get(0));
            p.getDeck().remove(0);
        }
    }


    public Player getWinnerOrDraw(String attrib){
        Player winner = playerList.get(0);
        for (int i = 1; i < playerList.size(); i++) {
            int winnersStat = getValueOfAttribute(attrib, winner);
            int enemyStat = getValueOfAttribute(attrib, playerList.get(i));
            if( winnersStat == enemyStat){
                addCardsToPile();
                return null;
            }else if(winnersStat < enemyStat){
                winner = playerList.get(i);
            }
        }
        transferCards(winner);
        winner.sendCardToBack();
        return winner;
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


}
