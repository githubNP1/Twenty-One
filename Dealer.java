package Twenty_One;

public class Dealer {
    Hand hand = new Hand();
    Boolean natural = false;
    
    public Boolean dealerCheckFaceUpCardForAce(){
        if(hand.hand.get(0).ace){
            return true;
        }
        return false;
    }
    
    public Boolean dealerCheckFaceUpCardForTen(){
        if(hand.hand.get(0).ten){
            return true;
        }
        return false;
    }
    
    public Card getFaceUpCard(){
        if(hand.hand.get(0).faceUp){
            return hand.hand.get(0);
        }
        else if(hand.hand.get(1).faceUp){
            return hand.hand.get(1); 
        }
        return null;
    }
    
    public Card getFaceDownCard(){
        if(!hand.hand.get(0).faceUp){
            return hand.hand.get(0);
        }
        else if(!hand.hand.get(1).faceUp){
            return hand.hand.get(1); 
        }
        return null;
    }
    
    public void turnOverFaceDownCard(){
        if(!hand.hand.get(0).faceUp){
            hand.hand.get(0).faceUp = true;
        }
        else if(!hand.hand.get(1).faceUp){
            hand.hand.get(1).faceUp = true;
        }
    }
}
    
    
