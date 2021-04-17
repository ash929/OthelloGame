package othello.viewcontroller;

import javafx.scene.control.Label;
import othello.model.GetCountVisitor;
import othello.model.Othello;
import othello.model.OthelloBoard;
import othello.util.Observable;
import othello.util.Observer;

public class ScoreView implements Observer {
	
	private Label score1, score2;

	public ScoreView(Label score1, Label score2) {
		this.score1 = score1;
		this.score2 = score2;
	}

	// @Override
	public void update(Observable o) {
		Othello othello = (Othello) o;
		GetCountVisitor count1 = new GetCountVisitor(OthelloBoard.P1);
		GetCountVisitor count2 = new GetCountVisitor(OthelloBoard.P2);
		othello.accept(count1);
		othello.accept(count2);
		this.score1.setText("Score: " + count1.c);
		this.score2.setText("Score: " + count2.c);
	}

}
