package minesweeper;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;

import java.util.Random;

/** A top-level GUI for a Minesweeper clone.
 *  @author Maxwell Gerber
 */

class mineGUI extends JPanel{

    /** A new window with given TITLE and displaying B. */
    mineGUI(Board b) {
        super();
        _b = b;
        _disp = new Display(b);
        _randomSource = new Random();

        _disp = new Display(b);        

        hover = new Timer(40, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt) {
                Point now = getMousePosition(false);
                if(now != null){
                    _disp.hoverEffect(now.getX(), now.getY());
                    repaint();
                }
            }
        });

        repainter = new Timer(300, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt) {
                makeMove();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mouseClickedResponse(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                hover.start();
            }
            @Override
            public void mouseExited(MouseEvent e) {
                hover.stop();
                _disp.hoverEffect(-16, -16);
                repaint();
            }
        });
    }

    public void paintComponent(Graphics g) {
        _disp.paintComponent((Graphics2D) g);
    }

    public JMenuBar createMenuBar() {
        final JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;
 
        //Create the menu bar.
        menuBar = new JMenuBar();
 
        //Build the first menu.
        menu = new JMenu("Game");
        menuBar.add(menu);
 
        menu.addSeparator();
        ButtonGroup group = new ButtonGroup();
        rbMenuItem = new JRadioButtonMenuItem("Beginner");
        rbMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
              _b = new Board(9,9,10);
              _disp.newGame(_b);
              repaint();
              menuBar.getParent().getParent().getParent().setSize(145,193);
            }
        });
        group.add(rbMenuItem);
        menu.add(rbMenuItem);
        rbMenuItem = new JRadioButtonMenuItem("Intermediate");
        rbMenuItem.setSelected(true);
        rbMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
              _b = new Board(16,16,40);
              _disp.newGame(_b);
              repaint();
              menuBar.getParent().getParent().getParent().setSize(258,304);
            }
        });
        group.add(rbMenuItem);
        menu.add(rbMenuItem);
        rbMenuItem = new JRadioButtonMenuItem("Expert");
        rbMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
              _b = new Board(16,30,99);
              _disp.newGame(_b);
              repaint();
              menuBar.getParent().getParent().getParent().setSize(481,304);
            }
        });
        group.add(rbMenuItem);
        menu.add(rbMenuItem);
        // rbMenuItem = new JRadioButtonMenuItem("Custom");
        // group.add(rbMenuItem);
        // menu.add(rbMenuItem);
        menu.addSeparator();
        menuItem = new JMenuItem("New Game");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                newGame();
            }
        });
        menu.add(menuItem);
        menu.addSeparator();
        menuItem = new JMenuItem("Quit");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                quit();
            }
        });
        menu.add(menuItem);

        menu = new JMenu("AI");
        menuBar.add(menu);
        menuItem = new JMenuItem("Make Move");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                makeMove();
            }
        });
        menu.add(menuItem);
        menuItem = new JMenuItem("Autocomplete");
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Autocomplete();
            }
        });
        menu.add(menuItem);

        return menuBar;
    }
 

    /** Respond to "Quit" button. */
    public void quit() {
        System.exit(0);
    }

    /** Respond to "New Game" button. */
    public void newGame() {
        _b = _b.newGame();
        _disp.newGame(_b);
        repaint();
    }

    /** Creates an AI instance and gets a move from it. 
     *  Executes the move. */
    public void makeMove() {
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
        } else {
            _b.setFlag(move[0], move[1]);
        }
        repaint();
    }

    /** calls the AI to make moves until the game is won or lost. 
     *  Can't figure out how to make display repaint though. */
    public void Autocomplete() {
        repainter.start();
    }

    /** Responds to MouseEvent EVENT by altering underlying board. */
    public void mouseClickedResponse(MouseEvent event) {
        int y = event.getX() / 16, x = (event.getY() - Display.TOP_MARGIN) / 16 ;
        if(x >= 0) {
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
        }
        repaint();
    }

    /** Ends the game and prompts the user using S. */
    public void gameOver(String s) {
        repainter.stop();
        _b.endGame();
        repaint();
        int response = JOptionPane.showConfirmDialog( null,
        "Would you like to play another game?", s,
        JOptionPane.YES_NO_OPTION);
        if (response == 1) {
            quit();
        } else {
            newGame();
        }
    }

    /** The board widget. */
    private Display _disp;

    /** The game I am consulting. */
    private Board _b;

    /** Might use this later. */
    private Random _randomSource;

    Timer hover;
    Timer repainter;
}
