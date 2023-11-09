import java.io.PrintWriter;
import java.util.Scanner;

public class Melee {

    /*Array to hold all the Cards played in a Melee, where the index 'i' corresponds to the Card played by the ith
    Player in the Game's 'players' array.*/
    private Card[] played;
    private String suit;                  //One of {"Sw", "Ar", "So", "De"}
    private int starter;                  //The index of the Player in the Game's 'players' array who started the Melee
    private Integer loser;               //The index of the Player in the Game's 'players' array who lost the melee,

    public Melee(byte numPlayers) {
        played = new Card[numPlayers];
        for(int i=0;i<numPlayers;i++) played[i] = null;
        suit = null;
        loser = null;
    }

    /*
    * Purpose: This method allows a Player to play a Card by adding it to the 'played' array at the same index as the index
    * the Player is found at in the Game's 'players' array.
    * Parameters: -playerInd: The index of the Player in the Game's 'players' array.
    *             -player: The Player object that represents the player playing the Card.
    *             -card: The Card that is being played.
    *             -input: Scanner from which input is given.
    *             -output: output to which output is written for testing.
    * Returns: true if the Card was played successfully (i.e. if it was allowed to be played according to the rules),
    *          false otherwise
    */
    public boolean playCard(byte playerInd, Player player, Card card, Scanner input, PrintWriter output) {

        //Check if this is the first Card played.
        boolean isEmpty = true;
        for (int i = 0; i < played.length; i++) {
            if (played[i] != null) {
                isEmpty = false;
                break;
            }
        }

        String toPrint;                                                 //String to hold the next value to be printed.

        //Case where there is no suit for the melee (goes to the bottom).
        if(!isEmpty && suit == null && Card.SUITS.contains(card.getType()));

        //Basic Weapon as first card.
        else if(isEmpty && Card.SUITS.contains(card.getType())) this.suit = card.getType();

        //Merlin or Apprentice as first Card.
        else if(isEmpty && (card.getType().equals("Me") || card.getType().equals("Ap"))) {
            input.nextLine();

            String suitChoice;                                              //The suit the user chooses for the Card.
            while(true) {
                //Getting the suit for the Merlin or Apprentice card and displaying it.
                if (card.getType().equals("Me")) toPrint = "Please enter a suit for the Merlin card: ";
                else toPrint = "Please enter a suit for the Apprentice card: ";
                System.out.println(toPrint);
                output.println(toPrint);

                suitChoice = input.nextLine();
                toPrint = suitChoice;
                System.out.println(toPrint);
                output.println(toPrint);

                //Invalid suit entered.
                if (!Card.SUITS.contains(suitChoice)) {
                    toPrint = String.format("ERROR: Please enter a valid suit from %s.", Card.SUITS);
                    System.out.println(toPrint);
                    output.println(toPrint);
                }
                //Valid suit entered.
                else break;
            }
            this.suit = suitChoice;                     //Set the suit for the Melee.

            Byte valChoice;                             //The value chosen for the Card.
            while(true) {
                //Getting the value for the Merlin or Apprentice card.
                if (card.getType().equals("Me")) toPrint = "Please enter a value for the Merlin card: ";
                else toPrint = "Please enter a value for the Apprentice card: ";
                System.out.println(toPrint);
                output.println(toPrint);

                valChoice = input.nextByte();
                toPrint = String.format("%d", valChoice);
                System.out.println(toPrint);
                output.println(toPrint);

                //Invalid value.
                if (valChoice < 1 || valChoice > 15) {
                    toPrint = "ERROR: Please enter a value between 1 and 15.";
                    System.out.println(toPrint);
                    output.println(toPrint);
                }
                //Valid value.
                else break;
            }
            card.setValue(valChoice);                   //Set the value of the card.
        }
        //Trying to play a Basic Weapon of a different suit.
        else if(Card.SUITS.contains(card.getType()) && !card.getType().equals(suit)) {
            toPrint = String.format("ERROR: A basic weapon card can only be played if it is of suit %s.", getSuit());
            System.out.println(toPrint);
            output.println(toPrint);
            return false;
        }

        //Playing a Merlin or Apprentice card after the first Card has been played.
        else if(card.getType().equals("Me") || card.getType().equals("Ap")) {
            while(true) {
                //Get the value of the Card and display it.
                if (card.getType().equals("Me")) toPrint = "Please enter a value for the Merlin card: ";
                else toPrint = "Please enter a value for the Apprentice card: ";
                System.out.println(toPrint);
                output.println(toPrint);

                Byte value = input.nextByte();
                toPrint = String.format("%d", value);
                System.out.println(toPrint);
                output.println(toPrint);

                //Check if the user entered a valid value.
                if (value < 1 || value > 15) {
                    toPrint = "ERROR: Please enter a value between 1 and 15.";
                    System.out.println(toPrint);
                    output.println(toPrint);
                } else {
                    card.setValue(value);
                    break;
                }
            }
        }

        //Playing an Alchemy Card.
        else if (card.getType().equals("Al")) {
            if(!isEmpty && !canPlayAl(player)) {
                toPrint = String.format("\nERROR: Cannot play an Alchemy card as you have a basic weapon card of the " +
                        "same suit as the melee (%s) or a Merlin or Apprentice card.\n", suit);
                System.out.println(toPrint);
                output.println(toPrint);
                return false;
            }
            //Trying to play an Alchemy Card as the first Card.
            else if (isEmpty) {
                //Cannot play as first Card if you have a Basic Weapon Card.
                if(player.getHand().getNumSw() > 0|| player.getHand().getNumAr() > 0 || player.getHand().getNumSo() > 0
                || player.getHand().getNumDe() > 0) {
                    toPrint = "ERROR: Cannot play an Alchemy Card as the first Card when you have a " +
                            "basic weapon card.";
                    System.out.println(toPrint);
                    output.println(toPrint);
                    return false;
                }
                //Cannot play as first Card if you have a Merlin Card.
                else if(player.getHand().getNumMe() > 0) {
                    toPrint = "ERROR: Cannot play an Alchemy Card as the first Card when you have a " +
                            "Merlin card.";
                    System.out.println(toPrint);
                    output.println(toPrint);
                    return false;
                }
                //Cannot play as first Card if you have an Apprentice Card.
                else if(player.getHand().getNumAp() > 0) {
                    toPrint = "ERROR: Cannot play an Alchemy Card as the first Card when you have an " +
                    "Apprentice card.";
                    System.out.println(toPrint);
                    output.println(toPrint);
                    return false;
                }
            }
        }

        //Playing a Card if possible (i.e. the method has not returned yet).
        toPrint = String.format
                ("Player %d played: %s\n", playerInd + 1,
                        card);
        System.out.printf("%s", toPrint);
        output.printf("%s", toPrint);
        played[playerInd] = card;

        //Remove the Card from the Player's hand and display their resulting hand.
        if(player != null) {
            player.getHand().removeCard(card);
            toPrint = "Resulting hand:\n" + player.displayHand();
            System.out.println(toPrint);
            output.println(toPrint);
        }
        return true;
    }

    /*
    Purpose: Helper method used to determine if a Player can play an Alchemy Card after the suit for the melee has
    been set.
    Parameters: -player: The Player trying to play the Alchemy Card.
    Returns: true if they can play an Alchemy Card, false otherwise.
     */
    private boolean canPlayAl(Player player) {
        boolean canPlay = true;
        if("Sw".equals(this.suit) && player.getHand().getNumSw() > 0) canPlay = false;
        else if("Ar".equals(this.suit) && player.getHand().getNumAr() > 0) canPlay = false;
        else if("So".equals(this.suit) && player.getHand().getNumSo() > 0) canPlay = false;
        else if("De".equals(this.suit) && player.getHand().getNumDe() > 0) canPlay = false;
        else if(player.getHand().getNumMe() > 0 || player.getHand().getNumAp() > 0) canPlay = false;
        return canPlay;
    }

    /*
    * Purpose: This method is used to determine whether there is any Card AT ALL that a Player can play.
    * Parameters: -player: The Player who we are trying to determine if they can play a Card.
    * Returns: true if there is a Card in the Player's hand that they can play, false otherwise.*/
    public boolean canPlayCard(Player player) {
        //Can always play with Merlin or Apprentice.
        if(player.getHand().getNumMe() > 0 || player.getHand().getNumAp() > 0) return true;
        //For playing an Alchemy card first.
        else if (suit == null && player.getHand().getNumCards() == player.getHand().getNumAl()) return true;
        //For playing a Basic Weapon first.
        else if(suit == null && (player.getHand().getNumSw() > 0 || player.getHand().getNumAr() > 0 ||
                player.getHand().getNumSo() > 0 || player.getHand().getNumDe() > 0)) return true;
        //For playing a Basic Weapon or Alchemy Card after the suit for the Melee has been set.
        else if (suit != null) {
            //Sw
            if(suit.equals("Sw") && player.getHand().getNumSw() > 0) return true;
            //Ar
            else if(suit.equals("Ar") && player.getHand().getNumAr() > 0) return true;
            //So
            else if(suit.equals("So") && player.getHand().getNumSo() > 0) return true;
            //De
            else if(suit.equals("De") && player.getHand().getNumDe() > 0) return true;
            //Al
            else return player.getHand().getNumAl() > 0;
        }
        return false;
    }

    /*
    * Purpose: This method is used to have a Player discard a Card from their hand in the case where they cannot play
    * a Card.
    * Parameters: -input: Scanner from which input is received.
    *             -output: PrintWriter to which output is written for testing.
    *             -currPlayer: The index of the Player who is discarding a Card in the Game's 'players' array.
    *             -player: Player object representing the Player that is discarding a Card.
    * Returns: true if a valid Card is chosen to discard and false otherwise.*/
    public boolean discardCard(Scanner input, PrintWriter output, byte currPlayer, Player player) {
        //Display the hand of the Player and prompt them to choose a Card to discard.
        String toPrint = String.format("Player %d please choose a card to discard as there is no Card you can play: ",
                currPlayer + 1);
        System.out.println(toPrint);
        output.println(toPrint);

        toPrint = player.getHand().toString();
        System.out.printf(toPrint);
        output.println(toPrint);

        //Determine the Player's choice of Card and display it.
        byte choice = input.nextByte();
        toPrint = String.format("%d\n", choice);
        System.out.printf(toPrint);
        output.printf(toPrint);

        /*If the choice is between 1 and the number of Cards in the Player's hand, the chosen Card is discarded
        * successfully.*/
        if (choice >= 1 && choice <= player.getHand().getNumCards()) {
            choice -= 1;                                                 //Convert to array index.

            //Display the Card to be removed.
            toPrint = String.format
                    ("Player %d discarded: %s\n", currPlayer + 1,
                            player.getHand().getCards().get(choice));
            System.out.printf("%s", toPrint);
            output.printf("%s", toPrint);

            //Remove the Card from the Player's hand and remove 5 HP (shaming).
            player.getHand().removeCard(choice);
            player.setHP(player.getHP() - 5);

            //Increment the number of times the Player has been shamed.
            player.setNumTimesShamed(player.getNumTimesShamed() + 1);

            //Display the resulting hand of the Player.
            toPrint = "Resulting hand:\n" + player.displayHand();
            System.out.println(toPrint);
            output.println(toPrint);

            return true;
        }
        //Display an error message if the user made an invalid choice.
        else {
            toPrint = String.format("ERROR: Please enter a value between 1 and %d.", player.getNumCards());
            System.out.println(toPrint);
            output.println(toPrint);
            return false;
        }
    }

    /*Purpose: This method is used to determine the loser of a Melee and if there is a loser, add the Cards from the
    Melee to their injuryDeck. It returns the index of the Player in the Game's
    'players' array that lost the Melee.
    Returns: The index of the Player in the Game's 'players' array that lost the Melee or null if there was no loser.*/
    public void computeLoser(Player[] players, PrintWriter output) {
        byte min_value = Byte.MAX_VALUE;
        for(int i=0;i<played.length;i++) {
            if(played[i] == null) continue;
            boolean ignore = false;
            //Check if this Card has a repeated value.
            for(int j=0;j<played.length;j++) {
                if(i != j && played[j] != null && played[i].getValue() == played[j].getValue()) {
                    ignore = true;
                    break;
                }
            }

            if(!ignore && played[i].getValue() < min_value) {
                min_value = played[i].getValue();
                loser= i;
            }
        }
        //If the loser isn't null, add the Cards from the Melee to their injury Deck.
        String toPrint;
        if(loser != null && players != null) {
            int totalInjury = 0;                    //The total injury points accumulated IN THIS MELEE
            Player player = players[loser];
            for (Card c : played) {
                if (c != null) {
                    player.addInjuryCard(c);
                    totalInjury += c.getDamage();
                }
            }
            toPrint = String.format("Player %d-%s lost the Melee. The total injury points they " +
                            "accumulated from this Melee is %d.\n", loser + 1,
                    player.getName(), totalInjury);
            System.out.println(toPrint);
            output.println(toPrint);
        } else if (loser == null) {
            toPrint = "No loser for this Melee. All Cards played have the same value.\n";
            System.out.println(toPrint);
            output.println(toPrint);
        }
    }

    //GETTER AND SETTER METHODS
    public Card[] getPlayed() {
        return played;
    }

    public String getSuit() {
        return suit;
    }

    public int getStarter() {
        return starter;
    }

    public Integer getLoser() {
        return loser;
    }

    public void setStarter(int starter) {
        this.starter = starter;
    }

}
