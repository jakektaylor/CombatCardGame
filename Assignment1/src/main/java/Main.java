import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Scanner;

public class Main {
    /*Allows the user to play the Game in the console.*/
    public static void main(String[] args) {
        Game game = new Game();
        game.play(new Scanner(System.in), new PrintWriter(new StringWriter()), null, null,
                null);
    }
}
