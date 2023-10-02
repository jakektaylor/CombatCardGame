import java.util.Arrays;
import java.util.HashSet;

public class Card {
    public static final HashSet<String> SUITS = new HashSet<>(Arrays.asList("Sw", "Ar", "So", "De"));
    private String type;
    private Byte value;
    public Card(String type, Byte value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public Byte getValue() {
        return value;
    }

    public Byte getDamage() {
        return -1;
    }
}
