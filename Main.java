package Twenty_One;

import java.io.*;
import java.util.*;

public class Main {
    ArrayList<Card> deck = new ArrayList<>();
    ArrayList<Card> fullDeck = new ArrayList<>();
    
    public static void main(String[] args) throws IOException{
        Main game = new Main();
        Player player = new Player(10000);
        Dealer dealer = new Dealer();
        game.getDeck();
        game.run(player, dealer);
    }

    public void run(Player player, Dealer dealer){
        Scanner scan = new Scanner(System.in); 
        while(player.chips > 1){
            if(deck.size() < 30){
                deck = fullDeck;
                Collections.shuffle(deck); 
            }
            playerMakesBet(scan, player);
            initialDeal(player, dealer);
            initialPlay(scan, player, dealer);
        }
        scan.close();
    }
    
    public void playerMakesBet(Scanner scan, Player player){
        System.out.println("Enter how much you would like to bet");
        int bet = scan.nextInt();
        while (bet <= 0 || bet > player.chips){
            System.out.println("You must make a bet you can afford");
            System.out.println("Enter another amount");
            bet = scan.nextInt();
        }
        player.hand.bet = bet;
        player.chips -= bet;
        System.out.println("Chips left: " + player.chips);
    }
    
    public void initialDeal(Player player, Dealer dealer){
        player.hand.add(deck.get(0), true); 
        dealer.hand.add(deck.get(1), true); 
        player.hand.add(deck.get(2), true); 
        dealer.hand.add(deck.get(3), false); 
        deck.removeIf(n -> (deck.indexOf(n) >= 0 && deck.indexOf(n) <= 3)); 
    }
    
    public void initialPlay(Scanner scan, Player player, Dealer dealer){
        checkNatural(player.hand, dealer.hand);
    }
    
    public void checkNatural(Hand playerHand, Hand dealerHand){
        
    }
    
    public void getDeck() throws FileNotFoundException, IOException{  //shuffles 6 decks of cards to be used
        for(int i = 0; i < 6; i++){
            BufferedReader reader = new BufferedReader(new FileReader("Deck.txt"));
            for (int j = 0; j < 52; j++){                                                          
                String[] line = reader.readLine().split("[:]");                                                
                Card one = new Card(line[0], line[1], Integer.valueOf(line[2]));                                     
                fullDeck.add(one);
            }
            reader.close();
        }
    }
}
