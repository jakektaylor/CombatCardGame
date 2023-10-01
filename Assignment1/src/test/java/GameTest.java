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

    @Test
    @DisplayName("U-TEST 002: Testing invalid user input when entering the number of players.")
    void testInvNumPlayers(){
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setNumPlayers(new Scanner("1\n"), new PrintWriter(output));
        assertTrue(output.toString().contains("ERROR: Invalid number of players."));

        game = new Game();
        output = new StringWriter();
        game.setNumPlayers(new Scanner("6\n"), new PrintWriter(output));
        assertTrue(output.toString().contains("ERROR: Invalid number of players."));
    }

    @Test
    @DisplayName("U-TEST 003: Testing that the user is forced to enter a valid number of players before proceeding.")
    void testPlayerInputLoop() {
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setNumPlayers(new Scanner("2\n8\n4\n"), new PrintWriter(output));
        assertEquals(4, game.getNumPlayers());
    }

    @Test
    @DisplayName("U-TEST 004: Testing valid user input when creating the players.")
    void testValPlayers() {
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setNumPlayers(new Scanner("3\n"), new PrintWriter(output));
        game.setupPlayers(new Scanner("Jake\nCaroline\nAlex\n300\n"), new PrintWriter(output));
        assertEquals("Jake", game.playerAt(0).getName());
        assertEquals("Caroline", game.playerAt(1).getName());
        assertEquals("Alex", game.playerAt(2).getName());
    }

    @Test
    @DisplayName("U-TEST 005: Testing invalid user input results in an error message when creating the Players.")
    void testInvPlayers(){
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setNumPlayers(new Scanner("3\n"), new PrintWriter(output));
        game.setupPlayers(new Scanner("\n\n\nJake\nCaroline\nAlex\n300\n"), new PrintWriter(output));
        //Test that the error message was printed 3 times.
        assertTrue(output.toString().split
                ("ERROR: Player name must be non-empty.", -1).length - 1 == 3);
    }

    @Test
    @DisplayName("U-TEST 006: Testing that the user is forced to enter valid Player names before proceeding.")
    void testNameInputLoop() {
        Game game = new Game();
        StringWriter output = new StringWriter();
        game.setNumPlayers(new Scanner("3\n"), new PrintWriter(output));
        game.setupPlayers(new Scanner("\n\nJake\n\nCaroline\n\nAlex\n300\n"), new PrintWriter(output));

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
        game.setNumPlayers(new Scanner("3\n"), new PrintWriter(output));
        game.setupPlayers(new Scanner("Jake\nCaroline\nAlex\n300\n"), new PrintWriter(output));

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
        game.setNumPlayers(new Scanner("3\n"), new PrintWriter(output));
        game.setupPlayers(new Scanner("Jake\nCaroline\nAlex\n-50\n300\n"), new PrintWriter(output));

        //Ensure the error message was printed and the correct values were set afterward.
        assertTrue(output.toString().contains("ERROR: Invalid number of health points."));
        assertEquals(300, game.playerAt(0).getHP());
        assertEquals(300, game.playerAt(1).getHP());
        assertEquals(300, game.playerAt(2).getHP());
    }

}