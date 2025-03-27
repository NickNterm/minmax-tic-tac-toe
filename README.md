### MIN|MAX Game

This project is a min max implementation of a game like tic tac toe and SOS

You are MIN(O) and the computer is MAX(X). The game is simple. You type your x y position on your turn to place your symbol on the board. Don't forget, you win ONLY if you write `OXO` and computer wins when he writes `XOX`

Implementing MINMAX Algorithm I thought that this might be unwinnable for the player. So to help you out there is clue for you to make a better move. It might help you!!

# Example

```
-------------------- Score 0:0 (MAX : MIN) --------------------
|-|O|-|         Turn: MAX
|-|-|-|
|X|-|-|
|-|-|-|
Calculating best move for MAX!
```

```
-------------------- Score 0:0 (MAX : MIN) --------------------
|-|-|X|         Turn: MIN
|-|-|-|         Suggest Move is: 0 0
|O|-|-|
|-|-|X|
Enter the x and y coordinates of the cell you want to pick:
```

```
-------------------- Score 1:0 (MAX : MIN) --------------------
|X|-|X|         Turn: MIN
|-|O|-|
|O|-|X|
|-|-|-|
MAX wins
```
