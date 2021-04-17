package othello.viewcontroller;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.scene.control.Label;
import othello.model.Othello;

public class Timers extends Timer{
	private int numOfSecs;
	private Othello othello;
	public Label ptimer;
	private char player;
	private TimerTask task;
	
	public Timers(Othello othello, int numOfSecs, Label ptimer, char player) {
		this.othello = othello;
		this.numOfSecs = numOfSecs;
		this.ptimer = ptimer;
		this.player = player;
	}
	
	public int getNumOfSecs() {
		return this.numOfSecs;
	}
	
	public void restartTimer() {
		task.cancel();
		this.numOfSecs = OthelloApplication.numOfSecs;
		this.createTimer();
	}
	
	public void createTimer() {
		Runnable updateTimer = new Runnable() {
			public void run() {
				String time = (numOfSecs - (numOfSecs % 60)) / 60 + ":" + numOfSecs % 60;
				if (numOfSecs % 60 == 0)
					time = (numOfSecs - (numOfSecs % 60)) / 60 + ":" + numOfSecs % 60 + "0";
				else if (numOfSecs % 60 < 10)
					time = (numOfSecs - (numOfSecs % 60)) / 60 + ":0" + numOfSecs % 60;
				ptimer.setText(time);
				if (numOfSecs == 0) {
					othello.forceGameOver();
					othello.notifyObservers();
				}
			}
		};

		task = new TimerTask() {
			
			public void run() {
				if (numOfSecs == 0) {
					task.cancel();
				}
				Platform.runLater(updateTimer);
				if (othello.getWhosTurn() == player)
					numOfSecs--;
			}
		};
		
		this.scheduleAtFixedRate(task, 1000l, 1000l);
	}
}
