package Twenty_One;

public class Card {
    String number;
    String suit;
    int value;
    Boolean faceUp;

    public Card(String number, String suit, int value, Boolean faceUp){
        this.number = number;
        this.suit = suit;
        this.value = value;
        this.faceUp = faceUp;
    }
}
