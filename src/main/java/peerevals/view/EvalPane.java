package peerevals.view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import peerevals.model.Eval;
import peerevals.model.Student;

public class EvalPane extends GridPane {
    // private static final Font DEFAULT_FONT = new Font("Helvetica", 14);

    public EvalPane(Eval eval) {
        setPadding(new Insets(20));
        Student evaluator = eval.getEvaluator();
        Label name = Widgets.makeLabel("Evaluator: " + evaluator.getFull());

        add(name, 0, 0, 4, 1);
        add(Widgets.makeLabel("Overall: " + eval.getOverall()), 0, 1);
        add(Widgets.makeLabel("Inclusive:" + eval.getInclusive()), 1, 1);
        add(Widgets.makeLabel("Avg. Overall:" + evaluator.overallIGave()),
                0, 2);
        add(Widgets.makeLabel("Avg. Inclusive:" + evaluator.inclusiveIGave()),
                1, 2);

        add(Widgets.makeLabel("Private Comments:"), 0, 3, 4, 1);
        TextArea privateComments = Widgets.makeTextArea();
        privateComments.setText(eval.getPrivateComments());
        add(privateComments, 0, 4, 5, 1);

        add(Widgets.makeLabel("Public Comments:"), 0, 5, 4, 1);
        TextArea publicComments = Widgets.makeTextArea();
        publicComments.setText(eval.getPublicComments());
        add(publicComments, 0, 6, 4, 1);
    }
}
