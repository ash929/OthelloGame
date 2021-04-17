package othello.model;

public abstract class OthelloControllerVerbose extends OthelloController {

	protected void reportMove(char whosTurn, Move move) {
		System.out.println(whosTurn + " makes move " + move + "\n");
	}

	protected void report() {
		GetCountVisitor count1 = new GetCountVisitor(OthelloBoard.P1);
		GetCountVisitor count2 = new GetCountVisitor(OthelloBoard.P2);
		othello.accept(count1);
		othello.accept(count2);
		String s = othello.getBoardString() + OthelloBoard.P1 + ":" 
				+ count1.c + " "
				+ OthelloBoard.P2 + ":" + count2.c + "  " 
				+ othello.getWhosTurn() + " moves next";
		System.out.println(s);
	}

	protected void reportFinal() {
		GetCountVisitor count1 = new GetCountVisitor(OthelloBoard.P1);
		GetCountVisitor count2 = new GetCountVisitor(OthelloBoard.P2);
		othello.accept(count1);
		othello.accept(count2);
		
		String s = othello.getBoardString() + OthelloBoard.P1 + ":" 
				+ count1.c + " "
				+ OthelloBoard.P2 + ":" + count2.c
				+ "  " + othello.getWinner() + " won\n";
		System.out.println(s);
	}

}
