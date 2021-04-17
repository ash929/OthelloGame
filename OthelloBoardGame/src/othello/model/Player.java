package othello.model;

public class Player {
	protected Othello othello;
	protected char player;
	private PlayerMoveStrategy strategy;

	public Player(Othello othello, char player) {
		this.othello=othello;
		this.player=player;
		this.strategy = null;
	}
	
	public void setStrategy(char gamemode) {
		if(gamemode == 'R') {
			this.strategy = new PlayerRandom(this.othello, OthelloBoard.P2);
		}else if(gamemode == 'G') {
			this.strategy = new PlayerGreedy(this.othello, OthelloBoard.P2);
		}else if(gamemode == 'I') {
				this.strategy = new PlayerImproved(this.othello, OthelloBoard.P2);
		}
	}
	
	public char getPlayer() {
		return this.player;
	}
	public Move getMove() {
		return this.strategy.getMove();	
	}
	
}
