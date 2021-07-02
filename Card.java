package Twenty_One;

public class Card {
    String number;
    String suit;
    int value;
    int x1, x2, y1, y2;
    Boolean faceUp;
    Boolean ace = false;
    Boolean ten = false;
    
    public Card(String number, String suit, int value, int x1, int y1, int x2, int y2){
        this.number = number;
        this.suit = suit;
        this.value = value;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        if(value == 11){ace = true;}
        else if(value == 10){ten = true;}
    }
}
