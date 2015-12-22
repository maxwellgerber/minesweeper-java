package minesweeper;

public enum Tile {
	EMPTY, ONE, TWO, THREE,
	FOUR, FIVE, SIX, SEVEN, EIGHT, BOMB;

	static Tile toTile(int i) {
		return values()[i];
	}

}