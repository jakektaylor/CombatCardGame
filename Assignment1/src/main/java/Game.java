import java.io.PrintWriter;
import java.util.ArrayList;
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

    public Melee[] playRound(Scanner input, PrintWriter output, Deck[] overrideDecks, int numMelees) {

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
        Melee[] summary = new Melee[numMelees];
        int nextMeleeStarter = nextRoundStarter;    //Variable to store the index of the Player to start the next Melee.
        for(int i=0;i<numMelees;i++) {
            summary[i] = new Melee(getNumPlayers());
            summary[i].setStarter(nextMeleeStarter);
            String toPrint;
            //Have each Player select a card.
            for (int j = 0; j < numPlayers; j++) {
                //Display what each Player chooses.
                byte selection;
                byte currPlayer = (byte) ((summary[i].getStarter() + j) % numPlayers);          //Index of the current Player.

                //Player cannot play a Card.
                if (!summary[i].canPlayCard(playerAt(currPlayer))) {
                    while (!summary[i].discardCard(input, output, currPlayer, playerAt(currPlayer))) ;
                    toPrint = "Resulting hand:\n" + playerAt(currPlayer).displayHand();
                    System.out.println(toPrint);
                    output.println(toPrint);

                    //Check if the Player reached 0 HP.
                    if(playerAt(currPlayer).getHP() == 0) {
                        return null;
                    }
                }
                //Player can play a Card.
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
                            boolean played = summary[i].playCard(currPlayer, playerAt(currPlayer),
                                    playerAt(currPlayer).getHand().getCards().get(selection), input, output);

                            //Remove the Card from the Player's hand.
                            if (played) {
                                playerAt(currPlayer).getHand().removeCard(selection);
                                toPrint = "Resulting hand:\n" + playerAt(currPlayer).displayHand();
                                System.out.println(toPrint);
                                output.println(toPrint);
                                break;
                            }
                        }
                    }
                }
            }
            //Determine the loser and add the Cards played in the Melee to their injury Deck.
            Byte loser = summary[i].computeLoser();
            if (loser != null) {
                //Compute the total injury points the loser accumulated this round.
                int totalInjury = 0;
                for (Card c : summary[i].getPlayed()) {
                    if (c != null) {
                        playerAt(loser).addInjuryCard(c);
                        totalInjury += c.getDamage();
                    }
                }
                toPrint = String.format("Player %d-%s lost the Melee. The total injury points they " +
                        "accumulated from this Melee is %d.\n", loser + 1, playerAt(loser).getName(), totalInjury);
                System.out.println(toPrint);
                output.println(toPrint);
                nextMeleeStarter = loser;               //Have the loser start the next Melee.
            } else {
                toPrint = "No loser for this Melee. All Cards played have the same value.\n";
                System.out.println(toPrint);
                output.println(toPrint);
            }
        }
        //Inflict the injury points each Player has accumulated.
        for(Player p: players) p.setHP(p.getHP() - p.getInjuryDeck().getInjuryPoints());

        //Display a summary at the end of the round.
        displayRoundSummary(output);

        //End the Game if a Player has reached 0 HP.
        for(Player p:players) {
            if(p.getHP() == 0) {
                displayWinner(output);
                return null;
            }
        }

        //Move to the next Player to start the next round.
        nextRoundStarter = (nextRoundStarter + 1) % numPlayers;
        return summary;
    }

    /*Helper method used to display the number of health points suffered by each Player in a round and the number of
    * remaining health points (HP) that they have.
    */
    private void displayRoundSummary(PrintWriter output) {
        String toPrint = String.format("End of round summary:\n%-28s%-28s%-28s\n", "","Injury Points Inflicted",
                "Remaining HP");
        System.out.printf(toPrint);
        output.printf(toPrint);

        for(int i = 0;i<numPlayers;i++) {
            toPrint = String.format("%-28s%-28d%-28d\n", String.format("Player %d: %s", i + 1, playerAt(i).getName()),
                    playerAt(i).computeTotalInjury(), playerAt(i).getHP());
            System.out.printf(toPrint);
            output.printf(toPrint);
        }
    }

    public Deck getDeck() {
        return deck;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void play(Scanner input, PrintWriter output, Deck[][] overrideDeckArray, int numRounds, int meleesPerRound) {
        setupGame(input, output);
        for(int i=0;i<numRounds;i++) {
            playRound(input, output, overrideDeckArray[i], meleesPerRound);
            for(Player p:players) {
                p.getInjuryDeck().clear();
                p.setNumTimesShamed(0);
            }
        }
    }

    //Helper method used to indicate that the game is over and display the winners(s) of the Game.
    void displayWinner(PrintWriter output) {
        ArrayList<Player> winners = new ArrayList<>();
        int maxHP = 0;

        //Determine the max HP among the Players.
        for(int i=0;i<numPlayers;i++) {
            Player p = playerAt(i);
            if(p.getHP() > maxHP) {
                maxHP = p.getHP();
            }
        }

        if(maxHP > 0) {
            //Add the winners to the list.
            for (int i = 0; i < numPlayers; i++) {
                if (playerAt(i).getHP() == maxHP) winners.add(playerAt(i));
            }
        }

        StringBuilder toPrint = new StringBuilder();
        //Multiple winners.
        if(!winners.isEmpty()) {
            toPrint.append(String.format("\nGAME OVER\nWinners: %s", winners.get(0).getName()));
            for(int i=1;i<winners.size();i++) {
                toPrint.append(String.format(", %s", winners.get(i).getName()));
            }
            toPrint.append("\n");
            System.out.printf(toPrint.toString());
            output.printf(toPrint.toString());
        }
        //No winners.
        else {
            toPrint.append("\nGAME OVER\nNo winners...\n");
            System.out.printf(toPrint.toString());
            output.printf(toPrint.toString());
        }

    }
}
