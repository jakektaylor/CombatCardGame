import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MeleeTest {

    @Test
    @DisplayName("U-TEST 023: Testing valid user input for the suit when the first Card played is a Merlin or Apprentice" +
            "Card.")
    void testValInput() {
        Melee melee = new Melee((byte) 3);
        StringWriter output = new StringWriter();
        melee.playCard((byte) 0, new Card("Me", null), new Scanner("\nSw\n9\n"), new PrintWriter(output));
        assertEquals("Sw", melee.getSuit());
        assertEquals((byte)9, melee.getPlayed()[0].getValue());
    }

    @Test
    @DisplayName("U-TEST 024: Testing invalid user input for the suit when the first Card played is a Merlin or Apprentice" +
            "Card.")
    void testInvInput() {
        Melee melee = new Melee((byte) 3);
        StringWriter output = new StringWriter();
        melee.playCard((byte) 0, new Card("Ap", null), new Scanner("\nPa\nSo\n2\n"), new PrintWriter(output));
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
        melee.playCard((byte) 0, new Card("Me", null), new Scanner("\nAr\n-8\n3\n"), new PrintWriter(output));
        assertTrue(output.toString().contains("ERROR: Please enter a value between 1 and 15."));

        //Testing a value that is too large.
        melee = new Melee((byte) 3);
        output = new StringWriter();
        melee.playCard((byte) 0, new Card("Ap", null), new Scanner("\nSo\n50\n14\n"), new PrintWriter(output));
        assertTrue(output.toString().contains("ERROR: Please enter a value between 1 and 15."));
    }

    @Test
    @DisplayName("U-TEST 026: Testing that if the suit for the melee has been set, if a Player tries to play a basic weapon" +
            "Card of a different suit, an error message will be displayed and the Card is not played.")
    void testInvBasic() {
        Melee melee = new Melee((byte) (3));
        StringWriter output = new StringWriter();
        //Set the suit for the Melee to "De".
        melee.playCard((byte) 0, new Card("De", (byte) 12), new Scanner(""), new PrintWriter(output));

        //Try to play Cards of a different suit after.
        melee.playCard((byte) 1, new Card("Sw", (byte) 8), new Scanner(""), new PrintWriter(output));
        melee.playCard((byte) 2, new Card("Ar", (byte) 13), new Scanner(""), new PrintWriter(output));

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
        melee.playCard((byte) 0, new Card("Sw", (byte) 10), new Scanner(""), new PrintWriter(output));

        //Play a Merlin card after the suit has been set.
        melee.playCard((byte) 1, new Card("Me", null), new Scanner("\n2\n"), new PrintWriter(output));
        assertTrue(output.toString().contains("Please enter a value for the Merlin card: "));
        assertFalse(output.toString().contains("Please enter a suit for the Merlin card: "));
        assertTrue(output.toString().contains("\n2"));

        //Play an Apprentice card after the suit has been set.
        output = new StringWriter();
        melee.playCard((byte) 2, new Card("Ap", null), new Scanner("\n8\n"), new PrintWriter(output));
        assertTrue(output.toString().contains("Please enter a value for the Apprentice card: "));
        assertFalse(output.toString().contains("Please enter a suit for the Apprentice card: "));
        assertTrue(output.toString().contains("\n8"));
    }
}