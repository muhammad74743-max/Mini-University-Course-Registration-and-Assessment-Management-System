public class Exam extends Assessment {
    private static final long serialVersionUID = 1L;
    private String examType; // "Midterm" or "Final"

    public Exam(double maxMarks, String examType) {
        super(maxMarks);
        this.examType = examType;
    }

    @Override
    public String getType() { return examType; }

    @Override
    public double weightedScore() {
        // Final exam polymorphically carries more weight in discussion (kept simple: same value)
        return obtainedMarks;
    }
}
