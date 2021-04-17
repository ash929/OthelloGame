package othello.viewcontroller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import othello.model.GetCountVisitor;
import othello.model.Othello;
import othello.model.OthelloBoard;
import othello.util.Observable;
import othello.util.Observer;

public class LeadingView implements Observer {

	private Button button;
	private Label label;
	private Timers timer1, timer2;

	public LeadingView(Button button, Label label, Timers timer1, Timers timer2) {
		this.button = button;
		this.label = label;
		this.timer1 = timer1;
		this.timer2 = timer2;
	}

	// @Override
	public void update(Observable o) {
		Othello othello = (Othello) o;
		GetCountVisitor count1 = new GetCountVisitor(OthelloBoard.P1);
		GetCountVisitor count2 = new GetCountVisitor(OthelloBoard.P2);
		othello.accept(count1);
		othello.accept(count2);
		
		// Player 2 is in the lead
		if (count2.c > count1.c) {
			this.button.setVisible(true);
			this.button.setStyle(OthelloApplication.whiteToken);
			label.setText("Current Leader: ");
		
		// Player 1 is in the lead
		} else if (count1.c > count2.c) {
			this.button.setVisible(true);
			this.button.setStyle(OthelloApplication.blackToken);
			label.setText("Current Leader: ");
		
		// Tie
		} else if (count1.c == count2.c) {
			this.button.setVisible(false);
			label.setText("Current Leader: ");
		}
		
		// Player 1 timer hits 0:00
		if (timer1.getNumOfSecs() == 0) {
			this.button.setVisible(true);
			this.button.setStyle(OthelloApplication.whiteToken);
			label.setText("Winner: ");
		}

		// Player 2 timer hits 0:00
		else if (timer2.getNumOfSecs() == 0) {
			this.button.setVisible(true);
			this.button.setStyle(OthelloApplication.blackToken);
			label.setText("Winner: ");
		}

		// Game over
		else if (othello.isGameOver()) {
			// Tie Game
			if (count1.c == count2.c) {
				this.button.setVisible(false);
			}
			label.setText("Winner: ");
		}

	}

}
