package minesweeper;

public enum Tile {
	EMPTY, BOMB, ONE, TWO, THREE,
	FOUR, FIVE, SIX, SEVEN, EIGHT;

	static Tile toTile(int i) {
		return values()[i + 1];
	}
}