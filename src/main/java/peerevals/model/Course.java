package peerevals.model;

import java.util.*;

public class Course implements Iterable<Student> {
    private final Map<String, Student> students;
    private final Map<Student, Team> teams;

    public Course() {
        students = new TreeMap<>();
        teams = new TreeMap<>();
    }

    public int numberOfStudents() {
        return students.size();
    }

    public int numberOfTeams() {
        return teams.size();
    }

    public Student getStudent(String full) {
        if(full.startsWith("I ")) {
            return null;
        }else if(!students.containsKey(full)) {
            // first time seeing this student
            Student student = new Student(full);
            students.put(full, student);
        }
        return students.get(full);
    }

    public void addToTeam(Student one, Student two) {
        if(teams.containsKey(one)) {
            Team team = teams.get(one);
            team.addStudent(two);
            teams.put(two, team);
        } else if(teams.containsKey(two)) {
            Team team = teams.get(two);
            team.addStudent(one);
            teams.put(one, team);
        } else {
            Team team = new Team();
            team.addStudent(one);
            team.addStudent(two);
            teams.put(one, team);
            teams.put(two, team);
        }
    }

    public Team getTeam(Student student) {
        return teams.get(student);
    }

    public Collection<Team> getTeams() {
        return teams.values();
    }

    @Override
    public Iterator<Student> iterator() {
        return new StudentIterator();
    }

    private class StudentIterator implements Iterator<Student> {
        private final Iterator<String> keys;

        StudentIterator() {
            keys = students.keySet().iterator();
        }

        @Override
        public boolean hasNext() {
            return keys.hasNext();
        }

        @Override
        public Student next() {
            return students.get(keys.next());
        }
    }
}
