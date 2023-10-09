import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Deck {
    private ArrayList<Card> cards;
    private HashMap<String, Integer> cardCounts;         //HashMap to store the number of cards of each type in the Deck.

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

    public void removeCard(int index) {
        String cardType = cards.get(index).getType();
        cardCounts.put(cardType, cardCounts.get(cardType) - 1);
        cards.remove(index);
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
            builder.append(String.format("|%-11d", i+1));
        }
        builder.append("\n");
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        Deck other = (Deck) obj;
        //Check that the two Decks have the same cards in the same order.
        for(int i=0;i<getNumCards();i++) {
            if(!this.cards.get(i).equals(other.cards.get(i))) return false;
        }
        return true;
    }

    public Deck deepCopy() {
        Deck copy = new Deck();
        for(int i=0;i<getNumCards();i++) {
            copy.addCard(this.cards.get(i));
        }
        return copy;
    }

    public void shuffle() {
        Deck previous = deepCopy();
        //Ensure it is shuffled to a different permutation than original for testing.
        while(previous.equals(this)) Collections.shuffle(this.cards);
    }

    public int getInjuryPoints() {
        int total = 0;
        for(Card c: cards) total += c.getDamage();
        return total;
    }

    public void clear() {
        cards.clear();
        //Set all Card counts to 0.
        for(String key: cardCounts.keySet()) cardCounts.put(key, 0);
    }
}
