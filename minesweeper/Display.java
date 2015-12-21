package minesweeper;

import ucb.gui.Pad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.imageio.ImageIO;

import java.io.InputStream;
import java.io.IOException;

/** A widget that displays a Pinball playfield.
 *  @author P. N. Hilfinger
 */
class Display extends Pad {

    /** Color of display field. */
    private static final Color BACKGROUND_COLOR = Color.white;

    /* Coordinates and lengths in pixels unless otherwise stated. */

    /** Preferred dimensions of the playing surface. */
    private static final int TILE_WIDTH = 16, TILE_HEIGHT = 16;
    private static int BOARD_WIDTH, BOARD_HEIGHT;
    private static Image emptyImg, bombImg, blankImg, oneImg, twoImg, threeImg,
    fourImg, fiveImg, sixImg, sevenImg, eightImg, flagImg;

    /** A graphical representation of B. */
    public Display(Board b) {
        _b = b;
        BOARD_WIDTH = TILE_WIDTH * b.getCols();
        BOARD_HEIGHT = TILE_HEIGHT * b.getRows();
        setPreferredSize(BOARD_WIDTH, BOARD_HEIGHT);
        emptyImg = getImage("empty");
        bombImg = getImage("bomb");
        flagImg = getImage("flag");
        oneImg = getImage("one");
        twoImg = getImage("two");
        threeImg = getImage("three");
        fourImg = getImage("four");
        fiveImg = getImage("five");
        sixImg = getImage("six");
        sevenImg = getImage("seven");
        eightImg = getImage("eight");
    }

    public void paintTiles(Graphics2D g) {
        int r = _b.getRows();
        int c = _b.getCols();
        for(int i = 0; i < r; i++) {
            for(int j = 0; j < c; j++) {
                g.drawImage(getTileImage(i, j), i * TILE_WIDTH, j * TILE_HEIGHT,
                    TILE_WIDTH, TILE_HEIGHT, null);
            }
        }
    }

    public Image getTileImage(int r, int c) {
        if(_b.isClicked(r,c)) {
            return getBottomTileImage(r, c);    
        } else if( _b.isFlag(r,c)) {
            return flagImg;
        } else {
            return emptyImg;
        }
    }

    public Image getBottomTileImage(int r, int c) {
        switch(_b.getTile(r,c)) {
        case EMPTY:
            return blankImg;
        case BOMB:
            return bombImg;
        case ONE:
            return oneImg;
        case TWO:
            return twoImg;
        case THREE:
            return threeImg;
        case FOUR:
            return fourImg;
        case FIVE:
            return fiveImg;
        case SIX:
            return sixImg;
        case SEVEN:
            return sevenImg;
        case EIGHT:
            return eightImg;
        default:
            return blankImg;
        }
    }

    public Image getImage(String name) {
        InputStream in =
            getClass().getResourceAsStream("/resources/" + name +".png");
        try {
            return ImageIO.read(in);
        } catch (IOException excp) {
            return null;
        }
    }


    /** Prints the background, all static objects, and calls drag function. */
    @Override
    public synchronized void paintComponent(Graphics2D g) {
        g.setColor(BACKGROUND_COLOR);
        Rectangle b = g.getClipBounds();
        g.fillRect(0, 0, b.width, b.height);
        paintTiles(g);
    }

    /** Game I am displaying. */
    private Board _b;

    /** Internal mouse coordinates. */
    private int _mouseX, _mouseY;

    /** Spaces out the board. */
    private static int boardSpace = 8 * 9 + 3;

    /** Diameter of the board pieces. */
    private static int diam = 6 * 9 + 5;
}
