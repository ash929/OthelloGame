package othello.viewcontroller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class HBoxFactory {
	public static HBox create(int spacing, Pos position, String style, Insets i, Object[] o) {
		HBox h = new HBox();
		h.setSpacing(spacing);
		h.setAlignment(position);
		h.setStyle(style);
		h.setPadding(i);
		for (int j = 0;j < o.length;j++) {
			h.getChildren().add((Node) o[j]);
		}
		return h;
	}

}
