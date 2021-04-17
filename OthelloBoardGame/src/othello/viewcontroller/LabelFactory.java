package othello.viewcontroller;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LabelFactory {
	public static Label create(String txt, String fontName, int size, boolean bold) {
		Label label = new Label(txt);
		label.setTextFill(Color.web("#EDCCC4"));
		if (bold)
			label.setFont(Font.font(fontName, FontWeight.BOLD, size));
		else
			label.setFont(Font.font(fontName, size));
		return label;
	}

}
