import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class AcceptanceTestSuite {
    @Test
    @DisplayName("A-TEST 001: Testing the composition of the Game Deck before playing a single round consisting of 12 " +
            "Melees, where in each Melee all Cards played are Basic Weapons and Player 1 loses each Melee. Player 1 " +
            "reaches 0 HP by the end of the round.")
    void A1() {
        Game game = new Game();
        StringWriter output = new StringWriter();

        /*Check that the constitution of the Game Deck is correct.*/
        assertEquals(15, game.getDeck().getNumSw());
        assertEquals(15, game.getDeck().getNumAr());
        assertEquals(15, game.getDeck().getNumSo());
        assertEquals(15, game.getDeck().getNumDe());
        assertEquals(15, game.getDeck().getNumAl());
        assertEquals(3, game.getDeck().getNumMe());
        assertEquals(2, game.getDeck().getNumAp());
        assertEquals(80, game.getDeck().getNumCards());

        //Check for the correct value and damage for each Card.
        for(int i=0;i<game.getDeck().getNumCards();i++) {
            Card current = game.getDeck().getCards().get(i);
            //Checking Sword cards.
            if(current.getType().equals("Sw")) {
                assertTrue(current.getValue() >= 1 && current.getValue() <= 15);
                if(current.getValue() == 6 || current.getValue() == 7 || current.getValue() == 8 ||
                        current.getValue() == 9) {
                    assertEquals((byte) 10, current.getDamage());
                } else assertEquals((byte) 5, current.getDamage());
            }
            //Checking Arrow Cards.
            else if(current.getType().equals("Ar")) {
                assertTrue(current.getValue() >= 1 && current.getValue() <=15);
                if(current.getValue() == 8 || current.getValue() == 9 || current.getValue() == 10 ||
                        current.getValue() == 11) {
                    assertEquals((byte) 10, current.getDamage());
                } else assertEquals((byte) 5, current.getDamage());
            }
            //Checking Sorcery Cards
            else if(current.getType().equals("So")) {
                assertTrue(current.getValue() >= 1 && current.getValue() <= 15);
                if(current.getValue() == 5 || current.getValue() == 6 || current.getValue() == 11 ||
                        current.getValue() == 12) {
                    assertEquals((byte) 10, current.getDamage());
                } else assertEquals((byte) 5, current.getDamage());
            }
            //Checking Deception Cards.
            else if (current.getType().equals("De")) {
                assertTrue(current.getValue() >= 1 && current.getValue() <= 15);
                if(current.getValue() == 6 || current.getValue() == 7 || current.getValue() == 9 ||
                        current.getValue() == 10) {
                    assertEquals((byte) 10, current.getDamage());
                } else assertEquals((byte) 5, current.getDamage());
            }
            //Checking Merlin Cards
            else if (current.getType().equals("Me")) {
                assertNull(current.getValue());
                assertEquals((byte) 25, current.getDamage());
            }
            //Checking Apprentice Cards
            else if (current.getType().equals("Ap")) {
                assertNull(current.getValue());
                assertEquals((byte) 5, current.getDamage());
            } else {
                //Checking Alchemy Cards.
                assertEquals("Al", current.getType());
                assertTrue(current.getValue() >= 1 && current.getValue() <= 15);
                assertEquals((byte)5, current.getDamage());
            }
        }

        //Create the override Decks.
        Deck[] overrideDecks = new Deck[3];
        for(int i=0;i<3;i++) {
            overrideDecks[i] = new Deck();
            //Add Sword Cards to each Deck.
            overrideDecks[i].addCard(new Card("Sw", (byte)((i*4)+1)));
            overrideDecks[i].addCard(new Card("Sw", (byte)((i*4)+2)));
            overrideDecks[i].addCard(new Card("Sw", (byte)((i*4)+3)));
            overrideDecks[i].addCard(new Card("Sw", (byte)((i*4)+4)));

            //Add Arrow Cards to each Deck.
            overrideDecks[i].addCard(new Card("Ar", (byte)((i*4)+1)));
            overrideDecks[i].addCard(new Card("Ar", (byte)((i*4)+2)));
            overrideDecks[i].addCard(new Card("Ar", (byte)((i*4)+3)));
            overrideDecks[i].addCard(new Card("Ar", (byte)((i*4)+4)));

            //Add Sorcery Cards to each Deck.
            overrideDecks[i].addCard(new Card("So", (byte)((i*4)+1)));
            overrideDecks[i].addCard(new Card("So", (byte)((i*4)+2)));
            overrideDecks[i].addCard(new Card("So", (byte)((i*4)+3)));
            overrideDecks[i].addCard(new Card("So", (byte)((i*4)+4)));
        }

        int initialHP = 240;                                             //The initial health of each Player.
        game.setupGame(new Scanner(String.format("3\nJake\nCaroline\nAlex\n%d\n", initialHP)), new PrintWriter(output));

        /*Before overriding the Decks, check that each Player has been given 12 Cards*/
        for(int i=0;i<game.getNumPlayers();i++) assertEquals(12, game.playerAt(i).getNumCards());

        //Tests for the correct values having been set from setup.
        assertEquals(3, game.getNumPlayers());
        assertEquals("Jake", game.playerAt(0).getName());
        assertEquals("Caroline", game.playerAt(1).getName());
        assertEquals("Alex", game.playerAt(2).getName());
        assertEquals(initialHP, game.playerAt(0).getHP());
        assertEquals(initialHP, game.playerAt(1).getHP());
        assertEquals(initialHP, game.playerAt(2).getHP());

        //Tests for the queries and responses a part of the initial setup having been displayed.
        assertTrue(output.toString().contains("Please enter the number of players (3-5):\n"));
        assertTrue(output.toString().contains("3\n"));
        assertTrue(output.toString().contains("Please enter a name for Player 1:\n"));
        assertTrue(output.toString().contains("Jake\n"));
        assertTrue(output.toString().contains("Please enter a name for Player 2:\n"));
        assertTrue(output.toString().contains("Caroline\n"));
        assertTrue(output.toString().contains("Please enter a name for Player 3:\n"));
        assertTrue(output.toString().contains("Alex\n"));
        assertTrue(output.toString().contains("Please enter the number of health points:\n"));
        assertTrue(output.toString().contains("240\n"));

        //Build the input string that will cause each Player to play the first Card in their hand at every turn.
        StringBuilder inputString = new StringBuilder();
        int numMelees = 12;                                                 //The number of Melees to play per round
        for(int i=0;i<numMelees;i++) inputString.append("1\n1\n1\n");

        /*Create a copy of overrideDecks to check that the initial hand of each Player is displayed at the start
         * of a round.*/
        Deck[] overrideDecksCopy = new Deck[3];
        for(int i=0;i<3;i++) {
            overrideDecksCopy[i] = overrideDecks[i].deepCopy();
        }

        /*Play a round*/
        Melee[] summary = game.playRound(new Scanner(inputString.toString()), new PrintWriter(output), overrideDecks,
                numMelees);

        /*Check that the hand of each Player was displayed at the beginning of the round.*/
        for(int i=0;i<3;i++) assertTrue(output.toString().contains(String.format("Hand for Player %d-%s:\n%s", i+1,
                game.playerAt(i).getName(), overrideDecksCopy[i].toString())));

        /*Check that the sum of injury points inflicted upon the loser of a Melee and their identity
        are displayed after a Melee.*/
        assertTrue(output.toString().contains(String.format("Player %d-%s lost the Melee. The total injury points they " +
                "accumulated from this Melee is %d.\n", 1, game.playerAt(0).getName(), 20)));

        /*Check that the Cards Played in each Melee are added to the loser's injury Deck. Since Player 1 lost each Melee,
         * their injury Deck should contain all Cards played in all Melees.*/
        for(int i=0;i<3;i++) {
            assertTrue(game.playerAt(0).getInjuryDeck().getCards().contains(new Card("Sw", (byte)((i*4)+1))));
            assertTrue(game.playerAt(0).getInjuryDeck().getCards().contains(new Card("Sw", (byte)((i*4)+2))));
            assertTrue(game.playerAt(0).getInjuryDeck().getCards().contains(new Card("Sw", (byte)((i*4)+3))));
            assertTrue(game.playerAt(0).getInjuryDeck().getCards().contains(new Card("Sw", (byte)((i*4)+4))));

            assertTrue(game.playerAt(0).getInjuryDeck().getCards().contains(new Card("Ar", (byte)((i*4)+1))));
            assertTrue(game.playerAt(0).getInjuryDeck().getCards().contains(new Card("Ar", (byte)((i*4)+2))));
            assertTrue(game.playerAt(0).getInjuryDeck().getCards().contains(new Card("Ar", (byte)((i*4)+3))));
            assertTrue(game.playerAt(0).getInjuryDeck().getCards().contains(new Card("Ar", (byte)((i*4)+4))));

            assertTrue(game.playerAt(0).getInjuryDeck().getCards().contains(new Card("So", (byte)((i*4)+1))));
            assertTrue(game.playerAt(0).getInjuryDeck().getCards().contains(new Card("So", (byte)((i*4)+2))));
            assertTrue(game.playerAt(0).getInjuryDeck().getCards().contains(new Card("So", (byte)((i*4)+3))));
            assertTrue(game.playerAt(0).getInjuryDeck().getCards().contains(new Card("So", (byte)((i*4)+4))));
        }

        /*Check that the total injury points ine each Player's injury Deck were deducted at the end of the round.*/
        assertEquals(initialHP - game.playerAt(0).getInjuryDeck().getInjuryPoints(),
                game.playerAt(0).getHP());
        assertEquals(initialHP - game.playerAt(1).getInjuryDeck().getInjuryPoints(),
                game.playerAt(1).getHP());
        assertEquals(initialHP - game.playerAt(2).getInjuryDeck().getInjuryPoints(),
                game.playerAt(2).getHP());

        /*Check that the correct winners were chosen and displayed.*/
        assertTrue(output.toString().contains("Winners: Caroline, Alex\n"));
    }
}