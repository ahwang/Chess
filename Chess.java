/*
 * Name:	Andrew Hwang
 * UID: 	ahwang96
 * UID #: 	113610271
 * Section:	0103
 * I pledge on my honor that I have not received any unauthorized
 * assistance on this assignment.
 * 
 * This class creates a new 8x8 2D array chess board and fills it with empty
 * spaces. It is also able place pieces on the board and get the information
 * of the piece at a certain location. It also checks the validity of certain
 * moves by specific pieces as.
 */
package chess;

import java.util.NoSuchElementException;

public class Chess {
	// Creates a new 8x8 2D array board
	private BoardSquare[][] board = new BoardSquare[8][8];
	
	// This method fills the board with empty squares
	public Chess() {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				board[row][col] = BoardSquare.EMPTYSQUARE;
			}
		}
	}
	
	// This method creates a deep copy
	public Chess(Chess otherGame) {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				board[row][col] = otherGame.board[row][col];
			}
		}
	}

	// This method places a piece onto the board
	public void setEntry(BoardSquare piece, char col, int row) throws NoSuchElementException {
		if (row <= 8 && row >= 1 && col <= 104 && col >= 97) {
			board[row - 1][col - 97] = piece;
		} else {
			throw new NoSuchElementException();
		}
	}
	
	// This method returns the piece in a specific location on the board
	public BoardSquare getEntry(char col, int row) throws NoSuchElementException {
		if (row <= 8 && row >= 1 && col <= 104 && col >= 97) {
			return board[row - 1][col - 97];
		} else {
			throw new NoSuchElementException();
		}
	}

	// This method returns the total number of a specific piece on the board
	public int count(BoardSquare piece) {
		int count = 0;
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				if (board[row][col] == piece) {
					count++;
				}
			}
		}
		return count;
	}

	// This method returns true if there is only a black king left on the board
	public boolean blackWon() {
		int number = 0;
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				if (board[row][col] == BoardSquare.BLACKKING) {
					number++;
				}
			}
		}
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				if (board[row][col] == BoardSquare.WHITEKING) {
					number = 0;
				}
			}
		}
		if (number > 0) {
			return true;
		} else {
			return false;
		}
	}

	// This method returns true if there is only a white king left on the board
	public boolean whiteWon() {
		int number = 0;
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				if (board[row][col] == BoardSquare.WHITEKING) {
					number++;
				}
			}
		}
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				if (board[row][col] == BoardSquare.BLACKKING) {
					number = 0;
				}
			}
		}
		if (number > 0) {
			return true;
		} else {
			return false;
		}
	}

	// This method returns true if the end placement of a piece is valid
	public boolean validMove(char startCol, int startRow, char endCol, int endRow) {
		// This if statement checks if the end location is on the board and if
		// the end location does not overlap with a piece of the same color
		if (endRow <= 8 && endRow >= 1 && endCol <= 104 && endCol >= 97 && 
			startRow <= 8 && startRow >= 1 && startCol <= 104 && endCol >= 97 &&
			!board[startRow - 1][startCol - 97].sameColors(board[endRow - 1][endCol - 97])) {
			// Checks if the black pawn can move down one row
			if (board[startRow - 1][startCol - 97] == BoardSquare.BLACKPAWN) {
				if (startCol == endCol && startRow - 1 == endRow) {
					return true;
				}
			}
			// Checks if the white pawn can move up one row
			if (board[startRow - 1][startCol - 97] == BoardSquare.WHITEPAWN) {
				if (startCol == endCol && startRow + 1 == endRow) {
					return true;
				}
			}
			// Checks the start row and start col difference is equal to end
			// row and end col difference
			if (board[startRow - 1][startCol - 97].isBishop()) {
				if (Math.abs(startRow - startCol) == Math.abs(endRow - endCol) 
					|| startRow + startCol == endRow + endCol ) {
					return true;
				}
			}
			// Checks if the start is two steps in one direction and 
			// one step in another in the end
			if (board[startRow - 1][startCol - 97].isKnight()) {
				if ((startCol + 1 == endCol && startRow + 2 == endRow)
						|| (startCol - 1 == endCol && startRow + 2 == endRow)
						|| (startCol + 1 == endCol && startRow - 2 == endRow)
						|| (startCol - 1 == endCol && startRow - 2 == endRow)
						|| (startCol + 2 == endCol && startRow + 1 == endRow)
						|| (startCol + 2 == endCol && startRow - 1 == endRow)
						|| (startCol - 2 == endCol && startRow + 1 == endRow)
						|| (startCol - 2 == endCol && startRow - 1 == endRow)) {
					return true;
				}
			}
			// Checks if the king can move in any direction one step
			if (board[startRow - 1][startCol - 97].isKing()) {
				if ((startCol == endCol && startRow + 1 == endRow) || 
					(startCol == endCol && startRow - 1 == endRow) || 
					(startCol + 1 == endCol && startRow == endRow) || 
					(startCol - 1 == endCol && startRow == endRow) || 
					(startCol + 1 == endCol && startRow + 1 == endRow) || 
					(startCol + 1 == endCol && startRow - 1 == endRow) || 
					(startCol - 1 == endCol && startRow + 1 == endRow) || 
					(startCol - 1 == endCol && startRow - 1 == endRow)) {
					return true;
				}
			}
			// Checks if the start row is equal to the end row or the start col
			// is equal the end col
			if (board[startRow - 1][startCol - 97].isRook()) { 
				if (startRow == endRow || startCol == endCol) {
					return true;
				}
			}
			// Combines the bishop and the rook validity moves 
			if(board[startRow - 1][startCol - 97].isQueen()) {
				if (startRow == endRow || startCol == endCol || 
					Math.abs(startRow - startCol) == 
					Math.abs(endRow - endCol) || startRow + startCol == 
					endRow + endCol ) {
					return true;
				}
			}
			
		}
		return false;
	}
	
	// This method checks if the move is possible then replaces the start 
	// location piece to the end location
	public boolean move(char startCol, int startRow, char endCol, int endRow) {
		if(!validMove(startCol, startRow, endCol, endRow)) {
			return false;
		} 
		setEntry(getEntry(startCol, startRow), endCol, endRow);
		setEntry(BoardSquare.EMPTYSQUARE, startCol, startRow);
		return true;
	}
}
