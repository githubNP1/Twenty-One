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
        player.hands.clear();
        player.newPlayerHand().bet = bet;
        //player.hand.bet = bet;
        player.chips -= bet;
        System.out.println("Chips left: " + player.chips);
    }
    
    public void initialDeal(Player player, Dealer dealer){
        dealer.hand.clear();  //maybe put this in a seperate method
        dealer.hand.bust = false;
        player.natural = false;
        dealer.natural = false;
        player.getFirstHand().add(deck.get(0), true); 
        //player.hands.get(0).add(deck.get(0), true); 
        dealer.hand.add(deck.get(1), true); 
        player.getFirstHand().add(deck.get(2), true);  
        dealer.hand.add(deck.get(3), false); 
        deck.removeIf(n -> (deck.indexOf(n) >= 0 && deck.indexOf(n) <= 3)); 
        //System.out.println("You have a " + player.hand.hand.get(0).number + " of " + player.hand.hand.get(0).suit + " and a " + player.hand.hand.get(1).number + " of " + player.hand.hand.get(1).suit);
        player.seeCards();
        if(player.getFirstHand().checkNatural()){player.natural = true; System.out.println("You have a natural");}
        dealer.seeCards();
        //if(player.hand.hand.get(0).number.equals(player.hand.hand.get(1).number)){player.hand.splittable = true;}
        //else{player.hand.splittable = false;}
        player.checkIfFirstHandCanBeSplit();
    }
    
    public void initialPlay(Scanner scan, Player player, Dealer dealer){
        //first see if dealers faceup card is ace, if so, player can make an insurance bet
        if(dealer.getFaceUpCard().ace){
            System.out.println("Would you like to make an insurance bet?");
            String choice = scan.next();
            while (!"yes".equals(choice) && !"no".equals(choice)){
                System.out.println("Enter 'yes' or 'no'");
                choice = scan.next();
            }
            if(choice.equals("yes")){
                System.out.println("Enter how much you would like to bet");
                System.out.println("You can bet up to half your original bet");
                player.insuranceBet = scan.nextInt();
                while (player.insuranceBet <= 0 || player.insuranceBet > player.chips || player.insuranceBet > player.getFirstHand().bet / 2){
                    System.out.println("You must enter a valid amount");
                    player.insuranceBet = scan.nextInt();
                }
                player.chips -= player.insuranceBet;
                player.insurance = true;
                System.out.println("Chips left: " + player.chips);
            }
        }
        //if face up card is ace of ten card, dealer checks facedown card
        if(dealer.getFaceUpCard().ace || dealer.getFaceUpCard().ten){
            System.out.println("The dealer checks the hole card"); 
            if(dealer.hand.checkNatural()){
                dealer.natural = true;
                dealer.turnOverFaceDownCard();
                System.out.println("The dealer has a natural");
                if(player.insurance){
                    System.out.println("You win twice your insurance bet");
                    player.chips += player.insuranceBet * 3;
                    System.out.println("Chips left: " + player.chips);
                    player.insurance = false;
                }
            }
            else{
                System.out.println("The dealer does not have a natural");
                if(player.insurance){
                    System.out.println("You lose your insurance bet");
                    player.insurance = false;
                }
            }
        }
        if(player.natural && dealer.natural){ //standoff
            System.out.println("Standoff: Your bet is returned to you");
            player.chips += player.getFirstHand().bet;
            System.out.println("Chips left: " + player.chips);
        }
        else if(player.natural && !dealer.natural){ //player wins
            System.out.println("You receive 1.5 times your bet in winnings");
            player.chips += player.getFirstHand().bet * 2.5;
            System.out.println("Chips left: " + player.chips);
        }
        else{
            play(player, dealer, scan);
        }
    }
    
    public void play(Player player, Dealer dealer, Scanner scan){
        //for every hand
        for(Hand hand : player.hands){
            hand.checkBust();
            while(!hand.bust){
                System.out.println(hand.makeMove());
                String choice = scan.next();
                while (!hand.moveChoices().contains(choice)){ 
                    System.out.println("Enter a valid decision");
                    choice = scan.next();
                }
                if(choice.equals("stand")){break;}
                else if(choice.equals("hit")){ //make hit its own method
                    hit(hand, true);
                    player.seeCards();
                }
                else if(choice.equals("double")){
                    player.chips -= hand.bet;
                    hand.bet *= 2;
                    hit(hand, false);
                    player.seeCards();
                    hand.doubled = true;
                    hand.checkBust();
                    break;
                }
                else if(choice.equals("split")){ 
                    if(hand.hand.get(0).ace){
                        player.split();
                        hit(player.getFirstHand(), true);
                        hit(player.getSecondHand(), true);
                        player.seeCards();
                        player.getFirstHand().checkBust();
                        player.getSecondHand().checkBust();
                        break;
                    }
                    else{
                        player.split();
                        hit(player.getFirstHand(), true);
                        hit(player.getSecondHand(), true);
                        player.seeCards();
                    }
                }
            }
        }
        player.turnOverFaceDownCards();
        for(Hand hand : player.hands){
            if(hand.bust){
                System.out.println("You are bust, dealer wins");
            }
            else{
                dealersPlay(player, dealer, hand);
            }
        }
    }
    
    public void hit(Hand hand, Boolean faceUp){
        hand.add(deck.get(0), true); 
        deck.remove(0);
    }
    
    public void dealersPlay(Player player, Dealer dealer, Hand playerHand){
        dealer.turnOverFaceDownCard();
        dealer.seeCards();
        while(!dealer.hand.bust){
            if(dealer.hand.getTotalScore() >= 17){
                break;
            }
            else{
                hit(dealer.hand, true);
                System.out.println("Dealer hits"); 
                dealer.seeCards();
            }
            dealer.hand.checkBust();
        }
        if(dealer.hand.bust){
            System.out.println("Dealer is bust, you win");
            player.chips += 2 * playerHand.bet;
            System.out.println("Chips left: " + player.chips);
        }
        //settlement
        else if(dealer.hand.getTotalScore() > playerHand.getTotalScore()){
            System.out.println("Dealer wins");
        }
        else if(dealer.hand.getTotalScore() < playerHand.getTotalScore()){
            System.out.println("You win");
            player.chips += 2 * playerHand.bet;
            System.out.println("Chips left: " + player.chips);
        }
        else if(dealer.hand.getTotalScore() == playerHand.getTotalScore()){
            System.out.println("Standoff");
            player.chips += playerHand.bet;
            System.out.println("Chips left: " + player.chips);
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
