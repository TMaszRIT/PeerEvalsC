module peerevals {
    requires transitive javafx.graphics;
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;

    opens peerevals to javafx.fxml;
    exports peerevals;
    opens peerevals.model to javafx.fxml;
    exports peerevals.model;
    opens peerevals.view to javafx.fxml;
    exports peerevals.view;
}
