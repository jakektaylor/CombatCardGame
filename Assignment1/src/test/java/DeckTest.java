import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {
    @Test
    @DisplayName("U-TEST 010: Testing the correct number of each type of card is present and that all cards are " +
            "correctly formatted after creating the entire deck.")
    void testCorrectNumCards() {
        Deck deck = Deck.createGameDeck();
        assertEquals(80, deck.getNumCards());
        assertEquals(15, deck.getNumSw());
        assertEquals(15, deck.getNumAr());
        assertEquals(15, deck.getNumSo());
        assertEquals(15, deck.getNumDe());
        assertEquals(15, deck.getNumAl());
        assertEquals(3, deck.getNumMe());
        assertEquals(2, deck.getNumAp());
        //Check that all cards are correctly formatted.
        ArrayList<Card> cards = deck.getCards();
        Set<String> types = new HashSet<>(Arrays.asList("Sw", "Ar", "So", "De", "Al", "Me", "Ap"));
        for(Card card: cards) {
            assertTrue(types.contains(card.getType()));                     //Check for valid type.
            if(card.getType().equals("Sw") || card.getType().equals("Ar") || card.getType().equals("So") ||
                    card.getType().equals("De") || card.getType().equals("Al")) {
                assertTrue(card.getValue() >= 1 && card.getValue() <= 15);
            } else assertNull(card.getValue());
        }
    }

    @Test
    @DisplayName("U-TEST 011: Test that non-poisoned Basic Weapon cards and Alchemy cards and Merlin cards and Apprentice" +
            "cards have the correct damage value.")
    void testNonPoison() {
        Deck deck = Deck.createGameDeck();
        ArrayList<Card> cards = deck.getCards();
        for(int i=0;i<cards.size();i++) {
            String type = cards.get(i).getType();
            Byte value = cards.get(i).getValue();
            Byte damage = cards.get(i).getDamage();
            if(type.equals("Sw") && (value < 6 || value > 9)) assertEquals((byte)5, damage);
            else if (type.equals("Ar") && (value < 8 || value > 11)) assertEquals((byte) 5, damage);
            else if (type.equals("So") && value != 5 && value != 6 && value != 11 && value != 12) assertEquals((byte)5, damage);
            else if (type.equals("De") && value != 6 && value != 7 && value != 9 && value != 10) assertEquals((byte) 5, damage);
            else if(type.equals("Al")) assertEquals((byte)5, damage);
            else if (type.equals("Me")) assertEquals((byte)25, damage);
            else if (type.equals("Ap")) assertEquals((byte) 5, damage);
        }
    }

    @Test
    @DisplayName("U-TEST 012: Testing that poisoned basic weapon cards have the correct damage value.")
    void testPoison() {
        Deck deck = Deck.createGameDeck();
        ArrayList<Card> cards = deck.getCards();
        for(Card card:cards) {
            String type = card.getType();
            Byte value = card.getValue();
            Byte damage = card.getDamage();
            if(type.equals("Sw") && !(value < 6 || value > 9)) assertEquals((byte)10, damage);
            else if (type.equals("Ar") && !(value < 8 || value > 11)) assertEquals((byte) 10, damage);
            else if (type.equals("So") && (value == 5 || value == 6 || value == 11 || value == 12)) assertEquals((byte)10, damage);
            else if (type.equals("De") && (value == 6 || value == 7 || value == 9 || value == 10)) assertEquals((byte) 10, damage);
        }
    }

}