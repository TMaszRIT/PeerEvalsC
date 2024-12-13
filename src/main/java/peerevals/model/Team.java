package peerevals.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class Team {
    private final Set<Student> students;

    public Team() {
        this.students = new TreeSet<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public Collection<Student> getStudents() {
        return Collections.unmodifiableSet(students);
    }

    public double getAverageOverall() {
        double total = 0.0d;
        for(Student student : students) {
            total += student.overallIReceived();
        }
        return total / students.size();
    }

    public double getAverageInclusive() {
        double total = 0.0d;
        for(Student student : students) {
            total += student.inclusiveIReceived();
        }
        return total / students.size();
    }

    public double getRecommendedFactor(Student student) {
        double overall = student.overallIReceived();
        double teamOverall = getAverageOverall();

        double base = overall / teamOverall;
        double recommended;
        if(base >= 1.1) {
            recommended = 1.1;
        } else if(base >= 1.05) {
            recommended = 1.05;
        } else if(base >= 0.88) {
            recommended = 1;
        } else if(base >= 0.8) {
            recommended = 0.88;
        } else if(base > 0.6) {
            recommended = 0.75;
        } else {
            recommended = 0.5;
        }
        return recommended;
    }

    @Override
    public String toString() {
        return "Team{" + students + "}";
    }
}
