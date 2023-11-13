import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class Part2StepDefinitions {

    //GAME CONTEXT VARIABLES
    private Game game;
    private Deck[] overrideDecks;        //The Decks we will use to determine what Card each Player plays in each Melee.
    private StringBuilder input;         //Used to provide input to a Melee when passed to a Scanner constructor.
    private Melee[] melees;              //Array to hold the Melees played.

    //STEP DEFINITIONS

    //Game setup--------------------------------------------------------------------------------------------------------

    /*Purpose: Step definition responsible for beginning a new Game and instantiating context variables.*/
    @Given("we start a game")
    public void we_start_a_game() {
        game = new Game();
        melees = new Melee[4];
        input = new StringBuilder();
    }

    //Purpose: Step definition responsible for attempting to set the number of Players to an invalid number (-1).
    @Given("enter an invalid number of players")
    public void enter_an_invalid_number_of_players() {
        input.append("-1\n");
    }

    //Purpose: Step definition responsible for setting the number of Players to be 3.
    @Given("enter 3 as the number of players")
    public void enter_as_the_number_of_players() {
        input.append(String.format("%d\n", 3));
        overrideDecks = new Deck[3];
        for(int i=0;i<3;i++) overrideDecks[i] = new Deck();
    }

    //Purpose: Step definition for entering blank as the name for the first Player.
    @Given("enter blank as name for the first player")
    public void enter_blank_as_name_for_the_first_player() {
        input.append("\n");
    }

    /*  Purpose: Step definition for entering a valid name for the first Player.
        Parameters: -name: The name of the first Player
    */
    @Given("enter {string} as name for the first player")
    public void enter_as_name_for_the_first_player(String name) {
        input.append(String.format("%s\n", name));
    }

    //Purpose: Step definition for entering blank as the name for the second Player.
    @Given("enter blank as name for the second player")
    public void enter_blank_as_name_for_the_second_player() {
        input.append("\n");
    }

    /*  Purpose: Step definition for entering a valid name for the second Player.
        Parameters: -name: The name of the second Player
    */
    @Given("enter {string} as name for the second player")
    public void enter_as_name_for_the_second_player(String name) {
        input.append(String.format("%s\n", name));
    }

    //Purpose: Step definition for entering blank for the name of the third Player.
    @Given("enter blank as name for the third player")
    public void enter_blank_as_name_for_the_third_player() {
        input.append("\n");
    }

    /*  Purpose: Step definition for entering a valid name for the third Player.
        Parameters: -name: The name of the third Player
    */
    @Given("enter {string} as name for the third player")
    public void enter_as_name_for_the_third_player(String name) {
        input.append(String.format("%s\n", name));
    }

    //Purpose: Step definition for trying to set the initial number of health points to be -10.
    @Given("Try to input initial health points as -10")
    public void try_to_input_initial_health_points_asInteger() {
        input.append("-10\n");
    }

    //Purpose: Step definition for setting the initial number of health points to be 50.
    @Given("set initial health points of players at 50")
    public void set_initial_health_points_of_players_at() {

        input.append("50\n");
        game.setupGame(new Scanner(input.toString()), new PrintWriter(new StringWriter()));
        input = new StringBuilder();
    }

    //Round 1-----------------------------------------------------------------------------------------------------------

    //Purpose: Step definition responsible for creating the override Decks for each Player and beginning the round.
    @When("We begin round 1 and distribute 12 cards to each player")
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

        game.beginRound(new PrintWriter(new StringWriter()), overrideDecks);
    }

    //Melee 1-----------------------------------------------------------------------------------------------------------

    //Purpose: Step definition responsible for Fred trying to play an invalid Alchemy Card.
    @When("Fred tries to play an Alchemy when he has basic weapons and Me or Ap")
    public void fred_tries_to_play_an_alchemy_when_he_as_basic_weapons_and_me_or_ap() {
        input.append("5\n");
    }

    //Purpose: Step definition responsible for Fred playing So11.
    @When("Fred plays So11")
    public void fred_plays_so11() {
        input.append("1\n");
    }

    //Purpose: Step definition for Joe trying to play a Sword Card when he has a Sorcery Card.
    @When("Joe tries to play a Sword card when he has a Sorcery card")
    public void joe_tries_to_play_a_sword_card_when_he_has_a_sorcery_card() {
        input.append("3\n");
    }

    //Purpose: Step definition for Joe trying to play a Deception Card when he has a Sorcery Card.
    @When("Joe tries to play a Deception card when he has a Sorcery card")
    public void joe_tries_to_play_a_deception_card_when_he_has_a_sorcery_card() {
        input.append("4\n");
    }

    //Purpose: Step definition for Joe trying to play an Arrow Card when he has a Sorcery Card.
    @When("Joe tries to play an Arrow card when he has a Sorcery card")
    public void joe_tries_to_play_an_arrow_card_when_he_has_a_sorcery_card() {
        input.append("2\n");
    }

    //Purpose: Step definition for Joe trying to play an Alchemy Card when he has a Sorcery Card.
    @When("Joe tries to play an Alchemy card when he has a Sorcery card")
    public void joe_tries_to_play_an_alchemy_card_when_he_has_a_sorcery_card() {
        input.append("5\n");
    }

    //Purpose: Step definition for Joe playing So6.
    @When("Joe plays So6")
    public void joe_plays_so6() {
        input.append("1\n");
    }

    //Purpose: Step definition for Paul playing So7.
    @When("Paul plays So7")
    public void paul_plays_so7() {
        input.append("1\n");
    }

    /*  Purpose: Step definition for computing the loser for the first Melee and adding the Cards played in the Melee to
    the loser's injury Deck. In this case, the loser is Joe.*/
    @When("Joe is the loser with 25 injury points for this Melee")
    public void joe_is_the_loser_with_25_injury_points_for_this_melee() {
        melees[0] = game.playMelee(new Scanner(input.toString()), new PrintWriter(new StringWriter()));
        input = new StringBuilder();                    //Reset the input for the next Melee
    }

    //Melee 2-----------------------------------------------------------------------------------------------------------

    //Purpose: Step definition for Joe starting the second Melee of the round with the Card Ar8.
    @When("Joe starts with Ar8")
    public void joe_starts_with_ar8() {
        input.append("1\n");
    }

    //Purpose: Step definition for Paul playing Me and trying to assign 16 to it.
    @When("Paul plays Me and tries to assign 16 to it")
    public void paul_plays_me_and_tries_to_assign_to_it() {
        input.append("1\n16\n");
    }

    //Purpose: Step definition for Paul assigning the played Me Card the value of 9.
    @When("Paul assigns 9 to Me")
    public void paul_assigns_to_me() {
        input.append("9\n");
    }

    //Purpose: Step definition for Fred playing Ap and trying to assign the value 20 to it.
    @When("Fred plays Ap and tries to assign 20 to it")
    public void fred_plays_ap_and_tries_to_assign_to_it() {
        input.append("1\n20\n");
    }

    //Purpose: Step definition for Fred assigning the played Ap Card the value of 10.
    @When("Fred assigns 10 to Ap")
    public void fred_assigns_to_ap() {
        input.append("10\n");
    }

    /*  Purpose: Step definition for computing the loser for the second Melee and adding the Cards played in the Melee
        to the loser's injury Deck. In this case, the loser is Joe.
    */
    @When("Joe is the loser with 40 injury points for this Melee")
    public void joe_is_the_loser_with_injury_points_for_this_melee() {
        melees[1] = game.playMelee(new Scanner(input.toString()), new PrintWriter(new StringWriter()));
        input = new StringBuilder();                    //Reset the input for the next Melee
    }

    //Melee 3-----------------------------------------------------------------------------------------------------------

    //Purpose: Step definition for Joe starting the third Melee with the Card Sw9.
    @When("Joe starts with Sw9")
    public void joe_starts_with_sw9() {
        input.append("1\n");
    }

    //Purpose: Step definition for Paul trying to play an Alchemy Card when he has Sw7.
    @When("Paul tries to play an Alchemy when he has Sw7")
    public void paul_tries_to_play_an_alchemy_when_he_has_sw7() {
        input.append("3\n");
    }

    //Purpose: Step definition for Paul playing Sw7.
    @When("Paul plays Sw7")
    public void paul_plays_sw7() {
        input.append("1\n");
    }

    //Purpose: Step definition for Fred playing Sw3.
    @When("Fred plays Sw3")
    public void fred_plays_sw3() {
        input.append("1\n");
    }

    /*  Purpose: Step definition for computing the loser for the third Melee and adding the Cards played in the Melee
        to the loser's injury Deck. In this case, the loser is Fred.
    */
    @When("Fred is the loser with 25 injury points for this Melee")
    public void fred_is_the_loser_with_injury_points_for_this_melee() {
        melees[2] = game.playMelee(new Scanner(input.toString()), new PrintWriter(new StringWriter()));
        input = new StringBuilder();                    //Reset the input for the next Melee
    }

    //Melee 4-----------------------------------------------------------------------------------------------------------

    //Purpose: Step definition for Fred starting the fourth Melee by playing the Card De9.
    @When("Fred plays De9")
    public void fred_plays_de9() {
        input.append("1\n");
    }

    //Purpose: Step definition for Joe trying to play an Alchemy Card when he has a Deception Card.
    @When("Joe tries to play an Alchemy card when he has a Deception card")
    public void joe_tries_to_play_an_alchemy_card_when_he_has_a_deception_card() {
        input.append("2\n");
    }

    //Purpose: Step definition for Joe playing the Card De6.
    @When("Joe plays De6")
    public void joe_plays_de6() {
        input.append("1\n");
    }

    //Purpose: Step definition for Paul playing the Card De1.
    @When("Paul plays De1")
    public void paul_plays_de1() {
        input.append("1\n");
    }

    /*  Purpose: Step definition for computing the loser for the fourth Melee and adding the Cards played in the Melee
        to the loser's injury Deck. In this case, the loser is Paul.
    */
    @When("Paul is the loser with 25 points of injury for this melee")
    public void paul_is_the_loser_with_points_of_injury_for_this_melee() {
        melees[3] = game.playMelee(new Scanner(input.toString()), new PrintWriter(new StringWriter()));
    }

    //END OF THE MELEES-------------------------------------------------------------------------------------------------

    //Purpose: Step definition that asserts that Fred lost the third Melee and accumulated 25 injury points from it.
    @Then("Fred has accumulated 25 injury points")
    public void fred_has_accumulated_injury_points_this_round() {
        /*Note: 0 represents Fred as he is the Player at index 0 in the Game's 'players' array. The getLoser() method of
        a Melee returns the index of the loser in the Game's 'players' array or 'null' if there is no loser.*/

        assertEquals(0, melees[2].getLoser());
        assertEquals(25, game.playerAt(0).getInjuryDeck().getInjuryPoints());
    }

    /*Purpose: Step definition that asserts that Joe lost Melees 1 and 2 and accumulated 65 injury points total from
    them.*/
    @Then("Joe has accumulated 65 injury points")
    public void joe_has_accumulated_injury_points_this_round() {
        /*Note: 1 represents Joe as he is the Player at index 1 in the Game's 'players' array. The getLoser() method of
        a Melee returns the index of the loser in the Game's 'players' array or 'null' if there is no loser.*/
        assertEquals(1, melees[0].getLoser());
        assertEquals(1, melees[1].getLoser());
        assertEquals(65, game.playerAt(1).getInjuryDeck().getInjuryPoints());
    }

    /*Purpose: Step definition that asserts that Paul lost Melee 4 and accumulated 25 injury points from it.*/
    @Then("Paul has accumulated 25 injury points")
    public void paul_has_accumulated_injury_points_this_round() {
        /*Note: 2 represents Paul as he is the Player at index 2 in the Game's 'players' array. The getLoser() method of
        a Melee returns the index of the loser in the Game's 'players' array or 'null' if there is no loser.*/
        assertEquals(2, melees[3].getLoser());
        assertEquals(25, game.playerAt(2).getInjuryDeck().getInjuryPoints());
    }

}
