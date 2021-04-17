package othello.viewcontroller;


import javafx.event.ActionEvent;

import javafx.animation.ScaleTransition;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class gamemodeButtonPressEventHandler implements EventHandler<ActionEvent> {
	private Label p2;

	public gamemodeButtonPressEventHandler(Label p2) {
		this.p2 = p2;
	}
	
	private void animation(Label label) {
		ScaleTransition transition = new ScaleTransition(Duration.millis(100), label);
		transition.setCycleCount(2);
		transition.setAutoReverse(true);
		transition.setFromX(1);
		transition.setFromY(1);
		transition.setToX(1.3);
		transition.setToY(1.3);
		transition.play();
	}

	@Override
	public void handle(ActionEvent event) {
		Button source=(Button)event.getSource();
		if (source.getId() == "HUMAN VS HUMAN") {
			this.p2.setText("Human");
			OthelloApplication.gameMode = 'H';
		} else if (source.getId() == "HUMAN VS GREEDY") {
			this.p2.setText("Greedy");
			OthelloApplication.gameMode = 'G';
		} else if (source.getId() == "HUMAN VS RANDOM") {
			this.p2.setText("Random");
			OthelloApplication.gameMode = 'R';
		} else if (source.getId() == "HUMAN VS IMPROVED") {
			this.p2.setText("Improved");
			OthelloApplication.gameMode = 'I';
		}
		animation(this.p2);
	}
}