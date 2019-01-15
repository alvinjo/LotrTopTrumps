package toptrumps.deck;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeckBuilder {

    public static List<Card> deck = new ArrayList<Card>();

    private static Gson gson = new Gson();
    private static BufferedReader br;

    public static List<Card> getDeck(){
        if(deck.isEmpty()){
            return buildDeckFromFile();
        }
        return deck;
    }

    private static List<Card> buildDeckFromFile(){
        try{
            br = new BufferedReader(new FileReader("C:\\Users\\Alvin\\IdeaProjects\\Lotr\\src\\main\\java\\toptrumps\\deck\\cards.txt"));
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
