package othello.viewcontroller;

import javafx.scene.control.Button;
import othello.model.Othello;
import othello.util.Observable;
import othello.util.Observer;

public class WhosNextView extends Button implements Observer {
	
	private Button random, greedy, improved, hint;

	public WhosNextView(Button random, Button greedy, Button improved, Button hint) {
		this.random = random;
		this.greedy = greedy;
		this.improved = improved;
		this.hint = hint;
	}

	// @Override
	public void update(Observable o) {
		Othello othello = (Othello) o;
		char t = othello.getWhosTurn();
		this.setMouseTransparent(true);
		// PLAYER 2'S TURN, DISABLE GAME MODE & HINT BUTTONS
		if (t == 'O') {
			this.setStyle(OthelloApplication.whiteToken);
			this.random.setDisable(true);
			this.greedy.setDisable(true);
			this.improved.setDisable(true);
			if (OthelloApplication.gameMode != 'H') {
				this.hint.setDisable(true);
			}
			// PLAYER 1'S TURN, ENABLE GAME MODE & HINT BUTTONS
		} else if (t == 'X') {
			this.setStyle(OthelloApplication.blackToken);
			this.random.setDisable(false);
			this.greedy.setDisable(false);
			this.improved.setDisable(false);
			this.hint.setDisable(false);
		}
	}

}