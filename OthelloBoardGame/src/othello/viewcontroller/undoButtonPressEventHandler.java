package othello.viewcontroller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import othello.model.Othello;
import othello.model.OthelloBoard;

public class undoButtonPressEventHandler implements EventHandler<ActionEvent> {
	private Othello othello;

	public undoButtonPressEventHandler(Othello othello) {
		this.othello = othello;
	}

	@Override
	public void handle(ActionEvent event) {
		// HUMAN VS HUMAN
		if (OthelloApplication.gameMode == 'H') {
			this.othello.undo();
			// HUMAN VS (ANY AI)
		} else {
			this.othello.undo();
			if (this.othello.board.hasMove() == OthelloBoard.P2 || this.othello.board.hasMove() == OthelloBoard.BOTH) {
				this.othello.undo();
			}
		}
	}
}