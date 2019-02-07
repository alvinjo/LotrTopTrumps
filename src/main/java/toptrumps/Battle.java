package toptrumps;

import toptrumps.deck.Card;
import java.util.*;

import static toptrumps.constants.Constants.*;

public class Battle {

    private static List<Card> cardPile;
    private static Battle battle;

    private Battle(){
        cardPile = new ArrayList<>();
    }

    public static Battle getInstance(){
        if(battle != null){
            return battle;
        }else{
            battle = new Battle();
            return battle;
        }
    }

    public static synchronized void transferCards(Player winner){
        List<Player> activePlayers = Game.getActivePlayers();
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

    private static void playerWinsCardPile(Player winner){
        for (Card c : cardPile) {
            winner.addCard(c);
        }
        cardPile.clear();
    }

    public static synchronized void addCardsToPile(){
        List<Player> activePlayers = Game.getActivePlayers();
        List<Card> newCardPile = new ArrayList<>(cardPile);
        for(Iterator<Player> pIterator = activePlayers.iterator(); pIterator.hasNext();) {
            newCardPile.add(pIterator.next().getDeck().get(0));
        }
        removeAllTopCards();
        setCardPile(newCardPile);
    }

    private static void removeAllTopCards(){
        List<Player> activePlayers = Game.getActivePlayers();
        for(Iterator<Player> pIterator = activePlayers.iterator(); pIterator.hasNext();){
            Player player = pIterator.next();
            List<Card> newDeck = new ArrayList<>(player.getDeck().subList(1, player.getDeck().size()));
            player.setDeck(newDeck);
        }
    }

    public static Player getWinnerOrDraw(String[] attribAndCondition){
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
        switch(attrib.toLowerCase()){
            case "rs":
            case "resistance":
                value = Stats.getResistanceStat(player);
                break;
            case "a":
            case "age":
                value = Stats.getAgeStat(player);
                break;
            case "rl":
            case "resilience":
                value = Stats.getResilienceStat(player);
                break;
            case "f":
            case "ferocity":
                value = Stats.getFerocityStat(player);
                break;
            case "m":
            case "magic":
                value = Stats.getMagicStat(player);
                break;
            case "h":
            case "height":
                value = Stats.getHeightStat(player);
                break;
             default:
                 value = Stats.getResistanceStat(player);                        // #####DO SOMETHING ELSE HERE
                 break;
        }
        return value;
    }


    public static void displayBattleResultTable(String[] attribCondition){
        List<Player> activePlayers = Game.getActivePlayers();
        for (Player player : activePlayers) {
            player.printOut(battleResultTable(attribCondition));
        }
    }

    private static String battleResultTable(String[] attribCondition){
        List<Player> activePlayers = sortPlayersByAttributeStat(attribCondition);

        StringBuilder sb = new StringBuilder();
        String attribStatHeader = getAttributeNameFromInput(attribCondition[0]) + getConditionFromInput(attribCondition[1]);
        sb.append("\n\r").append(TABLE_BORDER);
        sb.append("\n\r").append(String.format(TABLE_HEADER_FORMAT, TABLE_HEADER_PLAYER, TABLE_HEADER_CARD ,attribStatHeader));
        for (Player p : activePlayers) {
            sb.append("\n\r");
            sb.append(String.format(TABLE_FORMAT, p.getUsername(), p.getDeck().get(0).getName(),
                    getValueOfAttribute(attribCondition[0], p)));
        }
        sb.append("\n\r").append(TABLE_BORDER);
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
            case "resistance":
                return "Resistance";
            case "a":
            case "age":
                return "Age";
            case "rl":
            case "resilience":
                return "Resilience";
            case "f":
            case "ferocity":
                return "Ferocity";
            case "m":
            case "magic":
                return "Magic";
            case "h":
            case "height":
                return "Height";
            default:
                return "?";                   // #####DO SOMETHING ELSE HERE
        }
    }

    private static void setCardPile(List<Card> cardPile){
        Battle.cardPile = cardPile;
    }

}
