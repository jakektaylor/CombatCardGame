import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;

class GameTest {
    @Test
    @DisplayName("U-TEST 001: Testing valid user input when entering the number of players.")
    void testValNumPlayers(){
        //Testing valid input.
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("3\nJake\nCaroline\nAlex\n300\n"), new PrintWriter(output));
        assertEquals(3, game.getNumPlayers());
    }

    @Test
    @DisplayName("U-TEST 002: Testing invalid user input when entering the number of players.")
    void testInvNumPlayers(){
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("1\n3\nJake\nCaroline\nAlex\n300\n"), new PrintWriter(output));
        assertTrue(output.toString().contains("ERROR: Invalid number of players."));

        game = new Game();
        output = new StringWriter();
        game.setupGame(new Scanner("6\n3\nJake\nCaroline\nAlex\n300\n"), new PrintWriter(output));
        assertTrue(output.toString().contains("ERROR: Invalid number of players."));
    }

    @Test
    @DisplayName("U-TEST 003: Testing that the user is forced to enter a valid number of players before proceeding.")
    void testPlayerInputLoop() {
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("2\n8\n4\nJake\nCaroline\nAlex\nSophie\n300\n"), new PrintWriter(output));
        assertEquals(4, game.getNumPlayers());
    }

    @Test
    @DisplayName("U-TEST 004: Testing valid user input when creating the players.")
    void testValPlayers() {
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("3\nJake\nCaroline\nAlex\n300\n"), new PrintWriter(output));
        assertEquals("Jake", game.playerAt(0).getName());
        assertEquals("Caroline", game.playerAt(1).getName());
        assertEquals("Alex", game.playerAt(2).getName());
    }

    @Test
    @DisplayName("U-TEST 005: Testing invalid user input results in an error message when creating the Players.")
    void testInvPlayers(){
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("3\n\n\n\nJake\nCaroline\nAlex\n300\n"), new PrintWriter(output));
        //Test that the error message was printed 3 times.
        assertEquals(3, output.toString().split
                ("ERROR: Player name must be non-empty.", -1).length - 1);
    }

    @Test
    @DisplayName("U-TEST 006: Testing that the user is forced to enter valid Player names before proceeding.")
    void testNameInputLoop() {
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("3\n\n\nJake\n\nCaroline\n\nAlex\n300\n"), new PrintWriter(output));

        //Test that the 3 Players were correctly created afterward.
        assertEquals("Jake", game.playerAt(0).getName());
        assertEquals("Caroline", game.playerAt(1).getName());
        assertEquals("Alex", game.playerAt(2).getName());
    }

    @Test
    @DisplayName("U-TEST 007: Testing a valid number of health points.")
    void testValHP() {
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("3\nJake\nCaroline\nAlex\n300\n"), new PrintWriter(output));

        assertEquals(300, game.playerAt(0).getHP());
        assertEquals(300, game.playerAt(1).getHP());
        assertEquals(300, game.playerAt(2).getHP());
    }

    @Test
    @DisplayName("U-TEST 008: Testing a negative number of health points results in an error message" +
            "and requires the user to enter a valid number before proceeding.")
    void testInvHP() {
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("3\nJake\nCaroline\nAlex\n-50\n300\n"), new PrintWriter(output));

        //Ensure the error message was printed and the correct values were set afterward.
        assertTrue(output.toString().contains("ERROR: Invalid number of health points."));
        assertEquals(300, game.playerAt(0).getHP());
        assertEquals(300, game.playerAt(1).getHP());
        assertEquals(300, game.playerAt(2).getHP());
    }

    @Test
    @DisplayName("U-TEST 009: Testing a non-integer number of health points results in an error message and requires" +
            "the user to enter a valid number before proceeding.")
    void testNonIntHP(){
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("3\nJake\nCaroline\nAlex\n3.14\n300\n"), new PrintWriter(output));

        //Ensure the error message was printed and the correct values were set afterward.
        assertTrue(output.toString().contains("ERROR: Invalid number of health points."));
        assertEquals(300, game.playerAt(0).getHP());
        assertEquals(300, game.playerAt(1).getHP());
        assertEquals(300, game.playerAt(2).getHP());
    }

    @Test
    @DisplayName("U-TEST 013: Testing that the dealCards() method gives each player 12 cards.")
    void testStartingCards(){
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("4\nJake\nCaroline\nAlex\nJohn\n300\n"), new PrintWriter(output));
        assertEquals(12, game.playerAt(0).getNumCards());
        assertEquals(12, game.playerAt(1).getNumCards());
        assertEquals(12, game.playerAt(2).getNumCards());
        assertEquals(12, game.playerAt(3).getNumCards());
    }

    @Test
    @DisplayName("U-TEST 014: Testing that the order of the starting Player chosen each round is respected.")
    void testRoundStarter() {
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("5\nJake\nCaroline\nAlex\nJohn\nJessica\n300\n"), new PrintWriter(output));

        //Create the override decks.
        Deck[] overrideDecks = new Deck[game.getNumPlayers()];
        for(int i=0;i<game.getNumPlayers();i++) {
            overrideDecks[i] = new Deck();
            for(int j=0;j<5;j++) overrideDecks[i].addCard(new Card("Sw", (byte)9));
        }

        //Check that the starting Player changes correctly each round.
        assertEquals(0, game.nextRoundStarter());
        game.playRound(new Scanner("1\n1\n1\n1\n1\n"), new PrintWriter(output), overrideDecks, 1);

        assertEquals(1, game.nextRoundStarter());
        game.playRound(new Scanner("1\n1\n1\n1\n1\n"), new PrintWriter(output), overrideDecks, 1);

        assertEquals(2, game.nextRoundStarter());
        game.playRound(new Scanner("1\n1\n1\n1\n1\n"), new PrintWriter(output), overrideDecks, 1);

        assertEquals(3, game.nextRoundStarter());
        game.playRound(new Scanner("1\n1\n1\n1\n1\n"), new PrintWriter(output), overrideDecks, 1);

        assertEquals(4, game.nextRoundStarter());
        game.playRound(new Scanner("1\n1\n1\n1\n1\n"), new PrintWriter(output), overrideDecks, 1);

        //Check that it loops around back to 0.
        assertEquals(0, game.nextRoundStarter());
    }

    @Test
    @DisplayName("U-TEST 015: Testing that the initial hand of each Player is displayed in the execution window.")
    void testInitialHand() {
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("3\nJake\nCaroline\nAlex\n300\n"), new PrintWriter(output));

        //Create the override deck.
        Deck[] overrideDecks = new Deck[3];
        overrideDecks[0] = new Deck();
        overrideDecks[1] = new Deck();
        overrideDecks[2] = new Deck();
        for(String suit: Card.SUITS) {
            for(int j=0;j<3;j++) {
                overrideDecks[0].addCard(new Card(suit, (byte)(j+1)));
                overrideDecks[1].addCard(new Card(suit, (byte)(4+j)));
                overrideDecks[2].addCard(new Card(suit, (byte)(7+j)));
            }
        }

        String p1Hand = overrideDecks[0].toString();
        String p2Hand = overrideDecks[1].toString();
        String p3Hand = overrideDecks[2].toString();

        game.playRound(new Scanner("1\n1\n1\n"), new PrintWriter(output), overrideDecks, 1);
        assertTrue(output.toString().contains(p1Hand));
        assertTrue(output.toString().contains(p2Hand));
        assertTrue(output.toString().contains(p3Hand));
    }

    @Test
    @DisplayName("U-TEST 016: Testing that the deck is shuffled at the beginning of a round.")
    void testShuffle() {
        Game game = new Game();
        Deck starting = game.getDeck().deepCopy();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("3\nJake\nCaroline\nAlex\n300\n"), new PrintWriter(output));

        //Create the override deck.
        Deck[] overrideDecks = new Deck[3];
        overrideDecks[0] = new Deck();
        overrideDecks[1] = new Deck();
        overrideDecks[2] = new Deck();
        for(String suit: Card.SUITS) {
            for(int j=0;j<3;j++) {
                overrideDecks[0].addCard(new Card(suit, (byte)(j+1)));
                overrideDecks[1].addCard(new Card(suit, (byte)(4+j)));
                overrideDecks[2].addCard(new Card(suit, (byte)(7+j)));
            }
        }

        game.playRound(new Scanner("1\n1\n1\n"), new PrintWriter(output), overrideDecks, 1);
        Deck shuffled = game.getDeck();
        assertNotEquals(starting, shuffled);
    }

    @Test
    @DisplayName("U-TEST 017: Testing that each Player is shown their hand and prompted to enter a number for the Card" +
            "they want to play when it is their turn in the Melee.")
    void testShowHand() {
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("3\nJake\nCaroline\nAlex\n300\n"), new PrintWriter(output));

        //Create the override deck.
        Deck[] overrideDecks = new Deck[3];
        overrideDecks[0] = new Deck();
        overrideDecks[1] = new Deck();
        overrideDecks[2] = new Deck();
        for(String suit: Card.SUITS) {
            for(int j=0;j<3;j++) {
                overrideDecks[0].addCard(new Card(suit, (byte)(j+1)));
                overrideDecks[1].addCard(new Card(suit, (byte)(4+j)));
                overrideDecks[2].addCard(new Card(suit, (byte)(7+j)));
            }
        }

        String p1Hand = overrideDecks[0].toString();
        int p1NumCards = overrideDecks[0].getNumCards();

        String p2Hand = overrideDecks[1].toString();
        int p2NumCards = overrideDecks[1].getNumCards();

        String p3Hand = overrideDecks[2].toString();
        int p3NumCards = overrideDecks[2].getNumCards();

        game.playRound(new Scanner("1\n1\n1\n"), new PrintWriter(output), overrideDecks, 1);
        assertTrue(output.toString().contains(String.format("Player 1 please choose a card:\n%s\nEnter a number between " +
                        "1 and %d:", p1Hand, p1NumCards)));
        assertTrue(output.toString().contains(String.format("Player 2 please choose a card:\n%s\nEnter a number between " +
                "1 and %d:", p2Hand, p2NumCards)));
        assertTrue(output.toString().contains(String.format("Player 3 please choose a card:\n%s\nEnter a number between " +
                "1 and %d:", p3Hand, p3NumCards)));
    }

    @Test
    @DisplayName("U-TEST 018: Testing that when a Player selects a Card to play, the number they entered is displayed along" +
            "with the Card they chose.")
    void testShowSelection() {
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("3\nJake\nCaroline\nAlex\n300\n"), new PrintWriter(output));

        //Create the override Decks.
        Deck[] override = new Deck[3];
        override[0] = new Deck();
        override[0].addCard(new Card("Sw", (byte) 7));

        override[1] = new Deck();
        override[1].addCard(new Card("Ar", (byte) 2));
        override[1].addCard(new Card("Sw", (byte) 11));

        override[2] = new Deck();
        override[2].addCard(new Card("Sw", (byte) 9));
        override[2].addCard(new Card("De", (byte) 8));
        override[2].addCard(new Card("Sw", (byte) 3));

        game.playRound(new Scanner("1\n2\n3\n"), new PrintWriter(output), override, 1);

        //Check that the numbers entered are displayed.
        assertTrue(output.toString().contains("1\n"));
        assertTrue(output.toString().contains("2\n"));
        assertTrue(output.toString().contains("3\n"));

        //Check that the window displays which card each Player plays.
        assertTrue(output.toString().contains(String.format
                ("Player 1 played: %s\n", new Card("Sw", (byte) 7))));
        assertTrue(output.toString().contains(String.format
                ("Player 2 played: %s\n", new Card("Sw", (byte) 11))));
        assertTrue(output.toString().contains(String.format
                ("Player 3 played: %s\n", new Card("Sw", (byte) 3))));

    }

    @Test
    @DisplayName("U-TEST 019: Testing invalid user input when selecting a Card to play.")
    void testInvCard() {
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("3\nJake\nCaroline\nAlex\n300\n"), new PrintWriter(output));

        //Create the override deck.
        Deck[] overrideDecks = new Deck[3];
        overrideDecks[0] = new Deck();
        overrideDecks[1] = new Deck();
        overrideDecks[2] = new Deck();
        for(String suit: Card.SUITS) {
            for(int j=0;j<3;j++) {
                overrideDecks[0].addCard(new Card(suit, (byte)(j+1)));
                overrideDecks[1].addCard(new Card(suit, (byte)(4+j)));
                overrideDecks[2].addCard(new Card(suit, (byte)(7+j)));
            }
        }

        //Testing negative value for card choice.
        game.playRound(new Scanner("-1\n1\n1\n1\n"), new PrintWriter(output), overrideDecks, 1);
        assertTrue(output.toString().contains("ERROR: Card selection must be between 1 and 12.\n"));

        //Testing 0 for the Card choice.
        game.playRound(new Scanner("1\n0\n1\n1\n"), new PrintWriter(output), overrideDecks, 1);
        assertTrue(output.toString().contains("ERROR: Card selection must be between 1 and 12.\n"));

        //Test a value greater than the number of Cards the Player has in their hand.
        game.playRound(new Scanner("1\n1\n20\n1\n"), new PrintWriter(output), overrideDecks, 1);
        assertTrue(output.toString().contains("ERROR: Card selection must be between 1 and 12.\n"));
    }

    @Test
    @DisplayName("U-TEST 020: Testing that when a card is played, it leaves the Player's hand and goes into the Melee's" +
            "'played' deck.")
    void testPlayCard() {
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("3\nJake\nCaroline\nAlex\n300\n"), new PrintWriter(output));

        Card[] played = new Card[]{new Card("So", (byte) 7), new Card("So", (byte) 11),
                new Card("So", (byte) 3)};

        //Create the override Decks.
        Deck[] override = new Deck[3];
        override[0] = new Deck();
        override[0].addCard(played[0]);

        override[1] = new Deck();
        override[1].addCard(new Card("Ar", (byte) 2));
        override[1].addCard(played[1]);

        override[2] = new Deck();
        override[2].addCard(new Card("Sw", (byte) 9));
        override[2].addCard(new Card("De", (byte) 8));
        override[2].addCard(played[2]);

        //Play the round (consists of 1 Melee for now)
        Melee[] summary = game.playRound(new Scanner("1\n2\n3\n"), new PrintWriter(output), override,1);

        //Check that the selected card is no longer in each Player's deck.
        assertFalse(game.playerAt(0).getHand().getCards().contains(played[0]));
        assertEquals(0, game.playerAt(0).getHand().getNumCards());

        assertFalse(game.playerAt(1).getHand().getCards().contains(played[1]));
        assertEquals(1, game.playerAt(1).getHand().getNumCards());

        assertFalse(game.playerAt(2).getHand().getCards().contains(played[2]));
        assertEquals(2, game.playerAt(2).getHand().getNumCards());

        //Check that the Cards are added to the Melee's 'played' Deck of cards.
        for(int i=0;i<game.getNumPlayers();i++) assertEquals(summary[0].getPlayed()[i],
                played[i]);
    }

    @Test
    @DisplayName("U-TEST 021: Testing that the first Player playing a basic weapon card sets the Suit for the Melee.")
    void testFirstBasic() {
        //Have the first Player play each type of basic weapon card.
        for(String suit:Card.SUITS) {
            Game game = new Game();
            StringWriter output = new StringWriter();
            game.setupGame(new Scanner("3\nJake\nCaroline\nAlex\n300\n"), new PrintWriter(output));

            //Create the override Decks.
            Deck[] override = new Deck[3];
            override[0] = new Deck();
            override[0].addCard(new Card(suit, (byte) 3));

            override[1] = new Deck();
            override[1].addCard(new Card(suit, (byte) 5));

            override[2] = new Deck();
            override[2].addCard(new Card(suit, (byte) 7));

            Melee[] summary = game.playRound(new Scanner("1\n1\n1\n"), new PrintWriter(output), override, 1);
            assertEquals(suit, summary[0].getSuit());
        }
    }

    @Test
    @DisplayName("U-TEST 022: Testing that if the first Player plays a Merlin or Apprentice card, they are prompted to enter" +
            "the value and suit of the Card and their responses are displayed.")
    void testFirstMeAp() {
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("3\nJake\nCaroline\nAlex\n300\n"), new PrintWriter(output));

        //Create the override Decks.
        Deck[] overrideDecks = new Deck[3];
        overrideDecks[0] = new Deck();
        overrideDecks[0].addCard(new Card("Me", null));
        overrideDecks[0].addCard(new Card("Ap", null));

        overrideDecks[1] = new Deck();
        overrideDecks[1].addCard(new Card("Sw", (byte) 7));
        overrideDecks[1].addCard(new Card("So", (byte) 2));

        overrideDecks[2] = new Deck();
        overrideDecks[2].addCard(new Card("Sw", (byte) 15));
        overrideDecks[2].addCard(new Card("So", (byte) 5));

        game.playRound(new Scanner("1\nSw\n2" +
                "\n1\n1\n"), new PrintWriter(output), overrideDecks, 1);
        assertTrue(output.toString().contains("Please enter a suit for the Merlin card: "));
        assertTrue(output.toString().contains("\nSw"));
        assertTrue(output.toString().contains("Please enter a value for the Merlin card: "));
        assertTrue(output.toString().contains("\n2"));

        game = new Game();
        output = new StringWriter();
        game.setupGame(new Scanner("3\nJake\nCaroline\nAlex\n300\n"), new PrintWriter(output));
        game.playRound(new Scanner("1\nSo\n10" +
                "\n1\n1\n"), new PrintWriter(output), overrideDecks, 1);
        assertTrue(output.toString().contains("Please enter a suit for the Apprentice card: "));
        assertTrue(output.toString().contains("\nSo"));
        assertTrue(output.toString().contains("Please enter a value for the Apprentice card: "));
        assertTrue(output.toString().contains("\n10"));
    }

    @Test
    @DisplayName("U-TEST 030: Testing that after the suit for the Melee has been set, a Player cannot play an Alchemy" +
            "card if they have a Basic Weapon of the same suit or Merlin or Apprentice.")
    void testInvAlc() {
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("4\nJake\nCaroline\nAlex\nJohn\n300\n"), new PrintWriter(output));

        //Create the override decks.
        Deck[] overrideDecks = new Deck[4];
        overrideDecks[0] = new Deck();
        //Set the suit of the Melee to "Sw".
        overrideDecks[0].addCard(new Card("Sw", (byte) 5));

        overrideDecks[1] = new Deck();
        overrideDecks[1].addCard(new Card("Al", (byte) 15));
        overrideDecks[1].addCard(new Card("Sw", (byte) 12));

        overrideDecks[2] = new Deck();
        overrideDecks[2].addCard(new Card("Al", (byte) 12));
        overrideDecks[2].addCard(new Card("Me", null));

        overrideDecks[3] = new Deck();
        overrideDecks[3].addCard(new Card("Al", (byte)10));
        overrideDecks[3].addCard(new Card("Ap", null));



        output = new StringWriter();
        Melee[] summary = game.playRound(new Scanner("1\n1\n2\n1\n2\n12\n1\n2\n8\n"), new PrintWriter(output),
                overrideDecks, 1);

        //Check that the  error message was printed 3 times.
        int currIndex = 0;
        String toFind = String.format("\nERROR: Cannot play an Alchemy card as you have a basic weapon card of the " +
        "same suit as the melee (%s) or a Merlin or Apprentice card.\n", summary[0].getSuit());
        String toSearch = output.toString();
        int numOccurences = 0;                                          //Number of occurences of the error message.
        while(currIndex != -1) {
            currIndex = toSearch.indexOf(toFind, currIndex);
            if(currIndex != -1) {
                numOccurences++;
                currIndex+=toFind.length();
            }
        }
        //Check that the error message was displayed 3 times.
        assertEquals(3, numOccurences);
        //Check that the Alchemy cards weren't played.
        assertEquals(new Card("Sw", (byte) 12), summary[0].getPlayed()[1]);
        assertEquals(new Card("Me", (byte) 12), summary[0].getPlayed()[2]);
        assertEquals(new Card("Ap", (byte)8), summary[0].getPlayed()[3]);
    }

    @Test
    @DisplayName("U-TEST 031: Testing that after the suit has been set, if a Player does not have a Basic Weapon of " +
            "the same suit or Merlin or Apprentice or Alchemy card, they are prompted to choose a Card to discard. Testing" +
            " that their response is displayed on screen.")
    void testDiscardPrompt() {
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("3\nJake\nCaroline\nAlex\n300\n"), new PrintWriter(output));

        //Create the override decks.
        Deck[] overrideDecks = new Deck[3];
        overrideDecks[0] = new Deck();
        //Set the suit of the Melee to "Sw".
        overrideDecks[0].addCard(new Card("Sw", (byte) 5));

        overrideDecks[1] = new Deck();
        overrideDecks[1].addCard(new Card("Ar", (byte) 7));
        overrideDecks[1].addCard(new Card("Ar", (byte) 9));

        overrideDecks[2] = new Deck();
        overrideDecks[2].addCard(new Card("Sw", (byte) 8));

        String toFind = overrideDecks[1].toString();
        game.playRound(new Scanner("1\n2\n1\n"), new PrintWriter(output), overrideDecks, 1);

        //Check that the prompt was displayed and the user's response was displayed.
        assertTrue(output.toString().contains("\nPlayer 2 please choose a card to discard as there is no Card you" +
                " can play: "));
        assertTrue(output.toString().contains("2\n"));
        //Ensure the Deck of Player 2 was displayed twice: once at the beginning and once after being asked to discard
        //a Card.
        int currIndex = 0;
        String toSearch = output.toString();
        int numOccurences = 0;                                          //Number of occurrences of the error message.
        while(currIndex != -1) {
            currIndex = toSearch.indexOf(toFind, currIndex);
            if(currIndex != -1) {
                numOccurences++;
                currIndex+=toFind.length();
            }
        }
        assertEquals(2, numOccurences);
    }

    @Test
    @DisplayName("U-TEST 038: Testing that the resulting hand of each Player is displayed after they play a Card.")
    void testResultingHand() {
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("3\nJake\nCaroline\nAlex\n300\n"), new PrintWriter(output));

        //Create the override decks.
        Deck[] overrideDecks = new Deck[3];

        overrideDecks[0] = new Deck();
        overrideDecks[0].addCard(new Card("Sw", (byte) 5));
        overrideDecks[0].addCard(new Card("Ar", (byte) 8));

        overrideDecks[1] = new Deck();
        overrideDecks[1].addCard(new Card("Ar", (byte) 7));
        overrideDecks[1].addCard(new Card("Ar", (byte) 9));

        overrideDecks[2] = new Deck();
        overrideDecks[2].addCard(new Card("Ar", (byte) 14));
        overrideDecks[2].addCard(new Card("Ar", (byte) 12));

        game.playRound(new Scanner("2\n1\n2\n"), new PrintWriter(output), overrideDecks, 1);
        assertTrue(output.toString().contains("Resulting hand:\n" + game.playerAt(0).displayHand()));
        assertTrue(output.toString().contains("Resulting hand:\n" + game.playerAt(1).displayHand()));
        assertTrue(output.toString().contains("Resulting hand:\n" + game.playerAt(2).displayHand()));
    }

    @Test
    @DisplayName("U-TEST 040: Testing that the order of play for the first Melee of a round follows the same order as" +
            " is defined for each successive round i.e. if there are 3 Players, the first player starts the first" +
            "melee of the first round, the second Player starts the first melee of the second round etc.")
    void testFirstMelee() {
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("3\nJake\nCaroline\nAlex\n300\n"), new PrintWriter(output));

        //Create the override decks.
        Deck[] overrideDecks = new Deck[game.getNumPlayers()];
        Deck[] overrideCopy = new Deck[game.getNumPlayers()];

        for(int i=0;i<game.getNumPlayers();i++) {
            overrideDecks[i] = new Deck();
            overrideCopy[i] = new Deck();
            for(int j=(i*4) + 1;j<=(i*4) + 4;j++) {
                overrideDecks[i].addCard(new Card("Sw", (byte)(j)));
                overrideCopy[i].addCard(new Card("Sw", (byte)(j)));
            }
        }
        for(int i=0;i<3;i++) {
            Melee[] summary = game.playRound(new Scanner("1\n1\n1\n"),
                    new PrintWriter(new StringWriter()), overrideDecks, 1);
            assertEquals(i % game.getNumPlayers(), summary[0].getStarter());

            //Check that the right Players played at the right time.
            assertEquals(overrideCopy[i % game.getNumPlayers()].getCards().get(i),
                    summary[0].getPlayed()[i % game.getNumPlayers()]);
            assertEquals(overrideCopy[(i+1) % game.getNumPlayers()].getCards().get(i),
                    summary[0].getPlayed()[(i+1) % game.getNumPlayers()]);
            assertEquals(overrideCopy[(i+2) % game.getNumPlayers()].getCards().get(i),
                    summary[0].getPlayed()[(i+2) % game.getNumPlayers()]);
        }
    }

    @Test
    @DisplayName("U-TEST 049: Testing that after the suit for a Melee has been set, if a Player does not have a " +
            "Basic Weapon of the right suit or Merlin or Apprentice, they can play an Alchemy card.")
    void testAl() {
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("3\nJake\nCaroline\nAlex\n300\n"), new PrintWriter(output));

        //Create the override decks.
        Deck[] overrideDecks = new Deck[game.getNumPlayers()];

        overrideDecks[0] = new Deck();
        overrideDecks[0].addCard(new Card("Sw", (byte) 7));

        overrideDecks[1] = new Deck();
        overrideDecks[1].addCard(new Card("Sw", (byte) 8));

        overrideDecks[2] = new Deck();
        overrideDecks[2].addCard(new Card("Al", (byte) 7));
        overrideDecks[2].addCard(new Card("Ar", (byte) 5));

        Melee[] summary = game.playRound(new Scanner("1\n1\n1\n"), new PrintWriter(new StringWriter()),
                overrideDecks, 1);
        assertEquals(summary[0].getPlayed()[2], new Card("Al", (byte) 7));
    }

    @Test
    @DisplayName("U-TEST 053: Testing that all of the Cards played in the Melee are added to the loser's injury deck.")
    void testInjuryDeck() {
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("3\nJake\nCaroline\nAlex\n300\n"), new PrintWriter(output));

        //Create the override decks.
        Deck[] overrideDecks = new Deck[game.getNumPlayers()];
        //The first Player is given the lowest valued Card and will lose the Melee.
        for(int i=0;i<game.getNumPlayers();i++) {
            overrideDecks[i] = new Deck();
            overrideDecks[i].addCard(new Card("Sw", (byte)(i+1)));
        }
        Melee[] summary = game.playRound(new Scanner("1\n1\n1\n"), new PrintWriter(new StringWriter()),
                overrideDecks, 1);
        Player loser = game.getPlayers()[0];
       for(int i=0;i<game.getNumPlayers();i++) {
           assertEquals(summary[0].getPlayed()[i], loser.getInjuryDeck().getCards().get(i));
       }
    }

    @Test
    @DisplayName("U-TEST 054: Testing that if a Melee has a loser, their identity is displayed along with the total " +
            "injury points they accumulated from this Melee.")
    void testDisplayLoser() {
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("3\nJake\nCaroline\nAlex\n300\n"), new PrintWriter(output));

        //Create the override decks.
        Deck[] overrideDecks = new Deck[game.getNumPlayers()];
        //The first Player is given the lowest valued Card and will lose the Melee.
        for (int i = 0; i < game.getNumPlayers(); i++) {
            overrideDecks[i] = new Deck();
            overrideDecks[i].addCard(new Card("Sw", (byte) (i + 1)));
        }
        Melee[] summary = game.playRound(new Scanner("1\n1\n1\n"), new PrintWriter(output),
                overrideDecks, 1);
        Byte loser = summary[0].computeLoser();
        assertTrue(output.toString().contains(String.format("Player %d-%s lost the Melee. The total injury points they " +
                        "accumulated from this Melee is %d.\n", loser + 1, game.playerAt(loser).getName(), 15)));
    }

    @Test
    @DisplayName("U-TEST 055: Testing that if the first Player has no Basic Weapon, Merlin or Apprentice cards, they" +
            " can start the Melee with an Alchemy Card and the suit for the Melee is not set.")
    void testAlFirst() {
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("3\nJake\nCaroline\nAlex\n300\n"), new PrintWriter(output));

        //Create the override decks.
        Deck[] overrideDecks = new Deck[3];

        overrideDecks[0] = new Deck();
        overrideDecks[0].addCard(new Card("Al", (byte) 7));

        overrideDecks[1] = new Deck();
        overrideDecks[1].addCard(new Card("Sw", (byte) 8));

        overrideDecks[2] = new Deck();
        overrideDecks[2].addCard(new Card("Ar", (byte) 9));

        Melee[] summary = game.playRound(new Scanner("1\n1\n1\n"), new PrintWriter(output),
                overrideDecks, 1);
        assertAll(()->assertEquals(new Card("Al", (byte) 7), summary[0].getPlayed()[0]),
                ()->assertNull(summary[0].getSuit()));
    }

    @Test
    @DisplayName("U-TEST 056: Testing that if a Melee has no loser, a message is displayed explaining this.")
    void testNoWinner() {
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("3\nJake\nCaroline\nAlex\n300\n"), new PrintWriter(output));

        //Create the override Decks.
        Deck[] overrideDecks = new Deck[3];

        overrideDecks[0] = new Deck();
        overrideDecks[0].addCard(new Card("Al", (byte) 12));

        overrideDecks[1] = new Deck();
        overrideDecks[1].addCard(new Card("So", (byte) 12));

        overrideDecks[2] = new Deck();
        overrideDecks[2].addCard(new Card("De", (byte) 12));
        Melee[] summary = game.playRound(new Scanner("1\n1\n1\n"), new PrintWriter(output),
                overrideDecks, 1);
        //Check that there was no loser and that the message was displayed.
        assertAll(()->assertTrue(output.toString().contains("No loser for this Melee. All Cards played have the same value.\n")),
                ()->assertNull(summary[0].computeLoser()));
    }

    @Test
    @DisplayName("U-TEST 057: Testing that if there is a loser for a Melee, the loser starts the following Melee.")
    void testLoserStarts() {
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("3\nJake\nCaroline\nAlex\n300\n"), new PrintWriter(output));

        //Create the override Decks.
        Deck[] overrideDecks = new Deck[3];

        overrideDecks[0] = new Deck();
        overrideDecks[0].addCard(new Card("Al", (byte) 12));
        overrideDecks[0].addCard(new Card("Al", (byte) 10));

        overrideDecks[1] = new Deck();
        overrideDecks[1].addCard(new Card("So", (byte) 10));
        overrideDecks[1].addCard(new Card("De", (byte) 9));

        overrideDecks[2] = new Deck();
        overrideDecks[2].addCard(new Card("De", (byte) 3));
        overrideDecks[2].addCard(new Card("Al", (byte) 15));

        Melee[] summary = game.playRound(new Scanner("1\n1\n1\n1" +
                        "\n1\n1\n"), new PrintWriter(output),
                overrideDecks, 2);
        //Check that the loser of the first Melee, Player 3, started the second Melee.
        assertEquals((int)summary[0].computeLoser(), summary[1].getStarter());
    }

    @Test
    @DisplayName("U-TEST 058: Testing that if there is no loser for a Melee, the Player who started this Melee starts " +
            "the next one.")
    void testSameStarts() {
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("3\nJake\nCaroline\nAlex\n300\n"), new PrintWriter(output));

        //Create the override Decks.
        Deck[] overrideDecks = new Deck[3];

        overrideDecks[0] = new Deck();
        overrideDecks[0].addCard(new Card("Al", (byte) 12));
        overrideDecks[0].addCard(new Card("Al", (byte) 8));

        overrideDecks[1] = new Deck();
        overrideDecks[1].addCard(new Card("So", (byte) 12));
        overrideDecks[1].addCard(new Card("De", (byte) 10));

        overrideDecks[2] = new Deck();
        overrideDecks[2].addCard(new Card("De", (byte) 12));
        overrideDecks[2].addCard(new Card("So", (byte) 14));

        Melee[] summary = game.playRound(new Scanner("1\n1\n1\n1" +
                        "\n1\n1\n"), new PrintWriter(output),
                overrideDecks, 2);
        //Check that the Player who started the first Melee also started the second one.
        assertEquals(summary[0].getStarter(), summary[1].getStarter());
    }

    @Test
    @DisplayName("U-TEST 060: Testing that at the end of each round, the total injury points a Player has accumulated" +
            " in the round are deducted from their hp.")
    void testInflictInjury() {
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("3\nJake\nCaroline\nAlex\n300\n"), new PrintWriter(output));

        int startingHP = 300;

        //Create the override Decks.
        Deck[] overrideDecks = new Deck[3];
        overrideDecks[0] = new Deck();
        overrideDecks[0].addCard(new Card("Ar", (byte) 12));
        overrideDecks[0].addCard(new Card("So", (byte) 8));

        overrideDecks[1] = new Deck();
        overrideDecks[1].addCard(new Card("Ar", (byte) 14));
        overrideDecks[1].addCard(new Card("So", (byte) 7));

        overrideDecks[2] = new Deck();
        overrideDecks[2].addCard(new Card("Ar", (byte) 13));
        overrideDecks[2].addCard(new Card("So", (byte) 10));

        /*Player 1 will lose the first Melee, accumulating 15 injury points. Player 2 will lose the second Melee,
        accumulating 15 injury points. Player 3 never loses and accumulates 0 injury points.*/
        game.playRound(new Scanner("1\n1\n1\n1\n1\n1\n"), new PrintWriter(output), overrideDecks, 2);
        assertAll(()->assertEquals(startingHP - 15, game.playerAt(0).getHP()),
                ()->assertEquals(startingHP - 15, game.playerAt(1).getHP()),
                ()->assertEquals(startingHP, game.playerAt(2).getHP()));
    }

    @Test
    @DisplayName("U-TEST 061: Testing that at the end of a round, the number of injury points suffered by each " +
            "Player in this round is displayed along with their current number of remaining health points.")
    void testRoundSummary() {
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setupGame(new Scanner("3\nJake\nCaroline\nAlex\n300\n"), new PrintWriter(output));

        //Create the override Decks.
        Deck[] overrideDecks = new Deck[3];
        overrideDecks[0] = new Deck();
        overrideDecks[0].addCard(new Card("Ar", (byte) 12));
        overrideDecks[0].addCard(new Card("So", (byte) 8));

        overrideDecks[1] = new Deck();
        overrideDecks[1].addCard(new Card("Ar", (byte) 14));
        overrideDecks[1].addCard(new Card("So", (byte) 7));

        overrideDecks[2] = new Deck();
        overrideDecks[2].addCard(new Card("Ar", (byte) 13));
        overrideDecks[2].addCard(new Card("So", (byte) 10));

        /*Playing a round with 2 Melees.*/
        game.playRound(new Scanner("1\n1\n1\n1\n1\n1\n"), new PrintWriter(output), overrideDecks, 2);
        assertTrue(output.toString().contains(String.format("End of round summary:\n%-28s%-28s%-28s\n" +
                        "%-28s%-28d%-28d\n%-28s%-28d%-28d\n%-28s%-28d%-28d\n","","Injury Points Inflicted", "Remaining HP",
                "Player 1: Jake", 15, 285, "Player 2: Caroline", 15, 285, "Player 3: Alex", 0, 300)));
    }

    @Test
    @DisplayName("U-TEST 062: Testing that at the end of a Round, each Player's injury Deck is cleared when playing" +
            " the Game.")
    void testNumMelees() {
        Game game = new Game();
        StringWriter output = new StringWriter();

        //Create the override Decks (one per Player for each Round we are playing (1 in this case)).
        Deck[][] overrideDeckArray = new Deck[1][3];
        overrideDeckArray[0] = new Deck[3];
        overrideDeckArray[0][0] = new Deck();
        overrideDeckArray[0][0].addCard(new Card("Ar", (byte) 12));

        overrideDeckArray[0][1] = new Deck();
        overrideDeckArray[0][1].addCard(new Card("Ar", (byte) 14));

        overrideDeckArray[0][2] = new Deck();
        overrideDeckArray[0][2].addCard(new Card("Ar", (byte) 13));

        game.play(new Scanner("3\nJake\nCaroline\nAlex\n300\n1\n1\n1\n"), new PrintWriter(output), overrideDeckArray,
                1, 1);
        for(int i=0;i<game.getNumPlayers();i++) assertEquals(0, game.playerAt(i).getInjuryDeck().getNumCards());
    }
}