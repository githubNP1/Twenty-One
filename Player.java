package Twenty_One;

import java.util.*;

public class Player {
    int chips;  //this is decided at beginning
    ArrayList<Hand> hands = new ArrayList<>();
    
    public Player(int chips){
        this.chips = chips;
    }
}
