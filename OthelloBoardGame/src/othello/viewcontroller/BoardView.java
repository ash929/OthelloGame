package othello.viewcontroller;

import othello.model.Othello;
import othello.util.Observable;
import othello.util.Observer;

public class BoardView implements Observer {

	@Override
    public void update(Observable o) {
        Othello othello = (Othello) o;
		OthelloApplication.update(othello);
    }
}