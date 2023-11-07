import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class Part3StepDefinitions {

    private Game game;
    private Deck[] overrideDecks;                     //The override Decks for each Player to be used in a given round.

    private StringBuilder input;
    private StringWriter output;

    private ArrayList<Melee[]> roundSummaries;             //Contains the round results
    Melee[] currentRound;                                  //Contains the Melees from the current round.

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

    @Given("set initial health points at {int}")
    public void set_initial_health_points_at(Integer hp) {
        input.append(String.format("%d\n", hp));
    }

    //Round 1-----------------------------------------------------------------------------------------------------------
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

    @When("Melee {int} has no loser")
    public void melee_has_no_loser(Integer melee) {
        currentRound[melee-1] = game.playMelee(new Scanner(input.toString()),
                new PrintWriter(output));
        input = new StringBuilder();                    //Reset the StringBuilder.
    }

    /*General Step Definition for a Player playing a Merlin or Apprentice Card after the first Card has been played.*/
    @When("{string} plays {string} and assigns {int} to it")
    public void play_me_or_ap(String name, String cardType, Integer value) {
        input.append(String.format("1\n%d\n", value));
    }

    /*General Step Definition for a Player playing a Card that is not a Merlin or Apprentice.*/
    @When("{string} plays {string}")
    public void playCard(String name, String card) {
        input.append("1\n");
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

    /*General Step Definition for the end of a round*/
    @When("round {int} ends")
    public void round_ends(Integer int1) {
        roundSummaries.add(game.getRoundResult(new PrintWriter(output), currentRound));
    }

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

    @Then("the Game ends after round {int}")
    public void the_game_ends_after_round(Integer roundNumber) {
        assertNull(roundSummaries.get(roundNumber-1));
    }
    @Then("the Game has no winner")
    public void the_game_has_no_winner() {
        assertEquals(0, game.getWinners().size());
    }

}
