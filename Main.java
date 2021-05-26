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
            System.out.println("You must enter a valid amount");
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
        System.out.println("You have a " + player.hand.hand.get(0).number + " of " + player.hand.hand.get(0).suit + " and a " + player.hand.hand.get(1).number + " of " + player.hand.hand.get(1).suit);
        if(player.hand.hand.get(0).number.equals(player.hand.hand.get(1).number)){player.hand.splittable = true;}
        else{player.hand.splittable = false;}
    }
    
    public void initialPlay(Scanner scan, Player player, Dealer dealer){
        
        //If dealer has an ace faceup then player can make insurance bet if dealers face down card is a ten
        if(dealer.hand.hand.get(0).ace){
            int insurance = 0;
            System.out.println("Dealer has an ace, would you like to make an insurance bet?");
            String choice = scan.next();
            while (!"yes".equals(choice) && !"no".equals(choice)){
                System.out.println("Enter 'yes' or 'no'");
                choice = scan.next();
            }
            if(choice.equals("yes")){
                System.out.println("Enter how much you would like to bet");
                insurance = scan.nextInt();
                while (insurance <= 0 || insurance > player.chips){
                    System.out.println("You must enter a valid amount");
                    insurance = scan.nextInt();
                }
                player.chips -= insurance;
                System.out.println("Chips left: " + player.chips);
            }
            System.out.println("The dealer turns over the hole card");
            if(dealer.hand.hand.get(1).ten){
                dealer.hand.hand.get(1).faceUp = true;
                System.out.println("The card is a ten");
                System.out.println("You win double your insurance bet");
                player.chips += insurance * 3;
                System.out.println("Chips left: " + player.chips);
            }
            else{
                System.out.println("The card is not a ten");
                System.out.println("You lose your insurance bet");
            }
        }
        else if(dealer.hand.hand.get(0).ten){
            System.out.println("The dealer has a ten");
            System.out.println("The dealer looks at the hole card");
            if(dealer.hand.hand.get(1).ace){
                dealer.hand.hand.get(1).faceUp = true;
                System.out.println("The dealer has a natural");
                if(player.hand.checkNatural()){
                    System.out.println("You have a natural too");
                    System.out.println("Standoff: Your bet is returned to you");
                    player.chips += player.hand.bet;
                    System.out.println("Chips left: " + player.chips);
                }
                else{
                    System.out.println("You have lost this hand");
                }
            }
            else{
                System.out.println("The dealer does not have an ace");
                firstPlay(player, dealer, scan);
            }
        }
        else{
            if(player.hand.checkNatural()){
                System.out.println("You have a natural, you win");
                System.out.println("You receive 1.5 times your bet in winnings");
                player.chips += player.hand.bet * 2.5;
                System.out.println("Chips left: " + player.chips);
            }
            else{
                firstPlay(player, dealer, scan);
            }
        }
        player.hand.clear();
        dealer.hand.clear();
    }
    
    public void firstPlay(Player player, Dealer dealer, Scanner scan){
        while(!player.hand.bust){
            System.out.println(player.hand.makeMove());
            String choice = scan.next();
            while (!"stand".equals(choice) && !"hit".equals(choice) && !"double".equals(choice) && !"split".equals(choice)){
                System.out.println("Enter a valid decision");
                choice = scan.next();
            }
            if(choice.equals("stand")){break;}
            else if(choice.equals("hit")){
                player.hand.add(deck.get(0), true); 
                deck.remove(0);
                System.out.println(player.hand.hand.get(player.hand.hand.size() - 1).number + " of " + player.hand.hand.get(player.hand.hand.size() - 1).suit); 
            }
        }
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
