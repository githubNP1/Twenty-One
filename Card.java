package Twenty_One;

public class Card {
    String number;
    String suit;
    int value;
    Boolean faceUp;
    Boolean ace = false;
    Boolean ten = false;

    public Card(String number, String suit, int value){
        this.number = number;
        this.suit = suit;
        this.value = value;
        if(value == 11){ace = true;}
        else if(value == 10){ten = true;}
    }
}
