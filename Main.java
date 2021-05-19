package Twenty_One;

import java.io.*;
import java.util.*;

public class Main {
    ArrayList<Card> deck = new ArrayList<>();
    
    public static void main() throws IOException{
        Main game = new Main();
        Player player = new Player(10000);
        Dealer dealer = new Dealer();
        game.getDeck();
        game.run();
    }

    public void run(){
        while(deck.size() > 30){
            
        }
    }
    
    
    
    public void getDeck() throws FileNotFoundException, IOException{  //shuffles 6 decks of cards to be used
        for(int i = 0; i < 6; i++){
            BufferedReader reader = new BufferedReader(new FileReader("Deck.txt"));
            for (int j = 0; j < 52; j++){                                                          
                String[] line = reader.readLine().split("[:]");                                                
                Card one = new Card(line[0], line[1], Integer.valueOf(line[2]));                                     
                deck.add(one);
            }
            reader.close();
        }
        Collections.shuffle(deck); 
    }
}
