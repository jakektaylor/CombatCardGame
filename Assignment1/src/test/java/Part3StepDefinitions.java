import io.cucumber.java.bs.A;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class Part3StepDefinitions {

    private Game game;
    private Deck[] overrideDecks;                     //The override Decks for each Player to be used in a given round.

    private StringBuilder input;
    private StringWriter output;

    private ArrayList<Melee[]> roundSummaries;             //Contains the round results
    Melee[] currentRound;                                  //Contains the Melees from the current round.
    int currentMelee;                                      //The current Melee we are playing.

    //Game setup--------------------------------------------------------------------------------------------------------
    @Given("we begin a game")
    public void we_begin_a_game() {
        game = new Game();
        input = new StringBuilder();
        output = new StringWriter();
        roundSummaries = new ArrayList<>();
    }
    @Given("enter {int} for the number of players")
    public void enter_for_the_number_of_players(Integer numPlayers) {
        input.append(String.format("%d\n", numPlayers));
        overrideDecks = new Deck[numPlayers];
        for(int i=0;i<overrideDecks.length;i++) overrideDecks[i] = new Deck();
    }
    @Given("enter {string} for the name of the first player")
    public void enter_for_the_name_of_the_first_player(String p1) {
        input.append(String.format("%s\n", p1));
    }
    @Given("enter {string} for the name of the second player")
    public void enter_for_the_name_of_the_second_player(String p2) {
        input.append(String.format("%s\n", p2));
    }
    @Given("enter {string} for the name of the third player")
    public void enter_for_the_name_of_the_third_player(String p3) {
        input.append(String.format("%s\n", p3));
    }

    @Given("enter {string} for the name of the fourth player")
    public void enter_for_the_name_of_the_fourth_player(String p4) {input.append(String.format("%s\n", p4));}


    @Given("set initial health points at {int}")
    public void set_initial_health_points_at(Integer hp) {
        input.append(String.format("%d\n", hp));
    }

    //Beginning Rounds--------------------------------------------------------------------------------------------------
    // Beginning the first round in Scenario A
    @When("begin the first round and distribute 12 cards to each player")
    public void begin_the_first_round_and_distribute_cards_to_each_player() {
        game.setupGame(new Scanner(input.toString()), new PrintWriter(output));
        input = new StringBuilder();                                                //Reset the input StringBuilder

        //Create the override Deck for the first round for P1.
        overrideDecks[0].addCard(new Card("Ar", (byte) 1));
        overrideDecks[0].addCard(new Card("Ar", (byte) 2));
        overrideDecks[0].addCard(new Card("Ar", (byte) 5));
        overrideDecks[0].addCard(new Card("Sw", (byte) 5));
        overrideDecks[0].addCard(new Card("Sw", (byte) 8));
        overrideDecks[0].addCard(new Card("Sw", (byte) 9));
        overrideDecks[0].addCard(new Card("Sw", (byte) 10));
        overrideDecks[0].addCard(new Card("Sw", (byte) 13));
        overrideDecks[0].addCard(new Card("So", (byte) 3));
        overrideDecks[0].addCard(new Card("So", (byte) 5));
        overrideDecks[0].addCard(new Card("So", (byte) 7));
        overrideDecks[0].addCard(new Card("So", (byte) 15));

        //Create the override Deck for the first round for P2.
        overrideDecks[1].addCard(new Card("Me", null));
        overrideDecks[1].addCard(new Card("Ar", (byte) 3));
        overrideDecks[1].addCard(new Card("Ar", (byte) 4));
        overrideDecks[1].addCard(new Card("Sw", (byte) 2));
        overrideDecks[1].addCard(new Card("Sw", (byte) 15));
        overrideDecks[1].addCard(new Card("Sw", (byte) 1));
        overrideDecks[1].addCard(new Card("Sw", (byte) 12));
        overrideDecks[1].addCard(new Card("Sw", (byte) 4));
        overrideDecks[1].addCard(new Card("So", (byte) 2));
        overrideDecks[1].addCard(new Card("So", (byte) 4));
        overrideDecks[1].addCard(new Card("So", (byte) 9));
        overrideDecks[1].addCard(new Card("So", (byte) 12));

        //Create the override Deck for the first round for P3.
        overrideDecks[2].addCard(new Card("Me", null));
        overrideDecks[2].addCard(new Card("Ap", null));                 //Legal alchemy card to be played
        overrideDecks[2].addCard(new Card("Al", (byte) 4));
        overrideDecks[2].addCard(new Card("Sw", (byte) 3));
        overrideDecks[2].addCard(new Card("Sw", (byte) 6));
        overrideDecks[2].addCard(new Card("Sw", (byte) 7));
        overrideDecks[2].addCard(new Card("Sw", (byte) 11));
        overrideDecks[2].addCard(new Card("Sw", (byte) 14));
        overrideDecks[2].addCard(new Card("So", (byte) 1));
        overrideDecks[2].addCard(new Card("So", (byte) 6));
        overrideDecks[2].addCard(new Card("So", (byte) 8));
        overrideDecks[2].addCard(new Card("So", (byte) 14));

        //Begin the round
        game.beginRound(new PrintWriter(output), overrideDecks);
        currentRound = new Melee[12];
    }

    //Beginning the second round in Scenario A
    @When("begin the second round and distribute 12 cards to each player")
    public void begin_the_second_round_and_distribute_cards_to_each_player() {
        //Create the override Decks for the second round.
        for(int i=0;i<game.getNumPlayers();i++) overrideDecks[i] = new Deck();

        //Create the override Deck for P1.
        overrideDecks[0].addCard(new Card("Sw", (byte) 1));
        overrideDecks[0].addCard(new Card("Sw", (byte) 6));
        overrideDecks[0].addCard(new Card("Sw", (byte) 8));
        overrideDecks[0].addCard(new Card("Sw", (byte) 10));
        overrideDecks[0].addCard(new Card("Sw", (byte) 15));
        overrideDecks[0].addCard(new Card("Ar", (byte) 3));
        overrideDecks[0].addCard(new Card("Ar", (byte) 4));
        overrideDecks[0].addCard(new Card("Ar", (byte) 9));
        overrideDecks[0].addCard(new Card("Ar", (byte) 10));
        overrideDecks[0].addCard(new Card("Ar", (byte) 15));
        overrideDecks[0].addCard(new Card("So", (byte) 7));
        overrideDecks[0].addCard(new Card("De", (byte) 10));

        //Create the override Deck for P2.
        overrideDecks[1].addCard(new Card("Sw", (byte) 2));
        overrideDecks[1].addCard(new Card("Sw", (byte) 5));
        overrideDecks[1].addCard(new Card("Sw", (byte) 7));
        overrideDecks[1].addCard(new Card("Sw", (byte) 11));
        overrideDecks[1].addCard(new Card("Sw", (byte) 14));
        overrideDecks[1].addCard(new Card("Ar", (byte) 2));
        overrideDecks[1].addCard(new Card("Ar", (byte) 5));
        overrideDecks[1].addCard(new Card("Ar", (byte) 7));
        overrideDecks[1].addCard(new Card("Ar", (byte) 11));
        overrideDecks[1].addCard(new Card("Ar", (byte) 14));
        overrideDecks[1].addCard(new Card("So", (byte) 12));
        overrideDecks[1].addCard(new Card("De", (byte) 9));

        //Create the override Deck for P3.
        overrideDecks[2].addCard(new Card("Sw", (byte) 3));
        overrideDecks[2].addCard(new Card("Sw", (byte) 4));
        overrideDecks[2].addCard(new Card("Sw", (byte) 9));
        overrideDecks[2].addCard(new Card("Sw", (byte) 12));
        overrideDecks[2].addCard(new Card("Sw", (byte) 13));
        overrideDecks[2].addCard(new Card("Ar", (byte) 1));
        overrideDecks[2].addCard(new Card("Ar", (byte) 6));
        overrideDecks[2].addCard(new Card("Ar", (byte) 8));
        overrideDecks[2].addCard(new Card("Ar", (byte) 12));
        overrideDecks[2].addCard(new Card("Ar", (byte) 13));
        overrideDecks[2].addCard(new Card("So", (byte) 11));
        overrideDecks[2].addCard(new Card("De", (byte) 1));

        //Begin the round.
        game.beginRound(new PrintWriter(output), overrideDecks);
        currentRound = new Melee[12];
    }

    @When("we begin the first round and distribute {int} cards to each player in Scenario {string}")
    public void we_begin_the_first_round_and_distribute_cards_to_each_player_in_scenario(Integer numCards,
                                                                                         String scenario) {
        game.setupGame(new Scanner(input.toString()), new PrintWriter(output));
        input = new StringBuilder();                                                //Reset the input StringBuilder

        if(scenario.equals("B") || scenario.equals("C") || scenario.equals("D")) {
            //Create the override Deck for P1
            overrideDecks[0].addCard(new Card("Ar", (byte) 1));
            overrideDecks[0].addCard(new Card("Me", null));
            overrideDecks[0].addCard(new Card("Sw", (byte) 5));
            overrideDecks[0].addCard(new Card("Sw", (byte) 12));
            overrideDecks[0].addCard(new Card("So", (byte) 1));
            overrideDecks[0].addCard(new Card("So", (byte) 7));
            overrideDecks[0].addCard(new Card("So", (byte) 10));
            overrideDecks[0].addCard(new Card("So", (byte) 15));
            overrideDecks[0].addCard(new Card("De", (byte) 2));
            overrideDecks[0].addCard(new Card("De", (byte) 4));
            overrideDecks[0].addCard(new Card("De", (byte) 13));
            overrideDecks[0].addCard(new Card("De", (byte) 14));

            //Create the override Deck for P2
            overrideDecks[1].addCard(new Card("Ar", (byte) 2));
            overrideDecks[1].addCard(new Card("Sw", (byte) 2));
            overrideDecks[1].addCard(new Card("Sw", (byte) 10));
            overrideDecks[1].addCard(new Card("Sw", (byte) 11));
            overrideDecks[1].addCard(new Card("So", (byte) 3));
            overrideDecks[1].addCard(new Card("So", (byte) 8));
            overrideDecks[1].addCard(new Card("So", (byte) 13));
            overrideDecks[1].addCard(new Card("Al", (byte) 15));
            overrideDecks[1].addCard(new Card("De", (byte) 1));
            overrideDecks[1].addCard(new Card("De", (byte) 8));
            overrideDecks[1].addCard(new Card("De", (byte) 11));
            overrideDecks[1].addCard(new Card("Al", (byte) 1));

            //Create the override Deck for P3
            overrideDecks[2].addCard(new Card("Sw", (byte) 1));
            overrideDecks[2].addCard(new Card("Sw", (byte) 3));
            overrideDecks[2].addCard(new Card("Sw", (byte) 4));
            overrideDecks[2].addCard(new Card("Sw", (byte) 13));
            overrideDecks[2].addCard(new Card("So", (byte) 2));
            overrideDecks[2].addCard(new Card("So", (byte) 4));
            overrideDecks[2].addCard(new Card("So", (byte) 9));
            overrideDecks[2].addCard(new Card("So", (byte) 14));
            overrideDecks[2].addCard(new Card("De", (byte) 3));
            overrideDecks[2].addCard(new Card("De", (byte) 5));
            overrideDecks[2].addCard(new Card("De", (byte) 12));
            overrideDecks[2].addCard(new Card("De", (byte) 15));
            //Modify some of the Cards if the scenario is C or D.
            if (scenario.equals("C")) overrideDecks[2].replaceCard(new Card("Ar", (byte) 3), 0);
            else if(scenario.equals("D")) {
                overrideDecks[1].replaceCard(new Card("Al", (byte) 14), overrideDecks[1].getNumCards() - 1);
                overrideDecks[0].replaceCard(new Card("Ap", null), 1);
            }
        } else if (scenario.equals("E")) {
            //Create the override Deck for P1.
            overrideDecks[0].addCard(new Card("Ar", (byte) 15));
            overrideDecks[0].addCard(new Card("Ar", (byte) 12));
            overrideDecks[0].addCard(new Card("Ap", null));
            overrideDecks[0].addCard(new Card("Ar", (byte) 9));
            overrideDecks[0].addCard(new Card("So", (byte) 1));
            overrideDecks[0].addCard(new Card("So", (byte) 2));
            overrideDecks[0].addCard(new Card("So", (byte) 3));
            overrideDecks[0].addCard(new Card("So", (byte) 4));
            overrideDecks[0].addCard(new Card("So", (byte) 5));
            overrideDecks[0].addCard(new Card("De", (byte) 1));
            overrideDecks[0].addCard(new Card("De", (byte) 2));
            overrideDecks[0].addCard(new Card("De", (byte) 3));

            //Create the override Deck for P2.
            overrideDecks[1].addCard(new Card("Ar", (byte) 14));
            overrideDecks[1].addCard(new Card("Me", null));
            overrideDecks[1].addCard(new Card("Me", null));
            overrideDecks[1].addCard(new Card("Ar", (byte) 8));
            overrideDecks[1].addCard(new Card("So", (byte) 6));
            overrideDecks[1].addCard(new Card("So", (byte) 7));
            overrideDecks[1].addCard(new Card("So", (byte) 8));
            overrideDecks[1].addCard(new Card("So", (byte) 9));
            overrideDecks[1].addCard(new Card("So", (byte) 10));
            overrideDecks[1].addCard(new Card("De", (byte) 4));
            overrideDecks[1].addCard(new Card("De", (byte) 5));
            overrideDecks[1].addCard(new Card("De", (byte) 6));

            //Create the override Deck for P3.
            overrideDecks[2].addCard(new Card("Sw", (byte) 1));
            overrideDecks[2].addCard(new Card("Sw", (byte) 2));
            overrideDecks[2].addCard(new Card("Sw", (byte) 3));
            overrideDecks[2].addCard(new Card("Sw", (byte) 4));
            overrideDecks[2].addCard(new Card("Sw", (byte) 5));
            overrideDecks[2].addCard(new Card("Sw", (byte) 6));
            overrideDecks[2].addCard(new Card("Sw", (byte) 7));
            overrideDecks[2].addCard(new Card("Sw", (byte) 8));
            overrideDecks[2].addCard(new Card("Sw", (byte) 9));
            overrideDecks[2].addCard(new Card("Sw", (byte) 10));
            overrideDecks[2].addCard(new Card("Sw", (byte) 11));
            overrideDecks[2].addCard(new Card("Sw", (byte) 12));

            //Create the override Deck for P4.
            overrideDecks[3].addCard(new Card("Ar", (byte) 13));
            overrideDecks[3].addCard(new Card("Ar", (byte) 11));
            overrideDecks[3].addCard(new Card("Ar", (byte) 10));
            overrideDecks[3].addCard(new Card("Ar", (byte) 7));
            overrideDecks[3].addCard(new Card("So", (byte) 11));
            overrideDecks[3].addCard(new Card("So", (byte) 12));
            overrideDecks[3].addCard(new Card("So", (byte) 13));
            overrideDecks[3].addCard(new Card("So", (byte) 14));
            overrideDecks[3].addCard(new Card("So", (byte) 15));
            overrideDecks[3].addCard(new Card("De", (byte) 7));
            overrideDecks[3].addCard(new Card("De", (byte) 8));
            overrideDecks[3].addCard(new Card("De", (byte) 9));
        }

        //Begin the round.
        game.beginRound(new PrintWriter(output), overrideDecks);
        currentRound = new Melee[12];
    }

    //Purpose: General step definition for creating a new Melee.
    @When("we begin Melee {int}")
    public void we_begin_melee(Integer currentMelee) {
        this.currentMelee = currentMelee-1;
        this.currentRound[this.currentMelee] = new Melee(game.getNumPlayers());
    }

    /*General Step Definition for playing a Merlin or Apprentice Card at the start of a Melee.*/
    @When("{string} plays {string} and gives it the suit {string} and assigns the value {int} to it")
    public void plays_and_gives_it_the_suit_and_assigns_the_value_to_it(String player, String type, String suit, Integer value) {
        input.append(String.format("1\n%s\n%d\n", suit, value));
    }

    /*General Step Definition for a Player playing a Merlin or Apprentice Card after the first Card has been played. This
    * is done indirectly by simply adding to the input String for the next call to game.playMelee()*/
    @When("{string} plays {string} and assigns {int} to it")
    public void play_me_or_ap(String name, String cardType, Integer value) {
        input.append(String.format("1\n%d\n", value));
    }

    /*General Step Definition for a Player playing a Merlin or Apprentice Card after the first Card has been played. This
     * is directly by calling the playCard() method of the current Melee.*/
    @When("{string} plays the card {string} and assigns the value {int} to it")
    public void plays_the_card_and_assigns_the_value_to_it(String playerName, String card, Integer value) {
        Card toPlay = new Card(card, null);
        Player current = getPlayer(playerName);
        int playerIndex = -1;
        for(int i=0;i<game.getNumPlayers();i++) {
            if(game.playerAt(i).equals(current)) {
                playerIndex = i;
                break;
            }
        }
        currentRound[currentMelee].playCard((byte)playerIndex, current, toPlay, new Scanner(String.format("%d\n", value)),
                new PrintWriter(output));
    }



    /*General Step Definition for a Player playing a Card that is not a Merlin or Apprentice. They do so indirectly by
    * providing input to the program.*/
    @When("{string} plays {string}")
    public void playCard(String name, String card) {
        input.append("1\n");
    }

    /*General Step Definition for a Player playing a Card that is not a Merlin or Apprentice. They do so directly by
    * calling the 'playCard' method of the current Melee.*/
    @When("{string} plays the card {string}")
    public void plays_the_card(String playerName, String card) {
        Card toPlay = parseCard(card);
        Player current = getPlayer(playerName);
        int playerIndex = -1;
        for(int i=0;i<game.getNumPlayers();i++) {
            if(game.playerAt(i).equals(current)) {
                playerIndex = i;
                break;
            }
        }
        currentRound[currentMelee].playCard((byte)playerIndex, current, toPlay, new Scanner(input.toString()),
                new PrintWriter(output));
    }

    /*General Step Definition for when a Player must discard a Card. They do so indirectly by providing input to the
    program.*/
    @When("{string} discards {string} and immediately suffers {int} injury points due to shaming")
    public void discards_and_immediately_suffers_injury_points_due_to_shaming(String string, String string2, Integer int1) {
        input.append("1\n");
        currentRound[0] = game.playMelee(new Scanner(input.toString()), new PrintWriter(output));
    }

    /*General Step Definition for when a Player must discard a Card. They do so directly by calling the 'discardCard'
    * method of the current Melee.*/
    @When("{string} discards the card {string} and immediately suffers {int} injury points due to shaming")
    public void discards_the_card_and_immediately_suffers_injury_points_due_to_shaming(String playerName, String card,
                                                                                       Integer injuryPoints) {
        Player player = getPlayer(playerName);
        Card toPlay = parseCard(card);
        int playerIndex = -1;
        for(int i=0;i<game.getNumPlayers();i++) {
            if(game.playerAt(i).equals(player)) {
                playerIndex = i;
                break;
            }
        }
        currentRound[currentMelee].discardCard(new Scanner("1\n"), new PrintWriter(output),
                (byte)playerIndex, player);
    }

    /*General Step Definition for the end of a Melee when there is a loser.*/
    @When("{string} is the loser and accumulates {int} injury points from Melee {int}")
    public void player_is_the_loser_and_accumulates_injury_points_from_melee(String name, Integer injuryPoints,
                                                                             Integer melee) {
        Melee current = game.playMelee(new Scanner(input.toString()), new PrintWriter(output));
        currentRound[melee-1] = current;
        assertEquals(name, game.playerAt(currentRound[melee-1].getLoser()).getName());
        input = new StringBuilder();                    //Reset the StringBuilder.
    }

    //Purpose: General step definition for when a Melee has no loser.
    @When("Melee {int} has no loser")
    public void melee_has_no_loser(Integer melee) {
        currentRound[melee-1] = game.playMelee(new Scanner(input.toString()),
                new PrintWriter(output));
        input = new StringBuilder();                    //Reset the StringBuilder.
    }

    /*Purpose: General step definition that determines the loser of a Melee by calling the Melee method computeLoser()
    directly.
     */
    @When("{string} is the loser")
    public void is_the_loser(String string) {
        currentRound[currentMelee].computeLoser(game.getPlayers(), new PrintWriter(output));
    }

    /*General Step Definition for the end of a round.*/
    @When("round {int} ends")
    public void round_ends(Integer integer) {
        roundSummaries.add(game.getRoundResult(new PrintWriter(output), currentRound));
    }

    /*General Step definition for checking that a Game ends after a given round.*/
    @Then("the Game ends after round {int}")
    public void the_game_ends_after_round(Integer roundNumber) {
        assertNull(roundSummaries.get(roundNumber-1));
    }

    /*General Step definition for checking that a Game ends during (i.e. before the end of) a round.*/
    @Then("the Game ends before the round ends")
    public void the_game_ends_before_the_round_ends() {
        boolean roundIncomplete = false;
        for(int i=0;i<12;i++) if(currentRound[i] == null) roundIncomplete = true;
        assertTrue(roundIncomplete);
        assertNull(currentRound[0]);
    }

    /*General step definition for checking the HP of a Player.*/
    @Then("{string} has {int} HP")
    public void has_hp(String name, Integer hp) {
        Player player = getPlayer(name);
        assertEquals(hp, player.getHP());
    }

    /*Purpose: Used to determine how many injury points  a Player has accumulated during a round.*/
    @Then("{string} has accumulated {int} injury points having lost the Melees {string}")
    public void has_accumulated_injury_points(String playerName, Integer injuryPoints, String melees) {
        Player player = getPlayer(playerName);

        //Determine the Melees the Player lost.
        List<Integer> meleesLost = parseMelees(melees);

        //Check that the correct losers were computed.
        if(!meleesLost.isEmpty()) {
            for(int melee:meleesLost)
                if(currentRound[melee] != null)
                    assertEquals(player, game.playerAt(currentRound[currentMelee].getLoser()));
        }

        assertEquals(injuryPoints, player.computeTotalInjury());
    }

    /*Purpose: General step definition for checking that a Game has a single winner.*/
    @Then("{string} is the winner")
    public void is_the_winner(String winner) {
        assertEquals(1, game.getWinners().size());
        assertTrue(game.getWinners().contains(getPlayer(winner)));
    }

    /*Purpose: General step definition for checking that a Game has 2 winners.*/
    @Then("{string} and {string} are the winners")
    public void and_are_the_winners(String p1Name, String p2Name) {
        assertEquals(2, game.getWinners().size());
        assertTrue(game.getWinners().contains(getPlayer(p1Name)));
        assertTrue(game.getWinners().contains(getPlayer(p2Name)));
    }

    /*Purpose: General step definition for checking that a Game has no winner.*/
    @Then("the Game has no winner")
    public void the_game_has_no_winner() {
        assertEquals(0, game.getWinners().size());
    }

    //HELPER FUNCTIONS

    /*Purpose: This function is responsible for finding the corresponding Player object with a given name.
    * Parameters: name-The name of the Player
    * Returns: The Player object in the Game's 'players' array with the given name, or null if no such Player exists
    */
    private Player getPlayer(String name) {
        Player p = null;
        for(int i=0;i<game.getNumPlayers();i++) {
            if(game.playerAt(i).getName().equals(name)) {
               p = game.playerAt(i);
               break;
            }
        }
        return p;
    }

    /*Purpose: This method is responsible for creating a Card object from a String that represents the Card.
    Parameters: card-String representing the Card object to be played
    Returns: the Card object corresponding to the Card specified in the input String.
     */
    private Card parseCard(String card) {
        String type = card.substring(0, 2);
        if(type.equals("Me") || type.equals("Ap")) return new Card(type, null);
        Byte value = Byte.parseByte(card.substring(2));
        return new Card(type, value);
    }

    /*Purpose: This method is responsible for creating an ArrayList of integers corresponding to the Melees a Player
    lost from a String indicating the Melees they lost.
    Parameters: -meleesLost: String of the form {x,y,...,z} where the numbers in the curly braces like x,y and z are
    the numbers of the melees that the player lost.
    Returns: An ArrayList of integers corresponding to the input String 'meleesLost'.
     */
    private ArrayList<Integer> parseMelees(String meleesLost) {
        String[] meleeStrings  = meleesLost.substring(1, meleesLost.length()-1).split(",");
        ArrayList<Integer> meleeNumbers = new ArrayList<>();
        for (String meleeString : meleeStrings)
            if(!meleeString.isEmpty()) meleeNumbers.add(Integer.parseInt(meleeString));
        return meleeNumbers;
    }

}
