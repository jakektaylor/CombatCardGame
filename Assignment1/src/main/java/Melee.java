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

        //Basic Weapon as first card.
        if(isEmpty && Card.SUITS.contains(card.getType())) this.suit = card.getType();

        //Merlin or Apprentice as first Card.
        else if(isEmpty && (card.getType().equals("Me") || card.getType().equals("Ap"))) {
            input.nextLine();
            String toPrint;

            //Getting the suit for the Merlin or Apprentice card.
            if(card.getType().equals("Me")) toPrint = "Please enter a suit for the Merlin card: ";
            else toPrint = "Please enter a suit for the Apprentice card: ";
            System.out.println(toPrint);
            output.println(toPrint);
            String suitChoice = input.nextLine();
            toPrint = suitChoice;
            System.out.println(toPrint);
            output.println(toPrint);

            //Getting the value for the Merlin or Apprentice card.
            if(card.getType().equals("Me")) toPrint = "Please enter a value for the Merlin card: ";
            else toPrint = "Please enter a value for the Apprentice card: ";
            System.out.println(toPrint);
            output.println(toPrint);
            Byte valChoice = input.nextByte();
            toPrint = String.format("%d", valChoice);
            System.out.println(toPrint);
            output.println(toPrint);
        }
        played[currPlayer] = card;
    }

    public String getSuit() {
        return suit;
    }
}
