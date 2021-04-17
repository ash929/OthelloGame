package othello.util;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {
   private List<Memento> mementoList = new ArrayList<Memento>();

   public void add(Memento state){
      mementoList.add(state);
   }
   
   public void remove() {
	   if (mementoList.size() > 0)
		   mementoList.remove(mementoList.size()-1);
   }
   
   public void removeAll() {
	   mementoList = new ArrayList<Memento>();
   }

   public Memento get(int index){
      return mementoList.get(index);
   }
   
   public int size() {
	   return mementoList.size();
   }
}
