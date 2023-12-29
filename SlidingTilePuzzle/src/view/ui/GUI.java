package view.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.SlidingTilePuzzle;

/**
 * Creates a GUI for the sliding tile puzzle using JFrame.
 * 
 * @author Drew Hey
 */
public class GUI extends JFrame implements ActionListener {

    /** Serial version UID constant. */
    private static final long serialVersionUID = 1L;
    /** Container for the GUI. */
    private Container c;
    /** Handles the game logic. */
    private SlidingTilePuzzle puzzle;
    /** Grid of buttons to click. */
    private JButton[][] buttonGrid;
    /** Button for starting a new game. */
    private JButton newGameButton;
    /** Label for the grid size. */
    private JLabel gridSizeLabel;
    /** Lets the user select a custom grid size. */
    private JComboBox<Integer> gridSizeBox;
    /** Panel in the center of the GUI. */
    private JPanel centerPanel;
    /** Provides instructions or tells the user that they have won. */
    private JLabel instructionsLabel;
    /** Tells the user how many moves they have used. */
    private JLabel moveCountLabel;
    /** Tracks the time when the puzzle was started. */
    private long startTime;
    
    /**
     * Constructs the GUI.
     */
    public GUI() {
        puzzle = new SlidingTilePuzzle();
        initializeGUI();
    }
    
    /**
     * Initializes the GUI.
     */
    public void initializeGUI() {
        //set up the window
        c = getContentPane();
        setTitle("Sliding Tile Puzzle");
        setLocation(100, 100);
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //set up the border layout
        c.setLayout(new BorderLayout());
        
        //set up north panel
        JPanel northPanel = new JPanel();
        instructionsLabel = new JLabel("Move all tiles into position to win!");
        northPanel.add(instructionsLabel);
        moveCountLabel = new JLabel("Moves: 0");
        northPanel.add(moveCountLabel);
        c.add(northPanel, BorderLayout.NORTH);
        
        //set up south panel
        JPanel southPanel = new JPanel();
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(this);
        gridSizeLabel = new JLabel(" Grid Size: ");
        gridSizeBox = new JComboBox<Integer>();
        for (int i = 3; i <= 10; i++) {
            gridSizeBox.addItem(Integer.valueOf(i));
        }
        southPanel.add(newGameButton);
        southPanel.add(gridSizeLabel);
        southPanel.add(gridSizeBox);
        c.add(southPanel, BorderLayout.SOUTH);
        
        //sets up the grid and all of its buttons
        setupCenterPanel();
        
        setVisible(true);
    }
    
    /**
     * Initializes or resets the center panel using the grid size specified
     * by the selected value in gridSizeBox.
     */
    public void setupCenterPanel() {
        //removes the centerPanel if it is already in the container
        if (centerPanel != null) {
            c.remove(centerPanel);
        }
        
        //creates the centerPanel with the specified grid size
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(puzzle.getRows(), puzzle.getCols()));
        int[][] tiles = puzzle.getGrid();
        buttonGrid = new JButton[puzzle.getRows()][puzzle.getCols()];
        //iterate through each tile of the puzzle
        for (int i = 0; i < puzzle.getRows(); i++) {
            for (int j = 0; j < puzzle.getCols(); j++) {
                //gets the value of each tile, then labels a button with that value
                int value = tiles[i][j];
                JButton button = new JButton(value == 0 ? null : "" + value);
                buttonGrid[i][j] = button;
                button.addActionListener(this);
                
                //disables the empty tile, enables all other tiles
                button.setEnabled(value != 0);
                
                centerPanel.add(button);
            }
        }
        //adds the centerPanel back to the container
        c.add(centerPanel, BorderLayout.CENTER);
    }
    
    /**
     * Updates the buttons in the grid to show that a tile has been moved.
     */
    public void updateGrid() {
        int[][] tiles = puzzle.getGrid();
        for (int i = 0; i < puzzle.getRows(); i++) {
            for (int j = 0; j < puzzle.getCols(); j++) {
                int value = tiles[i][j];
                JButton button = buttonGrid[i][j];
                button.setText(value == 0 ? null : "" + value);
                
                button.setEnabled(value != 0);
            }
        }
    }
    
    /**
     * Handles the logic for starting a new game or for moving a tile.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //checks if the new game button was pressed
        if (e.getSource() == newGameButton) {
            //creates a new puzzle with the specified size
            Integer size = (Integer)gridSizeBox.getSelectedItem();
            puzzle = new SlidingTilePuzzle(size.intValue(), size.intValue());
            //updates the labels on screen
            instructionsLabel.setText("Move all tiles into position to win!");
            moveCountLabel.setText("Moves: 0");
            //updates the center panel and the button grid
            setupCenterPanel();
            updateGrid();
        }
        //else, iterate through button grid and see if e.getSource == buttonGrid[i][j]
        //then attempt to move the piece, and update the grid
        else if (!puzzle.solved()) {
            //flag to break from the loop early if needed
            boolean found = false;
            for (int i = 0; !found && i < buttonGrid.length; i++) {
                for (int j = 0; !found && j < buttonGrid[i].length; j++) {
                    if (e.getSource() == buttonGrid[i][j]) {
                        found = true;
                        puzzle.moveTile(i, j);
                        
                        updateGrid();
                        moveCountLabel.setText("Moves: " + puzzle.moveCount());
                        if (puzzle.moveCount() == 1) {
                            startTime = System.currentTimeMillis();
                        }
                        if (puzzle.solved()) {
                            long endTime = System.currentTimeMillis();
                            long seconds = (endTime - startTime) / 1000;
                            long milliseconds = (endTime - startTime) % 1000;
                            instructionsLabel.setText("Congratulations, you solved the puzzle in " + 
                                                        seconds + "." + milliseconds + " seconds!");
                        } //end if (puzzle.solved())
                    } //end if (e.getSource() == buttonGrid[i][j])
                } //end for j
            } //end for i
        } //end if (!puzzle.solved())
    } //end method

    /**
     * Starts the program.
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        new GUI();
    }
}
