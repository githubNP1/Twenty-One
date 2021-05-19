package Twenty_One;

public class Player {
    int chips;  //this is decided at beginning
    Hand hand = new Hand();
    Hand secondHand = new Hand();
    Boolean split = false;
    
    public Player(int chips){
        this.chips = chips;
    }
}
