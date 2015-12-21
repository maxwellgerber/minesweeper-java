package minesweeper;

import ucb.gui.TopLevel;
import ucb.gui.LayoutSpec;

import java.awt.event.MouseEvent;

import java.util.Random;

/** A top-level GUI for a Minesweeper clone.
 *  @author Maxwell Gerber
 */

class mineGUI extends TopLevel{

    /** A new window with given TITLE and displaying B. */
    mineGUI(String title, Game b) {
        super(title, true);
        _b = b;
        _randomSource = new Random();

        addMenuButton("Game->New Game", "newGame");
        addMenuButton("Game->Undo", "undo");
        addMenuButton("Game->Quit", "quit");

        _disp = new Display(b);
        add(_display, new LayoutSpec("y", 2, "width", 2));
        _display.setMouseHandler("click", this, "mouseClicked");
    }

    /** Starts gameplay. */
    public void play() {
        _playable = true;

    }

    /** Returns the board this contains. */
    public Board getBoard() {
        return _b;
    }

    /** Respond to "Quit" button. */
    public void quit(String dummy) {
        if (showOptions("Really quit?", "Quit?", "question",
                "Yes", "Yes", "No") == 0) {
            System.exit(0);
        }
    }

    /** Respond to "New Game" button. */
    public void newGame(String dummy) {
        _b.clear();
        _display.repaint();
        _playable = true;
    }

    /** Respond to "Undo"" button. */
    public void undo(String dummy) {
        _b.retract();
        _display.repaint();
    }

    /** Responds to MouseEvent EVENT by alterint underlying board. */
    public void mouseClicked(MouseEvent event) {
        int x = event.getX() / (8 * 9 + 3), y = event.getY() / (8 * 9 + 3);
    }

    /** The board widget. */
    private final Display _disp;

    /** The game I am consulting. */
    private Board _b;
}
