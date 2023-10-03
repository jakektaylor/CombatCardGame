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

    public void playCard(byte currPlayer, Card card, Scanner input, PrintWriter output) {
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
            return;
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

        played[currPlayer] = card;
    }

    public String getSuit() {
        return suit;
    }
}
