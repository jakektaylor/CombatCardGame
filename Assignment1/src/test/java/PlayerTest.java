import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    @DisplayName("U-TEST 059: Testing that a Player's HP cannot fall below 0.")
    void testMinZero() {
        Player p = new Player("");
        p.setHP(300);
        p.setHP(p.getHP() - 500);
        assertEquals(0, p.getHP());
    }
}