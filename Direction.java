package minesweeper;

/** A direction of motion on a game board.
 *  @author M. Gerber
 */
enum Direction {

    //** Compass directions. */
    NOWHERE(0, 0),
        N(0, 1), NE(1, 1), E(1, 0), SE(1, -1), S(0, -1),
        SW(-1, -1), W(-1, 0), NW(-1, 1);

    /** Return a Direction in which each step increments the column
     *  by DC0 and the row by DR0. */
    Direction(int dc0, int dr0) {
        c = dc0;
        r = dr0;
    }

    /** Returns the unit direction indicated by DC1, DR1, DC2, and DR2. */
    static Direction lookup(int dc1, int dr1, int dc2, int dr2) {
        int dc0 = dc2 - dc1;
        int dr0 = dr2 - dr1;
        if (dc0 != 0) {
            dc0 /= Math.abs(dc0);
        }
        if (dr0 != 0) {
            dr0 /= Math.abs(dr0);
        }
        for (Direction dir : Direction.values()) {
            if (dir.c == dc0 && dir.r == dr0) {
                return dir;
            }
        }
        return null;
    }

    /** Returns the direction opposite that of DIR. */
    static Direction flip(Direction dir) {
        switch (dir) {
        case N:
            return S;
        case NE:
            return SW;
        case E:
            return W;
        case SE:
            return NW;
        case S:
            return N;
        case SW:
            return NE;
        case W:
            return E;
        case NW:
            return SE;
        default:
            return null;
        }
    }

    /** Return the next direction clockwise.  NOWHERE's successor by
     *  convention is N, and the successor of NW is null. */
    Direction succ() {
        return (this == NW) ? null : values()[ordinal() + 1];
    }

    /** Direction vector. */
    protected final int c, r;

}
