package Twenty_One;

import java.util.*;

public class Player {
    int chips;  //this is decided at beginning
    ArrayList<Hand> hands = new ArrayList<>();
    //Hand hand = new Hand();
    //Hand secondHand = new Hand(false);
    //Boolean split = false;
    Boolean insurance = false;
    int insuranceBet;
    Boolean natural = false;
    
    public Player(int chips){
        this.chips = chips;
    }
    
    public Hand newPlayerHand(){
        Hand hand = new Hand();
        hands.add(hand);
        return hand;
    }
    
    public void seeCards(){ //prints faceup cards
        for(Hand hand : hands){
            System.out.print("You have "); 
            for(Card card : hand.hand){
                if(card.faceUp = true){System.out.print("a " + card.number + " of " + card.suit + ", ");}
            }
            System.out.println();
        }
    }
    
    public void checkIfFirstHandCanBeSplit(){
        if(hands.get(0).hand.get(0).number.equals(hands.get(0).hand.get(1).number)){
            hands.get(0).splittable = true;
        }
        else{
            hands.get(0).splittable = false;
        }
    }
    
    public Hand getFirstHand(){
        return hands.get(0);
    }
    
    public Hand getSecondHand(){
        return hands.get(1);
    }
}
