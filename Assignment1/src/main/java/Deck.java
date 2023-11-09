import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Deck {
    private ArrayList<Card> cards;                       //ArrayList to hold all the Cards in the Deck.
    private HashMap<String, Integer> cardCounts;        //HashMap to store the number of cards of each type in the Deck.

    public Deck() {
        cards = new ArrayList<>();
        cardCounts = new HashMap<>();
        //Set all Card counts to 0 initially.
        cardCounts.put("Sw", 0);
        cardCounts.put("Ar", 0);
        cardCounts.put("So", 0);
        cardCounts.put("De", 0);
        cardCounts.put("Al", 0);
        cardCounts.put("Me", 0);
        cardCounts.put("Ap", 0);
    }

    /*
    * Purpose: The purpose of this method is to create the Deck used in the Game (i.e. the Deck of Cards 80 Cards from
    * which the Players' hands are dealt).
    * Returns: A Deck with all the Cards needed to play the Game.
    */
    public static Deck createGameDeck() {
        Deck deck = new Deck();
        //Add the basic weapon cards.
        for(String suit:Card.SUITS) {
            for(int i=0;i<15;i++) deck.addCard(new Card(suit, (byte)(i+1)));
        }
        //Add the Alchemy cards.
        for(int i=0;i<15;i++) deck.addCard(new Card("Al", (byte) (i+1)));
        //Add the Merlin cards.
        for(int i=0;i<3;i++)  deck.addCard(new Card("Me", null));
        //Add the Apprentice cards.
        for(int i=0;i<2;i++)  deck.addCard(new Card("Ap", null));

        return deck;
    }

    /*
    * Purpose: The purpose of this method is to add a given Card to the Deck.
    * Parameters: card-The Card to add to the Deck.
    */
    public void addCard(Card card) {
        cards.add(card);
        cardCounts.put(card.getType(), cardCounts.get(card.getType()) + 1);
    }

    /*Purpose: This method is responsible for replacing one Card in the Deck with a different Card.
    Parameters: card-The Card to add to the Deck.
                index-int corresponding to the index of the Card in the Deck to be replaced.
     */
    public void replaceCard(Card card, int index) {
        removeCard(index);
        cards.add(index, card);
        cardCounts.put(card.getType(), cardCounts.get(card.getType()) + 1);
    }

    /*
    * Purpose: The purpose of this method is to remove a given Card from the Deck using the index of the Card.
    * Parameters: index-The index of the Card in the 'cards' ArrayList to remove.
    */
    public void removeCard(int index) {
        String cardType = cards.get(index).getType();
        cardCounts.put(cardType, cardCounts.get(cardType) - 1);
        cards.remove(index);
    }

    /*
     * Purpose: The purpose of this method is to remove a given Card from the Deck by providing a Card object to remove.
     * Parameters: card-The Card in the 'cards' ArrayList to remove.
    */
    public void removeCard(Card card) {
        cardCounts.put(card.getType(), cardCounts.get(card.getType()) - 1);
        cards.remove(card);
    }

    /*This method returns a String representation of the Deck.*/
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        //Display the Cards.
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

    /*
    Purpose: This method determines whether two Deck objects are considered equal or not.
    Parameters: obj-The Deck object being compared with 'this' Deck object.
    Returns: true if both Decks contain the same Cards in the same order, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        Deck other = (Deck) obj;
        //Check that the two Decks have the same cards in the same order.
        for(int i=0;i<getNumCards();i++) {
            if(!this.cards.get(i).equals(other.cards.get(i))) return false;
        }
        return true;
    }

    //This method is used to make a deep copy of a Deck object.
    public Deck deepCopy() {
        Deck copy = new Deck();
        for(int i=0;i<getNumCards();i++) {
            copy.addCard(this.cards.get(i));
        }
        return copy;
    }

    /*Purpose: This method is responsible for shuffling the Cards in the Deck.*/
    public void shuffle() {
        Deck previous = deepCopy();
        //Ensure it is shuffled to a different permutation than original for testing.
        while(previous.equals(this)) Collections.shuffle(this.cards);
    }

    /*Purpose: This method is used to compute the total injury points of all Cards in the Deck.
    Returns: The sum of the injury points of all Cards in the Deck.
    */
    public int getInjuryPoints() {
        int total = 0;
        for(Card c: cards) total += c.getDamage();
        return total;
    }

    /*Purpose: This method clears the Deck by removing all Cards from the 'cards' ArrayList and setting all values in
    * cardCounts to 0.
    */
    public void clear() {
        cards.clear();
        //Set all Card counts to 0.
        for(String key: cardCounts.keySet()) cardCounts.put(key, 0);
    }

    //GETTER METHODS
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
}
