Feature: ScenarioB
  Scenario: ScenarioB
    Given we begin a game

    # Game setup
    And enter 3 for the number of players
    And enter "P1" for the name of the first player
    And enter "P2" for the name of the second player
    And enter "P3" for the name of the third player
    And set initial health points at 5

    #Round 1------------------------------------------------------------------------------------------------------------
    When we begin the first round and distribute 12 cards to each player in Scenario "B"
    #-------Melee 1-----------------------------------------------------------------------------------------------------
    And "P1" plays "Ar1"
    And "P2" plays "Ar2"
    And "P3" discards "Sw1" and immediately suffers 5 injury points due to shaming

    #Game ends----------------------------------------------------------------------------------------------------------
    Then the Game ends before the round ends
    And  "P3" has 0 HP
    And  "P1" has 5 HP
    And  "P2" has 5 HP
    And  "P1" and "P2" are the winners

