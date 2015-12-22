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

        addMenuButton("Game->New Game", "newGame");
        // addMenuButton("Game->Undo", "undo");
        addMenuButton("Game->Quit", "quit");

        addMenuButton("AI->Make Move", "makeMove");
        addMenuButton("AI->Autocomplete", "Autocomplete");

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

    /** Respond to "New Game" button. */
    public void newGame(String dummy) {
        _b = _b.newGame();
        _disp.newGame(_b);
        _disp.repaint();
    }

    public void makeMove(String dummy) {
        autoplayer AI = new autoplayer(_b);
        int[] move = AI.nextMove("flag");
        if(move == null) {
            move = AI.nextMove("click");
            if(_b.isBomb(move[0], move[1])) {
                gameOver("YOU LOSE");
            } else {
                _b.click(move[0], move[1]);
                if(_b.isWon()) {
                    gameOver("YOU WIN!");
                }   
            }
        }
        _b.setFlag(move[0], move[1]);
        _disp.repaint();
    }

    public void Autocomplete(String dummy) {
        autoplayer AI = new autoplayer(_b, _disp);
        while(!_b.isWon()) {
            int[] move = AI.nextMove("flag");
            if(move == null) {
                move = AI.nextMove("click");
                if(_b.isBomb(move[0], move[1])) {
                    gameOver("YOU LOSE");
                    break;
                } else {
                    _b.click(move[0], move[1]);
                    if(_b.isWon()) {
                        gameOver("YOU WIN!");
                        break;
                    }   
                }
            }
            _b.setFlag(move[0], move[1]);
            _disp.repaint(1);
        }
    }

    /** Responds to MouseEvent EVENT by alterint underlying board. */
    public void mouseClicked(MouseEvent event) {
        int x = event.getX() / (16), y = event.getY() / (16);
        if(event.getButton() == 1) {
            if(_b.isBomb(x, y)) {
                gameOver("YOU LOSE");
            } else {
                _b.click(x, y);
                if(_b.isWon()) {
                    gameOver("YOU WIN!");
                }   
            }
        } else {
            _b.setFlag(x, y);
        }
        // System.out.println("X: " + x + " Y: " + y);
        // System.out.println(event.getButton());
        _disp.repaint();
    }

    public void gameOver(String s) {
            _b.endGame();
            _disp.repaint();
        if (showOptions(s, "", "question",
            "New Game", "New Game", "Quit") == 1) {
            System.exit(1);
        } else {
            newGame("dummy");
        }
    }

    /** The board widget. */
    private Display _disp;

    /** The game I am consulting. */
    private Board _b;

    private Random _randomSource;
}
