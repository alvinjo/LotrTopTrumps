package toptrumps.deck;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static toptrumps.constants.Constants.PATH_TO_CARD_FILE;

public class DeckBuilder {

    private static List<Card> deck = new ArrayList<>();

    private static Gson gson = new Gson();

    public static List<Card> getDeck(){
        if(deck.isEmpty()){
            return buildDeckFromFile();
        }
        return deck;
    }

    private static List<Card> buildDeckFromFile(){
        try{
            BufferedReader br = new BufferedReader(new FileReader(PATH_TO_CARD_FILE));
            String line = br.readLine();
            while(line != null){
                deck.add(gson.fromJson(line, Card.class));
                line = br.readLine();
            }
        }catch(IOException e){
            System.out.println(e.toString());
        }
        return deck;
    }

}