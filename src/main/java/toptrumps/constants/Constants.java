package toptrumps.constants;

public class Constants {

    public static final int SERVER_PORT = 8888;

    //Input Class
    public static final String LOGIN_INPUT_TEXT = "Enter username (1-15 alphanumeric characters): ";
    public static final String LOGIN_USERNAME_SET_TO = "Username set to: ";
    public static final String LOGIN_BAD_USERNAME = "Bad username";
    public static final String ATTRIBUTE_INPUT_TEXT = "\n\rSelect attribute to compare\n\r" +
            "Resistance(Rs) Age(A) Resilience(Rl) Ferocity(F) Magic(M) Height(H)\n\r" +
            "Followed by a high(H) or low(L) condition, separated by a space e.g. Rs H\n\r";
    public static final String ATTRIBUTE_BAD_INPUT = "Bad Input. Try again.";
    public static final String USERNAME_REGEX = "^[a-zA-Z0-9]{1,15}$";
    public static final String ATTRIBUTE_REGEX = "rs|resistance|rl|resilience|a|age|f|ferocity|m|magic|h|height";
    public static final String CONDITION_REGEX = "h|high|l|low";


    //Battle Class
    public static final String WORD_RESISTANCE = "Resistance";
    public static final String WORD_AGE = "Age";
    public static final String WORD_RESILIENCE = "Resilience";
    public static final String WORD_FEROCITY = "Ferocity";
    public static final String WORD_MAGIC = "Magic";
    public static final String WORD_HEIGHT = "Height";
    public static final String TABLE_BORDER = "###########################################################";
    public static final String TABLE_HEADER_FORMAT = "%-17s%-25s%-17s";
    public static final String TABLE_HEADER_PLAYER = "Player";
    public static final String TABLE_HEADER_CARD = "Card";
    public static final String TABLE_FORMAT = "%-17s%-25s%-17d";


    //Player Class
    public static final String MY_TURN = "\n\rMy turn";
    public static String NOT_MY_TURN(String username){return "\n\r" + username + " is making a move";}
    public static String HOW_MANY_CARDS(int num){return "You have " + num + " card/s";}

    //Game Class
    public static final int ROUND_LIMIT = 20;
    public static final String ROUND_WIN_MESSAGE = "\n\rYou won! The cards have been added to your deck.";
    public static String ROUND_LOSE_MESSAGE(String username){return "\n\r" + username + " won! You lost a card!";}
    public static final String ROUND_DRAW_MESSAGE = "\n\rOne or more players tied! Everyone's top card is added to the card pile.";

    //EndGame Class
    public static final String WIN_MESSAGE = "\n\r##### You win the game! #####";
    public static final String LOSE_MESSAGE = "\n\r##### You ran out of cards! #####";
    public static String DRAW_SCENARIO_LOSE(String username){return "\n\r##### " + username + " won #####";}
    public static final String DRAW_SCENARIO_DRAW = "\n\r##### It was a draw! #####";
    public static final String ROUND_LIMIT_MESSAGE = "\n\r##### ROUND LIMIT REACHED #####";

    //DeckBuilder Class
    public static final String PATH_TO_CARD_FILE = "C:\\Users\\Alvin\\IdeaProjects\\Lotr\\src\\main\\java\\toptrumps\\deck\\cardsoriginal.txt";

}
