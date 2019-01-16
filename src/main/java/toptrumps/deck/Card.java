package toptrumps.deck;

public class Card {

    private String name;
    private String culture;
    private String factfile;
    private int resistance;
    private int age;
    private int resilience;
    private int ferocity;
    private int magic;
    private int height;

    public Card(String name, String culture, int resistance, int age, int resilience, int ferocity, int magic, int height) {
        this.name = name;
        this.culture = culture;
        this.resistance = resistance;
        this.age = age;
        this.resilience = resilience;
        this.ferocity = ferocity;
        this.magic = magic;
        this.height = height;
    }

    public Card(){
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public String getFactfile() {
        return factfile;
    }

    public void setFactfile(String factfile) {
        this.factfile = factfile;
    }

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getResilience() {
        return resilience;
    }

    public void setResilience(int resilience) {
        this.resilience = resilience;
    }

    public int getFerocity() {
        return ferocity;
    }

    public void setFerocity(int ferocity) {
        this.ferocity = ferocity;
    }

    public int getMagic() {
        return magic;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "{Name:"+name + ", Culture:"+culture + ", Factfile:"+factfile +  ", Resistance:"+resistance
                + ", Age:"+age + ", Resilience:"+resilience + ", Ferocity:"+ferocity + ", Magic:"+magic
                + ", Height:"+height + "}";
    }

}
