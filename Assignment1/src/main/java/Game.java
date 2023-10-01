import java.io.PrintWriter;
import java.util.Scanner;

public class Game {
    private byte numPlayers;
    private Player[] players;

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
        players = new Player[numPlayers];
    }

    public void setupPlayers(Scanner input, PrintWriter output) {
        for(int i=0;i<numPlayers;i++) {
            String name = "";
            while(name.isEmpty()) {
                System.out.printf("Please enter a name for Player %d:\n", i+1);
                name = input.nextLine();
                System.out.println(name);
                if (name.isEmpty()) {
                    System.out.println("ERROR: Player name must be non-empty.");
                    output.println("ERROR: Player name must be non-empty.");
                }
            }
            players[i] = new Player(name);
        }

        //Get the number of health points.
        System.out.println("Please enter the number of health points: ");
        int HP = input.nextInt();
        System.out.println(HP);
        for(Player p:players) p.setHP(HP);
    }

    public byte getNumPlayers() {
        return numPlayers;
    }

    public Player playerAt(int i) {
        return players[i];
    }
}
