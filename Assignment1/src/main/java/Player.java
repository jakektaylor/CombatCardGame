
public class Player {

    private String name;
    private int hp;
    private Deck hand;

    public Player(String name) {
        this.name = name;
        this.hp = 0;
        this.hand = new Deck();
    }
    public String getName(){
        return name;
    }

    public int getHP() {
        return hp;
    }

    public void setHP(int hp) {
        this.hp = hp;
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
}
