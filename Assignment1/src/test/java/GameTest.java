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
        game.setNumPlayers(new Scanner("3\n"), new PrintWriter(output));
        assertEquals(3, game.getNumPlayers());
    }
}