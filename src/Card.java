// Card class
public class Card {
    // enum for each suit in a card deck
    public enum Suit {
        S, D, C, H
    }

    // enum for each rank in a card deck
    public enum Rank {
        TWO("2"), THREE("3"), FOUR("4"), FIVE("5"),
        SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"), TEN("10"),
        JACK("J"), QUEEN("Q"), KING("K"), ACE("A");

        private String cardValue;

        Rank(String cardValue) {
            this.cardValue = cardValue;
        }

        public String getCardValue() { return this.cardValue; }
    }

    public Suit suit;
    private Rank rank;

    // Card constructor
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    // Getter for a Card's suit
    public Suit getSuit() {
        return this.suit;
    }

    // Getter for a Card's rank
    public Rank getRank() {
        return this.rank;
    }
}
