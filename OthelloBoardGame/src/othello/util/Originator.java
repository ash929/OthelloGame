package othello.util;

import othello.model.Othello;

public class Originator {
	   private Othello state;

	   public void setState(Othello state){
	      this.state = state;
	   }

	   public Othello getState(){
	      return state;
	   }

	   public Memento saveStateToMemento(){
	      return new Memento(state);
	   }

	   public void getStateFromMemento(Memento memento){
	      state = memento.getState();
	   }
	}
