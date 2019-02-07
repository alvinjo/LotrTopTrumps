package toptrumps.constants;

public class Constants {

    public static final int SERVER_PORT = 8888;

    //Input Class
    public static final String LOGIN_INPUT_TEXT = "Enter username (1-15 alphanumeric characters): ";
    public static final String LOGIN_USERNAME_SET_TO = "Username set to: ";
    public static final String LOGIN_BAD_USERNAME = "Bad username";
    public static final String ATTRIBUTE_INPUT_TEXT = "\nSelect attribute to compare\n\r" +
            "Resistance(Rs) Age(A) Resilience(Rl) Ferocity(F) Magic(M) Height(H)\n\r" +
            "Followed by a high(H) or low(L) condition, separated by a space e.g. Rs H\n\r";
    public static final String ATTRIBUTE_BAD_INPUT = "Bad Input. Try again.";
    public static final String USERNAME_REGEX = "^[a-zA-Z0-9]{1,15}$";
    public static final String ATTRIBUTE_REGEX = "rs|resistance|rl|resilience|a|age|f|ferocity|m|magic|h|height";
    public static final String CONDITION_REGEX = "h|high|l|low";

    //Battle Class
    public static final String TABLE_BORDER = "###########################################################";
    public static final String TABLE_HEADER_FORMAT = "%-17s%-25s%-17s";
    public static final String TABLE_HEADER_PLAYER = "Player";
    public static final String TABLE_HEADER_CARD = "Card";
    public static final String TABLE_FORMAT = "%-17s%-25s%-17d";

}
