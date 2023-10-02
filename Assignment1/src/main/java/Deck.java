import java.util.ArrayList;
import java.util.HashMap;

public class Deck {
    private ArrayList<Card> cards;
    HashMap<String, Integer> cardCounts;         //HashMap to store the number of cards of each type in the Deck.

    public Deck() {
        cards = new ArrayList<>();
        cardCounts = new HashMap<>();
        cardCounts.put("Sw", 0);
        cardCounts.put("Ar", 0);
        cardCounts.put("So", 0);
        cardCounts.put("De", 0);
        cardCounts.put("Al", 0);
        cardCounts.put("Me", 0);
        cardCounts.put("Ap", 0);
    }

    public static Deck createGameDeck() {
        Deck deck = new Deck();
        //Create the basic weapon cards.
        for(String suit:Card.SUITS) {
            for(int i=0;i<15;i++) deck.addCard(new Card(suit, (byte)(i+1)));
        }
        //Create the alchemy cards.
        for(int i=0;i<15;i++) deck.addCard(new Card("Al", (byte) (i+1)));
        for(int i=0;i<3;i++)  deck.addCard(new Card("Me", null));
        for(int i=0;i<2;i++)  deck.addCard(new Card("Ap", null));

        return deck;
    }

    public void addCard(Card card) {
        cards.add(card);
        cardCounts.put(card.getType(), cardCounts.get(card.getType()) + 1);
    }

    public int getNumCards() {
        return cards.size();
    }

    public int getNumSw() {
        return cardCounts.get("Sw");
    }

    public int getNumAr() {
        return cardCounts.get("Ar");
    }

    public int getNumSo() {
        return cardCounts.get("So");
    }

    public int getNumDe() {
        return cardCounts.get("De");
    }

    public int getNumAl() {
        return cardCounts.get("Al");
    }

    public int getNumMe() {
        return cardCounts.get("Me");
    }

    public int getNumAp() {
        return cardCounts.get("Ap");
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(Card c: cards) {
            builder.append(c.toString());
            builder.append("   ");
        }
        builder.append("\n");
        //Display numbers beneath the cards so that they can be selected.
        for(int i=0;i<getNumCards();i++) {
            builder.append(String.format("|%-9d", i+1));
        }
        builder.append("\n");
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }
}
