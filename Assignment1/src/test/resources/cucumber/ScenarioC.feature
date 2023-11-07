Feature: ScenarioC
  Scenario: ScenarioC
    Given we begin a game

    # Game setup
    And enter 3 for the number of players
    And enter "P1" for the name of the first player
    And enter "P2" for the name of the second player
    And enter "P3" for the name of the third player
    And set initial health points at 80

    #Round 1------------------------------------------------------------------------------------------------------------
    When we begin the first round and distribute 12 cards to each player in Scenario "C"
    #-------Melee 1-----------------------------------------------------------------------------------------------------
    And "P1" plays "Ar1"
    And "P2" plays "Ar2"
    And "P3" plays "Ar3"
    And "P1" is the loser and accumulates 15 injury points from Melee 1
    #-------Melee 2-----------------------------------------------------------------------------------------------------
    And "P1" plays "Me" and gives it the suit "Sw" and assigns the value 1 to it
    And "P2" plays "Sw2"
    And "P3" plays "Sw3"
    And "P1" is the loser and accumulates 35 injury points from Melee 2
    #-------Melee 3-----------------------------------------------------------------------------------------------------
    And "P1" plays "Sw5"
    And "P2" plays "Sw10"
    And "P3" plays "Sw4"
    And "P3" is the loser and accumulates 15 injury points from Melee 3
    #-------Melee 4-----------------------------------------------------------------------------------------------------
    And "P3" plays "Sw13"
    And "P1" plays "Sw12"
    And "P2" plays "Sw11"
    And "P2" is the loser and accumulates 15 injury points from Melee 4
    #-------Melee 5-----------------------------------------------------------------------------------------------------
    And "P2" plays "So3"
    And "P3" plays "So2"
    And "P1" plays "So1"
    And "P1" is the loser and accumulates 15 injury points from Melee 5
    #-------Melee 6-----------------------------------------------------------------------------------------------------
    And "P1" plays "So7"
    And "P2" plays "So8"
    And "P3" plays "So4"
    And "P3" is the loser and accumulates 15 injury points from Melee 6
    #-------Melee 7-----------------------------------------------------------------------------------------------------
    And "P3" plays "So9"
    And "P1" plays "So10"
    And "P2" plays "So13"
    And "P3" is the loser and accumulates 15 injury points from Melee 7
    #-------Melee 8-----------------------------------------------------------------------------------------------------
    And "P3" plays "So14"
    And "P1" plays "So15"
    And "P2" plays "Al15"
    And "P3" is the loser and accumulates 15 injury points from Melee 8
    #-------Melee 9-----------------------------------------------------------------------------------------------------
    And "P3" plays "De3"
    And "P1" plays "De2"
    And "P2" plays "De1"
    And "P2" is the loser and accumulates 15 injury points from Melee 9
    #-------Melee 10----------------------------------------------------------------------------------------------------
    And "P2" plays "De8"
    And "P3" plays "De5"
    And "P1" plays "De4"
    And "P1" is the loser and accumulates 15 injury points from Melee 10
    #-------Melee 11----------------------------------------------------------------------------------------------------
    And "P1" plays "De13"
    And "P2" plays "De11"
    And "P3" plays "De12"
    And "P2" is the loser and accumulates 15 injury points from Melee 11
    #-------Melee 12----------------------------------------------------------------------------------------------------
    And "P2" plays "Al1"
    And "P3" plays "De15"
    And "P1" plays "De14"
    And "P2" is the loser and accumulates 15 injury points from Melee 12

    And round 1 ends
    #Game over----------------------------------------------------------------------------------------------------------
    Then the Game ends after round 1
    And  "P2" and "P3" are the winners