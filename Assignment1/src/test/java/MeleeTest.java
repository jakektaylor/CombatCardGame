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
}