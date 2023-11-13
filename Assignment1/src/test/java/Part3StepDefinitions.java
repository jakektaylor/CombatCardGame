import io.cucumber.java.bs.A;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


import javax.xml.transform.sax.SAXResult;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class Part3StepDefinitions {

    //GAME CONTEXT VARIABLES
    private Game game;
    private Deck[] overrideDecks;             //The override Decks for each Player to be used in a given round.

    private StringBuilder input;              //Used to provide input to the Game when passed to a Scanner constructor

    private ArrayList<Melee[]> roundSummaries;             //Contains the round results
    Melee[] currentRound;                                  //Contains the Melees from the current round.
    int currentMelee;                                      //The current Melee we are playing.

    //Game setup--------------------------------------------------------------------------------------------------------

    /*  Purpose: Step definition for beginning a new Game and instantiating some of the context variables.*/
    @Given("we begin a game")
    public void we_begin_a_game() {
        game = new Game();
        input = new StringBuilder();
        roundSummaries = new ArrayList<>();
    }

    /*  Purpose: Step definition for handling the entry of a valid number of Players. It also instantiates the
    *   overrideDecks array and the Deck objects within it.
    *   Parameters: -numPlayers: Integer specifying the number of Players in the Game.*/
    @Given("enter {int} for the number of players")
    public void enter_for_the_number_of_players(Integer numPlayers) {
        input.append(String.format("%d\n", numPlayers));
        overrideDecks = new Deck[numPlayers];
        for(int i=0;i<overrideDecks.length;i++) overrideDecks[i] = new Deck();
    }

    /*  Purpose: Step definition for entering the name of the first Player.
    *   Parameters: -p1: The name of the first Player (String)*/
    @Given("enter {string} for the name of the first player")
    public void enter_for_the_name_of_the_first_player(String p1) {
        input.append(String.format("%s\n", p1));
    }

    /*  Purpose: Step definition for entering the name of the second Player.
    *   Parameters: -p2: The name of the second Player (String)*/
    @Given("enter {string} for the name of the second player")
    public void enter_for_the_name_of_the_second_player(String p2) {
        input.append(String.format("%s\n", p2));
    }

    /*  Purpose: Step definition for entering the name of the third Player.
    *   Parameters: -p3: The name of the third Player (String)*/
    @Given("enter {string} for the name of the third player")
    public void enter_for_the_name_of_the_third_player(String p3) {
        input.append(String.format("%s\n", p3));
    }

    /*  Purpose: Step definition for entering the name of the fourth Player.
    *   Parameters: -p4: The name of the fourth Player (String)*/
    @Given("enter {string} for the name of the fourth player")
    public void enter_for_the_name_of_the_fourth_player(String p4) {input.append(String.format("%s\n", p4));}

    /*  Purpose: Step definition for entering the number of health points of each Player.
    *   Parameters: -hp: Integer representing the starting health points of each Player*/
    @Given("set initial health points at {int}")
    public void set_initial_health_points_at(Integer hp) {
        input.append(String.format("%d\n", hp));
    }

    //Beginning Rounds--------------------------------------------------------------------------------------------------

    /*  Purpose: Step definition responsible for setting up the Game, creating the override Decks of each Player for
    *   the first round, and beginning the round for Scenarios A-E.
    *   Parameters: -scenario: One of {"A", "B", "C", "D", "E"} representing the scenario to be tested*/
    @When("we begin the first round and distribute 12 cards to each player in Scenario {string}")
    public void we_begin_the_first_round_and_distribute_cards_to_each_player_in_scenario(String scenario) {
        game.setupGame(new Scanner(input.toString()), new PrintWriter(new StringWriter()));
        input = new StringBuilder();                                                //Reset the input StringBuilder

        if(scenario.equals("A")) {
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
        }

        else if(scenario.equals("B") || scenario.equals("C") || scenario.equals("D")) {
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
                overrideDecks[0].replaceCard(new Card("Ap", null), 1);
                overrideDecks[1].replaceCard(new Card("Al", (byte) 14), overrideDecks[1].getNumCards() - 1);
                overrideDecks[2].replaceCard(new Card("Ar", (byte) 3), 0);
            }
        } else if (scenario.equals("E")) {
            //Create the override Deck for P1.
            overrideDecks[0].addCard(new Card("Ar", (byte) 15));
            overrideDecks[0].addCard(new Card("Ar", (byte) 13));
            overrideDecks[0].addCard(new Card("Ap", null));
            overrideDecks[0].addCard(new Card("De", (byte) 1));
            overrideDecks[0].addCard(new Card("Ar", (byte) 9));
            overrideDecks[0].addCard(new Card("So", (byte) 1));
            overrideDecks[0].addCard(new Card("So", (byte) 2));
            overrideDecks[0].addCard(new Card("So", (byte) 3));
            overrideDecks[0].addCard(new Card("So", (byte) 4));
            overrideDecks[0].addCard(new Card("So", (byte) 5));
            overrideDecks[0].addCard(new Card("De", (byte) 2));
            overrideDecks[0].addCard(new Card("De", (byte) 3));

            //Create the override Deck for P2.
            overrideDecks[1].addCard(new Card("Ar", (byte) 14));
            overrideDecks[1].addCard(new Card("Me", null));
            overrideDecks[1].addCard(new Card("Me", null));
            overrideDecks[1].addCard(new Card("De", (byte) 6));
            overrideDecks[1].addCard(new Card("Ar", (byte) 8));
            overrideDecks[1].addCard(new Card("So", (byte) 6));
            overrideDecks[1].addCard(new Card("So", (byte) 7));
            overrideDecks[1].addCard(new Card("So", (byte) 8));
            overrideDecks[1].addCard(new Card("So", (byte) 9));
            overrideDecks[1].addCard(new Card("So", (byte) 10));
            overrideDecks[1].addCard(new Card("De", (byte) 4));
            overrideDecks[1].addCard(new Card("De", (byte) 5));

            //Create the override Deck for P3.
            overrideDecks[2].addCard(new Card("Sw", (byte) 1));
            overrideDecks[2].addCard(new Card("Sw", (byte) 2));
            overrideDecks[2].addCard(new Card("Sw", (byte) 3));
            overrideDecks[2].addCard(new Card("De", (byte) 10));
            overrideDecks[2].addCard(new Card("Sw", (byte) 5));
            overrideDecks[2].addCard(new Card("Sw", (byte) 6));
            overrideDecks[2].addCard(new Card("Sw", (byte) 7));
            overrideDecks[2].addCard(new Card("Sw", (byte) 8));
            overrideDecks[2].addCard(new Card("Sw", (byte) 9));
            overrideDecks[2].addCard(new Card("Sw", (byte) 10));
            overrideDecks[2].addCard(new Card("Sw", (byte) 11));
            overrideDecks[2].addCard(new Card("Sw", (byte) 12));

            //Create the override Deck for P4.
            overrideDecks[3].addCard(new Card("Ar", (byte) 11));
            overrideDecks[3].addCard(new Card("Sw", (byte) 14));
            overrideDecks[3].addCard(new Card("Ar", (byte) 10));
            overrideDecks[3].addCard(new Card("De", (byte) 7));
            overrideDecks[3].addCard(new Card("Ar", (byte) 7));
            overrideDecks[3].addCard(new Card("So", (byte) 11));
            overrideDecks[3].addCard(new Card("So", (byte) 12));
            overrideDecks[3].addCard(new Card("So", (byte) 13));
            overrideDecks[3].addCard(new Card("So", (byte) 14));
            overrideDecks[3].addCard(new Card("So", (byte) 15));
            overrideDecks[3].addCard(new Card("De", (byte) 8));
            overrideDecks[3].addCard(new Card("De", (byte) 9));
        }

        //Begin the round.
        game.beginRound(new PrintWriter(new StringWriter()), overrideDecks);
        currentRound = new Melee[12];
    }

    /*  Purpose: Step definition used only in Scenario A that creates the override Decks of each Player for the second
    *   round and begins the second round.*/
    @When("we begin the second round and distribute 12 cards to each player")
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
        game.beginRound(new PrintWriter(new StringWriter()), overrideDecks);
        currentRound = new Melee[12];
    }

    /*  Purpose: General step definition for creating a new Melee, used only in Scenario E.
    *   Parameters: -currentMelee: Integer representing the number of the current Melee we are in*/
    @When("we begin Melee {int}")
    public void we_begin_melee(Integer currentMelee) {
        this.currentMelee = currentMelee-1;                 //Convert to an array index
        this.currentRound[this.currentMelee] = new Melee(game.getNumPlayers());
    }

    /*  Purpose: General Step Definition for playing a Merlin or Apprentice Card at the start of a Melee.
    *   Parameters: -player: String representing the Player playing the Card
    *               -type: One of "Me" or "Ap"
    *               -suit: String representing the suit to be assigned to the Merlin or Apprentice Card
    *               -value: Integer representing the value to be assigned to the Merlin or Apprentice Card*/
    @When("{string} plays {string} and gives it the suit {string} and assigns the value {int} to it")
    public void plays_and_gives_it_the_suit_and_assigns_the_value_to_it(String player, String type, String suit, Integer value) {
        input.append(String.format("1\n%s\n%d\n", suit, value));
    }

    /*  Purpose: General Step Definition for a Player playing a Merlin or Apprentice Card after the first Card has been
     *  played. This is done by simply adding to the input String for the next call to game.playMelee().
     *  Parameters: -name: The name of the Player who is playing te Card
     *              -cardType: One of "Me" or "Ap"
     *              -value: Integer representing the value to be assigned to the Merlin or Apprentice Card*/
    @When("{string} plays {string} and assigns {int} to it")
    public void play_me_or_ap(String name, String cardType, Integer value) {
        input.append(String.format("1\n%d\n", value));
    }

    /*  Purpose: General Step Definition for a Player playing a Merlin or Apprentice Card after the first Card has been
     *  played.This is done by calling the playCard() method of the current Melee. Used in Scenario E only.
     *  Parameters: -playerName: The name of the Player playing the Card
     *              -card: Card object which is to be played (a Merlin or Apprentice)
     *              -value: The value to be assigned to the Merlin or Apprentice Card*/
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
                new PrintWriter(new StringWriter()));
    }



    /*Purpose: General Step Definition for a Player playing a Card that is not a Merlin or Apprentice. This is done by
    * providing input to the program.
    * Parameters: -name: The name of the Player playing the Card
    *             -card: String representing the Card to be played, such as "De14"*/
    @When("{string} plays {string}")
    public void playCard(String name, String card) {
        input.append("1\n");
    }

    /*Purpose: General Step Definition for a Player playing a Card that is not a Merlin or Apprentice. They do so by
    * calling the 'playCard' method of the current Melee. Used in Scenario E only.
    * Parameters: -playerName: The name of the Player playing the Card
    *             -card: String representing the Card to be played, such as "Ar11"*/
    @When("{string} plays the card {string}")
    public void plays_the_card(String playerName, String card) {
        Card toPlay = parseCard(card);
        Player current = getPlayer(playerName);
        int playerIndex = -1;
        //Determine the index of the Player in the Game's 'players' array.
        for(int i=0;i<game.getNumPlayers();i++) {
            if(game.playerAt(i).equals(current)) {
                playerIndex = i;
                break;
            }
        }
        currentRound[currentMelee].playCard((byte)playerIndex, current, toPlay, new Scanner(input.toString()),
                new PrintWriter(new StringWriter()));
    }

    /*  Purpose: General Step Definition for when a Player must discard a Card. They do so by providing input to the
        program. This step definition causes the Player who discarded a Card to immediately suffer 5 injury points.
        Parameters: -name: The name of the Player who must discard a Card
                    -card: String representing the Card to be discarded, such as "Sw1"
    */
    @When("{string} discards {string} and immediately suffers 5 injury points due to shaming")
    public void discards_and_immediately_suffers_injury_points_due_to_shaming(String name, String card) {
        input.append("1\n");
        currentRound[0] = game.playMelee(new Scanner(input.toString()), new PrintWriter(new StringWriter()));
    }

    /*  Purpose: General Step Definition for when a Player must discard a Card. They do so by calling the
     *  'discardCard' method of the current Melee and immediately suffer 5 injury points. Used only in Scenario E.
     *  Parameters: -playerName: The name of the Player discarding a Card
     *              -card: String representing the Card to be discarded*/
    @When("{string} discards the card {string} and immediately suffers 5 injury points due to shaming")
    public void discards_the_card_and_immediately_suffers_injury_points_due_to_shaming(String playerName, String card) {
        Player player = getPlayer(playerName);
        int playerIndex = -1;
        for(int i=0;i<game.getNumPlayers();i++) {
            if(game.playerAt(i).equals(player)) {
                playerIndex = i;
                break;
            }
        }
        currentRound[currentMelee].discardCard(new Scanner("1\n"), new PrintWriter(new StringWriter()),
                (byte)playerIndex, player);
    }

    /*  Purpose: General Step Definition for the end of a Melee when there is a loser.
    *   Parameters: -name: The name of the losing Player
    *               -injuryPoints: The number of injury points accumulated by the Player in the Melee
    *               -melee: Integer representing the number of the Melee that was lost*/
    @When("{string} is the loser and accumulates {int} injury points from Melee {int}")
    public void player_is_the_loser_and_accumulates_injury_points_from_melee(String name, Integer injuryPoints,
                                                                             Integer melee) {
        Melee current = game.playMelee(new Scanner(input.toString()), new PrintWriter(new StringWriter()));
        currentRound[melee-1] = current;

        //Sum injury points and ensure they are correct.
        int sum =0;
        for(int i=0;i<current.getPlayed().length;i++)
            if(current.getPlayed()[i] != null) sum += current.getPlayed()[i].getDamage();

        input = new StringBuilder();                    //Reset the StringBuilder.
    }

    /*  Purpose: General step definition for when a Melee has no loser. It calls the playMelee() method of the Game
        class, which computes the loser of the Melee.
        Parameters: -melee: Integer specifying the number of the Melee that has no loser*/
    @When("Melee {int} has no loser")
    public void melee_has_no_loser(Integer melee) {
        currentRound[melee-1] = game.playMelee(new Scanner(input.toString()),
                new PrintWriter(new StringWriter()));
        input = new StringBuilder();                    //Reset the StringBuilder.
    }

    /*Purpose: General step definition that determines the loser of a Melee by calling the Melee method computeLoser()
    directly. Used in Scenario E only.
    Parameters: -loser: The name of the Player that lost the Melee (String)*/
    @When("{string} is the loser")
    public void is_the_loser(String loser) {
        currentRound[currentMelee].computeLoser(game.getPlayers(), new PrintWriter(new StringWriter()));
    }

    /*Purpose: General Step Definition for the end of a round. It is responsible for determining the result of a round
    * (i.e. if the Game has ended and computing winners if so) and applying injury points accumulated in each Player's
    * injuryDeck during the round.
    * Parameters: -roundNumber: Integer corresponding to the round that has ended*/
    @When("round {int} ends")
    public void round_ends(Integer roundNumber) {
        roundSummaries.add(game.getRoundResult(new PrintWriter(new StringWriter()), currentRound));
    }

    /*Purpose: General Step definition for checking that the Game ended after a given round.
    * Parameters: -roundNumber: Integer representing the number of the round after which the Game ended. In
    * roundSummaries, the entry at index 'roundNumber-1' should be null if the Game ended after round 'roundNumber'.*/
    @Then("the Game ends after round {int}")
    public void the_game_ends_after_round(Integer roundNumber) {
        assertNull(roundSummaries.get(roundNumber-1));
    }

    /*  Purpose: General Step definition for checking that a Game ends during (i.e. before the end of) the first round.
    *   This means asserting that at least 1 of the Melees in the Melee[] currentRound must be null to check that the
    *   round did not finish and also asserting that the single Melee played, currentRound[0], returned null.*/
    @Then("the Game ends before the end of the first round")
    public void the_game_ends_before_the_round_ends() {
        boolean roundIncomplete = false;
        for(int i=0;i<12;i++) if(currentRound[i] == null) roundIncomplete = true;
        assertTrue(roundIncomplete);
        assertNull(currentRound[0]);
    }

    /*  Purpose: General step definition for checking the HP of a Player.
    *   Parameters: -name: The name of the Player whose HP we would like to verify
    *               -hp: Integer specifying the amount of HP the Player should have.
    */
    @Then("{string} has {int} HP")
    public void has_hp(String name, Integer hp) {
        Player player = getPlayer(name);
        assertEquals(hp, player.getHP());
    }

    /*  Purpose: Used to confirm how many injury points a Player has accumulated during a round and confirm the Melees
        that they lost. Used in Scenario E only.
        Parameters: -playerName: The name of the Player
                    -injuryPoints: Integer representing the number of injury points the Player lost during the round
                    -melees: String of the form {...}, where the curly braces contain a list of integers corresponding
                     to the numbers of the Melees that the Player lost (if any)*/
    @Then("{string} has accumulated {int} injury points having lost the Melees {string}")
    public void has_accumulated_injury_points(String playerName, Integer injuryPoints, String melees) {
        Player player = getPlayer(playerName);

        //Determine the Melees the Player lost.
        List<Integer> meleesLost = parseMelees(melees);
        //Check that the correct losers were computed.
        if(!meleesLost.isEmpty()) {
            //Check that the Player lost the correct Melees in this case
            for(int melee:meleesLost) {
                assertEquals(player, game.playerAt(currentRound[melee-1].getLoser()));
            }
        } else {
            //Check that the Player lost no Melees in this case.
            for(int i=0;i<currentRound.length;i++) {
                if(currentRound[i] != null && currentRound[i].getLoser() != null) {
                    assertNotEquals(player, game.playerAt(currentRound[i].getLoser()));
                }
            }
        }

        //Check that the Player accumulated the correct number of injury points.
        assertEquals(injuryPoints, player.computeTotalInjury());
    }

    /*  Purpose: General step definition for checking that a Game has a single winner. This means asserting that the size
    *   of the Game's 'winners' ArrayList<Player> is 1 and asserting that it contains the correct Player.
    *   Parameters: -winner: the name of the winning Player*/
    @Then("{string} is the winner")
    public void is_the_winner(String winner) {
        assertEquals(1, game.getWinners().size());
        assertTrue(game.getWinners().contains(getPlayer(winner)));
    }

    /*  Purpose: General step definition for checking that a Game has 2 winners. This means asserting that the Game's
    *   'winners' ArrayList<Player> has a size of 2 and asserting that it contains the correct Players.
    *   Parameters: -p1Name: The name of the first winning Player
    *               -p2Name: The name of the second winning Player*/
    @Then("{string} and {string} are the winners")
    public void and_are_the_winners(String p1Name, String p2Name) {
        assertEquals(2, game.getWinners().size());
        assertTrue(game.getWinners().contains(getPlayer(p1Name)));
        assertTrue(game.getWinners().contains(getPlayer(p2Name)));
    }

    /*  Purpose: General step definition for checking that a Game has no winner. It does so by asserting that the size
    *   of the Game's 'winners' ArrayList<Player> is empty.*/
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
