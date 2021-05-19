package Twenty_One;

import java.util.*;

public class Hand {
    ArrayList<Card> hand = new ArrayList<>();
    Boolean bust = false;
    Boolean natural = false;
    int score;
    int bet;
    
    public int getTotalScore(){
        score = 0;
        for (Card card : hand){
            score += card.value;
        }
        return score;
    }
    
    public void add(Card card, Boolean faceUp){
        hand.add(card);
        card.faceUp = faceUp;
    }
}
