import java.util.ArrayList;

public class Player {

    private String name;
    private int hp;
    private ArrayList<Card> hand;

    public Player(String name) {
        this.name = name;
        this.hp = 0;
        this.hand = new ArrayList<>();
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
        return hand.size();
    }

    public void dealCard(Card card) {
        hand.add(card);
    }
}
