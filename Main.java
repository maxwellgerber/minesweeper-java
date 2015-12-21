package minesweeper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import ucb.util.CommandArgs;

/** Main class of the Lines of Action program.
 * @author Maxwell Gerber
 */
public class Main {

    /** Version designator. */
    public static final String VERSION = ".01";

    /** The main function for Minesweeper. Possible ARGS are
     *      --display       Use a GUI.
     *      
     */
    public static void main(String... args) {

        System.out.printf("Minesweeper. Version %s.%nType ? for help.%n",
                          VERSION);
    }

}
