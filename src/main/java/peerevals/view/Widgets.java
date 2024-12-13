package peerevals.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public interface Widgets {
    Font DEFAULT_FONT = new Font("Helvetica", 14);

    static String formatDecimal(double input) {
        return String.format("%.2f", input);
    }

    static Label makeFillerLabel() {
        Label label = new Label(" ");
        // label.setBackground(new Background(new BackgroundFill(Color.RED,
        // CornerRadii.EMPTY, Insets.EMPTY)));
        VBox.setVgrow(label, Priority.ALWAYS);
        HBox.setHgrow(label, Priority.ALWAYS);
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        return label;
    }

    static Label makeLabel(String text) {
        Label label = new Label(text);
        label.setAlignment(Pos.CENTER_LEFT);
        label.setPadding(new Insets(5));
        label.setFont(DEFAULT_FONT);
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(label, Priority.SOMETIMES);
        VBox.setVgrow(label, Priority.SOMETIMES);
        return label;
    }

    static TextArea makeTextArea() {
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setPrefRowCount(4);
        textArea.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(textArea, Priority.ALWAYS);
        return textArea;
    }
}
