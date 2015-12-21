package minesweeper;

import java.util.Random;
import java.util.ArrayList;
import static minesweeper.Tile.*;
import static minesweeper.Direction.*;

public class Board {
	Tile[][] _tiles;
	int _r, _c;
	
	public Board() {
		this(50,50);
	}

	public Board(int r, int c) {
		this(r, c, r * c / 8);
	}

	public Board(int r, int c, int numMines) {
		_tiles = new Tile[r][c];
		_r = r;
		_c = c;

		for(int i = 0; i < r; i++) {
			for(int j = 0; j < c; j++) {
				_tiles[i][j] = EMPTY;
			}
		}

		Random rand = new Random();
		ArrayList<Integer> taken = new ArrayList<>();
		int filled = 0;
		while(filled < numMines){
			int temp = rand.nextInt(_r * _c);
			if(!taken.contains(temp)) {
				taken.add(temp);
				filled++;
				_tiles[temp / r][temp % r] = BOMB;
			}
		}

		evaluate();
	}

	private void evaluate() {
		for(int i = 0; i < _r; i++) {
			for(int j = 0; j < _c; j++) {
				if(_tiles[i][j] == EMPTY) {
					evaluateHelper(i,j);
				}
			}
		}
	}

	private void evaluateHelper(int r, int c) {
		int count = 0;
		for(Direction dir = NOWHERE; dir != null; dir = dir.succ()) {
			if(isValid(r + dir.r, c + dir.c)) {
				if(_tiles[r + dir.r][c + dir.c] == BOMB) {
					count++;
				}
			}
		}
		_tiles[r][c] = Tile.toTile(count);
	}

	private Boolean isValid(int r, int c) {
		return (r >= 0 && c >= 0) && (r < _r && c < _c);
	}
}