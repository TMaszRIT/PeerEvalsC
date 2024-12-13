package peerevals.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Student implements Comparable<Student> {
    public static final String UNKNOWN_EMAIL = "email unknown";

    private final String full;
    private final String first;
    private final String last;
    private final int hashCode;
    private String email;
    private int selfOverall;
    private String selfComments;

    private final List<Eval> evaluationsOfMe;
    private final List<Eval> evaluationsIDid;

    private boolean didEvaluate;

    public Student(String full) {
        this.full = full;
        System.out.println(full);
        String[] tokens = full.split(",");
        this.first = tokens[1].trim();
        this.last = tokens[0].trim();
        this.email = UNKNOWN_EMAIL;
        this.hashCode = full.hashCode();
        this.evaluationsIDid = new ArrayList<>();
        this.evaluationsOfMe = new ArrayList<>();
        this.didEvaluate = false;
    }

    public String getFull() {
        return full;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public int getSelfOverall() {
        return selfOverall;
    }

    public boolean didEvaluate() {
        return didEvaluate;
    }

    public String getSelfComments() {
        return selfComments;
    }

    public void addSelfEvaluation(int selfOverall, String selfComments) {
        this.selfOverall = selfOverall;
        this.selfComments = selfComments;
        this.didEvaluate = true;
    }

    public void addEvaluationIDid(Eval eval) {
        evaluationsIDid.add(eval);
    }

    public void addEvaluationOfMe(Eval eval) {
        evaluationsOfMe.add(eval);
    }

    public Collection<Eval> getEvaluationsOfMe() {
        return Collections.unmodifiableCollection(evaluationsOfMe);
    }

    public double overallIReceived() {
        double total = 0.0d;
        int count = 0;
        for(Eval eval : evaluationsOfMe) {
            total += (eval.getOverall() * 2);
            count += 2;
        }
        if(didEvaluate) {
            total += selfOverall;
            count += 1;
        }

        return total / count;
    }

    public double inclusiveIReceived() {
        double total = 0.0d;
        int count = 0;
        for(Eval eval : evaluationsOfMe) {
            total += eval.getInclusive();
            count += 1;
        }

        return total / count;
    }

    public double overallIGave() {
        double total = 0.0d;
        int count = 0;
        for(Eval eval : evaluationsIDid) {
            total += eval.getOverall();
            count += 1;
        }

        return total / count;
    }

    public double inclusiveIGave() {
        double total = 0.0d;
        int count = 0;
        for(Eval eval : evaluationsIDid) {
            total += eval.getInclusive();
            count += 1;
        }

        return total / count;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof Student) {
            Student other = (Student)o;
            return this.full.equals(other.full);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return last + ", " + first + " (" + email + ")";
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public int compareTo(Student o) {
        int result = this.last.compareTo(o.last);
        return result != 0 ? result : this.first.compareTo(o.first);
    }
}
