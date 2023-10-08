import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Test suite for running all of the Unit tests from Part 1.")
@SelectClasses({DeckTest.class, MeleeTest.class, GameTest.class, PlayerTest.class})
public class UnitTestSuite {
}
