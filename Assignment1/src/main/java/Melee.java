import java.io.PrintWriter;
import java.util.Scanner;

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

    public boolean playCard(byte playerInd, Player player, Card card, Scanner input, PrintWriter output) {
        //Check if this is the first Card played.
        boolean isEmpty = true;
        for (int i = 0; i < played.length; i++) {
            if (played[i] != null) {
                isEmpty = false;
                break;
            }
        }

        String toPrint;

        //Basic Weapon as first card.
        if(isEmpty && Card.SUITS.contains(card.getType())) this.suit = card.getType();

        //Merlin or Apprentice as first Card.
        else if(isEmpty && (card.getType().equals("Me") || card.getType().equals("Ap"))) {
            input.nextLine();

            String suitChoice;
            while(true) {
                //Getting the suit for the Merlin or Apprentice card.
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
                } else break;
            }
            this.suit = suitChoice;                     //Set the suit for the Melee.

            Byte valChoice;
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
                } else break;
            }
            card.setValue(valChoice);                   //Set the value of the card.
        }
        //Trying to play a Basic Weapon of a different suit.
        else if(Card.SUITS.contains(card.getType()) && !suit.equals(card.getType())) {
            toPrint = String.format("ERROR: A basic weapon card can only be played if it is of suit %s.", getSuit());
            System.out.println(toPrint);
            output.println(toPrint);
            return false;
        }

        //Playing a Merlin or Apprentice card after the first Card has been played.
        else if(card.getType().equals("Me") || card.getType().equals("Ap")) {
            while(true) {
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
        }

        //Playing a Card if possible.
        toPrint = String.format
                ("Player %d played: %s\n", playerInd + 1,
                        card);
        System.out.printf("%s", toPrint);
        output.printf("%s", toPrint);
        played[playerInd] = card;
        return true;
    }

    //Helper method used to determine if a a Player can play an Alchemy Card after the suit for the melee has been set.
    private boolean canPlayAl(Player player) {
        boolean canPlay = true;
        if(this.suit.equals("Sw") && player.getHand().getNumSw() > 0) canPlay = false;
        else if(this.suit.equals("Ar") && player.getHand().getNumAr() > 0) canPlay = false;
        else if(this.suit.equals("So") && player.getHand().getNumSo() > 0) canPlay = false;
        else if(this.suit.equals("De") && player.getHand().getNumDe() > 0) canPlay = false;
        else if(player.getHand().getNumMe() > 0 || player.getHand().getNumAp() > 0) canPlay = false;
        return canPlay;
    }

    public String getSuit() {
        return suit;
    }
}
