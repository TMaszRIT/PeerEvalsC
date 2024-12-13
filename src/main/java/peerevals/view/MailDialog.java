package peerevals.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import peerevals.model.EvalMessage;
import peerevals.model.Student;
import peerevals.model.Team;

// import javax.mail.MessagingException;
// import java.io.IOException;

public class MailDialog extends Dialog<ButtonType> {
    private static final MailDialog INSTANCE = new MailDialog();

    private final TextField to;
    private final TextField from;
    private final TextField cc;
    private final TextField subject;
    private final TextArea message;

    private MailDialog() {
        setTitle("Compose Email");
        to = makeTextField();
        from = makeTextField();
        cc = makeTextField();
        subject = makeTextField();
        message = new TextArea();
        message.setPrefRowCount(40);
        message.setPrefColumnCount(80);
        message.setWrapText(true);

        VBox box = new VBox();
        box.setSpacing(10);
        box.setPadding(new Insets(10));
        box.getChildren().addAll(
            makeLabeledTextField(to, "To: "),
            makeLabeledTextField(from, "From: "),
            makeLabeledTextField(cc, "CC: "),
            makeLabeledTextField(subject, "Subject: "),
            message
        );
        getDialogPane().setContent(box);

        getDialogPane().getButtonTypes().addAll(
            ButtonType.OK,
            ButtonType.CANCEL
        );
    }

    public static void showAndWait(Student student, double rating,
                                               Team team, double factor) {
        INSTANCE.prompt(student, rating, team, factor);
    }

    @SuppressWarnings("unused")
    private void prompt(Student student, double rating,
                                            Team team, double factor) {
        to.setText(student.getEmail());
        subject.setText("R1 Peer Evaluations");
        String body = EvalMessage.composeMessage(student, rating, team, factor);
        message.setText(body);

        showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> {
                    // try {
                    //     Mailer.sendMail("data/email.properties",
                    //             to.getText(), subject.getText(),
                    //             message.getText());
                    // } catch(MessagingException | IOException e) {
                    //     e.printStackTrace();
                    // }
                });
    }

    private static HBox makeLabeledTextField(TextField field, String text) {
        HBox.setHgrow(field, Priority.ALWAYS);
        Label label = Widgets.makeLabel(text);
        label.setPrefWidth(100);
        HBox box = new HBox();
        box.getChildren().addAll(
            label,
            field
        );
        return box;
    }

    private static TextField makeTextField() {
        TextField field = new TextField();
        field.setMaxWidth(Double.MAX_VALUE);
        return field;
    }
}
