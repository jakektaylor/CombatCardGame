import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private byte numPlayers;                    //The number of Players playing the Game.
    private Player[] players;                   //Array to hold the different Player objects.
    private Deck deck;                          //The Game Deck from which Cards are dealt.
    private int nextRoundStarter;               //Index of the Player in 'players' to start the next round.
    private int nextMeleeStarter;               //The Player that will start the next Melee.
    private final StringBuilder divider;        //Used to divide up different sections when displaying information.

    private ArrayList<Player> winners;    //When the Game ends, it is populated with the winning players (if any).


    public Game() {
        numPlayers = 0;
        this.deck = Deck.createGameDeck();
        nextRoundStarter = 0;
        divider = new StringBuilder();
        for(int i=0;i<160;i++) divider.append("-");
        winners = new ArrayList<>();
    }

    /*Purpose: The purpose of this method is to determine the number of Players that will be Playing the Game.
    * Parameters: input-Scanner to gather input from
    *             output-PrintWriter to write output to
    */
    public void setNumPlayers(Scanner input, PrintWriter output) {
        //Determine the number of players.
        byte result;
        String toPrint;                                             //Variable to hold any Strings to be displayed.
        toPrint = "Please enter the number of players (3-5):\n";
        System.out.printf(toPrint);
        output.printf(toPrint);

        numPlayers = input.nextByte();
        toPrint = String.format("%d\n", numPlayers);
        System.out.printf(toPrint);
        output.printf(toPrint);
        if (numPlayers < 3 || numPlayers > 5) {
            toPrint = "ERROR: Invalid number of players.";
            System.out.println(toPrint);
            output.println(toPrint);
        } else {
            players = new Player[numPlayers];
            input.nextLine();
        }
    }

    /*Purpose: This method is responsible for determining the name of a given Player.
    * Parameters: playerIndex-The index of the Player whose name we are trying to determine in the 'players' array.
    *             input-Scanner used to get the name
    *             output-Used to write output of the program to
    * Returns: true if a valid Player name is entered, false otherwise.
    */
    public boolean setPlayerName(int playerIndex, Scanner input, PrintWriter output) {
        String name;
        String toPrint;
        toPrint = String.format("Please enter a name for Player %d:\n", playerIndex+1);
        System.out.printf(toPrint);
        output.printf(toPrint);

        name = input.nextLine();
        toPrint = String.format("%s\n", name);
        System.out.printf(toPrint);
        output.printf(toPrint);

        if (name.isEmpty()) {
            toPrint = "ERROR: Player name must be non-empty.";
            System.out.println(toPrint);
            output.println(toPrint);
            return false;
        }
        players[playerIndex] = new Player(name);
        return true;
    }

    /*Purpose: THe purpose of this method is to set the number of health points for each Player.
    * Returns: true if a valid HP value is entered, false otherwise.
    */
    public boolean setHP(Scanner input, PrintWriter output) {
        String toPrint;
        int HP = -1;

        toPrint = "Please enter the number of health points:\n";
        System.out.printf(toPrint);
        output.printf(toPrint);

        if(input.hasNextInt()) {
            HP = input.nextInt();
            toPrint = String.format("%d\n", HP);
            System.out.println(toPrint);
            output.println(toPrint);
        } else {
            input.nextLine();
        }
        if(HP <= 0) {
            toPrint = "ERROR: Invalid number of health points.";
            System.out.println(toPrint);
            output.println(toPrint);
            return false;
        }
        for (Player p : players) p.setHP(HP);
        return true;
    }

    /* Purpose: The purpose of this method is to deal 12 Cards from the Game Deck to each Player.*/
    public void dealCards() {
        deck.shuffle();
        for(int i=0;i<numPlayers;i++) {
            for(int j=0; j < 12;j++) players[i].dealCard(this.deck.getCards().get(i*12 + j));
        }
    }

    /*
     * Purpose: This method is responsible for getting the number of Players, player names and initial HP of each
     * Player. It also sets the initial value of Game-related parameters and shuffles the Deck and deals each Player
     * Cards.
     * Parameters: -input: Scanner from which input is collected.
     *             -output: PrintWriter to write output to for testing.
     * */
    public void setupGame(Scanner input, PrintWriter output) {
        while(numPlayers < 3 || numPlayers > 5) setNumPlayers(input, output);    //Determine the number of players.

        for(int i=0;i<numPlayers;i++) {
            while(!setPlayerName(i, input, output));
        }

        while(!setHP(input, output));
    }

    /*Purpose: The purpose of this method is to handle the starting of a round. This includes dealing a new hand of
    * Cards to each Player, overriding their hands if necessary and displaying the initial hand of each Player.
    */
    public void beginRound(PrintWriter output, Deck[] overrideDecks) {
        dealCards();

        //Override the Decks of each Player.
        if (overrideDecks != null) {
            for(int i=0;i<getNumPlayers();i++) {
                if (overrideDecks[i] != null) playerAt(i).overrideDeck(overrideDecks[i]);
            }
        }

        System.out.println(divider);
        //Display the initial 'hand' of each Player at the start of a round.
        for(int i=0;i<numPlayers;i++) {
            String toPrint = String.format("Hand for Player %d-%s:\n%s\n", i+1, playerAt(i).getName(), playerAt(i).displayHand());
            System.out.printf(toPrint);
            output.printf(toPrint);
        }
        System.out.println(divider);

        nextMeleeStarter = nextRoundStarter;                    //Set the Player to start the first Melee.
    }

    /* Purpose: The purpose of this method is to play an individual Melee.
    Parameters: input: -Scanner that provides user input
                output-PrintWriter that is written to for testing
                meleeStarter-The index of the Player in the 'players' array who starts the Melee
    Returns: A Melee object summarizing what occurred in the Melee unless a Player reaches 0HP in the Melee due to
    shaming, in which case 'null' is returned.
    */
    public Melee playMelee(Scanner input, PrintWriter output) {
        Melee current = new Melee(getNumPlayers());
        current.setStarter(nextMeleeStarter);
        String toPrint;
        for (int j = 0; j < numPlayers; j++) {

            byte currPlayer = (byte) ((current.getStarter() + j) % numPlayers);   //Index of the current Player.

            //Player cannot play a Card.
            if (!current.canPlayCard(playerAt(currPlayer))) {
                //Wait until the Player chooses a valid Card to discard.
                while (!current.discardCard(input, output, currPlayer, playerAt(currPlayer)));
                toPrint = "Resulting hand:\n" + playerAt(currPlayer).displayHand();
                System.out.println(toPrint);
                output.println(toPrint);

                //Check if the Player reached 0 HP. If so, the Game ends early (shaming).
                if(playerAt(currPlayer).getHP() == 0) {
                    //Clear the injury decks and number of times shamed of the other Players.
                    for(int k =0;k<numPlayers;k++) {
                        if(k != currPlayer) {
                            playerAt(k).getInjuryDeck().clear();
                            playerAt(k).setNumTimesShamed(0);
                        }
                    }
                    displayRoundSummary(output);
                    computeWinners(output, true);
                    return null;
                }
            }

            //Player can play a Card.
            else {
                //Loop until the Player has chosen a valid Card to play.
                while (true) {
                    //Display the prompt.
                    toPrint = String.format("Player %d-%s please choose a card:\n%s\nEnter a number between " +
                                    "1 and %d:\n", currPlayer + 1, playerAt(currPlayer).getName(),
                            this.playerAt(currPlayer).displayHand(),
                            this.playerAt(currPlayer).getNumCards());
                    System.out.printf(toPrint);
                    output.printf(toPrint);

                    //Get input from the user which is a number corresponding to the Card they want to play.
                    byte selection = input.nextByte();
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
                        boolean played = current.playCard(currPlayer, playerAt(currPlayer),
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
            System.out.println(divider);
        }
        //Determine the loser and add the Cards played in the Melee to their injury Deck (if there was a loser).
        current.computeLoser();
        if (current.getLoser() != null) {
            //Compute the total injury points the loser accumulated this Melee.
            int totalInjury = 0;
            for (Card c : current.getPlayed()) {
                if (c != null) {
                    playerAt(current.getLoser()).addInjuryCard(c);
                    totalInjury += c.getDamage();
                }
            }
            toPrint = String.format("Player %d-%s lost the Melee. The total injury points they " +
                            "accumulated from this Melee is %d.\n", current.getLoser() + 1,
                    playerAt(current.getLoser()).getName(), totalInjury);
            System.out.println(toPrint);
            output.println(toPrint);

            nextMeleeStarter = current.getLoser();                      //Set the nextMeleeStarter
        } else {
            toPrint = "No loser for this Melee. All Cards played have the same value.\n";
            System.out.println(toPrint);
            output.println(toPrint);
        }
        System.out.println(divider);

        return current;
    }



    /*
    * Purpose: The purpose of this method is to play a single round of a Game.
    * Parameters: -input: Scanner from which input is provided
    *             -output: PrintWriter to which output is written for testing
    *             -overrideDecks-an array of Decks used to override the Cards dealt to each Player if it is not null
    *             -numMelees-the number of Melees to play in the round (12 is used when the Game is actually played)
    * Returns: Melee[] summary: An array of 'numMelees' Melee objects that provides the details of what occurred in
    *                           each Melee. It is 'null' if a Player reaches 0 HP to indicate that the Game is over.
    */
    public Melee[] playRound(Scanner input, PrintWriter output, Deck[] overrideDecks, int numMelees) {

        beginRound(output, overrideDecks);          //Set up the round.

        //Play the Melees
        Melee[] summary = new Melee[numMelees];
        for(int i=0;i<numMelees;i++) {
            summary[i] = playMelee(input, output);
            if(summary[i] == null) {
                return null;
            }
        }
        //End the round.
        return getRoundResult(output, summary);
    }

    /*Purpose: This method is called after the Melees for a round have been played and is responsible for applying
    injury points to each Player, displaying a summary for the round and preparing the Game state for the next round. It
    can also cause the Game to end if a Player reaches 0 HP.
    Parameters: output-PrintWriter to which output is written for testing
                summary-Melee[] consisting of all Melees played in the round
    Return: summary if the Game is not over, otherwise returns null.
    */
    public Melee[] getRoundResult(PrintWriter output, Melee[] summary) {
        //Inflict the injury points each Player has accumulated.
        for(Player p: players) p.setHP(p.getHP() - p.getInjuryDeck().getInjuryPoints());

        //Display a summary at the end of the round.
        displayRoundSummary(output);

        //End the Game if a Player has reached 0 HP.
        for(Player p:players) {
            if(p.getHP() == 0) {
                computeWinners(output, false);
                return null;
            }
        }

        //Move to the next Player to start the next round.
        nextRoundStarter = (nextRoundStarter + 1) % numPlayers;
        return summary;
    }

    /*Purpose: Helper method used to display the number of health points suffered by each Player in a round and the
    number of remaining health points (HP) that they have.
    Parameters: -output: PrintWriter used to write output to for testing
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

    /*Purpose: This method can be used for testing or to actually play the Game. It can be used to play multiple rounds
    * and specify the override Decks to use for each Player in each round when testing.
    * Parameters:   -input: Scanner from which input is collected
    *               -output: PrintWriter to which output is written for testing
    *               -overrideDeckArray: Deck[][], where the first dimension corresponds to the number of rounds and the
    *               second corresponds to the number of Players. The entry Deck[i][j] corresponds to the Deck to use in
    *               round i for the Player at index j in the 'players' array. Set to null to indicate the user would
    *               like to play the full Game in the console, where there is an undetermined number of rounds and
    *               each round consists of 12 melees.
    *               -numRounds: The number of rounds to play.
    *               -meleesPerRound: The number of melees to play per round.
    * Returns: summaries: An ArrayList<Melee[]> consisting of an Array of Melees played for each round.
    * */
    public ArrayList<Melee[]> play(Scanner input, PrintWriter output, Deck[][] overrideDeckArray, Integer numRounds, Integer meleesPerRound) {
        setupGame(input, output);
        ArrayList<Melee[]> summaries = new ArrayList<>();                 //ArrayList to hold each of the round summaries.

        //If overrideDeckArray is not null, we are testing.
        if(overrideDeckArray != null) {
            for (int i = 0; i < numRounds; i++) {
                Melee [] summary = playRound(input, output, overrideDeckArray[i], meleesPerRound);
                summaries.add(summary);
                if (summary == null) break;                             //If summary is null, the Game has ended.
                else {
                    //Clear each Player's injury Deck and reset the number of times shamed to 0 after the round.
                    for (Player p : players) {
                        p.getInjuryDeck().clear();
                        p.setNumTimesShamed(0);
                    }
                }
            }
        }
        //Setting overrideDeckArray to null indicates that the user would like to play the Game in the console.
        else {
            //Play until the Game ends (i.e. 1+ Players reach 0 HP)
            while(true) {
                Melee[] summary = playRound(input, output, null, 12);
                summaries.add(summary);
                if(summary == null) break;                              //If summary is null the Game has ended.
                else {
                    //Clear each Player's injury Deck and reset the number of times shamed to 0 after the round.
                    for (Player p : players) {
                        p.getInjuryDeck().clear();
                        p.setNumTimesShamed(0);
                    }
                }
            }
        }
        return summaries;
    }

    /*Purpose:Helper method used to indicate that the game is over and compute and display the winners(s) of the Game.
    * Parameters: -output: PrintWriter to which output is written to for testing
    *             -isShamingEnding: boolean variable that is true when the Game has ended early due to shaming.
    */
    void computeWinners(PrintWriter output, boolean isShamingEnding) {
        int maxHP = 0;

        //Determine the max HP among the Players.
        for(int i=0;i<numPlayers;i++) {
            Player p = playerAt(i);
            if(p.getHP() > maxHP) {
                maxHP = p.getHP();
            }
        }

        //There are only winners if maxHP is greater than 0.
        if(maxHP > 0) {
            //Add the winners to the list.
            for (int i = 0; i < numPlayers; i++) {
                if (playerAt(i).getHP() == maxHP) winners.add(playerAt(i));
            }
        }

        //Display a different message depending on whether the Game ended early due to shaming.
        StringBuilder toPrint = new StringBuilder();
        if(!isShamingEnding) toPrint.append("\nGAME OVER\n");
        else toPrint.append("\nGAME OVER DUE TO SHAMING\n");

        //1+ winners
        if(!winners.isEmpty()) {
            toPrint.append(String.format("Winners: %s", winners.get(0).getName()));
            for(int i=1;i<winners.size();i++) {
                toPrint.append(String.format(", %s", winners.get(i).getName()));
            }
            toPrint.append("\n");
            System.out.printf(toPrint.toString());
            output.printf(toPrint.toString());
        }
        //No winners.
        else {
            toPrint.append("No winners...\n");
            System.out.printf(toPrint.toString());
            output.printf(toPrint.toString());
        }
    }

    //GETTER METHODS
    public byte getNumPlayers() {
        return numPlayers;
    }

    public Player playerAt(int i) {
        return players[i];
    }

    public int nextRoundStarter() {
        return nextRoundStarter;
    }

    public Deck getDeck() {
        return deck;
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getNextRoundStarter() {
        return nextRoundStarter;
    }
    public ArrayList<Player> getWinners() {return winners;}
}
