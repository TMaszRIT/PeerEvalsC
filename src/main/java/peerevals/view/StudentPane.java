package peerevals.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import peerevals.model.Eval;
import peerevals.model.Student;
import peerevals.model.Team;

public class StudentPane extends VBox implements Widgets {
    private final Label name;
    private final TextField email;
    private final Label selfOverall;
    private final Label selfTeam;
    private final Label overall;
    private final Label inclusive;
    private final Label teamOverall;
    private final Label teamInclusive;
    private final TextArea selfComments;

    private final Label recommended;
    private final TextField actual;

    private final ListView<EvalPane> evals;

    private Student student;
    private Team team;

    @SuppressWarnings("unused")
    public StudentPane() {
        setPadding(new Insets(10));
        setSpacing(10);
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        name = Widgets.makeLabel("                    ");
        email = new TextField("                   ");
        email.setMaxWidth(Double.MAX_VALUE);
        selfComments = Widgets.makeTextArea();

        selfOverall = Widgets.makeLabel("--");
        selfTeam = Widgets.makeLabel("--");
        overall = Widgets.makeLabel("--");
        inclusive = Widgets.makeLabel("--");
        teamOverall = Widgets.makeLabel("--");
        teamInclusive = Widgets.makeLabel("--");

        HBox nameBox = new HBox();
        nameBox.getChildren().addAll(
            Widgets.makeLabel("Name:"),
            name,
            Widgets.makeFillerLabel(),
            Widgets.makeLabel("Email:"),
            email,
            Widgets.makeFillerLabel()
        );

        GridPane ratingsPane = new GridPane();
        ratingsPane.add(Widgets.makeLabel("Gave Self:"), 0, 0);
        ratingsPane.add(selfOverall, 1, 0);
        ratingsPane.add(Widgets.makeLabel(" Gave Team:"), 2, 0);
        ratingsPane.add(selfTeam, 3, 0);
        ratingsPane.add(Widgets.makeLabel("Overall Received:"), 0, 1);
        ratingsPane.add(overall, 1, 1);
        ratingsPane.add(Widgets.makeLabel("Inclusive Received:"), 2, 1);
        ratingsPane.add(inclusive, 3, 1);
        ratingsPane.add(Widgets.makeLabel("Team Overall:"), 0, 2);
        ratingsPane.add(teamOverall, 1, 2);
        ratingsPane.add(Widgets.makeLabel("Team Inclusive:"), 2, 2);
        ratingsPane.add(teamInclusive, 3, 2);

        evals = new ListView<>();
        evals.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        evals.setPrefWidth(600);
        VBox.setVgrow(evals, Priority.ALWAYS);

        recommended = Widgets.makeLabel("--");
        actual = new TextField("          ");
        Button compose = new Button("Compose Email");
        compose.setOnAction((e) -> {
            composeAndSend();
        });

        HBox controls = new HBox();
        controls.setSpacing(10);
        controls.getChildren().addAll(
            Widgets.makeFillerLabel(),
            Widgets.makeLabel("Recommended:"),
            recommended,
            Widgets.makeLabel("Actual:"),
            actual,
            compose
        );

        getChildren().addAll(
            nameBox,
            ratingsPane,
            selfComments,
            controls,
            evals
        );
    }

    public void setStudent(Student student, Team team) {
        this.student = student;
        this.team = team;
        // update view
        name.setText(student.getFull());
        email.setText(student.getEmail());
        if(student.didEvaluate()) {
            selfOverall.setText(Integer.toString(student.getSelfOverall()));
            selfTeam.setText(Widgets.formatDecimal(student.overallIGave()));
            selfComments.setText(student.getSelfComments());
        } else {
            selfOverall.setText("--");
            selfTeam.setText("--");
            selfComments.setText("");
        }
        overall.setText(Widgets.formatDecimal(student.overallIReceived()));
        inclusive.setText(Widgets.formatDecimal(student.inclusiveIReceived()));
        teamOverall.setText(Widgets.formatDecimal(team.getAverageOverall()));
        teamInclusive.setText(Widgets.formatDecimal(team.getAverageInclusive()));


        String rating = Widgets.formatDecimal(team.getRecommendedFactor(student));
        recommended.setText(rating);
        actual.setText(rating);

        evals.getItems().clear();
        boolean gray = true;
        for(Eval eval : student.getEvaluationsOfMe()) {
            EvalPane pane = new EvalPane(eval);
            if(gray) {
                pane.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY,
                        Insets.EMPTY)));
            }
            gray = !gray;
            evals.getItems().add(pane);
        }
        evals.scrollTo(0);
    }

    private void composeAndSend() {
        student.setEmail(email.getText());
        double factor = Double.parseDouble(actual.getText());
        MailDialog.showAndWait(student, student.overallIReceived(),
                team, factor);
    }
}
