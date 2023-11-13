Feature: Part1
  Scenario Outline: Part1
    Given we have started a game and are in the final Melee of a round
    When P1 plays the card "<P1>"
    And  P2 plays the card "<P2>"
    And  P3 plays the card "<P3>"
    And  P4 plays the card "<P4>"
    Then the loser is <Loser>
    And  <Loser> accumulates <InjuryPoints>.
    Examples:
    |P1          |P2          |P3          |P4          |Loser       |InjuryPoints|
    |Ar13        |Ar5         |Ar12        |Ar7         |P2          |20          |
    |Sw6         |Sw7         |Sw15        |Sw13        |P1          |30          |
    |So11        |So12        |So6         |So5         |P4          |40          |
    |De9         |De14        |De1         |De5         |P3          |25          |
    |Ar13        |Ar8         |Me7         |Ar14        |P3          |45          |
    |Ar13        |Ar8         |Me15        |Al14        |P2          |45          |
    |Ar13        |Ar8         |Ap7         |Ar14        |P3          |25          |
    |Ar13        |Ar8         |Ap15        |Ar14        |P2          |25          |
    |De13        |Me14        |Me14        |Me14        |P1          |80          |
    |De8         |Me14        |De9         |Ap10        |P1          |45          |
    |Sw10        |Sw1         |Sw2         |Me1         |P3          |40          |
    |Sw10        |Ap10        |Sw15        |Me10        |P3          |40          |
    |Sw10        |Sw1         |Al2         |Me2         |P2          |40          |
    |Al2         |De7         |Sw6         |Ar8         |P1          |35          |
    |Al6         |Me7         |Ap8         |So5         |P4          |45          |
    |Al12        |De7         |Sw6         |Ar8         |P3          |35          |
    |MeSw13      |Sw10        |Sw1         |Al2         |P3          |40          |
    |ApSw13      |Sw10        |Sw1         |Sw2         |P3          |20          |
    |MeSw13      |Sw10        |Al10        |Ap10        |P1          |40          |
    |ApSw13      |Sw10        |Al10        |Ap10        |P1          |20          |
    |MeDe13      |De7         |Me14        |De10        |P2          |70          |
    |MeDe13      |Ap7         |Me14        |De10        |P2          |65          |
    |Sw10        |Ap10        |Sw11        |Me11        |-           |0           |
    |Sw10        |Ap10        |Al10        |Me10        |-           |0           |