public class Melee {

    private Card[] played;
    public Melee(byte numPlayers) {
        played = new Card[numPlayers];
        for(int i=0;i<numPlayers;i++) played[i] = null;
    }

    public Card[] getPlayed() {
        return played;
    }

    public void playCard(byte currPlayer, Card card) {
        played[currPlayer] = card;
    }
}
