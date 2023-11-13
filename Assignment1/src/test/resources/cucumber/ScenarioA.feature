Feature: ScenarioA
  Scenario: ScenarioA
    Given we begin a game

    # Game setup
    And enter 3 for the number of players
    And enter "P1" for the name of the first player
    And enter "P2" for the name of the second player
    And enter "P3" for the name of the third player
    And set initial health points at 150

    #Round 1------------------------------------------------------------------------------------------------------------
    When we begin the first round and distribute 12 cards to each player in Scenario "A"
    #-------Melee 1 (Melee with no loser)-------------------------------------------------------------------------------
    And "P1" plays "Ar1"
    And "P2" plays "Me" and assigns 1 to it
    And "P3" plays "Me" and assigns 1 to it
    And Melee 1 has no loser
    #-------Melee 2-----------------------------------------------------------------------------------------------------
    And "P1" plays "Ar2"
    And "P2" plays "Ar3"
    And "P3" plays "Ap" and assigns 5 to it
    And "P1" is the loser and accumulates 15 injury points from Melee 2
    #-------Melee 3 (Melee with a non-leader playing a legal Alchemy Card)----------------------------------------------
    And "P1" plays "Ar5"
    And "P2" plays "Ar4"
    And "P3" plays "Al4"
    And "P1" is the loser and accumulates 15 injury points from Melee 3
    #-------Melee 4-----------------------------------------------------------------------------------------------------
    And "P1" plays "Sw5"
    And "P2" plays "Sw2"
    And "P3" plays "Sw3"
    And "P2" is the loser and accumulates 15 injury points from Melee 4
    #-------Melee 5-----------------------------------------------------------------------------------------------------
    And "P2" plays "Sw15"
    And "P3" plays "Sw6"
    And "P1" plays "Sw8"
    And "P3" is the loser and accumulates 25 injury points from Melee 5
    #-------Melee 6-----------------------------------------------------------------------------------------------------
    And "P3" plays "Sw7"
    And "P1" plays "Sw9"
    And "P2" plays "Sw1"
    And "P2" is the loser and accumulates 25 injury points from Melee 6
    #-------Melee 7-----------------------------------------------------------------------------------------------------
    And "P2" plays "Sw12"
    And "P3" plays "Sw11"
    And "P1" plays "Sw10"
    And "P1" is the loser and accumulates 15 injury points from Melee 7
    #-------Melee 8-----------------------------------------------------------------------------------------------------
    And "P1" plays "Sw13"
    And "P2" plays "Sw4"
    And "P3" plays "Sw14"
    And "P2" is the loser and accumulates 15 injury points from Melee 8
    #-------Melee 9-----------------------------------------------------------------------------------------------------
    And "P2" plays "So2"
    And "P3" plays "So1"
    And "P1" plays "So3"
    And "P3" is the loser and accumulates 15 injury points from Melee 9
    #-------Melee 10----------------------------------------------------------------------------------------------------
    And "P3" plays "So6"
    And "P1" plays "So5"
    And "P2" plays "So4"
    And "P2" is the loser and accumulates 25 injury points from Melee 10
    #-------Melee 11----------------------------------------------------------------------------------------------------
    And "P2" plays "So9"
    And "P3" plays "So8"
    And "P1" plays "So7"
    And "P1" is the loser and accumulates 15 injury points from Melee 11
    #-------Melee 12----------------------------------------------------------------------------------------------------
    And "P1" plays "So15"
    And "P2" plays "So12"
    And "P3" plays "So14"
    And "P2" is the loser and accumulates 20 injury points from Melee 12

    And round 1 ends
    #Round 2------------------------------------------------------------------------------------------------------------
    And we begin the second round and distribute 12 cards to each player
    #-------Melee 1-----------------------------------------------------------------------------------------------------
    And "P2" plays "Sw2"
    And "P3" plays "Sw3"
    And "P1" plays "Sw1"
    And "P1" is the loser and accumulates 15 injury points from Melee 1
    #-------Melee 2-----------------------------------------------------------------------------------------------------
    And "P1" plays "Sw6"
    And "P2" plays "Sw5"
    And "P3" plays "Sw4"
    And "P3" is the loser and accumulates 20 injury points from Melee 2
    #-------Melee 3-----------------------------------------------------------------------------------------------------
    And "P3" plays "Sw9"
    And "P1" plays "Sw8"
    And "P2" plays "Sw7"
    And "P2" is the loser and accumulates 30 injury points from Melee 3
    #-------Melee 4-----------------------------------------------------------------------------------------------------
    And "P2" plays "Sw11"
    And "P3" plays "Sw12"
    And "P1" plays "Sw10"
    And "P1" is the loser and accumulates 15 injury points from Melee 4
    #-------Melee 5-----------------------------------------------------------------------------------------------------
    And "P1" plays "Sw15"
    And "P2" plays "Sw14"
    And "P3" plays "Sw13"
    And "P3" is the loser and accumulates 15 injury points from Melee 5
    #-------Melee 6-----------------------------------------------------------------------------------------------------
    And "P3" plays "Ar1"
    And "P1" plays "Ar3"
    And "P2" plays "Ar2"
    And "P3" is the loser and accumulates 15 injury points from Melee 6
    #-------Melee 7-----------------------------------------------------------------------------------------------------
    And "P3" plays "Ar6"
    And "P1" plays "Ar4"
    And "P2" plays "Ar5"
    And "P1" is the loser and accumulates 15 injury points from Melee 7
    #-------Melee 8-----------------------------------------------------------------------------------------------------
    And "P1" plays "Ar9"
    And "P2" plays "Ar7"
    And "P3" plays "Ar8"
    And "P2" is the loser and accumulates 25 injury points from Melee 8
    #-------Melee 9-----------------------------------------------------------------------------------------------------
    And "P2" plays "Ar11"
    And "P3" plays "Ar12"
    And "P1" plays "Ar10"
    And "P1" is the loser and accumulates 25 injury points from Melee 9
    #-------Melee 10----------------------------------------------------------------------------------------------------
    And "P1" plays "Ar15"
    And "P2" plays "Ar14"
    And "P3" plays "Ar13"
    And "P3" is the loser and accumulates 15 injury points from Melee 10
    #-------Melee 11----------------------------------------------------------------------------------------------------
    And "P3" plays "So11"
    And "P1" plays "So7"
    And "P2" plays "So12"
    And "P1" is the loser and accumulates 25 injury points from Melee 11
    #-------Melee 12----------------------------------------------------------------------------------------------------
    And "P1" plays "De10"
    And "P2" plays "De9"
    And "P3" plays "De1"
    And "P3" is the loser and accumulates 25 injury points from Melee 12

    And round 2 ends

    #End of Game
    Then the Game ends after round 2
    And the Game has no winner