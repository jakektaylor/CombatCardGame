import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class Part1StepDefinitions {

    private Game game;
    private Deck[] overrideDecks;
    private StringBuilder input;
    private StringWriter output;
    private Melee melee;
    private static final int STARTING_HP = 300;

    @ParameterType("P1|P2|P3|P4|-")
    public Player Loser(String loser) {
        if(loser.equals("-")) return null;
        return game.playerAt(melee.getLoser());
    }

    @Given("we have started a game")
    public void we_have_started_a_game() {
        game = new Game();
        input = new StringBuilder();
        output = new StringWriter();

        game.setupGame(new Scanner(String.format("4\nP1\nP2\nP3\nP4\n%d\n", STARTING_HP)), new PrintWriter(output));

        //Initialize the override Decks.
        overrideDecks = new Deck[game.getNumPlayers()];
        for(int i=0;i<game.getNumPlayers();i++) overrideDecks[i] = new Deck();
    }

    @When("P1 plays the card {string}")
    public void p1_plays_the_card(String card) {
        Card c = parseCard(card);
        overrideDecks[0].addCard(c);
        input.append("1\n");
        if(c.getType().equals("Me") || c.getType().equals("Ap")) {
            input.append(playMeOrAp(card));
        }
    }
    @When("P2 plays the card {string}")
    public void p2_plays_the_card_sw7(String card) {
        Card c = parseCard(card);
        overrideDecks[1].addCard(c);
        input.append("1\n");
        if(c.getType().equals("Me") || c.getType().equals("Ap")) {
            input.append(playMeOrAp(card));
        }
    }
    @When("P3 plays the card {string}")
    public void p3_plays_the_card(String card) {
        Card c = parseCard(card);
        overrideDecks[2].addCard(c);
        input.append("1\n");
        if(c.getType().equals("Me") || c.getType().equals("Ap")) {
            input.append(playMeOrAp(card));
        }
    }
    @When("P4 plays the card {string}")
    public void p4_plays_the_card(String card) {
        Card c = parseCard(card);
        overrideDecks[3].addCard(c);
        input.append("1\n");
        if(c.getType().equals("Me") || c.getType().equals("Ap")) {
            input.append(playMeOrAp(card));
        }
    }

    @When("we execute the melee")
    public void we_execute_melee() {
        Melee[] summary = game.playRound(new Scanner(input.toString()), new PrintWriter(output),
                overrideDecks, 1);
        melee = summary[0];
    }

    @Then("the loser is {Loser}")
    public void the_loser_is(Player loser) {
        if(loser == null) assertNull(melee.getLoser());
        else assertEquals(loser, game.playerAt(melee.getLoser()));
    }
    @Then("{Loser} accumulates {int}.")
    public void accumulates(Player loser, Integer injuryPoints) {
        if(loser == null) assertEquals(0, injuryPoints);
        else assertEquals(STARTING_HP-injuryPoints, loser.getHP());
    }

    private Card parseCard(String card) {
        String type = card.substring(0, 2);
        if(type.equals("Me") || type.equals("Ap")) return new Card(type, null);
        Byte value = Byte.parseByte(card.substring(2));
        return new Card(type, value);
    }

    /*Purpose: The purpose of this method is to encode the information required to play a Merlin or Apprentice card as
    * a String.
    */
    private String playMeOrAp(String card) {
        String suit;
        Byte value;
        try {
            //Assumes the Merlin or Apprentice is being played after the first Card (no suit necessary)
            value = Byte.parseByte(card.substring(2));
            return String.format("%d\n", value);
        } catch (NumberFormatException e) {
            suit = card.substring(2, 4);
            value = Byte.parseByte(card.substring(4));
            return String.format("%s\n%d\n", suit, value);
        }
    }

}
