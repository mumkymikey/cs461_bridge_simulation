import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class BridgeSimulation {
    final static int HAND_SIZE = 13;
    public static final DecimalFormat round = new DecimalFormat("##.##");

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String anotherHand = "y";

        while (anotherHand.equalsIgnoreCase("y")) {
            int score = 0;
            // instantiate initial deck that player draws from
            Deck deck = new Deck();
            Collections.shuffle(deck.getDeck());

            // declare player and partner's hands
            ArrayList<Card> playerHand, partnerHand = new ArrayList<Card>();
            playerHand = deck.dealHand();

            // stores score distribution
            Map<String, Integer> scoringScale = new HashMap<String, Integer>() {{
                put("Pass", 0);
                put("Part Score", 0);
                put("Game", 0);
                put("Small Slam", 0);
                put("Grand Slam", 0);
            }};

            System.out.println("Your hand: ");
            viewHand(playerHand);
            System.out.println("This hand is worth " + evaluateHand(playerHand) + " points.");

            System.out.println("Running simulation.....\n\n" +
                               "The estimated probability based on 500 simulated hands:");

            // Monte Carlo simulation
            for (int i = 0; i < 500; i++) {
                Deck monteCarloDeck = new Deck(deck);
                Collections.shuffle(monteCarloDeck.getDeck());
                partnerHand = monteCarloDeck.dealHand();

                score = 0;
                score += evaluateHand(playerHand) + evaluateHand(partnerHand);

                evaluateScore(score, scoringScale);
            }

            System.out.println("Pass: " + evaluateProbability("Pass", scoringScale));
            System.out.println("Part Score: " + evaluateProbability("Part Score", scoringScale));
            System.out.println("Game: " + evaluateProbability("Game", scoringScale));
            System.out.println("Small Slam: " + evaluateProbability("Small Slam", scoringScale));
            System.out.println("Grand Slam: " + evaluateProbability("Grand Slam", scoringScale));

            System.out.println("\nAnother hand [y/n]? ");
            anotherHand = input.next();
        }
    }

    // Method for displaying each card in a hand
    private static void viewHand(ArrayList<Card> hand) {
        for (int i = 0; i < HAND_SIZE; i++) {
            System.out.print(hand.get(i).getRank().getCardValue()
                    + hand.get(i).getSuit() + " ");
        }
        System.out.println("\n");
    }

    private static String evaluateProbability(String key, Map<String, Integer> scoringScale) {
        double probability = (scoringScale.get(key) / 500.0) * 100.0;
        return round.format(probability) + "%";
    }

    private static void evaluateScore(int score, Map<String, Integer> scoringScale) {
        int count;
        if (score <= 20) {
            count = scoringScale.getOrDefault("Pass", 0);
            scoringScale.put("Pass", count + 1);
        } else if (score <= 25) {
            count = scoringScale.getOrDefault("Part Score", 0);
            scoringScale.put("Part Score", count + 1);
        } else if (score <= 31) {
            count = scoringScale.getOrDefault("Game", 0);
            scoringScale.put("Game", count + 1);
        } else if (score <= 35) {
            count = scoringScale.getOrDefault("Small Slam", 0);
            scoringScale.put("Small Slam", count + 1);
        } else {
            count = scoringScale.getOrDefault("Grand Slam", 0);
            scoringScale.put("Grand Slam", count + 1);
        }
    }

    private static int evaluateHand(ArrayList<Card> hand) {
        int score = 0;
        score = evaluateDistribution(hand) + evaluateHighCards(hand);
        return score;
    }

    private static int evaluateHighCards(ArrayList<Card> hand) {
        int score = 0;

        for (Card card : hand) {
            if (card.getRank() == Card.Rank.JACK)
                score += 1;
            else if (card.getRank() == Card.Rank.QUEEN)
                score += 2;
            else if (card.getRank() == Card.Rank.KING)
                score += 3;
            else if (card.getRank() == Card.Rank.ACE)
                score += 4;
        }
        return score;
    }

    private static int evaluateDistribution(ArrayList<Card> hand) {
        int score = 0;
        Map<Card.Suit, Integer> suits = new HashMap<Card.Suit, Integer>() {{
            put(Card.Suit.S, 0);
            put(Card.Suit.C, 0);
            put(Card.Suit.D, 0);
            put(Card.Suit.H, 0);
        }};

        for (Card card : hand) {
            Card.Suit suit = card.getSuit();
            int count = suits.getOrDefault(suit, 0);
            suits.put(suit, count + 1);

//            if (suit == Card.Suit.S)
//                suits.put(Card.Suit.S, count + 1);
//            else if (suit == Card.Suit.C)
//                suits.put(Card.Suit.C, count + 1);
//            else if (suit == Card.Suit.D)
//                suits.put(Card.Suit.D, count + 1);
//            else if (suit == Card.Suit.H)
//                suits.put(Card.Suit.H, count + 1);
        }

        for (Map.Entry<Card.Suit, Integer> distribution : suits.entrySet()) {
            if (distribution.getValue() == 2)
                score += 1;
            else if (distribution.getValue() == 1)
                score += 2;
            else if (distribution.getValue() == 0)
                score += 5;
        }
        return score;
    }
}
