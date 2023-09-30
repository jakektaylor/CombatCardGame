import java.io.PrintWriter;
import java.util.Scanner;

public class Game {
    private byte numPlayers;

    public Game() {
        numPlayers = 0;
    }

    public void setNumPlayers(Scanner input, PrintWriter output) {
        byte result = -1;
        while (result < 3 || result > 5) {
            System.out.println("Please enter the number of players (3-5): ");
            result = input.nextByte();
            System.out.println(result);
            if (result < 3 || result > 5) {
                System.out.println("ERROR: Invalid number of players.");
                output.println("ERROR: Invalid number of players.");
            }
        }
        numPlayers =  result;
    }

    public byte getNumPlayers() {
        return numPlayers;
    }
}
