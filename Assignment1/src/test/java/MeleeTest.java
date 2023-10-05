import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MeleeTest {

    @Test
    @DisplayName("U-TEST 023: Testing valid user input for the suit and value when the first Card played is a " +
            "Merlin or Apprentice Card.")
    void testValInput() {
        Melee melee = new Melee((byte) 3);
        StringWriter output = new StringWriter();
        melee.playCard((byte) 0, null, new Card("Me", null), new Scanner("\nSw\n9\n"), new PrintWriter(output));
        assertEquals("Sw", melee.getSuit());
        assertEquals((byte)9, melee.getPlayed()[0].getValue());
    }

    @Test
    @DisplayName("U-TEST 024: Testing invalid user input for the suit when the first Card played is a Merlin or Apprentice" +
            "Card.")
    void testInvInput() {
        Melee melee = new Melee((byte) 3);
        StringWriter output = new StringWriter();
        melee.playCard((byte) 0, null, new Card("Ap", null), new Scanner("\nPa\nSo\n2\n"), new PrintWriter(output));
        //Check that an error message is displayed.
        assertTrue(output.toString().contains(String.format("ERROR: Please enter a valid suit from %s.", Card.SUITS)));
    }

    @Test
    @DisplayName("U-TEST 025: Testing invalid user input for the value when the first Card played is a Merlin or Apprentice" +
            "Card.")
    void testInvVal() {
        //Testing a value that is too small.
        Melee melee = new Melee((byte) 3);
        StringWriter output = new StringWriter();
        melee.playCard((byte) 0, null, new Card("Me", null), new Scanner("\nAr\n-8\n3\n"), new PrintWriter(output));
        assertTrue(output.toString().contains("ERROR: Please enter a value between 1 and 15."));

        //Testing a value that is too large.
        melee = new Melee((byte) 3);
        output = new StringWriter();
        melee.playCard((byte) 0, null, new Card("Ap", null), new Scanner("\nSo\n50\n14\n"), new PrintWriter(output));
        assertTrue(output.toString().contains("ERROR: Please enter a value between 1 and 15."));
    }

    @Test
    @DisplayName("U-TEST 026: Testing that if the suit for the melee has been set, if a Player tries to play a basic weapon" +
            "Card of a different suit, an error message will be displayed and the Card is not played.")
    void testInvBasic() {
        Melee melee = new Melee((byte) (3));
        StringWriter output = new StringWriter();
        //Set the suit for the Melee to "De".
        melee.playCard((byte) 0, null, new Card("De", (byte) 12), new Scanner(""), new PrintWriter(output));

        //Try to play Cards of a different suit after.
        melee.playCard((byte) 1, null, new Card("Sw", (byte) 8), new Scanner(""), new PrintWriter(output));
        melee.playCard((byte) 2, null, new Card("Ar", (byte) 13), new Scanner(""), new PrintWriter(output));

        //Check that the error message was printed twice.
        assertEquals(2, output.toString().split
                (String.format("ERROR: A basic weapon card can only be played if it is of suit %s.", melee.getSuit()),
                        -1).length - 1);

        //Check that the invalid Cards were not played.
        assertNull(melee.getPlayed()[1]);
        assertNull(melee.getPlayed()[2]);
    }

    @Test
    @DisplayName("U-TEST 027: Testing the user is prompted only to enter the value of a Merlin or Apprentice card when " +
            "played after the first Card has been played. Testing their response is displayed.")
    void testMerAfterFirst() {
        Melee melee = new Melee((byte) 3);
        StringWriter output = new StringWriter();

        //Set the suit for the melee to "Sw".
        melee.playCard((byte) 0, null, new Card("Sw", (byte) 10), new Scanner(""), new PrintWriter(output));

        //Play a Merlin card after the suit has been set.
        melee.playCard((byte) 1, null, new Card("Me", null), new Scanner("\n2\n"), new PrintWriter(output));
        assertTrue(output.toString().contains("Please enter a value for the Merlin card: "));
        assertFalse(output.toString().contains("Please enter a suit for the Merlin card: "));
        assertTrue(output.toString().contains("\n2"));

        //Play an Apprentice card after the suit has been set.
        output = new StringWriter();
        melee.playCard((byte) 2, null, new Card("Ap", null), new Scanner("\n8\n"), new PrintWriter(output));
        assertTrue(output.toString().contains("Please enter a value for the Apprentice card: "));
        assertFalse(output.toString().contains("Please enter a suit for the Apprentice card: "));
        assertTrue(output.toString().contains("\n8"));
    }

    @Test
    @DisplayName("U-TEST 028: Testing that invalid user input when entering the value of a Merlin or Apprentice Card " +
            "when played after the first Card has been played results in an error message and the Card is not played.")
    void testInvMer() {
        Melee melee = new Melee((byte) 3);
        StringWriter output = new StringWriter();

        //Set the suit for the melee to "So".
        melee.playCard((byte) 0, null, new Card("So", (byte) 10), new Scanner(""), new PrintWriter(output));

        //Play a Merlin card after the suit has been set and input a value that is too small.
        melee.playCard((byte) 1, null, new Card("Me", null), new Scanner("\n-20\n2\n"), new PrintWriter(output));
        assertTrue(output.toString().contains("ERROR: Please enter a value between 1 and 15."));

        //Play an Apprentice card after the suit has been set and input a value that is too large.
        output = new StringWriter();
        melee.playCard((byte) 2, null, new Card("Ap", null), new Scanner("\n100\n10\n"), new PrintWriter(output));
        assertTrue(output.toString().contains("ERROR: Please enter a value between 1 and 15."));

        //Check that the Merlin and Apprentice cards were not played with the invalid value but instead the valid ones.
        assertEquals(new Card("Me", (byte) 2), melee.getPlayed()[1]);
        assertEquals(new Card("Ap", (byte) 10), melee.getPlayed()[2]);
    }

    @Test
    @DisplayName("U-TEST 029: Testing that valid user input for the value of a Merlin or Apprentice Card after the first" +
            "Card has been played sets the value of the Card and causes it to be played.")
    void testPlayMerAfter() {
        Melee melee = new Melee((byte) 3);
        StringWriter output = new StringWriter();

        //Set the suit to "So".
        melee.playCard((byte)0, null, new Card("So", (byte) 9), new Scanner(""), new PrintWriter(output));

        //Play a Merlin card.
        melee.playCard((byte) 1, null, new Card("Me", null), new Scanner("\n7\n"), new PrintWriter(output));
        assertEquals("Me", melee.getPlayed()[1].getType());
        assertEquals((byte)7, melee.getPlayed()[1].getValue());

        //Play an Apprentice card.
        melee.playCard((byte) 2, null, new Card("Ap", null), new Scanner("\n5\n"), new PrintWriter(output));
        assertEquals("Ap", melee.getPlayed()[2].getType());
        assertEquals((byte) 5, melee.getPlayed()[2].getValue());
    }

    @Test
    @DisplayName("U-TEST 032: Testing valid user input when entering the number corresponding to the Card to be discarded " +
            "results in the Card being removed from the Player's hand, the Player losing 5 HP and the Card not being " +
            "played.")
    void testValDiscard() {
        Melee melee = new Melee((byte)1);

        Player player = new Player("Jake");
        player.setHP(300);
        player.getHand().addCard(new Card("Sw", (byte) 8));

        melee.discardCard(new Scanner("1\n"), new PrintWriter(new StringWriter()), (byte) 0, player);
        assertEquals(0, player.getHand().getNumCards());
        assertEquals(295, player.getHP());
        assertNull(melee.getPlayed()[0]);
    }

    @Test
    @DisplayName("U-TEST 033: Testing invalid user input when entering the number corresponding to the Card to be discarded " +
    "results in all Cards remaining in the Player's hand, the Player's HP remaining the same, the Card not being played and" +
            "an error message being displayed.")
    void testInvDiscard() {

        Melee melee = new Melee((byte) 1);
        StringWriter output = new StringWriter();

        Player player = new Player("Alex");
        player.setHP(300);
        player.getHand().addCard(new Card("Ar", (byte) 9));

        //Testing a value that is too large.
        melee.discardCard(new Scanner("10\n"), new PrintWriter(output), (byte) 0, player);
        assertEquals(1, player.getHand().getNumCards());
        assertEquals(300, player.getHP());
        assertNull(melee.getPlayed()[0]);
        assertTrue(output.toString().contains("ERROR: Please enter a value between 1 and 1."));

        //Testing a value that is too small.
        melee = new Melee((byte) 1);
        output = new StringWriter();
        melee.discardCard(new Scanner("0\n"), new PrintWriter(output), (byte) 0, player);
        assertEquals(1, player.getHand().getNumCards());
        assertEquals(300, player.getHP());
        assertNull(melee.getPlayed()[0]);
        assertTrue(output.toString().contains("ERROR: Please enter a value between 1 and 1."));
    }

    @Test
    @DisplayName("U-TEST 034: Testing that if the first Player has a Basic Weapon Card, they" +
            "cannot play an Alchemy card to start the Melee and an error message is displayed.")
    void testStartAl() {
        Melee melee = new Melee((byte) 3);
        Player player = new Player("Jake");
        Card alchemy = new Card("Al", (byte) 12);

        player.getHand().addCard(alchemy);
        player.getHand().addCard(new Card("Sw", (byte) 7));

        StringWriter output = new StringWriter();
        melee.playCard((byte) 0, player, alchemy, new Scanner(""), new PrintWriter(
                output));

        assertNull(melee.getPlayed()[0]);
        assertTrue(output.toString().contains("ERROR: Cannot play an Alchemy Card as the first Card when you have a " +
                "basic weapon card."));
    }

    @Test
    @DisplayName("U-TEST 035: Testing that if the first Player has a Merlin Card, they" +
            "cannot play an Alchemy card to start the Melee and an error message is displayed.")
    void testStartAl2() {
        Melee melee = new Melee((byte) 3);
        Player player = new Player("Jake");
        Card alchemy = new Card("Al", (byte) 12);

        player.getHand().addCard(alchemy);
        player.getHand().addCard(new Card("Me", null));

        StringWriter output = new StringWriter();
        melee.playCard((byte) 0, player, alchemy, new Scanner(""), new PrintWriter(
                output));

        assertNull(melee.getPlayed()[0]);
        assertTrue(output.toString().contains("ERROR: Cannot play an Alchemy Card as the first Card when you have a " +
                "Merlin card."));
    }

    @Test
    @DisplayName("U-TEST 036: Testing that if the first Player has an Apprentice Card, they" +
            "cannot play an Alchemy card to start the Melee and an error message is displayed.")
    void testStartAl3() {
        Melee melee = new Melee((byte) 3);
        Player player = new Player("Jake");
        Card alchemy = new Card("Al", (byte) 12);

        player.getHand().addCard(alchemy);
        player.getHand().addCard(new Card("Ap", null));

        StringWriter output = new StringWriter();
        melee.playCard((byte) 0, player, alchemy, new Scanner(""), new PrintWriter(
                output));

        assertNull(melee.getPlayed()[0]);
        assertTrue(output.toString().contains("ERROR: Cannot play an Alchemy Card as the first Card when you have an " +
                "Apprentice card."));
    }

    @Test
    @DisplayName("U-TEST 037: Testing that if the first Player plays an Alchemy Card, the following Players can play" +
            "a Basic Weapon Card of any suit.")
    void testAnyBasic() {
        Melee melee = new Melee((byte) 5);
        Player[] players = new Player[5];

        //Play the Alchemy Card.
        players[0] = new Player("");
        players[0].getHand().addCard(new Card("Al", (byte) 12));
        melee.playCard((byte) 0, players[0], players[0].getHand().getCards().get(0), new Scanner(""),
                new PrintWriter(new StringWriter()));

        //Play the Basic Weapon Cards.
        String[] suits = Card.SUITS.toArray(new String[Card.SUITS.size()]);
        for(int i=1;i<5;i++) {
            players[i] = new Player("");
            players[i].getHand().addCard(new Card(suits[i-1], (byte)i));
            melee.playCard((byte) i, players[i], players[i].getHand().getCards().get(0), new Scanner(""),
                    new PrintWriter(new StringWriter()));
        }

        //Check that all the correct cards were played.
        assertEquals(new Card("Al", (byte) 12), melee.getPlayed()[0]);
        for(int i=1;i<5;i++) {
            assertEquals(new Card(suits[i-1], (byte) i), melee.getPlayed()[i]);
        }
    }
}