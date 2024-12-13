package peerevals.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import peerevals.model.Course;
import peerevals.model.EvalParser;
import peerevals.model.Student;
import peerevals.model.Team;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.opencsv.exceptions.CsvValidationException;

public class PeerEvalGUI extends Application {
    private BorderPane main;
    private StudentPane studentPane;
    private ListView<Student> students;

    private Course course;

    @SuppressWarnings("unused")
    @Override
    public void start(Stage stage) throws Exception {
        main = new BorderPane();
        main.setTop(buildMenuBar(stage));

        studentPane = new StudentPane();
        main.setCenter(studentPane);

        students = new ListView<>();
        students.setEditable(false);
        students.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        students.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {
            studentPane.setStudent(nv, course.getTeam(nv));
        });
        main.setLeft(students);

        List<String> parameters = getParameters().getRaw();
        if(parameters.size() > 0) {
            openFile(parameters.get(0));
        }

        stage.setTitle("Peer Evaluations");
        stage.setScene(new Scene(main));
        stage.show();
    }

    @SuppressWarnings("unused")
    private MenuBar buildMenuBar(Stage stage) {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem open = new MenuItem("Open...");
        MenuItem quit = new MenuItem("Quit");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Response CSV File");
        fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV Files", "*.csv"));

        //open.setOnAction((e) -> System.out.println("Clicked!"));
        open.setOnAction((e) -> {
            File selectedFile = fileChooser.showOpenDialog(stage);
 
            if (selectedFile != null) {
                openFile(selectedFile.getPath());
            }
        });

        quit.setOnAction((e) -> {
            Platform.exit();
        });

        fileMenu.getItems().addAll(
            open,
            quit
        );

        menuBar.getMenus().addAll(
            fileMenu
        );

        return menuBar;
    }

    private void openFile(String filename) {
        try {
            course = EvalParser.parseEvals(filename);
            students.getItems().clear();
            if(course.numberOfStudents() > 0) {
                for(Student student : course) {
                    students.getItems().add(student);
                }
                Student student = students.getItems().get(0);
                Team team = course.getTeam(student);
                studentPane.setStudent(student, team);
            }
        } catch(IOException | CsvValidationException e) {}
    }

    public static void main(String[] args) {
        launch(args);
    }
}
