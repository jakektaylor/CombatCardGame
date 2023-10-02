import java.io.PrintWriter;
import java.util.Scanner;

public class Game {
    private byte numPlayers;
    private Player[] players;
    private Deck deck;

    public Game() {
        numPlayers = 0;
        this.deck = Deck.createGameDeck();
    }

    public void setupGame(Scanner input, PrintWriter output) {
        //Determine the number of players.
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
        input.nextLine();

        //Determine the names of the players.
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
        int HP = -1;
        while(HP < 0) {
            System.out.println("Please enter the number of health points: ");
            if(input.hasNextInt()) {
                HP = input.nextInt();
                System.out.println(HP);
            } else {
                input.nextLine();
            }
            if(HP < 0) {
                System.out.println("ERROR: Invalid number of health points.");
                output.println("ERROR: Invalid number of health points.");
            }
        }
        for (Player p : players) p.setHP(HP);
    }

    public byte getNumPlayers() {
        return numPlayers;
    }

    public Player playerAt(int i) {
        return players[i];
    }

    public void dealCards() {
        System.out.println(this.deck.getNumCards());
        for(int i=0;i<numPlayers;i++) {
            for(int j=0; j < 12;j++) players[i].dealCard(this.deck.getCards().get(i*12 + j));
        }

    }
}
