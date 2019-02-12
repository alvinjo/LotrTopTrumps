package toptrumps.player;

import static toptrumps.constants.Constants.*;

public class Stats {

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

    public static Object getAttribValueOrName(String attrib, Player player){
        switch(attrib){
            case "rs":
            case "resistance":
                return (player != null) ? getResistanceStat(player) : WORD_RESISTANCE;
            case "a":
            case "age":
                return (player != null) ? getAgeStat(player) : WORD_AGE;
            case "rl":
            case "resilience":
                return (player != null) ? getResilienceStat(player) : WORD_RESILIENCE;
            case "f":
            case "ferocity":
                return (player != null) ? getFerocityStat(player) : WORD_FEROCITY;
            case "m":
            case "magic":
                return (player != null) ? getMagicStat(player) : WORD_MAGIC;
            case "h":
            case "height":
                return (player != null) ? getHeightStat(player) : WORD_HEIGHT;
            default:
                return (player != null) ? getResistanceStat(player) : WORD_RESISTANCE;
        }
    }
}
