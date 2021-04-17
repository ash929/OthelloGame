package othello.viewcontroller;

import javafx.scene.control.Button;
import javafx.scene.shape.Circle;

public class ButtonFactory {
	public static Button create(int width, int height, String border, boolean isCirc, String style, String text) {
		Button button = new Button();
		if (isCirc)
			button.setShape(new Circle(60));
		button.setPrefSize(width, height);
		if (style != null)
			button.setStyle(style);
		button.setText(text);
		return button;
	}

}
