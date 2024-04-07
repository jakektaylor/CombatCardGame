# CombatCardGame
<ins>Summary of Game Responsibilities</ins>
  1. The game is to be played in a single window using a text-based interface.
  2. The card game must be played with between 3 and 5 players. The number of players is queried.
  3. Each player is queried for their (non-empty) name. The order in which players enter their names is the same as the order in which Players will play their cards.
  4. The initial number of (non-negative) health points is queried.  **The game ends when at least 1 player reaches 0 health points.**
  5. - The deck consists of 80 weapon Cards.
       - Basic Weapon Card Types (15 of each, numbered 1-15)
         * Swords(Sw)- 6, 7, 8, 9 are poisoned
         * Arrows(Ar)- 8, 9, 10, 11 are poisoned
         * Sorcery(So)- 5, 6, 11, 12 are poisoned      
         * Deception(De)- 6, 7, 9, 10 are poisoned
         * Damage- Poisoned basic weapon cards inflict 10 injury points while all others inflict 5 injury points
       - Alchemy (Al): (15 of each, numbered 1-15)
         * Damage-Inflicts 5 injury points
       - Merlin Cards (Me) and Apprentice Cards (Ap): There are 3 Me and 2 Ap Cards in the Deck
         * When a player plays a Merlin or Apprentice card, the player must immediately specify the value (between 1-15) and possibly which suit (Sw, Ar, So or De) this card   will take (SEE BELOW)
         * Damage: A Merlin card inflicts 25 injury points, an Apprentice card inflicts 5 injury points. 
  6. One round of combat consists of 12 melees (unless a player reaches 0 health points during a Melee via **SHAMING**)
  7.  - Beginning of a round of combat:
        - All 80 cards are shuffled
        - Each player receives 12 cards for this round
        - The first player to enter their name above starts the first round, the second player to enter their name above starts the second round and so on...
        - Each player has an empty injury Deck that accumultes cards when they lose a Melee. 

  8. Melees:
      * Leader: 
        - If it is the first Melee of the round, then it is the leader of the round as defined above (7).
        Else, it is the loser of the previous Melee of the round.
        - Plays a Basic Weapon, Merlin or Apprentice card if they have one. For a Merlin or Apprentice Card, a suit (Sw, Ar, So or De) and value (1-15) must be provided for the card.
        - The suit of the card the leader plays becomes the suit of the Melee
        - The leader can only start with an Alchemy card if they have no other card types in their hand. If this is the case, then for this Melee all of the following players can play any card in their hand since there is no suit for the Melee.
      * Remaining players:
        - In the order defined at the beginning of the Game, they must play a card of the same suit as the Melee, a Merlin card or an Apprentice card **if they have one of these cards in their hand**.
          * Merlin and Apprentice cards can be played regardless of the suit of the Melee, however a value must be specified for them (between 1-15)
        - Else if they do not have one of the above cards in their hand, then they must play an Alchemy card if they have one. 
          * The played alchemy card will act as though it is of the same suit as the Melee.
        - Else if the player does not have a basic weapon card of the same suit as the Melee, a Merlin card, an Apprentice card or an Alchemy card: 
          * **SHAMING**: The player chooses a card from their hand to discard and immediately loses 5 injury points. 
            * This player cannot lose the current Melee but may immediately reach 0 health points and end the game (jump to (10)).
      * Determining the Loser of a Melee:
        - Once all players have played a card, ignore all cards with the same value
        - **Among the non-ignored cards, the player who played the lowest valued card is the loser**
        - All cards played during the melee, including those ignored above but not including the ones discarded due to **SHAMING** are added to the loser's injury deck for the round. (The injury points from the cards in the injury deck are only applied at the end of the round) 
  9. At the end of a round of combat:
      * Each player adds up the injury points of all cards in their injury deck. This sum of injury points is then deducted from the player's health points. 
      * If one or more players end up with <= 0 health points, the Game is over and we proceed to (10).
      * Else, we return to (7).
  10. End of Game:
        * The winner is the player with the most health points. There may be multiple winners or no winner.
        * The process terminates.






      



