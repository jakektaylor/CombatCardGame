import java.util.Arrays;
import java.util.HashSet;

public class Card {
    //All the suits in the Game.
    public static final HashSet<String> SUITS = new HashSet<>(Arrays.asList("Sw", "Ar", "So", "De"));
    private String type;                                //One of {"Sw", "Ar", "So", "De", "Me", "Ap", "Al"}
    private Byte value;                       //The value of the Card (initially null for Merlin and Apprentice Cards)
    private Byte damage;                                //The injury points inflicted by the Card.
    public Card(String type, Byte value) {
        this.type = type;
        this.value = value;

        //Set the correct damage value.

        //Non-poisoned basic weapon Cards inflict 5 injury points.
        if(type.equals("Sw") && (value < 6 || value > 9)) damage =5;
        else if (type.equals("Ar") && (value < 8 || value > 11)) damage = 5;
        else if (type.equals("So") && value != 5 && value != 6 && value != 11 && value != 12) damage = 5;
        else if (type.equals("De") && value != 6 && value != 7 && value != 9 && value != 10) damage = 5;
        else if(type.equals("Al")) damage = 5;                              //Alchemy Cards inflict 5 injury points.
        else if (type.equals("Me")) damage = 25;                            //Merlin Cards inflict 25 injury points.
        else if (type.equals("Ap")) damage = 5;                             //Apprentice Cards inflict 5 injury points.

        //Poisoned basic weapon Cards inflict 10 injury points.
        else if(type.equals("Sw")) damage = 10;
        else if (type.equals("Ar")) damage = 10;
        else if (type.equals("So")) damage = 10;
        else if (type.equals("De")) damage= 10;
    }

    /*Defines the String representation of a given Card.*/
    @Override
    public String toString() {
        return String.format("{%2s,%4d}", type, value);
    }

    /*Purpose: Determines how two Cards are compared.
    * Parameters: obj-another Card object to which 'this' Card object is being compared.
    * Returns: true if the Cards have the same value and type, false otherwise.
    * */
    @Override
    public boolean equals(Object obj) {
        Card other = (Card) obj;
        //Check that both cards have the same type and value.
        if (!(this.value == null)) return this.value.equals(other.value) && this.type.equals(other.type);
        else return this.value == other.value && this.type.equals(other.type);
    }

    //GETTERS AND SETTERS
    public String getType() {
        return type;
    }

    public Byte getValue() {
        return value;
    }

    public Byte getDamage() {
        return damage;
    }
    public void setValue(Byte valChoice) {
        value = valChoice;
    }
}
