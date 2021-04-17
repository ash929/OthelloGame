package othello.viewcontroller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import othello.model.Othello;
import othello.model.PlayerImproved;

public class hintButtonPressEventHandler implements EventHandler<ActionEvent> {
    private Othello othello;

    public hintButtonPressEventHandler(Othello othello) {
        this.othello = othello;
    }

    @Override
    public void handle(ActionEvent event) {
        PlayerImproved i = new PlayerImproved(this.othello, othello.getWhosTurn());
        OthelloApplication.hintMove = i.getMove();
        othello.notifyObservers();
    }
}