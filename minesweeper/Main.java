package minesweeper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import ucb.util.CommandArgs;

/** Main class of the Minesweeper program.
 * @author Maxwell Gerber
 */
public class Main {

    /** Version designator. */
    public static final String VERSION = ".5";

    /** The main function for Minesweeper. 
     */
    public static void main(String... args) {
        System.out.printf("Minesweeper. Version %s%nType ? for help.%n",
                          VERSION);
        Board b = null;
        if(args.length == 0) {
            b = new Board();
        } else if(args.length == 2) {
            b = new Board(args[0], args[1]);
        } else if (args.length == 3) {
            b = new Board(args[0], args[1], args[2]);
        } else {
            System.out.printf("Usage: java minesweeper.Main [#rows][#cols][#mines]%n%n");
            System.exit(0);
        }
        mineGUI gui = new mineGUI("Minesweeper-java", b);
    }
}
