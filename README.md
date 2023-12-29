# Sliding Tile Puzzle

This is a recreation of the sliding tile puzzle game, also known as "9 Number Puzzle" or "Fifteen". I have included a GUI version that uses Java Swing, as well as a terminal version. The GUI allows you to select a grid size from 3x3 all the way up to 10x10, and it tracks your move count and how long it took to solve the puzzle.

## Usage
To run the GUI:
- Clone the repo into your preferred IDE (I used Eclipse)
- Navigate to src/ui/GUI.java
- Run GUI.java and the window should appear!

Once the GUI is running:
- Click on a tile adjacent to the empty tile in order to move it
- Move the tiles to be in increasing order from left to right, then from top to bottom (i.e. [1,2,3],[4,5,6],[7,8,0], where 0 is the empty tile)
- When all tiles are in the correct position, the GUI will display the time it took you to solve the puzzle (starting from the first move and ending on the last move).
- You can adjust the grid size by clicking on the dropdown menu and selecting a number from 3 to 10, inclusive.
- To start a new game, click the New Game button.
- To exit the GUI, click the X in the top-right corner of the window.

To run the terminal version:
- Clone the repo into your preferred IDE (I used Eclipse)
- Navigate to src/model/SlidingTilePuzzle.java
- Run SlidingTilePuzzle.java and the game should start!

Once the game is running in the terminal:
- The program will prompt you to choose a row between 0 and 2, inclusive. Entering an invalid value will cause the prompt to reappear.
- Similarly, you will be prompted to choose a column between 0 and 2, inclusive. Entering an invalid value will cause the prompt to reappear.
- If the selected row and column is adjacent to the empty tile (i.e. the 0 in the 3x3 grid), then that tile will be moved. Otherwise, the grid will not change.
- Move the tiles to be in increasing order from left to right, then from top to bottom (i.e. [1,2,3],[4,5,6],[7,8,0], where 0 is the empty tile)
- When all tiles are in the correct position, the terminal will display "You solved the puzzle!" and exit the program.
- If you wish to exit the program before solving the puzzle, you can hit Ctrl + C in the terminal (In Eclipse, you will need to click the red square that says "Terminate").

## Screenshots
GUI Start: <br>
![UI](https://github.com/drew-hey/SlidingTilePuzzle/blob/main/SlidingTilePuzzle/screenshots/ui.png?raw=true)

Selecting the grid size: <br>
![Grid Size](https://github.com/drew-hey/SlidingTilePuzzle/blob/main/SlidingTilePuzzle/screenshots/grid-size.png?raw=true)

Solved 3x3 grid: <br>
![3x3 Solve](https://github.com/drew-hey/SlidingTilePuzzle/blob/main/SlidingTilePuzzle/screenshots/3x3-solve.png?raw=true)

Solved 4x4 grid: <br>
![4x4 Solve](https://github.com/drew-hey/SlidingTilePuzzle/blob/main/SlidingTilePuzzle/screenshots/4x4-solve.png?raw=true)
