package othello.viewcontroller;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import othello.model.*;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class OthelloApplication extends Application {
	// REMEMBER: To run this in the lab put
	// --module-path "/usr/share/openjfx/lib" --add-modules javafx.controls,javafx.fxml
	// in the run configuration under VM arguments.
	// You can import the JavaFX.prototype launch configuration and use it as well.

	static int numOfSecs = 300;
	static double currentTimerSet;
	static ArrayList<Button> buttons = new ArrayList<Button>();
	static char gameMode = 'H';
	static Move hintMove = new Move(-1, -1);
	static String blackToken = "-fx-background-color: radial-gradient(center 70% 30%, radius 55%, rgba(90,90,90,1), rgba(0,0,0,1));";
	static String whiteToken = "-fx-background-color: radial-gradient(center 70% 30%, radius 500%, rgba(245,245,245,1), rgba(0,0,0,1));";
	static String emptyToken = "-fx-background-color: radial-gradient(center 70% 20%, radius 150%, rgba(102,157,168,1), rgba(192,221,226,1));";
	static String highlightToken = emptyToken + "-fx-border-color: #FFFFFF; -fx-border-width: 2px;";
	static String buttonEffect = "-fx-padding: 8 15 15 15;" + "-fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;"
			+ "-fx-background-radius: 8;" + "-fx-background-color"
			+ "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );" + "-fx-font-weight: bold;"
			+ "-fx-font-size: 22";

	// Updates the board
	public static void update(Othello othello) {
		int i = 0;
		ArrayList<Move> moves = othello.possibleMoves(othello.getWhosTurn());
		for (int row = 0; row < Othello.DIMENSION; row++) {
			for (int col = 0; col < Othello.DIMENSION; col++) {
				buttons.get(i).setMouseTransparent(false);
				// BLACK TOKEN
				if (othello.getToken(row, col) == OthelloBoard.P1) {
					if (buttons.get(i).getStyle() != blackToken) {
						buttons.get(i).setStyle(blackToken);
						rotate(500, buttons.get(i));
					}
					i += 1;
					// WHITE TOKEN
				} else if (othello.getToken(row, col) == OthelloBoard.P2) {
					if (buttons.get(i).getStyle() != whiteToken) {
						buttons.get(i).setStyle(whiteToken);
						rotate(500, buttons.get(i));
					}
					i += 1;
					// EMPTY SLOT
				} else {
					buttons.get(i).setStyle(emptyToken);
					// HIGHLIGHTS POSSIBLE MOVES
					if (!othello.isGameOver()) {
						for (int j = 0; j < moves.size(); j++) {
							if (moves.get(j).getRow() == row & moves.get(j).getCol() == col) {
								buttons.get(i)
										.setStyle(highlightToken);
							}
						}
						// HINT IF REQUESTED
						if (hintMove.getRow() == row && hintMove.getCol() == col) {
							buttons.get(i).setStyle(emptyToken + "-fx-border-color: #2AED51; -fx-border-width: 4px;");
							hintMove = new Move(-1, -1);
						}
					}
					i += 1;
				}
			}
		}

	}

	// Simplifies the process of creating a rotating animation
	private static void rotate(int duration, Button button) {
		RotateTransition transition = new RotateTransition(Duration.millis(duration), button);
		transition.setAxis(Rotate.Y_AXIS);
		transition.setFromAngle(180);
		transition.setToAngle(360);
		transition.setInterpolator(Interpolator.LINEAR);
		transition.setCycleCount(1);
		transition.play();
	}

	// Simplifes the process of creating button tooltips
	private static void tooltips(Button[] b, Tooltip[] t) {
		for (int i = 0;i < b.length;i++) {
			b[i].setTooltip(t[i]);
		}
	}
	
	// Set all visual buttons to transparent
	private static void transparent(Button[] b) {
		for (int i = 0;i < b.length;i++) {
			b[i].setMouseTransparent(true);
		}
	}

	// Simplifies the process of creating the grid (board)
	private static GridPane createGrid(Othello othello) {
		GridPane grid = new GridPane();
		ArrayList<Move> moves = othello.possibleMoves(OthelloBoard.P1);
		for (int row = 0; row < Othello.DIMENSION; row++) {
			for (int col = 0; col < Othello.DIMENSION; col++) {
				Button spot = ButtonFactory.create(80, 80, "0", true, emptyToken, null);
				// VIEW->CONTROLLER hookup
				spot.setOnAction(new spotButtonPressEventHandler(othello, row, col));
				// HIGHLIGHTS POSSIBLE MOVES
				for (int i = 0; i < moves.size(); i++) {
					if (moves.get(i).getRow() == row & moves.get(i).getCol() == col)
						spot.setStyle(highlightToken);
				}
				// BLACK TOKEN
				if (othello.getToken(row, col) == 'X') 
					spot.setStyle(blackToken);
					// WHITE TOKEN
				else if (othello.getToken(row, col) == 'O')
					spot.setStyle(whiteToken);
				buttons.add(spot);
				grid.add(spot, col, row);
			}
		}
		grid.setHgap(5); // horizontal gap in pixels
		grid.setVgap(5); // vertical gap in pixels
		grid.setStyle("-fx-background-color: #669DA8; -fx-border-color: #FFFFFF; -fx-border-width: 2px; ");
		grid.setPadding(new Insets(10, 10, 10, 10)); // margins around the whole grid
		return grid;
	}

	@Override
	public void start(Stage stage) throws Exception {
		// MODEL
		Othello othello = new Othello();

		// LABELS & BUTTONS
		Label p1 = LabelFactory.create("Human", "Verdana", 18, true);
		Label p2 = LabelFactory.create("Human", "Verdana", 18, true);
		Label WhosTurn = LabelFactory.create("Whose Turn: ", "Verdana", 30, true);
		Label blackScore = LabelFactory.create("Score: 2", "Verdana", 18, true);
		Label whiteScore = LabelFactory.create("Score: 2", "Verdana", 18, true);
		Label winner = LabelFactory.create("Current Leader: ", "Verdana", 18, true);
		Label p1timer = LabelFactory.create(String.valueOf(numOfSecs / 60) + ":00", "Verdana", 18, true);
		Label p2timer = LabelFactory.create(String.valueOf(numOfSecs / 60) + ":00", "Verdana", 18, true);
		Button black = ButtonFactory.create(45, 45, "0", true, blackToken, null);
		Button white = ButtonFactory.create(45, 45, "0", true, whiteToken, null);
		Button blackP1 = ButtonFactory.create(45, 45, "0", true, blackToken, null);
		Button whiteP2 = ButtonFactory.create(45, 45, "0", true, whiteToken, null);
		
		
		// create "bot" image icons
		ImageView[] imageBots = new ImageView[3];
		Image[] iconBots = new Image[3];
		for(int i = 0; i < 3; ++i) {
			iconBots[i] = new Image(getClass().getResourceAsStream("bot.png"));
			imageBots[i] = new ImageView(iconBots[i]);
			imageBots[i].setFitHeight(50);
			imageBots[i].setFitWidth(50);
		}
		
		// create "star" image icons
		ImageView[] imageStars = new ImageView[6];
		Image[] iconStars = new Image[6];
		for(int i = 0; i <6; ++i) {
			iconStars[i] = new Image(getClass().getResourceAsStream("star.png"));
			imageStars[i] = new ImageView(iconStars[i]);
			imageStars[i].setFitHeight(35);
			imageStars[i].setFitWidth(35);
		}
		
		// create "human" image icon
		Image iconHvH = new Image(getClass().getResourceAsStream("human.png"));
		ImageView imageHvH = new ImageView(iconHvH);
		imageHvH.setFitWidth(50);  
		imageHvH.setFitHeight(50);
		
		// create and set icon for Human vs Human button
		Button HvH = ButtonFactory.create(320, 80, "0", false, buttonEffect, "");
		HvH.setId("HUMAN VS HUMAN");
		HvH.setGraphic(imageHvH);
		
		// create and set icon for Human vs Greedy button
		HBox hBoxHVG = new HBox();
		hBoxHVG.setAlignment(Pos.CENTER);
		hBoxHVG.getChildren().addAll(imageBots[0], imageStars[0], imageStars[1]);
		Button HvG = ButtonFactory.create(320, 80, "0", false, buttonEffect, "");
		HvG.setId("HUMAN VS GREEDY");
		HvG.setGraphic(hBoxHVG);
		
		// create and set icon for Human vs Random button
		HBox hBoxHVR = new HBox();
		hBoxHVR.setAlignment(Pos.CENTER);
		hBoxHVR.getChildren().addAll(imageBots[1], imageStars[2]);
		Button HvR = ButtonFactory.create(320, 80, "0", false, buttonEffect, "");
		HvR.setId("HUMAN VS RANDOM");
		HvR.setGraphic(hBoxHVR);
		
		// create and set icon for Human vs Improved button
		HBox hBoxHVI = new HBox();
		hBoxHVI.setAlignment(Pos.CENTER);
		hBoxHVI.getChildren().addAll(imageBots[2], imageStars[3], imageStars[4], imageStars[5]);
		Button HvI = ButtonFactory.create(320, 80, "0", false, buttonEffect, "");
		HvI.setId("HUMAN VS IMPROVED");
		HvI.setGraphic(hBoxHVI);
		
		Button leader = ButtonFactory.create(60, 60, "0", true, null, null);
		
		// create and set icon for restart button
		Image iconRestart= new Image(getClass().getResourceAsStream("restart.png"));
		ImageView imageRestart = new ImageView(iconRestart);
		imageRestart.setFitWidth(40);  
		imageRestart.setFitHeight(40);
		Button restart = ButtonFactory.create(200, 60, "0", false, buttonEffect, "");
		restart.setGraphic(imageRestart);
		
		// create and set icon for undo button
		Image iconUndo= new Image(getClass().getResourceAsStream("undo.png"));
		ImageView imageUndo = new ImageView(iconUndo);
		imageUndo.setFitWidth(40);  
		imageUndo.setFitHeight(40);
		Button undo = ButtonFactory.create(200, 60, "0", false, buttonEffect, "");
		undo.setGraphic(imageUndo);
		
		// create and set icon for hint button
		Image iconHint= new Image(getClass().getResourceAsStream("hint.png"));
		ImageView imageHint = new ImageView(iconHint);
		imageHint.setFitWidth(40);  
		imageHint.setFitHeight(40);
		Button hint = ButtonFactory.create(200, 60, "0", false, buttonEffect, "");
		hint.setGraphic(imageHint);

		// Timer Slider
		Label setTimerLabel = LabelFactory.create("Set Timer: 5:00", "Verdana", 17, true);
		setTimerLabel.setTextFill(Color.web("#EDCCC4"));
		Slider timerScale = new Slider(1, 60, 5);
		timerScale.setStyle(
				"-fx-control-inner-background: palegreen; -fx-pref-width: 300px; -fx-font-size: 20; -fx-padding: 0 20 20 20;");
		timerScale.setMajorTickUnit(1);
		timerScale.setMinorTickCount(0);
		timerScale.setBlockIncrement(1);
		timerScale.setSnapToTicks(true);
		timerScale.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				currentTimerSet = timerScale.getValue();
				numOfSecs = (int) timerScale.getValue() * 60;
				setTimerLabel.setText("Set Timer: " + (numOfSecs / 60) + ":00");
				;
			}
		});

		// DISABLE BUTTON
		transparent(new Button[] {black, white, blackP1, whiteP2, leader});
		leader.setVisible(false);

		// GRID
		GridPane grid = createGrid(othello);

		// TIMERS
		Timers timer1 = new Timers(othello, numOfSecs, p1timer, OthelloBoard.P1);
		Timers timer2 = new Timers(othello, numOfSecs, p2timer, OthelloBoard.P2);
		timer1.createTimer();
		timer2.createTimer();

		// VIEW COMPONENTS
		BoardView guiView = new BoardView();
		WhosNextView whoNext = new WhosNextView(HvG, HvR, HvI, hint);
		whoNext.setShape(new Circle(60));
		whoNext.setPrefSize(80, 80);
		whoNext.setStyle(blackToken);
		ScoreView score = new ScoreView(blackScore, whiteScore);
		LeadingView lead = new LeadingView(leader, winner, timer1, timer2);

		// CONTROLLER->MODEL hookup
		restart.setOnAction(new restartButtonPressEventHandler(othello, timer1, timer2));
		undo.setOnAction(new undoButtonPressEventHandler(othello));
		hint.setOnAction(new hintButtonPressEventHandler(othello));
		HvH.setOnAction(new gamemodeButtonPressEventHandler(p2));
		HvG.setOnAction(new gamemodeButtonPressEventHandler(p2));
		HvR.setOnAction(new gamemodeButtonPressEventHandler(p2));
		HvI.setOnAction(new gamemodeButtonPressEventHandler(p2));

		// CSS Style: http://fxexperience.com/2011/12/styling-fx-buttons-with-css/
		tooltips(new Button[] {HvH, HvR, HvG, HvI, undo, restart, hint}, new Tooltip[] {new Tooltip("Play with your friends locally"), new Tooltip("Random AI chooses a move at random"), new Tooltip("Greedy AI maximizes the number of tokens it owns"), new Tooltip("Improved AI prioritizes corners and border moves.\n"+ "If none are availabe it mimics Greedy focusing on the center 4x4 square"),new Tooltip("Undo's last move"), new Tooltip("Restarts the game"), new Tooltip("Uses Improved AI to display the best possible move")});

		// VIEW LAYOUT
		HBox score1 = HBoxFactory.create(20, Pos.CENTER_LEFT, null, new Insets(0, 0, 0, 0), new Object[] {black, blackScore});
		HBox score2 = HBoxFactory.create(20, Pos.CENTER_LEFT, null, new Insets(0, 0, 0, 0), new Object[] {white, whiteScore});
		HBox leading = HBoxFactory.create(10, Pos.CENTER, null, new Insets(0, 0, 0, 0), new Object[] {winner, leader});
		VBox scorepanel = VBoxFactory.create(10, null, "-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-border-insets: 0;" + "-fx-border-radius: 0;" + "-fx-border-color: white;", new Insets(0, 0, 0, 0), new Object[] {score1, score2});
		HBox player1 = HBoxFactory.create(20, Pos.CENTER_LEFT, null, new Insets(0, 0, 0, 0), new Object[] {blackP1, p1, p1timer});
		HBox player2 = HBoxFactory.create(20, Pos.CENTER_LEFT, null, new Insets(0, 0, 0, 0), new Object[] {whiteP2, p2, p2timer});
		VBox players = VBoxFactory.create(20, null, null, new Insets(15, 15, 15, 15), new Object[] {player1, player2});
		HBox timerSlider = HBoxFactory.create(0, null, null, new Insets(0, 0, 0, 0), new Object[] {timerScale});		
		VBox timerScaleview = VBoxFactory.create(10, Pos.CENTER, null, new Insets(0, 0, 0, 0), new Object[] {setTimerLabel, timerSlider});
		VBox sub = VBoxFactory.create(20, Pos.CENTER, "-fx-background-color: radial-gradient(center 50% 50%, radius 80%, #586BA4, #314275);-fx-border-color: #FFFFFF; -fx-border-width: 2px;", new Insets(0, 0, 0, 0), new Object[] {WhosTurn, whoNext, scorepanel, leading, players, hint, undo, restart, timerScaleview});
		VBox mode1 = VBoxFactory.create(10, null, null, new Insets(0, 0, 0, 0), new Object[] {HvH, HvG});
		VBox mode2 = VBoxFactory.create(10, null, null, new Insets(0, 0, 0, 0), new Object[] {HvR, HvI});
		HBox mode = HBoxFactory.create(20, Pos.CENTER, "-fx-background-color: radial-gradient(center 50% 50%, radius 60%, #586BA4, #314275);-fx-border-color: #FFFFFF; -fx-border-width: 2px;", new Insets(15, 15, 15, 15), new Object[] {mode1, mode2});
		VBox game = VBoxFactory.create(10, Pos.CENTER, null, new Insets(0, 0, 0, 0), new Object[] {grid, mode});
		HBox root = HBoxFactory.create(10, null, null, new Insets(0, 0, 0, 0), new Object[] {game, sub});
		VBox overview = VBoxFactory.create(5, Pos.CENTER, null, new Insets(10, 10, 10, 10), new Object[] {root});
		HBox overLayer = HBoxFactory.create(0, Pos.CENTER, "-fx-background-color: radial-gradient(center 50% 50%, radius 60%, #3D4E51, #1B2021);", new Insets(0, 0, 0, 0), new Object[] {overview});

		// VIEW SCENE
		Scene scene = new Scene(overLayer);

		// VIEW STAGE
		stage.setTitle("Othello");
		stage.setScene(scene);
		// Defaults to full-screen
		stage.setMaximized(true);

		// MODEL->VIEW hookup
		othello.attach(guiView);
		othello.attach(whoNext);
		othello.attach(score);
		othello.attach(lead);

		// LAUNCH THE GUI
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
		System.exit(0);
	}
}
