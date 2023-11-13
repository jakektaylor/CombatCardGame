
public class Player {

    private String name;                          //The name of the Player.
    private int hp;                               //The number of health points the Player currently has.
    private Deck hand;                            //A Deck representing the Cards in the Player's hand.
    private Deck injuryDeck;                      //Injury Deck to which Cards are added when a Player loses a Melee.
    private int numTimesShamed;                  //The number of times the Player has been shamed in the current round.

    public Player(String name) {
        this.name = name;
        this.hp = 0;
        this.hand = new Deck();
        this.injuryDeck = new Deck();
        this.numTimesShamed = 0;
    }

    /*Purpose: The purpose of this method is to compare two Player objects.
    * Parameters: -other: Another Player Object to which this Player Object is being compared
    * */
    @Override
    public boolean equals(Object other) {
        Player op = (Player) other;
        return this.name.equals(op.name) && this.hp == op.hp && this.hand.equals(op.hand) &&
                this.injuryDeck.equals(op.injuryDeck) && this.numTimesShamed == op.numTimesShamed;
    }

    /*
    * Purpose: This method is responsible for adding a Card to the Player's hand.
    * Parameters: -card: The Card to add to the Player's hand.
    */
    public void dealCard(Card card) {
        hand.addCard(card);
    }

    /*Purpose: This method is responsible for formatting the Player's hand as a displayable String.
    * Returns: The Player's hand formatted as a String.*/
    public String displayHand() {
        return hand.toString();
    }

    /*
    * Purpose: This method is used for testing and is responsible for setting the hand of a Player to a given Deck.
    * Parameters: -overrideDeck: The Deck to set the Player's hand to be.
    */
    public void overrideDeck(Deck overrideDeck) {
        this.hand = overrideDeck;
    }

    /*
    * Purpose: This method is responsible for adding a Card to a Player's injury Deck.
    * Parameters: c-The Card to add to the Player's injury Deck.
    */
    public void addInjuryCard(Card c) {
        injuryDeck.addCard(c);
    }

    /*
    * Purpose: This method is responsible for computing the total injury points inflicted upon a Player in a round,
    * which includes the injury points inflicted by each Card in their injury Deck and 5 injury points for each time
    * they were shamed.
    * Returns: The total injury points inflicted upon a Player in a round.
    */
    public int computeTotalInjury() {
        return injuryDeck.getInjuryPoints() + (5 * numTimesShamed);
    }

    //GETTERS AND SETTERS
    public int getNumCards() {
        return hand.getNumCards();
    }
    public Deck getHand() {
        return hand;
    }
    public Deck getInjuryDeck() {
        return injuryDeck;
    }
    public int getNumTimesShamed() {
        return numTimesShamed;
    }
    public void setNumTimesShamed(int numTimes) {
        this.numTimesShamed = numTimes;
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

}
