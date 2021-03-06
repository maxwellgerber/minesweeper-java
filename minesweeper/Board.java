package minesweeper;

import java.util.Random;
import java.util.ArrayList;
import static minesweeper.Tile.*;
import static minesweeper.Direction.*;

public class Board {
	Tile[][] _tiles;
	Boolean[][] _clicked;
	Boolean[][] _flag;

	int _r, _c, _numMines, _numCleared;
	
	public Board() {
		this(16,16);
	}

	public Board(String r, String c) {
		this(Integer.parseInt(r), Integer.parseInt(c));
	}

	public Board(int r, int c) {
		this(r, c, r * c / 9);
	}

	public Board(String r, String c, String numMines) {
		this(Integer.parseInt(r), Integer.parseInt(c), Integer.parseInt(numMines));
	}

	public Board(int r, int c, int numMines) {
		_tiles = new Tile[r][c];
		_clicked = new Boolean[r][c];
		_flag = new Boolean[r][c];
		_r = r;
		_c = c;
		_numMines = numMines;
		_numCleared = 0;
		for(int i = 0; i < r; i++) {
			for(int j = 0; j < c; j++) {
				_tiles[i][j] = EMPTY;
				_clicked[i][j] = false;
				_flag[i][j] = false;
			}
		}
		Random rand = new Random();
		ArrayList<int[]> taken = new ArrayList<>();
		int filled = 0;
		while(filled < numMines){
			int tempr = rand.nextInt(_r);
			int tempc = rand.nextInt(_c);
			int[] temp = new int[]{tempr, tempc};
			if(!taken.contains(temp)) {
				//System.out.println("puttint a mine in at " + (temp / r) + " " + (temp % r));
				taken.add(temp);
				filled++;
				_tiles[tempr][tempc] = BOMB;
			}
		}
		evaluate();
	}

	public Board newGame() {
		return new Board(_r, _c, _numMines);
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
		for(Direction dir = N; dir != null; dir = dir.succ()) {
			if(isValid(r + dir.r, c + dir.c)) {
				if(_tiles[r + dir.r][c + dir.c] == BOMB) {
					count++;
				}
			}
		}
		_tiles[r][c] = Tile.toTile(count);
	}

	public Boolean isWon() {
		return _numCleared == _r * _c - _numMines;
	}

	public Boolean isBomb(int r, int c) {
		return _tiles[r][c] == BOMB;
	}

	public void endGame() {
		for(int i = 0; i < _r; i++) {
			for(int j = 0; j < _c; j++) {
				if(!_clicked[i][j]) {
					click(i, j);
				}
			}
		}
	}

	public ArrayList<int[]> click(int r, int c) {
		ArrayList<int[]> cleared = new ArrayList<>();
		if(!_clicked[r][c]) {
			cleared.add(new int[]{r, c});
			_numCleared++;
			_clicked[r][c] = true;
			if(_tiles[r][c] == EMPTY) {
				for(Direction dir = N; dir != null; dir = dir.succ()) {
					int r1 = r + dir.r;
					int c1 = c + dir.c;
					// System.out.println("Checkout out " + r1 + " " + c1);
					if(isValid(r1, c1) && !_clicked[r1][c1] && _tiles[r1][c1] != BOMB) {
						cleared.addAll(click(r1, c1));
					}
				}
			}
		}
		return cleared;
	}

	public Boolean isClicked(int[] pos) {
		return isClicked(pos[0], pos[1]);
	}

	public Boolean isClicked(int r, int c) {
		return _clicked[r][c];
	}

	public void setFlag(int r, int c) {
		_flag[r][c] = !_flag[r][c];
	}

	public Boolean isFlag(int r, int c) {
		return _flag[r][c];
	}

	public Tile getTile(int[] pos) {
		return getTile(pos[0],pos[1]);
	}

	public Tile getTile(int r, int c) {
		return _tiles[r][c];
	}

	public int getRows() {
		return _r;
	}

	public int getCols() {
		return _c;
	}

	public Boolean isValid(int r, int c) {
		return (r >= 0 && c >= 0) && (r < _r && c < _c);
	}
}