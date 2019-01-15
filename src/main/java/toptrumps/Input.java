package toptrumps;

import toptrumps.constants.Enums;

import java.io.PrintWriter;
import java.util.Scanner;

public class Input {

    public static void getInput(Enums.InputType inputType, Scanner in, PrintWriter out){


        if(inputType.equals(Enums.InputType.Login)){

        }
        if(inputType.equals(Enums.InputType.AttribInput)){

        }
    }


//    private static String loginInput(Scanner in, PrintWriter out){
//        String username;
//        out.println("Enter username (1-15 alphanumeric characters): ");
//        username = in.nextLine();
//
//        if(username.matches("^[a-zA-Z0-9]{1,15}$")){
//            out.println("Username set to: " + username);
//            return username;
//        }
//        out.println("Bad username");
//        return null;
//    }

    private static void attribInput(){

    }

}
