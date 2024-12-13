package peerevals.model;



import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class EvalParser {
    public static Course parseEvals(String filename)
            throws IOException, CsvValidationException {

        try(FileReader fr = new FileReader(filename);
            CSVReader reader = new CSVReader(fr)) {
            Course course = new Course();

            // skip header
            reader.readNext();

            String[] record = reader.readNext();
            while (record != null) {
                String email = record[1];
                String full = record[2];
                int selfOverall = Integer.parseInt(record[3]);
                String selfComments = record[4];

                Student student = course.getStudent(full);
                student.setEmail(email);
                student.addSelfEvaluation(selfOverall, selfComments);

                int index = 5;
                while(index < record.length) {
                    parseEval(course, student, record, index);
                    index += 5;
                }

                record = reader.readNext();
            }
            return course;
        }
    }

    private static Eval parseEval(Course course, Student evaluator, String[] record, int index) {
        String full = record[index];
        int overall = Integer.parseInt(record[index+1]);
        int inclusive = Integer.parseInt(record[index+2]);
        String privateComments = record[index+3];
        String publicComments = record[index+4];

        Student student = course.getStudent(full);
        if(student == null) {
            return null;
        } else {
            Eval eval = new Eval(evaluator, student, overall, inclusive, privateComments, publicComments);

            evaluator.addEvaluationIDid(eval);
            student.addEvaluationOfMe(eval);

            course.addToTeam(evaluator, student);

            return eval;
        }
    }

    public static void main(String[] args) throws IOException, CsvValidationException {
        Course course = parseEvals("data/2241/R1/SWEN-262_R1_Peer_Feedback_2241.csv");
        for (Student student : course) {
            System.out.println(student);
        }
        System.out.println(course.numberOfStudents());
        System.out.println(course.numberOfTeams());
        for(Team team : course.getTeams()) {
            System.out.println(team);
        }
    }
}