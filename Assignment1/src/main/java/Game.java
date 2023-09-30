import java.io.PrintWriter;
import java.util.Scanner;

public class Game {
    private byte numPlayers;

    public Game() {
        numPlayers = 0;
    }

    public void setNumPlayers(Scanner input, PrintWriter output) {
        byte result;
        System.out.println("Please enter the number of players (3-5): ");
        result = input.nextByte();
        System.out.println(result);
        numPlayers =  result;
    }

    public byte getNumPlayers() {
        return numPlayers;
    }
}
