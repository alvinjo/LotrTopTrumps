package toptrumps.player;

public class Stats {

    public static int getResilienceStat(Player player){
        return player.getDeck().get(0).getResilience();
    }
    public static int getAgeStat(Player player){
        return player.getDeck().get(0).getAge();
    }
    public static int getResistanceStat(Player player){
        return player.getDeck().get(0).getResistance();
    }
    public static int getFerocityStat(Player player){
        return player.getDeck().get(0).getFerocity();
    }
    public static int getMagicStat(Player player){
        return player.getDeck().get(0).getMagic();
    }
    public static int getHeightStat(Player player){
        return player.getDeck().get(0).getHeight();
    }
}
