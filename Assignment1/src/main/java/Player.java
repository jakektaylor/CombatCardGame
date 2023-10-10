
public class Player {

    private String name;
    private int hp;
    private Deck hand;
    private Deck injuryDeck;
    private int numTimesShamed;

    public Player(String name) {
        this.name = name;
        this.hp = 0;
        this.hand = new Deck();
        this.injuryDeck = new Deck();
        this.numTimesShamed = 0;
    }
    public String getName(){
        return name;
    }

    public int getHP() {
        return hp;
    }

    public void setHP(int hp) {
        if(hp > 0) this.hp = hp;
        else this.hp = 0;
    }

    public int getNumCards() {
        return hand.getNumCards();
    }

    public void dealCard(Card card) {
        hand.addCard(card);
    }

    public String displayHand() {
        return hand.toString();
    }

    public Deck getHand() {
        return hand;
    }

    public void overrideDeck(Deck overrideDeck) {
        this.hand = overrideDeck;
    }

    public Deck getInjuryDeck() {
        return injuryDeck;
    }

    public void addInjuryCard(Card c) {
        injuryDeck.addCard(c);
    }

    public int getNumTimesShamed() {
        return numTimesShamed;
    }

    public void setNumTimesShamed(int numTimes) {
        this.numTimesShamed = numTimes;
    }
}
