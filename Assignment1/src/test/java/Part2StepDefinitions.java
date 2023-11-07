import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class Part2StepDefinitions {
    private Game game;
    private Deck[] overrideDecks;        //The Decks we will use to determine what Card each Player plays at each step.
    private StringBuilder input;
    private StringWriter output;
    private Melee[] melees;

    //Game setup--------------------------------------------------------------------------------------------------------
    @Given("we start a game")
    public void we_start_a_game() {
        game = new Game();
        melees = new Melee[4];
        input = new StringBuilder();
        output = new StringWriter();
    }
    @Given("enter an invalid number of players")
    public void enter_an_invalid_number_of_players() {
        input.append("-1\n");
    }
    @Given("enter {int} as the number of players")
    public void enter_as_the_number_of_players(Integer numPlayers) {
        input.append(String.format("%d\n", numPlayers));
        overrideDecks = new Deck[numPlayers];
        for(int i=0;i<numPlayers;i++) overrideDecks[i] = new Deck();
    }
    @Given("enter blank as name for the first player")
    public void enter_blank_as_name_for_the_first_player() {
        input.append("\n");
    }
    @Given("enter {string} as name for the first player")
    public void enter_as_name_for_the_first_player(String name) {
        input.append(String.format("%s\n", name));
    }
    @Given("enter blank as name for the second player")
    public void enter_blank_as_name_for_the_second_player() {
        input.append("\n");
    }
    @Given("enter {string} as name for the second player")
    public void enter_as_name_for_the_second_player(String name) {
        input.append(String.format("%s\n", name));
    }
    @Given("enter blank as name for the third player")
    public void enter_blank_as_name_for_the_third_player() {
        input.append("\n");
    }
    @Given("enter {string} as name for the third player")
    public void enter_as_name_for_the_third_player(String name) {
        input.append(String.format("%s\n", name));
    }
    @Given("Try to input initial health points as {int}")
    public void try_to_input_initial_health_points_as(Integer hp) {
        input.append(String.format("%d\n", hp));
    }
    @Given("set initial health points of players at {int}")
    public void set_initial_health_points_of_players_at(Integer hp) {

        input.append(String.format("%d\n", hp));
        game.setupGame(new Scanner(input.toString()), new PrintWriter(output));
        input = new StringBuilder();
    }

    @Given("We begin round 1 and distribute 12 cards to each player")
    public void we_begin_the_round() {
        //Create the Deck for Fred.
        overrideDecks[0].addCard(new Card("So", (byte) 11));
        overrideDecks[0].addCard(new Card("Ap", null));
        overrideDecks[0].addCard(new Card("Sw", (byte) 3));
        overrideDecks[0].addCard(new Card("De", (byte) 9));
        overrideDecks[0].addCard(new Card("Al", (byte) 1));
        overrideDecks[0].addCard(new Card("Me", null));
        overrideDecks[0].addCard(new Card("Ar", (byte) 1));
        overrideDecks[0].addCard(new Card("Ar", (byte) 3));
        overrideDecks[0].addCard(new Card("Sw", (byte) 6));
        overrideDecks[0].addCard(new Card("So", (byte) 13));
        overrideDecks[0].addCard(new Card("So", (byte) 15));
        overrideDecks[0].addCard(new Card("De", (byte) 2));

        //Create the Deck for Joe.
        overrideDecks[1].addCard(new Card("So", (byte) 6));
        overrideDecks[1].addCard(new Card("Ar", (byte) 8));
        overrideDecks[1].addCard(new Card("Sw", (byte) 9));
        overrideDecks[1].addCard(new Card("De", (byte) 6));
        overrideDecks[1].addCard(new Card("Al", (byte) 2));
        overrideDecks[1].addCard(new Card("Al", (byte) 3));
        overrideDecks[1].addCard(new Card("Me", null));
        overrideDecks[1].addCard(new Card("Ar", (byte) 7));
        overrideDecks[1].addCard(new Card("Sw", (byte) 14));
        overrideDecks[1].addCard(new Card("So", (byte) 8));
        overrideDecks[1].addCard(new Card("So", (byte) 2));
        overrideDecks[1].addCard(new Card("De", (byte) 3));

        //Create the Deck for Paul.
        overrideDecks[2].addCard(new Card("So", (byte) 7));
        overrideDecks[2].addCard(new Card("Me", null));
        overrideDecks[2].addCard(new Card("Sw", (byte) 7));
        overrideDecks[2].addCard(new Card("De", (byte) 1));
        overrideDecks[2].addCard(new Card("Al", (byte) 4));
        overrideDecks[2].addCard(new Card("Ap", null));
        overrideDecks[2].addCard(new Card("Ar", (byte) 5));
        overrideDecks[2].addCard(new Card("Ar", (byte) 10));
        overrideDecks[2].addCard(new Card("So", (byte) 3));
        overrideDecks[2].addCard(new Card("So", (byte) 4));
        overrideDecks[2].addCard(new Card("De", (byte) 10));
        overrideDecks[2].addCard(new Card("De", (byte) 11));

        output = new StringWriter();
        game.beginRound(new PrintWriter(output), overrideDecks);
    }

    //Melee 1-----------------------------------------------------------------------------------------------------------
    @When("Fred tries to play an Alchemy when he as basic weapons and Me or Ap")
    public void fred_tries_to_play_an_alchemy_when_he_as_basic_weapons_and_me_or_ap() {
        input.append("5\n");
    }

    @When("Fred plays So11")
    public void fred_plays_so11() {
        input.append("1\n");
    }
    @When("Joe tries to play a Sword card when he has a Sorcery card")
    public void joe_tries_to_play_a_sword_card_when_he_has_a_sorcery_card() {
        input.append("3\n");
    }
    @When("Joe tries to play a Deception card when he has a Sorcery card")
    public void joe_tries_to_play_a_deception_card_when_he_has_a_sorcery_card() {
        input.append("4\n");
    }
    @When("Joe tries to play an Arrow card when he has a Sorcery card")
    public void joe_tries_to_play_an_arrow_card_when_he_has_a_sorcery_card() {
        input.append("2\n");
    }
    @When("Joe tries to play an Alchemy card when he has a Sorcery card")
    public void joe_tries_to_play_an_alchemy_card_when_he_has_a_sorcery_card() {
        input.append("5\n");
    }
    @When("Joe plays So6")
    public void joe_plays_so6() {
        input.append("1\n");
    }
    @When("Paul plays So7")
    public void paul_plays_so7() {
        input.append("1\n");
    }

    @When("Joe is the loser with 25 points injury points for this Melee")
    public void joe_is_the_loser_with_25_injury_points_for_this_melee() {
        melees[0] = game.playMelee(new Scanner(input.toString()), new PrintWriter(output));
        input = new StringBuilder();                    //Reset the input for the next Melee
    }

    //Melee 2-----------------------------------------------------------------------------------------------------------
    @When("Joe starts with Ar8")
    public void joe_starts_with_ar8() {
        input.append("1\n");
    }

    @When("Paul plays Me and tries to assign 16 to it")
    public void paul_plays_me_and_tries_to_assign_to_it() {
        input.append("1\n16\n");
    }
    @When("Paul assigns 9 to Me")
    public void paul_assigns_to_me() {
        input.append("9\n");
    }
    @When("Fred plays Ap and tries to assign 20 to it")
    public void fred_plays_ap_and_tries_to_assign_to_it() {
        input.append("1\n20\n");
    }
    @When("Fred assigns 10 to Ap")
    public void fred_assigns_to_ap() {
        input.append("10\n");
    }
    @When("Joe is the loser with 40 injury points for this Melee")
    public void joe_is_the_loser_with_injury_points_for_this_melee() {
        melees[1] = game.playMelee(new Scanner(input.toString()), new PrintWriter(output));
        input = new StringBuilder();                    //Reset the input for the next Melee
    }

    //Melee 3-----------------------------------------------------------------------------------------------------------
    @When("Joe starts with Sw9")
    public void joe_starts_with_sw9() {
        input.append("1\n");
    }
    @When("Paul tries to play an Alchemy when he has Sw7")
    public void paul_tries_to_play_an_alchemy_when_he_has_sw7() {
        input.append("3\n");
    }
    @When("Paul plays Sw7")
    public void paul_plays_sw7() {
        input.append("1\n");
    }
    @When("Fred plays Sw3")
    public void fred_plays_sw3() {
        input.append("1\n");
    }
    @When("Fred is the loser with 25 injury points for this Melee")
    public void fred_is_the_loser_with_injury_points_for_this_melee() {
        melees[2] = game.playMelee(new Scanner(input.toString()), new PrintWriter(output));
        input = new StringBuilder();                    //Reset the input for the next Melee
    }

    //Melee 4-----------------------------------------------------------------------------------------------------------
    @When("Fred plays De9")
    public void fred_plays_de9() {
        input.append("1\n");
    }
    @When("Joe tries to play an Alchemy card when he has a Deception card")
    public void joe_tries_to_play_an_alchemy_card_when_he_has_a_deception_card() {
        input.append("2\n");
    }
    @When("Joe plays De6")
    public void joe_plays_de6() {
        input.append("1\n");
    }
    @When("Paul plays De1")
    public void paul_plays_de1() {
        input.append("1\n");
    }
    @When("Paul is the loser with 25 points of injury for this melee")
    public void paul_is_the_loser_with_points_of_injury_for_this_melee() {
        melees[3] = game.playMelee(new Scanner(input.toString()), new PrintWriter(output));
    }
    @Then("Fred has accumulated 25 injury points this round")
    public void fred_has_accumulated_injury_points_this_round() {
        assertEquals(0, melees[2].getLoser());
        assertEquals(25, game.playerAt(0).getInjuryDeck().getInjuryPoints());
    }
    @Then("Joe has accumulated 65 injury points this round")
    public void joe_has_accumulated_injury_points_this_round() {
        assertEquals(1, melees[0].getLoser());
        assertEquals(1, melees[1].getLoser());
        assertEquals(65, game.playerAt(1).getInjuryDeck().getInjuryPoints());
    }
    @Then("Paul has accumulated 25 injury points this round")
    public void paul_has_accumulated_injury_points_this_round() {
        assertEquals(2, melees[3].getLoser());
        assertEquals(25, game.playerAt(2).getInjuryDeck().getInjuryPoints());
    }

}
