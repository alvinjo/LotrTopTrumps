package toptrumps.game;

import toptrumps.deck.Card;
import toptrumps.player.Player;
import toptrumps.player.Stats;

import java.util.*;

import static toptrumps.constants.Constants.*;

public class Battle {

    private static List<Card> cardPile = new ArrayList<>();

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
        boolean topTwoPlayersTie = (int) getAttribValueOrName(attribAndCondition[0], players.get(0)) == (int) getAttribValueOrName(attribAndCondition[0], players.get(1));
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


    private static Object getAttribValueOrName(String attrib, Player player){
        switch(attrib){
            case "rs":
            case "resistance":
                return (player != null) ? Stats.getResistanceStat(player) : WORD_RESISTANCE;
            case "a":
            case "age":
                return (player != null) ? Stats.getAgeStat(player) : WORD_AGE;
            case "rl":
            case "resilience":
                return (player != null) ? Stats.getResilienceStat(player) : WORD_RESILIENCE;
            case "f":
            case "ferocity":
                return (player != null) ? Stats.getFerocityStat(player) : WORD_FEROCITY;
            case "m":
            case "magic":
                return (player != null) ? Stats.getMagicStat(player) : WORD_MAGIC;
            case "h":
            case "height":
                return (player != null) ? Stats.getHeightStat(player) : WORD_HEIGHT;
            default:
                return (player != null) ? Stats.getResistanceStat(player) : WORD_RESISTANCE;
        }
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
        String attribStatHeader = getAttribValueOrName(attribCondition[0], null) + getConditionFromInput(attribCondition[1]);
        sb.append("\n\r").append(TABLE_BORDER);
        sb.append("\n\r").append(String.format(TABLE_HEADER_FORMAT, TABLE_HEADER_PLAYER, TABLE_HEADER_CARD ,attribStatHeader));
        for (Player p : activePlayers) {
            sb.append("\n\r");
            sb.append(String.format(TABLE_FORMAT, p.getUsername(), p.getDeck().get(0).getName(),
                    getAttribValueOrName(attribCondition[0], p)));
        }
        sb.append("\n\r").append(TABLE_BORDER);
        return sb.toString();
    }

    private static List<Player> sortPlayersByAttributeStat(String[] attribCondition){
        List<Player> sortedPlayers = Game.getActivePlayers();
        sortedPlayers.sort((o1, o2) -> {
            int winnerStat = (int) getAttribValueOrName(attribCondition[0], o1);
            int enemyStat = (int) getAttribValueOrName(attribCondition[0], o2);
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

    private static void setCardPile(List<Card> cardPile){
        Battle.cardPile = cardPile;
    }
}