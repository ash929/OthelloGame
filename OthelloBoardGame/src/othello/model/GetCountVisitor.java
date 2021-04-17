package othello.model;

import othello.util.Visitor;

public class GetCountVisitor implements Visitor{
	char player;
	public int c;
	
	public GetCountVisitor(char player) {
		this.player = player;
	}

	@Override
	public void visit(OthelloBoard ob) {
		int count = 0;
		for (int row = 0; row < ob.dim; row++) {
			for (int col = 0; col < ob.dim; col++) {
				if (ob.board[row][col] == player)
					count++;
			}
		}
		this.c = count;
		
	}

	@Override
	public void visit(Othello o) {
		//o.board.accept(new GetCountVisitor(player));
		int count = 0;
		for (int row = 0; row < o.DIMENSION; row++) {
			for (int col = 0; col < o.DIMENSION; col++) {
				if (o.board.board[row][col] == player)
					count++;
			}
		}
		this.c = count;
	}
	

}
