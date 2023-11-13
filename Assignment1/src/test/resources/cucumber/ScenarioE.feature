Feature: ScenarioE
  Scenario: ScenarioE
    Given we begin a game

    # Game setup
    And enter 4 for the number of players
    And enter "P1" for the name of the first player
    And enter "P2" for the name of the second player
    And enter "P3" for the name of the third player
    And enter "P4" for the name of the fourth player
    And set initial health points at 25

    #Round 1------------------------------------------------------------------------------------------------------------
    When we begin the first round and distribute 12 cards to each player in Scenario "E"
    #-------Melee 1-----------------------------------------------------------------------------------------------------
    And we begin Melee 1
    And "P1" plays the card "Ar15"
    And "P2" plays the card "Ar14"
    And "P3" discards the card "Sw1" and immediately suffers 5 injury points due to shaming
    And "P4" plays the card "Ar11"
    And "P4" is the loser
    #-------Melee 2-----------------------------------------------------------------------------------------------------
    And we begin Melee 2
    And "P4" plays the card "Sw14"
    And "P1" discards the card "Ar13" and immediately suffers 5 injury points due to shaming
    And "P2" plays the card "Me" and assigns the value 14 to it
    And "P3" plays the card "Sw2"
    And "P3" is the loser
    #-------Melee 3-----------------------------------------------------------------------------------------------------
    And we begin Melee 3
    And "P3" plays the card "Sw3"
    And "P4" discards the card "Ar10" and immediately suffers 5 injury points due to shaming
    And "P1" plays the card "Ap" and assigns the value 3 to it
    And "P2" plays the card "Me" and assigns the value 3 to it
    And "" is the loser
    #-------Melee 4-----------------------------------------------------------------------------------------------------
    And we begin Melee 4
    And "P3" plays the card "De10"
    And "P4" plays the card "De7"
    And "P1" plays the card "De1"
    And "P2" plays the card "De6"
    And "P1" is the loser
    #End of Scenario----------------------------------------------------------------------------------------------------
    Then "P1" has 20 HP
    And "P1" has accumulated 40 injury points having lost the Melees "{4}"

    And "P2" has 25 HP
    And "P2" has accumulated 0 injury points having lost the Melees "{}"

    And "P3" has 20 HP
    And "P3" has accumulated 40 injury points having lost the Melees "{2}"

    And "P4" has 20 HP
    And "P4" has accumulated 25 injury points having lost the Melees "{1}"