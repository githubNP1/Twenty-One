package Twenty_One;

import java.util.*;

public class Hand {
    ArrayList<Card> hand = new ArrayList<>();
    Boolean bust = false;
    Boolean doubled = false;
    Boolean splittable;
    //Boolean natural = false;
    //int score;
    int bet;
    
    public Hand(){}
    
    public Hand(Boolean splittable){
        this.splittable = splittable;
    }
    
    public int getTotalScore(){
        int score = 0;
        for (Card card : hand){
            score += card.value;
        }
        return score;
    }
    
    public void add(Card card, Boolean faceUp){
        hand.add(card);
        card.faceUp = faceUp;
    }
    
    public Boolean checkNatural(){  //returns Boolean, maybe no need for Boolean natural
        if(hand.get(0).ace && hand.get(1).ten || hand.get(0).ten && hand.get(1).ace){
            return true;
        }
        //if(hand.size() == 2 && getTotalScore() == 21){
        //    return true;
        //}
        return false;
    }
    
    public void checkBust(){
        if(getTotalScore() > 21){
            bust = true;
        }
    }
    
    public void clear(){
        hand.clear();
    }
    
    public Boolean checkIfDoubleable(){
        if(!doubled && hand.size() == 2 && getTotalScore() >= 9 && getTotalScore() <= 11){
            return true;
        }
        return false;
    }
    
    public String makeMove(){
        String moves = "Do you want to 1) stand, 2) hit";
        checkIfDoubleable();
        if(!doubled){moves += ", 3) double";}
        if(splittable){moves += " or 4) split";}
        return moves;
    }
    
    public ArrayList moveChoices(){
        ArrayList<String> choices = new ArrayList<>();
        choices.add("stand");
        choices.add("hit"); 
        checkIfDoubleable();
        if(!doubled){choices.add("double");}
        if(splittable){choices.add("split");} 
        return choices;
    }
}
