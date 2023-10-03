public class Melee {

    private Card[] played;
    private String suit;
    public Melee(byte numPlayers) {
        played = new Card[numPlayers];
        for(int i=0;i<numPlayers;i++) played[i] = null;
        suit = null;
    }

    public Card[] getPlayed() {
        return played;
    }

    public void playCard(byte currPlayer, Card card) {
        //Check if this is the first Card played.
        boolean isEmpty = true;
        for (int i = 0; i < played.length; i++) {
            if (played[i] != null) {
                isEmpty = false;
                break;
            }
        }

        //Basic Weapon as first card.
        if(isEmpty && Card.SUITS.contains(card.getType())) this.suit = card.getType();
        played[currPlayer] = card;
    }

    public String getSuit() {
        return suit;
    }
}
