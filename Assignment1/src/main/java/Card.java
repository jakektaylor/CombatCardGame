import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Card {
    public static final HashSet<String> SUITS = new HashSet<>(Arrays.asList("Sw", "Ar", "So", "De"));
    private String type;
    private Byte value;
    private Byte damage;
    public Card(String type, Byte value) {
        this.type = type;
        this.value = value;

        //Set the correct damage value.
        if(type.equals("Sw") && (value < 6 || value > 9)) damage =5;
        else if (type.equals("Ar") && (value < 8 || value > 11)) damage = 5;
        else if (type.equals("So") && value != 5 && value != 6 && value != 11 && value != 12) damage = 5;
        else if (type.equals("De") && value != 6 && value != 7 && value != 9 && value != 10) damage = 5;
        else if(type.equals("Al")) damage = 5;
        else if (type.equals("Me")) damage = 25;
        else if (type.equals("Ap")) damage = 5;

        //Setting the damage for the poison cards.
        else if(type.equals("Sw")) damage = 10;
        else if (type.equals("Ar")) damage = 10;
        else if (type.equals("So")) damage = 10;
        else if (type.equals("De")) damage= 10;
    }

    public String getType() {
        return type;
    }

    public Byte getValue() {
        return value;
    }

    public Byte getDamage() {
        return damage;
    }
}
