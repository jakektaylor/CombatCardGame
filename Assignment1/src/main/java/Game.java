import java.io.PrintWriter;
import java.util.Scanner;

public class Game {
    private byte numPlayers;
    private Player[] players;
    private Deck deck;
    private int nextRoundStarter;               //Index of the Player in 'players' to start the next round.

    public Game() {
        numPlayers = 0;
        this.deck = Deck.createGameDeck();
    }

    public void setupGame(Scanner input, PrintWriter output) {
        //Determine the number of players.
        byte result = -1;
        while (result < 3 || result > 5) {
            System.out.println("Please enter the number of players (3-5): ");
            result = input.nextByte();
            System.out.println(result);
            if (result < 3 || result > 5) {
                System.out.println("ERROR: Invalid number of players.");
                output.println("ERROR: Invalid number of players.");
            }
        }
        numPlayers =  result;
        players = new Player[numPlayers];
        input.nextLine();

        //Determine the names of the players.
        for(int i=0;i<numPlayers;i++) {
            String name = "";
            while(name.isEmpty()) {
                System.out.printf("Please enter a name for Player %d:\n", i+1);
                name = input.nextLine();
                System.out.println(name);
                if (name.isEmpty()) {
                    System.out.println("ERROR: Player name must be non-empty.");
                    output.println("ERROR: Player name must be non-empty.");
                }
            }
            players[i] = new Player(name);
        }

        //Get the number of health points.
        int HP = -1;
        while(HP < 0) {
            System.out.println("Please enter the number of health points: ");
            if(input.hasNextInt()) {
                HP = input.nextInt();
                System.out.println(HP);
            } else {
                input.nextLine();
            }
            if(HP < 0) {
                System.out.println("ERROR: Invalid number of health points.");
                output.println("ERROR: Invalid number of health points.");
            }
        }
        for (Player p : players) p.setHP(HP);

        //Set the starter of the next round.
        nextRoundStarter = 0;

        //Deal cards
        deck.shuffle();
        dealCards();
    }

    public byte getNumPlayers() {
        return numPlayers;
    }

    public Player playerAt(int i) {
        return players[i];
    }

    public void dealCards() {
        for(int i=0;i<numPlayers;i++) {
            for(int j=0; j < 12;j++) players[i].dealCard(this.deck.getCards().get(i*12 + j));
        }

    }

    public int nextRoundStarter() {
        return nextRoundStarter;
    }

    public Melee[] playRound(Scanner input, PrintWriter output, Deck[] overrideDecks) {

        //Override the Decks of each Player.
        if (overrideDecks != null) {
            for(int i=0;i<getNumPlayers();i++) {
                if (overrideDecks[i] != null) playerAt(i).overrideDeck(overrideDecks[i]);
            }
        }

        //Display the initial 'hand' of each Player at the start of a round.
        for(int i=0;i<numPlayers;i++) {
            String toPrint = String.format("Hand for Player %d:", i+1);
            System.out.println(toPrint);
            output.println(toPrint);

            toPrint = playerAt(i).displayHand();
            System.out.println(toPrint);
            output.println(toPrint);
        }

        //Start the Melees.
        Melee[] summary = new Melee[12];
        summary[0] = new Melee(getNumPlayers());
        summary[0].setStarter(nextRoundStarter);
        //Have each Player select a card.
        for(int i=0;i<numPlayers;i++) {
            //Display what each Player chooses.
            byte selection = -1;
            byte currPlayer = (byte) ((summary[0].getStarter()+ i) % numPlayers);          //Index of the current Player.
            String toPrint;

            //Player cannot play a Card.
            if (!summary[0].canPlayCard(playerAt(currPlayer))) {
                while(!summary[0].discardCard(input, output, currPlayer, playerAt(currPlayer)));
                toPrint = "Resulting hand:\n" + playerAt(currPlayer).displayHand();
                System.out.println(toPrint);
                output.println(toPrint);
            }
            //Play can play a Card.
            else {
                while (true) {
                    //Display the prompt.
                    toPrint = String.format("Player %d please choose a card:\n%s\nEnter a number between " +
                                    "1 and %d:", currPlayer + 1, this.playerAt(currPlayer).displayHand(),
                            this.playerAt(currPlayer).getNumCards());
                    System.out.println(toPrint);
                    output.println(toPrint);

                    //Get input from the user.
                    selection = input.nextByte();
                    toPrint = String.format("%d\n", selection);
                    System.out.println(toPrint);
                    output.println(toPrint);

                    if (selection < 1 || selection > playerAt(currPlayer).getNumCards()) {
                        toPrint = String.format("ERROR: Card selection must be between 1 and %d.\n",
                                playerAt(currPlayer).getNumCards());
                        System.out.println(toPrint);
                        output.println(toPrint);
                    } else {
                        selection -= (byte) 1;                             //Convert to an array index.
                        //Add the played Card to the Melee's 'played' array.
                        boolean played = summary[0].playCard(currPlayer, playerAt(currPlayer),
                                playerAt(currPlayer).getHand().getCards().get(selection), input, output);

                        //Remove the Card from the Player's hand.
                        if (played) {
                            playerAt(currPlayer).getHand().getCards().remove(selection);
                            toPrint = "Resulting hand:\n" + playerAt(currPlayer).displayHand();
                            System.out.println(toPrint);
                            output.println(toPrint);
                            break;
                        }
                    }
                }
            }
        }

        //Move to the next Player to start the next round.
        nextRoundStarter = (nextRoundStarter + 1) % numPlayers;
        return summary;
    }

    public Deck getDeck() {
        return deck;
    }

    public Player[] getPlayers() {
        return new Player[getNumPlayers()];
    }
}
