package minesweeper;

import java.util.*;
import java.awt.Point;
import static minesweeper.Direction.*;
import static minesweeper.Tile.*;

class autoplayer{

    private Board _b;
    private Display _disp;
    
    public autoplayer(Board b) {
        this(b, null);
    }

    public autoplayer(Board b, Display d) {
        _b = b;
        _disp = d;
    }

    public int[] nextMove(String behavior){

        Set<int[]> fringe = new HashSet<>();
        Set<int[]> clicked = new HashSet<>();
        Map<Point, Double> guess = new HashMap<>();

        for(int i = 0; i < _b._r; i++) {
            for(int j = 0; j < _b._c; j++) {
                if(_b._clicked[i][j]) {
                    clicked.add(new int[]{i,j});
                }
            }
        }
        
        for(int[] pos: clicked) {
            if(_b.getTile(pos) != EMPTY && notCleared(pos)) {
                fringe.add(pos);
            }
        }

        // if(fringe.size() == 0 && behavior.equals("click")) {
        //     Random r = new Random();
        //     System.out.printf("No tiles in fringe. Making a random guess.%n");
        //     return new int[] {r.nextInt(_b._r), r.nextInt(_b._c)};
        // }

        for(int[] pos: fringe) {
            System.out.println("Checking out: " + pos[0] + " " + pos[1]);
            int neighbors = _b.getTile(pos).ordinal();
            System.out.printf("This tile has %s bombs next to it.%n", neighbors);
            for(Direction dir = N; dir != null; dir = dir.succ()) {
                int r = pos[0] + dir.r;
                int c = pos[1] + dir.c;
                if(_b.isValid(r, c) && _b.isFlag(r,c) && !_b.isClicked(r,c)) {
                    neighbors--;
                    System.out.printf("Bomb already found.%n");
                }
            }

            if(neighbors == 0 && behavior.equals("click")) {
                System.out.printf("No unaccounted-for bombs. All unclicked/unflagged neighbors are safe.%n");
                for(Direction dir = N; dir != null; dir = dir.succ()) {
                    int r = pos[0] + dir.r;
                    int c = pos[1] + dir.c;
                    if(_b.isValid(r, c) && !_b.isFlag(r,c) && !_b.isClicked(r,c)) {
                        return new int[]{r,c};
                    }
                }
            } else {
                System.out.printf("Some bombs still unmarked. Seeing if we can mark them.%n");
                int unclicked = 0;
                for(Direction dir = N; dir != null; dir = dir.succ()) {
                    int r = pos[0] + dir.r;
                    int c = pos[1] + dir.c;
                    if(_b.isValid(r, c) && !_b.isFlag(r,c) && !_b.isClicked(r,c)) {
                        unclicked++;
                    }
                }
                System.out.printf("We have %s unclicked neighbors and %s unfound bombs.%n", unclicked, neighbors);
                if(unclicked == neighbors && behavior.equals("flag")) {
                    for(Direction dir = N; dir != null; dir = dir.succ()) {
                        int r = pos[0] + dir.r;
                        int c = pos[1] + dir.c;
                        if(_b.isValid(r, c) && !_b.isFlag(r,c) && !_b.isClicked(r,c)) {
                            return new int[]{r,c};
                        }
                    }
                }  else {                    
                    for(Direction dir = N; dir != null; dir = dir.succ()) {
                        int r = pos[0] + dir.r;
                        int c = pos[1] + dir.c;
                        if(_b.isValid(r, c) && !_b.isFlag(r,c) && !_b.isClicked(r,c)) {
                            double prop = neighbors/ (double) unclicked;
                            Point p = new Point(r, c);
                            if(guess.get(p) == null || guess.get(p) < prop) {
                                guess.put(p, prop);
                            }
                        }
                    }
                }
            }
        }

        if(behavior.equals("click")) {
            System.out.printf("No suitable tile found. Making an educated guess.%n");
            double score = 1.0;
            Random rand = new Random();
            int r = rand.nextInt(_b._r);
            int c = rand.nextInt(_b._c);
            for(Point p: guess.keySet()) {
                System.out.println("k");
                double s = guess.get(p);
                if(s < score) {
                    r = (int) p.getX();
                    c = (int) p.getY();
                    score = s;
                    System.out.printf("Probability of a bomb is %s at %s, %s %n", s, r, c);
                }
            }
            System.out.printf("Guessing %s,%s%n", r, c);
            return new int[]{r,c};
        }
        return null;
    }


    private Boolean notCleared(int[] pos) {
        for(Direction dir = N; dir != null; dir = dir.succ()) {
            int r = pos[0] + dir.r;
            int c = pos[1] + dir.c;
            if(_b.isValid(r, c) && !_b.isClicked(r, c) && !_b.isFlag(r,c)) {
                return true;
            }
        }
        return false;
    }

}