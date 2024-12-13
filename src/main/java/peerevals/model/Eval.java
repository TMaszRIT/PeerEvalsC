package peerevals.model;

public class Eval {
    private final Student evaluator;
    private final Student student;
    private final int overall;
    private final int inclusive;
    private final String privateComments;
    private final String publicComments;

    public Eval(Student evaluator, Student student, int overall,
                int inclusive, String privateComments,
                String publicComments) {

        this.evaluator = evaluator;
        this.student = student;
        this.overall = overall;
        this.inclusive = inclusive;
        this.privateComments = privateComments;
        this.publicComments = publicComments;
    }

    public Student getEvaluator() {
        return evaluator;
    }

    public Student getStudent() {
        return student;
    }

    public int getOverall() {
        return overall;
    }

    public int getInclusive() {
        return inclusive;
    }

    public String getPublicComments() {
        return publicComments;
    }

    public String getPrivateComments() {
        return privateComments;
    }

    @Override
    public String toString() {
        return "Evaluator: " + this.getEvaluator() + "\n"
                + "  overall: " + this.overall + "\n"
                + "  inclusive: " + this.inclusive + "\n"
                + "  public comments: " + this.publicComments + "\n"
                + "  private comments: " + this.privateComments + "\n";
    }
}
