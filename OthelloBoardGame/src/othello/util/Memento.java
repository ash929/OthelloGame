package othello.util;

import othello.model.Othello;

public class Memento {
	   private Othello state;

	   public Memento(Othello state){
	      this.state = state;
	   }

	   public Othello getState(){
	      return state;
	   }	
	}
