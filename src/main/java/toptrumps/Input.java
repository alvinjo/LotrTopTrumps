package toptrumps;

import toptrumps.constants.Constants;

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
        out.println(Constants.LOGIN_INPUT_TEXT);
        username = in.nextLine();

        if(username.matches(Constants.USERNAME_REGEX)){
            out.println(Constants.LOGIN_USERNAME_SET_TO + username);
            return username;
        }
        out.println(Constants.LOGIN_BAD_USERNAME);
        return null;
    }

    public static String[] attribInput(Scanner in, PrintWriter out){
        String[] choice;
        do{
            out.println(Constants.ATTRIBUTE_INPUT_TEXT);
            choice = attribInputValidation(in, out);
        }while(choice == null);

        return choice;
    }

    private static String[] attribInputValidation(Scanner in, PrintWriter out){
        String[] choice = in.nextLine().toLowerCase().split(" ");
        String attribRegex = Constants.ATTRIBUTE_REGEX;
        String conditionRegex = Constants.CONDITION_REGEX;

        try{
            if(choice[0].matches(attribRegex) && choice[1].matches(conditionRegex)){
                return choice;
            }
        }catch (IndexOutOfBoundsException ignored){
        }
        out.println(Constants.ATTRIBUTE_BAD_INPUT);
        return null;
    }


}
