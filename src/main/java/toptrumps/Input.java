package toptrumps;

import java.io.PrintWriter;
import java.util.Scanner;

public class Input {

    public static String login(Scanner in, PrintWriter out){
        String username;
        do{
            username = (loginInput(in, out));
        }while(username == null);

        return username;
    }


    private static String loginInput(Scanner in, PrintWriter out){
        String username;
        out.println("Enter username (1-15 alphanumeric characters): ");
        username = in.nextLine();

        if(username.matches("^[a-zA-Z0-9]{1,15}$")){
            out.println("Username set to: " + username);
            return username;
        }
        out.println("Bad username");
        return null;
    }

    public static String[] attribInput(Scanner in, PrintWriter out){
        String[] choice;
        do{
            out.println("\nSelect attribute to compare");
            out.println("Resistance(Rs) Age(A) Resilience(Rl) Ferocity(F) Magic(M) Height(H)");
            out.println("Followed by a high(H) or low(L) condition, separated by a space e.g. Rs H ");
            choice = attribInputValidation(in, out);
        }while(choice == null);

        return choice;
    }

    private static String[] attribInputValidation(Scanner in, PrintWriter out){
        String[] choice = in.nextLine().toLowerCase().split(" ");
        String attribRegex = "rs|rl|a|f|m|h";
        String conditionRegex = "h|high|l|low";

        try{
            if(choice[0].matches(attribRegex) && choice[1].matches(conditionRegex)){
                return choice;
            }
        }catch (IndexOutOfBoundsException ignored){
        }
        out.println("Bad Input. Try again.");
        return null;
    }


}
