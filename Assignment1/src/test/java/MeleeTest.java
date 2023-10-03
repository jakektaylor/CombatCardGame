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
}