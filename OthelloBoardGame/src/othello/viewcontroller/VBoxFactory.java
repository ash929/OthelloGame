package othello.viewcontroller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class VBoxFactory {
	public static VBox create(int spacing, Pos position, String style, Insets i, Object[] o) {
		VBox v = new VBox();
		v.setSpacing(spacing);
		v.setAlignment(position);
		v.setStyle(style);
		v.setPadding(i);
		for (int j = 0;j < o.length;j++) {
			v.getChildren().add((Node) o[j]);
		}
		return v;
	}

}
