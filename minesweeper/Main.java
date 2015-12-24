package minesweeper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;

/** Main class of the Minesweeper program.
 * @author Maxwell Gerber
 */
public class Main {

    /** Version designator. */
    public static final String VERSION = ".75";

    /** The main function for Minesweeper. 
     */
    public static void main(String... args) {
        System.out.printf("Minesweeper. Version %s%n",
                          VERSION);
        Board b = new Board();
        // if(args.length == 0) {
        //     b = new Board();
        // } else if(args.length == 2) {
        //     b = new Board(args[0], args[1]);
        // } else if (args.length == 3) {
        //     b = new Board(args[0], args[1], args[2]);
        // } else {
        //     System.out.printf("Usage: java minesweeper.Main [#rows][#cols][#mines]%n%n");
        //     System.exit(0);
        // }

        JFrame frame = new JFrame();
        frame.setTitle("Minesweeper-java");
        int BOARD_WIDTH = 16 * b.getCols();
        int BOARD_HEIGHT = 16 * b.getRows();
        frame.setSize(BOARD_WIDTH + 1, BOARD_HEIGHT + 49);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
                }
        });
        mineGUI board = new mineGUI(b);
        frame.add(board);
        frame.setJMenuBar(board.createMenuBar());
        frame.setVisible(true);
    }
}
