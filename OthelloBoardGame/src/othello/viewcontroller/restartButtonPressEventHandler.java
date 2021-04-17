package othello.viewcontroller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import othello.model.Move;
import othello.model.Othello;

public class restartButtonPressEventHandler implements EventHandler<ActionEvent> {
	private Othello othello;
	private Timers timer1, timer2;

	public restartButtonPressEventHandler(Othello othello, Timers timer1, Timers timer2) {
		this.othello = othello;
		this.timer1 = timer1;
		this.timer2 = timer2;
	}

	@Override
	public void handle(ActionEvent event) {
		OthelloApplication.hintMove = new Move(-1, -1);
		timer1.restartTimer();
		timer2.restartTimer();
		othello.restart();
	}
}
