package othello.viewcontroller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import othello.model.Move;
import othello.model.Othello;
import othello.model.OthelloBoard;
import othello.model.Player;

import java.util.Timer;
import java.util.TimerTask;

public class spotButtonPressEventHandler implements EventHandler<ActionEvent> {
	private int row, col;
	private Othello othello;

	public spotButtonPressEventHandler(Othello othello, int row, int col) {
		this.othello = othello;
		this.row = row;
		this.col = col;
	}

	// Disables highlighted moves on AI's turn
	private void disable() {
		for (int i = 0; i < OthelloApplication.buttons.size(); i++) {
			if (OthelloApplication.buttons.get(i).getStyle() == OthelloApplication.highlightToken) {
				OthelloApplication.buttons.get(i).setMouseTransparent(true);
				OthelloApplication.buttons.get(i).setStyle(OthelloApplication.emptyToken);
			}
		}
	}

	// Adds a move delay for the AI's
	private void delayMove(int row, int col, Othello o) {
		Runnable updateTimer = new Runnable() {
			public void run() {
				if (!o.move(row, col)) {
					OthelloApplication.update(othello);
				}
			}
		};

		Timer time = new Timer();
		TimerTask task = new TimerTask() {

			public void run() {
				Platform.runLater(updateTimer);
			}
		};

		time.schedule(task, 1250l);
	}

	@Override
	public void handle(ActionEvent event) {

		Player player = new Player(this.othello, OthelloBoard.P2);
		// Human Vs Human
		if (OthelloApplication.gameMode == 'H') {
			this.othello.move(this.row, this.col);
			// Human Vs (Any AI)
		} else {
			if (this.othello.move(this.row, this.col)) {
				disable();
				player.setStrategy(OthelloApplication.gameMode);
				Move curMove = player.getMove();
				if (othello.getWhosTurn() == OthelloBoard.P2)
					delayMove(curMove.getRow(), curMove.getCol(), this.othello);
				else
					delayMove(-1, -1, this.othello);
			}
		}
	}
}
