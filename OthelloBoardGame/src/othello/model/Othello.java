package othello.model;

import java.util.ArrayList;
import java.util.Random;

import othello.util.*;

/**
 * Capture an Othello game. This includes an OthelloBoard as well as knowledge
 * of how many moves have been made, whosTurn is next (OthelloBoard.P1 or
 * OthelloBoard.P2). It knows how to make a move using the board and can tell
 * you statistics about the game, such as how many tokens P1 has and how many
 * tokens P2 has. It knows who the winner of the game is, and when the game is
 * over.
 * 
 * See the following for a short, simple introduction.
 * https://www.youtube.com/watch?v=Ol3Id7xYsY4
 * 
 *
 */
public class Othello extends Observable implements Visitable{
	public static final int DIMENSION = 8; // This is an 8x8 game

	public OthelloBoard board = new OthelloBoard(Othello.DIMENSION);
	private char whosTurn = OthelloBoard.P1;
	private int numMoves = 0;
	Originator originator = new Originator();
	CareTaker careTaker = new CareTaker();
	
	/**
	 * Revert board to previous state (undo).
	 * 
	 */
	public void undo() {
		if (careTaker.size() > 0 && !this.isGameOver()) {
			originator.getStateFromMemento(careTaker.get(careTaker.size()-1));
			Othello undo = originator.getState();
			this.board = undo.board;
			this.whosTurn = undo.whosTurn;
			this.numMoves = undo.numMoves;
			careTaker.remove();
			this.notifyObservers();
		}
	}

	/**
	 * Revert board to initial state (restart).
	 * 
	 */
	public void restart() {
			if (careTaker.size() > 0) {
			originator.getStateFromMemento(careTaker.get(0));
			Othello undo = originator.getState();
			careTaker.removeAll();
			this.board = undo.board;
			this.whosTurn = undo.whosTurn;
			this.numMoves = undo.numMoves;
			this.notifyObservers();
		}
	}

	/**
	 * @param player
	 * @return ArrayList of all possible moves for player.
	 */
	public ArrayList<Move> possibleMoves(char player) {
		ArrayList<Move> moves = new ArrayList<Move>();
		for (int row = 0; row < DIMENSION; row++) {
			for (int col = 0; col < DIMENSION; col++) {
				Move move = new Move(row, col);
				if (hasMoveAtSpace(row, col, player))
					moves.add(move);
			}
		}
		return moves;
	}

	/**
	 * return P1,P2 or EMPTY depending on who moves next.
	 * 
	 * @return P1, P2 or EMPTY
	 */
	public char getWhosTurn() {
		return this.whosTurn;
	}

	/**
	 * 
	 * @param row
	 * @param col
	 * @return the token at position row, col.
	 */
	public char getToken(int row, int col) {
		return this.board.get(row, col);
	}

	/**
	 * Attempt to make a move for P1 or P2 (depending on whos turn it is) at
	 * position row, col. A side effect of this method is modification of whos turn
	 * and the move count.
	 * 
	 * @param row
	 * @param col
	 * @return whether the move was successfully made.
	 */
	public boolean hasMoveAtSpace(int row, int col, char player) {
		boolean move = false;
		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				if (this.board.hasMove(row, col, drow, dcol) == player) {
					move = true;
				}
			}
		}
		return move;
	}

	public boolean move(int row, int col) {
		if (this.hasMoveAtSpace(row, col, this.whosTurn) & !isGameOver()) {
			originator.setState(this.copy());
			careTaker.add(originator.saveStateToMemento());
		}
		if (this.board.move(row, col, this.whosTurn)) {
			this.whosTurn = OthelloBoard.otherPlayer(this.whosTurn);
			char allowedMove = board.hasMove();
			if (allowedMove != OthelloBoard.BOTH)
				this.whosTurn = allowedMove;
			this.numMoves++;
			this.notifyObservers();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param player P1 or P2
	 * @return the number of tokens for player on the board
	 */
	//public int getCount(char player) {
	//	return board.getCount(player);
	//}

	/**
	 * Returns the winner of the game.
	 * 
	 * @return P1, P2 or EMPTY for no winner, or the game is not finished.
	 */
	public char getWinner() {
		GetCountVisitor count1 = new GetCountVisitor(OthelloBoard.P1);
		GetCountVisitor count2 = new GetCountVisitor(OthelloBoard.P2);
		if (!this.isGameOver())
			return OthelloBoard.EMPTY;
		if (count1.c > count2.c)
			return OthelloBoard.P1;
		if (count1.c < count2.c)
			return OthelloBoard.P2;
		return OthelloBoard.EMPTY;
	}

	/**
	 * 
	 * @return whether the game is over (no player can move next)
	 */
	public boolean isGameOver() {
		return this.whosTurn == OthelloBoard.EMPTY;
	}

	/**
	 * Forces the game to end
	 */
	public void forceGameOver() {
		this.whosTurn = OthelloBoard.EMPTY;
		this.notifyObservers();
	}

	/**
	 * 
	 * @return a copy of this. The copy can be manipulated without impacting this.
	 */
	public Othello copy() {
		Othello o = new Othello();
		o.board = this.board.copy();
		o.numMoves = this.numMoves;
		o.whosTurn = this.whosTurn;
		return o;
	}

	/**
	 * 
	 * @return a string representation of the board.
	 */
	public String getBoardString() {
		return board.toString() + "\n";
	}

	/**
	 * run this to test the current class. We play a completely random game. DO NOT
	 * MODIFY THIS!! See the assignment page for sample outputs from this.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Random rand = new Random();

		Othello o = new Othello();
		System.out.println(o.getBoardString());
		while (!o.isGameOver()) {
			int row = rand.nextInt(8);
			int col = rand.nextInt(8);

			if (o.move(row, col)) {
				System.out.println("makes move (" + row + "," + col + ")");
				System.out.println(o.getBoardString() + o.getWhosTurn() + " moves next");
			}
		}

	}

	@Override
	public void accept(Visitor visitor) {
		// TODO Auto-generated method stub
		visitor.visit(this);
	}
}
