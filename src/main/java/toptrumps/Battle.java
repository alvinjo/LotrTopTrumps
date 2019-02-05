package toptrumps;

import toptrumps.deck.Card;
import java.util.*;

public class Battle {

    private List<Card> cardPile;
    private static Battle battle;
    private Game game;

    private Battle(Game game){
        cardPile = new ArrayList<>();
        this.game = game;
    }

    public static Battle getInstance(Game game){
        if(battle != null){
            return battle;
        }else{
            battle = new Battle(game);
            return battle;
        }
    }

    public synchronized void transferCards(Player winner){
        List<Player> activePlayers = game.getActivePlayers();
        List<Card> newDeck;
        for (int i = 0; i < activePlayers.size(); i++) {
            Player loser = activePlayers.get(i);
            if(!activePlayers.get(i).equals(winner)){
                newDeck = new ArrayList<>(loser.getDeck());

                winner.addCard(loser.getDeck().get(0));
                newDeck.remove(0);
                loser.setDeck(newDeck);
            }
        }
        playerWinsCardPile(winner);
    }

    private void playerWinsCardPile(Player winner){
        for (Card c : cardPile) {
            winner.addCard(c);
        }
        cardPile.clear();
    }

    public synchronized void addCardsToPile(){
        List<Player> activePlayers = Game.getActivePlayers();
        List<Card> newCardPile = new ArrayList<>(cardPile);
        for(Iterator<Player> pIterator = activePlayers.iterator(); pIterator.hasNext();) {
            newCardPile.add(pIterator.next().getDeck().get(0));
        }
        removeAllTopCards();
        setCardPile(newCardPile);
    }

    private void removeAllTopCards(){
        List<Player> activePlayers = Game.getActivePlayers();
        for(Iterator<Player> pIterator = activePlayers.iterator(); pIterator.hasNext();){
            Player player = pIterator.next();
            List<Card> newDeck = new ArrayList<>(player.getDeck().subList(1, player.getDeck().size()));
            player.setDeck(newDeck);
        }
    }

    public Player getWinnerOrDraw(String[] attribAndCondition){
        List<Player> players = sortPlayersByAttributeStat(attribAndCondition);
        boolean topTwoPlayersTie = getValueOfAttribute(attribAndCondition[0], players.get(0)) == getValueOfAttribute(attribAndCondition[0], players.get(1));
        if(topTwoPlayersTie){
            return null;
        }
        return players.get(0);
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


    public static void displayBattleResultTable(String[] attribCondition, Game game){
        List<Player> activePlayers = Game.getActivePlayers();
        for (Player player : activePlayers) {
            player.printOut(battleResultTable(attribCondition));
        }
    }

    private static String battleResultTable(String[] attribCondition){
        List<Player> activePlayers = sortPlayersByAttributeStat(attribCondition);

        StringBuilder sb = new StringBuilder();
        String attribStatHeader = getAttributeNameFromInput(attribCondition[0])
                                + getConditionFromInput(attribCondition[1]);
        sb.append("\n\r").append("###########################################################");
        sb.append("\n\r").append(String.format("%-17s%-25s%-17s", "Player", "Card" ,attribStatHeader));
        for (Player p : activePlayers) {
            sb.append("\n\r");
            sb.append(String.format("%-17s%-25s%-17d", p.getUsername(), p.getDeck().get(0).getName(),
                    getValueOfAttribute(attribCondition[0], p)));
        }
        sb.append("\n\r").append("###########################################################");
        return sb.toString();
    }

    private static List<Player> sortPlayersByAttributeStat(String[] attribCondition){
        List<Player> sortedPlayers = Game.getActivePlayers();
        sortedPlayers.sort((o1, o2) -> {
            int winnerStat = getValueOfAttribute(attribCondition[0], o1);
            int enemyStat = getValueOfAttribute(attribCondition[0], o2);
            if(winnerStat == enemyStat){
                return 0;
            }
            return (highestWinsOrLowestWins(attribCondition[1], winnerStat, enemyStat)) ? 1 : -1;
        });
        return sortedPlayers;
    }

    private static String getConditionFromInput(String conditionShortHand){
        if(conditionShortHand.equals("l")||conditionShortHand.equals("low")){
            return "(low)";
        }
        return "(high)";
    }

    private static String getAttributeNameFromInput(String attributeShortHand){
        switch (attributeShortHand){
            case "rs":
                return "Resistance";
            case "a":
                return "Age";
            case "rl":
                return "Resilience";
            case "f":
                return "Ferocity";
            case "m":
                return "Magic";
            case "h":
                return "Height";
            default:
                return "?";                   // #####DO SOMETHING ELSE HERE
        }
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
