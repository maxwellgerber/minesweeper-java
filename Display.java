package minesweeper;

import ucb.gui.Pad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;

import java.awt.BasicStroke;
import java.awt.RenderingHints;

import java.util.ArrayList;
import java.util.Iterator;

/** A widget that displays a Pinball playfield.
 *  @author P. N. Hilfinger
 */
class Display extends Pad {

    /** Color of display field. */
    private static final Color BACKGROUND_COLOR = Color.white;

    /* Coordinates and lengths in pixels unless otherwise stated. */

    /** Preferred dimensions of the playing surface. */
    private static final int BOARD_WIDTH = 600, BOARD_HEIGHT = 600;

    /** A graphical representation of B. */
    public Display(Board b) {
        _b = b;
        setPreferredSize(BOARD_WIDTH, BOARD_HEIGHT);
    }

    // /** Draw CARD at X, Y on G. */
    // private void paintBG(Graphics2D g) {
    //     for (int i = 0; i < 8; i++) {
    //         for (int j = 0; j < 8; j++) {
    //             if ((i + j) % 2 != 0) {
    //                 Rectangle2D.Double rect = new Rectangle2D.Double(
    //                     i * boardSpace + 1, j * boardSpace + 1,
    //                     boardSpace - 2, boardSpace - 2);
    //                 g.setColor(new Color(5 * 5 * 5, 5 * 5 * 3, 5 * 5 * 5));
    //                 g.fill(rect);
    //                 g.setColor(Color.BLACK);
    //                 g.draw(rect);
    //             } else {
    //                 Rectangle2D.Double rect = new Rectangle2D.Double(
    //                     i * boardSpace + 1, j * boardSpace + 1,
    //                     boardSpace - 2, boardSpace - 2);
    //                 g.setColor(new Color(5 * 5 * 5, 5 * 5 * 3, 5 * 5 * 3));
    //                 g.fill(rect);
    //                 g.setColor(Color.BLACK);
    //                 g.draw(rect);
    //             }
    //         }
    //     }
    // }

    // /** Paints all the pieces on G. */
    // private void paintPieces(Graphics2D g) {
    //     for (int i = 0; i < 8; i++) {
    //         for (int j = 7; j >= 0; j--) {
    //             if (_b.get(i, j) == WP) {
    //                 Ellipse2D.Double piece = new Ellipse2D.Double(
    //                     i * boardSpace + 8, j * boardSpace + 8,
    //                     diam, diam);
    //                 g.setColor(new Color(7 * 7 * 5, 7 * 7 * 5, 7 * 7 * 5));
    //                 g.fill(piece);
    //                 g.setColor(Color.BLACK);
    //                 g.draw(piece);
    //             } else if (_b.get(i, j) == BP) {
    //                 Ellipse2D.Double piece = new Ellipse2D.Double(
    //                     i * boardSpace + 8, j * boardSpace + 8,
    //                     diam, diam);
    //                 g.setColor(new Color(5 * 3, 5 * 3, 5 * 3));
    //                 g.fill(piece);
    //                 g.setColor(Color.BLACK);
    //                 g.draw(piece);
    //             }
    //         }
    //     }
    // }

    // /** Paints all the moves on G. */
    // private void paintLines(Graphics2D g) {
    //     for (Move m : _moves) {
    //         int x1 = m.getCol0() * boardSpace + boardSpace / 2;
    //         int x2 = m.getCol1() * boardSpace + boardSpace / 2;
    //         int y1 = m.getRow0() * boardSpace + boardSpace / 2;
    //         int y2 = m.getRow1() * boardSpace + boardSpace / 2;
    //         g.setColor(Color.BLACK);
    //         g.draw(new Line2D.Double(x1, y1, x2, y2));
    //         Ellipse2D.Double point = new Ellipse2D.Double(x2 - 4, y2 - 4, 8, 8);
    //         g.fill(point);
    //     }
    // }

    // /** Copies all the moves from I into this. */
    // public void setMoves(Iterator<Move> i) {
    //     _moves.clear();
    //     while (i.hasNext()) {
    //         _moves.add(i.next());
    //     }
    // }

    // /** Erases moves held. */
    // public void eraseMoves() {
    //     _moves.clear();
    // }

    /** Prints the background, all static objects, and calls drag function. */
    @Override
    public synchronized void paintComponent(Graphics2D g) {
        g.setColor(BACKGROUND_COLOR);
        RenderingHints rh = g.getRenderingHints();
        rh.put(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHints(rh);
        Rectangle b = g.getClipBounds();
        g.setStroke(new BasicStroke(2));
        g.fillRect(0, 0, b.width, b.height);
        // paintBG(g);
        // paintPieces(g);
        // paintLines(g);
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