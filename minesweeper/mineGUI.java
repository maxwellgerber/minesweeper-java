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
    mineGUI(String title, Board b) {
        super(title, true);
        _b = b;
        _disp = new Display(b);
        _randomSource = new Random();

        // addMenuButton("Game->New Game", "newGame");
        // addMenuButton("Game->Undo", "undo");
        addMenuButton("Game->Quit", "quit");

        _disp = new Display(b);
        add(_disp, new LayoutSpec("y", 2, "width", 2));
        _disp.setMouseHandler("click", this, "mouseClicked");
        display(true);
    }

    /** Respond to "Quit" button. */
    public void quit(String dummy) {
        if (showOptions("Really quit?", "Quit?", "question",
                "Yes", "Yes", "No") == 0) {
            System.exit(0);
        }
    }

    // /** Respond to "New Game" button. */
    // public void newGame(String dummy) {
    //     _b.clear();
    //     _disp.repaint();
    //     _playable = true;
    // }

    // /** Respond to "Undo"" button. */
    // public void undo(String dummy) {
    //     _b.retract();
    //     _disp.repaint();
    // }

    /** Responds to MouseEvent EVENT by alterint underlying board. */
    public void mouseClicked(MouseEvent event) {
        int x = event.getX() / (16), y = event.getY() / (16);
        System.out.println("X: " + x + " Y: " + y);
        _b.click(x, y);
        _disp.repaint();
    }

    /** The board widget. */
    private Display _disp;

    /** The game I am consulting. */
    private Board _b;

    private Random _randomSource;
}
