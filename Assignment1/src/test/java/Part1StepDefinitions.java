import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class Part1StepDefinitions {

    //GAME CONTEXT VARIABLES
    private Game game;                                      //The Game object to be used.
    private Deck[] overrideDecks;                           //Decks to be used to run the Melee.
    private StringBuilder input;                            //Contains the input required to execute the scenario
    private StringWriter output;                            //Written to for testing
    private Melee melee;                                    //The current Melee
    private static final int STARTING_HP = 300;             //The starting HP of each Player to be used in the example.

    //STEP DEFINITIONS AND PARAMETER TYPE DEFINITIONS

    /*  Purpose: This method defines a custom Parameter type "Loser", which is used in the below step definitions as
    {Loser}.
    * Parameters: -loser: String consisting of the name of the 'loser'
    * Returns: The Player object of the loser or 'null' whenever the loser is "-"
    */
    @ParameterType("P1|P2|P3|P4|-")
    public Player Loser(String loser) {
        if(loser.equals("-")) return null;
        return game.playerAt(melee.getLoser());
    }

    //  Purpose: Step definition for beginning a new Game and instantiating context variables.
    @Given("we have started a game and are in the final Melee of a round")
    public void we_have_started_a_game() {
        game = new Game();
        input = new StringBuilder();
        output = new StringWriter();

        game.setupGame(new Scanner(String.format("4\nP1\nP2\nP3\nP4\n%d\n", STARTING_HP)), new PrintWriter(output));

        //Initialize the override Decks.
        overrideDecks = new Deck[game.getNumPlayers()];
        for(int i=0;i<game.getNumPlayers();i++) overrideDecks[i] = new Deck();
    }

    /*  Purpose: Step definition responsible for handling the Player P1 playing a Card.
        Parameters: -card: String from the column "P1" in the Example Table representing the Card to be played,
                    such as Ar11
     */
    @When("P1 plays the card {string}")
    public void p1_plays_the_card(String card) {
        Card c = parseCard(card);
        overrideDecks[0].addCard(c);
        input.append("1\n");
        if(c.getType().equals("Me") || c.getType().equals("Ap")) {
            input.append(playMeOrAp(card));
        }
    }

    /*  Purpose: Step definition responsible for handling the Player P2 playing a Card.
        Parameters: -card: String from the column "P2" in the Example Table representing the Card to be played,
                    such as Ar11
     */
    @When("P2 plays the card {string}")
    public void p2_plays_the_card_sw7(String card) {
        Card c = parseCard(card);
        overrideDecks[1].addCard(c);
        input.append("1\n");
        if(c.getType().equals("Me") || c.getType().equals("Ap")) {
            input.append(playMeOrAp(card));
        }
    }

    /*  Purpose: Step definition responsible for handling the Player P3 playing a Card.
        Parameters: -card: String from the column "P3" in the Example Table representing the Card to be played,
                    such as Ar11
     */
    @When("P3 plays the card {string}")
    public void p3_plays_the_card(String card) {
        Card c = parseCard(card);
        overrideDecks[2].addCard(c);
        input.append("1\n");
        if(c.getType().equals("Me") || c.getType().equals("Ap")) {
            input.append(playMeOrAp(card));
        }
    }

    /*  Purpose: Step definition responsible for handling the Player P4 playing a Card.
        Parameters: -card: String from the column "P4" in the Example Table representing the Card to be played,
                    such as Ar11
     */
    @When("P4 plays the card {string}")
    public void p4_plays_the_card(String card) {
        Card c = parseCard(card);
        overrideDecks[3].addCard(c);
        input.append("1\n");
        if(c.getType().equals("Me") || c.getType().equals("Ap")) {
            input.append(playMeOrAp(card));
        }

        //Execute the Melee.
        Melee[] summary = game.playRound(new Scanner(input.toString()), new PrintWriter(output),
                overrideDecks, 1);
        melee = summary[0];
    }

    /*  Purpose: Step definition responsible for asserting that the correct loser of the Melee was computed.
    *   Parameters: -loser: Player object corresponding to the loser of the Melee or 'null' if there was no loser.
    * */
    @Then("the loser is {Loser}")
    public void the_loser_is(Player loser) {
        if(loser == null) assertNull(melee.getLoser());
        else assertEquals(loser, game.playerAt(melee.getLoser()));
    }

    /*  Purpose: Step definition responsible for asserting that the loser accumulated the correct number of injury
    *   points from the Melee.
    *   Parameters: -loser: Player object corresponding to the loser of the Melee or 'null' if there was no loser.
                    -injuryPoints: The expected number of injury points accumulated by the loser from the Melee.
    *   NOTE: Since playRound was set to play a single Melee, the injury points have been applied to the Players' HP.
    */
    @Then("{Loser} accumulates {int}.")
    public void accumulates(Player loser, Integer injuryPoints) {
        if(loser == null) {
            //Check that no Player accumulated any injury points.
            assertAll(()->assertEquals(STARTING_HP, game.playerAt(0).getHP()),
                      ()->assertEquals(STARTING_HP, game.playerAt(1).getHP()),
                      ()->assertEquals(STARTING_HP, game.playerAt(2).getHP()),
                      ()->assertEquals(STARTING_HP, game.playerAt(3).getHP()));
        }
        else assertEquals(STARTING_HP-injuryPoints, loser.getHP());
    }

    /*Purpose: This method is responsible for creating a new Card object that corresponds to a String from the Example
    Table that represents the Card such as "Ar9".
    Parameters: -card: A String that represents a Card
    Returns: A new Card object that corresponds to the input String
     */
    private Card parseCard(String card) {
        String type = card.substring(0, 2);
        if(type.equals("Me") || type.equals("Ap")) return new Card(type, null);
        Byte value = Byte.parseByte(card.substring(2));
        return new Card(type, value);
    }

    /*  Purpose: The purpose of this method is to encode the information required to play a Merlin or Apprentice card as
    *   a String.
    *   Parameters: card-String representing the Merlin or Apprentice card to be played, such as Me11 or MeSw13
    *   Returns: a String with the input required to play the Merlin or Apprentice Card
    */
    private String playMeOrAp(String card) {
        String suit;
        Byte value;
        try {
            //Assumes the Merlin or Apprentice Card is being played after the first Card (no suit necessary)
            value = Byte.parseByte(card.substring(2));
            return String.format("%d\n", value);
        } catch (NumberFormatException e) {
            //In this case the Merlin or Apprentice Card is being played as the first Card in the Melee
            suit = card.substring(2, 4);
            value = Byte.parseByte(card.substring(4));
            return String.format("%s\n%d\n", suit, value);
        }
    }

}
