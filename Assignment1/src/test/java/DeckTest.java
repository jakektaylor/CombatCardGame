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

    @Test
    @DisplayName("U-TEST 041: Testing that for an empty Deck, the total injury points is 0.")
    void testInjuryEmpty() {
        Deck deck = new Deck();
        assertEquals(0, deck.getInjuryPoints());
    }

    @Test
    @DisplayName("U-TEST 042: Testing that for a Deck consisting of all Basic Weapon cards, the total" +
            " injury points is equal to 10 * (number of poisoned Cards) + 5 * (number of non-poisoned cards).")
    void testInjuryPoisoned() {
        Deck deck = new Deck();

        //Add poisoned Cards.
        deck.addCard(new Card("Sw", (byte) 6));
        deck.addCard(new Card("De", (byte) 9));

        //Add non-poisoned Cards.
        deck.addCard(new Card("So", (byte) 15));
        deck.addCard(new Card("Ar", (byte) 3));
        assertEquals(30, deck.getInjuryPoints());
    }

    @Test
    @DisplayName("U-TEST 043: Testing that for a Deck consisting of all Merlin cards, the total injury points is equal" +
            " to 25 * (the number of Cards).")
    void testInjuryMerlin() {
        Deck deck = new Deck();

        //Add Merlin cards.
        for(int i=0;i<3;i++) deck.addCard(new Card("Me", null));
        assertEquals(75, deck.getInjuryPoints());
    }

    @Test
    @DisplayName("U-TEST 044: Testing that for a Deck consisting of all Apprentice cards, the total injury points is" +
            " equal to 5 * (the number of Cards).")
    void testInjuryAp() {
        Deck deck = new Deck();

        //Add Apprentice cards.
        for(int i=0;i<2;i++) deck.addCard(new Card("Ap", null));
        assertEquals(10, deck.getInjuryPoints());
    }

    @Test
    @DisplayName("U-TEST 045: Testing that for a Deck consisting of all Alchemy cards, the total injury points is " +
            " equal to 5 * (the number of Cards).")
    void testInjuryAl() {
        Deck deck = new Deck();

        //Add Alchemy cards.
        for(int i=0;i<12;i++) deck.addCard(new Card("Al", (byte) (i+1)));
        assertEquals(60, deck.getInjuryPoints());
    }

    @Test
    @DisplayName("U-TEST 046: Testing that for a Deck consisting of many different types of Cards, the total injury" +
            " points is computed correctly.")
    void testInjury() {
        Deck deck = new Deck();

        //Add poisoned basic weapon Cards.
        deck.addCard(new Card("Sw", (byte) 8));
        deck.addCard(new Card("Ar", (byte) 11));
        deck.addCard(new Card("So", (byte) 12));
        deck.addCard(new Card("De", (byte) 10));

        //Add non-poisoned basic weapon Cards.
        deck.addCard(new Card("Sw", (byte) 3));
        deck.addCard(new Card("Ar", (byte) 13));
        deck.addCard(new Card("So", (byte) 7));
        deck.addCard(new Card("De", (byte) 12));

        //Add a Merlin card.
        deck.addCard(new Card("Me", null));

        //Add an Apprentice card.
        deck.addCard(new Card("Ap", null));

        //Add an Alchemy card.
        deck.addCard(new Card("Al", null));

        assertEquals(95, deck.getInjuryPoints());
    }
}