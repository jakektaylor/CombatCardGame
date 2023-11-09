Feature: ScenarioE
  Scenario: ScenarioE
    Given we begin a game

    # Game setup
    And enter 4 for the number of players
    And enter "P1" for the name of the first player
    And enter "P2" for the name of the second player
    And enter "P3" for the name of the third player
    And enter "P4" for the name of the fourth player
    And set initial health points at 20

    #Round 1------------------------------------------------------------------------------------------------------------
    When we begin the first round and distribute 12 cards to each player in Scenario "E"
    #-------Melee 1-----------------------------------------------------------------------------------------------------
    And we begin Melee 1
    And "P1" plays the card "Ar15"
    And "P2" plays the card "Ar14"
    And "P3" discards the card "Sw1" and immediately suffers 5 injury points due to shaming
    And "P4" plays the card "Ar13"
    And "P4" is the loser
    #-------Melee 2-----------------------------------------------------------------------------------------------------
    And we begin Melee 2
    And "P4" plays the card "Ar11"
    And "P1" plays the card "Ar12"
    And "P2" plays the card "Me" and assigns the value 12 to it
    And "P3" discards the card "Sw2" and immediately suffers 5 injury points due to shaming
    And "P4" is the loser
    #-------Melee 3-----------------------------------------------------------------------------------------------------
    And we begin Melee 3
    And "P4" plays the card "Ar10"
    And "P1" plays the card "Ap" and assigns the value 10 to it
    And "P2" plays the card "Me" and assigns the value 10 to it
    And "P3" discards the card "Sw3" and immediately suffers 5 injury points due to shaming
    And "" is the loser
    #-------Melee 4-----------------------------------------------------------------------------------------------------
    And we begin Melee 4
    And "P4" plays the card "Ar7"
    And "P1" plays the card "Ar9"
    And "P2" plays the card "Ar8"
    And "P3" discards the card "Sw4" and immediately suffers 5 injury points due to shaming
    And "P4" is the loser
    #End of Game--------------------------------------------------------------------------------------------------------
    Then "P1" has 20 HP
    And "P1" has accumulated 0 injury points having lost the Melees "{}"

    And "P2" has 20 HP
    And "P2" has accumulated 0 injury points having lost the Melees "{}"

    And "P3" has 0 HP
    And "P3" has accumulated 20 injury points having lost the Melees "{}"

    And "P4" has 20 HP
    And "P4" has accumulated 80 injury points having lost the Melees "{1,2,4}"