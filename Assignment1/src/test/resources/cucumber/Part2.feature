Feature: Robustness
  Scenario: Robustness
    Given we start a game

    # Game setup
    And enter an invalid number of players
    And enter 3 as the number of players
    And enter blank as name for the first player
    And enter "Fred" as name for the first player
    And enter blank as name for the second player
    And enter "Joe" as name for the second player
    And enter blank as name for the third player
    And enter "Paul" as name for the third player
    And Try to input initial health points as -10
    And set initial health points of players at 50

    #We begin the round
    When We begin round 1 and distribute 12 cards to each player
    #Melee 1------------------------------------------------------------------------------------------------------------
    And Fred tries to play an Alchemy when he has basic weapons and Me or Ap
    And Fred plays So11

    And Joe tries to play a Sword card when he has a Sorcery card
    And Joe tries to play a Deception card when he has a Sorcery card
    And Joe tries to play an Arrow card when he has a Sorcery card
    And Joe tries to play an Alchemy card when he has a Sorcery card
    And Joe plays So6

    And Paul plays So7

    And Joe is the loser with 25 injury points for this Melee

    #Melee 2------------------------------------------------------------------------------------------------------------
    And Joe starts with Ar8

    And Paul plays Me and tries to assign 16 to it
    And Paul assigns 9 to Me

    And Fred plays Ap and tries to assign 20 to it
    And Fred assigns 10 to Ap

    And Joe is the loser with 40 injury points for this Melee

    #Melee 3------------------------------------------------------------------------------------------------------------
    And Joe starts with Sw9

    And Paul tries to play an Alchemy when he has Sw7
    And Paul plays Sw7

    And Fred plays Sw3

    And Fred is the loser with 25 injury points for this Melee

    #Melee 4------------------------------------------------------------------------------------------------------------
    And Fred plays De9

    And Joe tries to play an Alchemy card when he has a Deception card
    And Joe plays De6

    #My implementation does not allow illegal shaming so no step for "Paul tries to shame but can play De1"
    And Paul plays De1

    And Paul is the loser with 25 points of injury for this melee

    #End of Scenario
    Then Fred has accumulated 25 injury points
    And  Joe has accumulated 65 injury points
    And  Paul has accumulated 25 injury points








