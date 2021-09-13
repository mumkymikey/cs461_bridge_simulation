import java.util.ArrayList;

// Deck class
public class Deck {
    // declaring constants and class attrs
    final int HAND_SIZE = 13;
    public ArrayList<Card> deck;

    // Deck constructor
    public Deck() {
        deck = new ArrayList<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                Card card = new Card(suit, rank);
                this.deck.add(card);
            }
        }
    }

    // Copy constructor
    public Deck(Deck deckToCopy) {
        deck = new ArrayList<>();
        deck.addAll(deckToCopy.deck);
    }

    // Getter for deck attribute
    public ArrayList<Card> getDeck() {
        return this.deck;
    }

    // Method for displaying each card in the deck
    public void viewDeck() {
        for (Card card : deck) {
            System.out.print(card.getRank().getCardValue()
                    + card.getSuit() + " ");
        }
        System.out.println("\n");
    }

    // deals a hand from the deck
    public ArrayList<Card> dealHand() {
        ArrayList<Card> hand = new ArrayList<>();
        for (int i = 0; i < HAND_SIZE; i++) {
            hand.add(this.deck.get(i));
            this.deck.remove(i);
        }
        sortHand(hand);
        return hand;
    }

    // sorts a hand by suit and rank
    // p.s. gnome sort rocks!
    private void sortHand(ArrayList<Card> hand) {
        int i = 0;

        while (i < hand.size()) {
            if (i == 0)
                i++;

            int currentSuit = hand.get(i).getSuit().ordinal();
            int previousSuit = hand.get(i-1).getSuit().ordinal();
            int currentRank = hand.get(i).getRank().ordinal();
            int previousRank = hand.get(i-1).getRank().ordinal();

            if (currentSuit > previousSuit)
                i++;
            else if (currentSuit >= previousSuit) {
                if (currentRank <= previousRank) {
                    Card temp;
                    temp = hand.get(i);
                    hand.set(i, hand.get(i-1));
                    hand.set(i-1, temp);
                    i--;
                }
                i++;
            }
            else {
                Card temp;
                temp = hand.get(i);
                hand.set(i, hand.get(i-1));
                hand.set(i-1, temp);
                i--;
            }
        }
    }
}
