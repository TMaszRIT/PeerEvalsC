package peerevals.model;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;



public class EvalMessage {

    public static final String formatDecimal(double d) {
            return String.format("%.2f", d);
    }

    public static String composeMessage(Student student, double rating, Team team,
                                        double factor) {
        StringWriter sw = new StringWriter();
        PrintWriter writer = new PrintWriter(sw);
        
        writer.println("Dear " + student.getFirst() + ",\n");

        writer.print("Your overall rating based on your peer evaluations (including your self-assessment) is ");
        writer.print(formatDecimal(rating));
        writer.println(" out of 5");
        writer.println();

        double percentage = (factor - 1.0) * 100;

        writer.print("Your team's average rating was " + formatDecimal(team.getAverageOverall()) + ". ");

        if(percentage != 0.0) {
            writer.print(" This means that your grade MAY be adjusted by " + formatDecimal(percentage) + "%.");
            writer.print(" For example, if your team's overall grade is 85%, your grade may be ");
            writer.print(formatDecimal(85 * factor) + "%. ");
        } else {
            writer.print(" This means that your grade will NOT be adjusted based on this feedback. ");
        }

        writer.println("Of course, your grader will consider other factors such as your contributions to the code.");
        writer.println();

        if(!student.didEvaluate()) {
            writer.print("Because you did not submit an evaluation of your team, your individual factor will be ");
            writer.println("reduced by 5% to " + formatDecimal(factor - 0.05));
            writer.println();
        }

        String selfComments = student.getSelfComments();
        if(selfComments != null && selfComments.trim().length() != 0) {
            writer.println("Here is what you had to say about your own contributions: ");
            writer.println();
            writer.println(selfComments);
            writer.println();
        }

        writer.println("Here are the comments that your team had to share regarding your contributions:");
        writer.println();

        for(Eval eval : student.getEvaluationsOfMe()) {
            writer.println(eval.getPublicComments());
            writer.println();
        }

        writer.println("Thanks, and let me know if you have any questions.");
        writer.println("\nTom.");

        addToCSVFile(student, factor);

        writer.flush();
    
        return sw.toString();
    }


    public static void addToCSVFile(Student student, double factor) {

        // for CSV output
        File file = new File("Adjustments.csv");

        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file, true);
            // create CSVWriter object filewriter object as parameter
            CSVWriter CSVwriter = new CSVWriter(outputfile);

            String[] studentData = { student.toString(), Double.toString(factor) };
            CSVwriter.writeNext(studentData);
            CSVwriter.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static String composeMessageOld(Student student, double rating, Team team,
                                        double factor) {
        StringWriter sw = new StringWriter();
        PrintWriter writer = new PrintWriter(sw);

        writer.println("Dear " + student.getFirst() + ",\n");

        writer.print("Your overall rating based on your peer evaluations (including your self-assessment) is ");
        writer.print(formatDecimal(rating));
        writer.println(" out of 5");
        writer.println();

        writer.print("Your team's average rating was " + formatDecimal(team.getAverageOverall()));
        writer.print(" which means that your current team project factor is " + formatDecimal(factor));
        writer.print(". This factor has been used to adjust your overall grade on the project.");
        writer.print(" For example, if your team's overall grade is 85%, your grade will be ");
        writer.println(formatDecimal(85 * factor) + "%.");
        writer.println();

        if(!student.didEvaluate()) {
            writer.print("Because you did not submit an evaluation of your team, your individual factor will be ");
            writer.println("reduced by 5% to " + formatDecimal(factor - 0.05));
            writer.println();
        }

        String selfComments = student.getSelfComments();
        if(selfComments != null && selfComments.trim().length() != 0) {
            writer.println("Here is what you had to say about your own contributions: ");
            writer.println();
            writer.println(selfComments);
            writer.println();
        }

        writer.println("Here are the comments that your team had to share regarding your contributions:");
        writer.println();

        for(Eval eval : student.getEvaluationsOfMe()) {
            writer.println(eval.getPublicComments());
            writer.println();
        }

        writer.println("Thanks, and let me know if you have any questions.");
        writer.println("\nTom.");

        writer.flush();
        return sw.toString();
    }
}
