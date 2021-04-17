package othello.model;
/**
 * PlayerImproved makes a move by considering all possible moves that the player
 * can make. 
 * 1. It first prioritizes any of the four corners, that is:
 *    (0,0), (0,7), (7,0), (7,7)
 * 2. If a corner move cannot be made, it then checks for any border move, that is:
 *    (0, y), (7, y), (x, 0), (x, 7)
 * 3. If a border move cannot be made, it then selects the move that maximizes the number
 *    of tokens owned by this player within the 4x4 center square.
 * 4. If a move within the 4x4 center square cannot be made, it then behaves like
 * 	  PlayerGreedy. 
 * 
 * getMove() returns the move with the highest priority.
 * In the case of a tie, PlayerImproved follows the same logic
 * as PlayerGreedy, it prioritizes the smallest row, and if still a tie,
 * the smallest column.
 * 
 * @author Shae Simeoni
 *
 */

import java.util.ArrayList;

public class PlayerImproved extends Player implements PlayerMoveStrategy{
	public PlayerImproved(Othello othello, char player) {
		super(othello, player);
	}

	@Override
	public Move getMove() {
		Othello othelloCopy = othello.copy();
		ArrayList<Move> corner = new ArrayList<Move>();
		ArrayList<Move> side = new ArrayList<Move>();
		ArrayList<Move> square = new ArrayList<Move>();
		Move bestMove=new Move(0,0);
		GetCountVisitor count1 = new GetCountVisitor(this.player);
		othelloCopy.accept(count1);
		int bestMoveCount1 = count1.c;
		int bestMoveCount2 = count1.c;
		for (int row = 0; row < Othello.DIMENSION; row++) {
			for (int col = 0; col < Othello.DIMENSION; col++) {
				othelloCopy = othello.copy();
				Othello othelloCopy2 = othello.copy();
				GetCountVisitor count2 = new GetCountVisitor(this.player);
				// CORNER
				if ((row == 0 && col == 0) || (row == 0 && col == Othello.DIMENSION - 1) || (row == Othello.DIMENSION - 1 && col == 0) || (row == Othello.DIMENSION - 1 && col == Othello.DIMENSION - 1)) {
					if (othelloCopy.move(row, col)) {
						corner.add(new Move(row, col));
					}
				// SIDES
				} if ((row == 0 || row == Othello.DIMENSION - 1) || (col == 0 || col == Othello.DIMENSION - 1)) {
					if (othelloCopy.move(row, col)) {
						side.add(new Move(row, col));
					}
				// 4x4 SQUARE
				} if ((row > 1 && row < 6) && (col > 1 && col < 6)) {
					if (othelloCopy2.move(row, col))
						othelloCopy2.accept(count2);
					if (othelloCopy.move(row, col) && count2.c > bestMoveCount1) {
						bestMoveCount1 = count2.c;
						if (square.isEmpty())
							square.add(new Move(row, col));
						else
							square.set(0, new Move(row, col));
					}
				// GREEDY
					if (othelloCopy2.move(row, col))
						othelloCopy2.accept(count2);
				} if (othelloCopy.move(row, col) && count2.c > bestMoveCount2) {
					bestMoveCount2 = count2.c;
					bestMove = new Move(row,col);
				}
			}
		}
		// SET RETURN PRIORITY: 
		// 1. CORNER 
		// 2. SIDE 
		// 3. 4x4 SQUARE 
		// 4. GREEDY
		if (!corner.isEmpty())
			return corner.get(0);
		else if (!side.isEmpty())
			return side.get(0);
		else if (!square.isEmpty())
			return square.get(0);
		else
			return bestMove;
	}
}
