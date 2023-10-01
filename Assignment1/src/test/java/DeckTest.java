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
}