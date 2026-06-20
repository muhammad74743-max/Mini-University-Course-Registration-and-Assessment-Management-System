import java.io.Serializable;

public abstract class Assessment implements Serializable {
    private static final long serialVersionUID = 1L;
    protected double obtainedMarks;
    protected double maxMarks;

    public Assessment(double maxMarks) {
        this.maxMarks = maxMarks;
        this.obtainedMarks = 0;
    }

    public double getMaxMarks() { return maxMarks; }
    public double getObtainedMarks() { return obtainedMarks; }

    // Overloaded methods: setMarks(double) and setMarks(double, boolean) demonstrate overloading
    public void setMarks(double marks) throws InvalidMarksException {
        if (marks < 0 || marks > maxMarks) {
            throw new InvalidMarksException("Invalid marks for " + getType() + ": must be between 0 and " + maxMarks);
        }
        this.obtainedMarks = marks;
    }

    public void setMarks(double marks, boolean roundOff) throws InvalidMarksException {
        if (roundOff) marks = Math.round(marks);
        setMarks(marks);
    }

    public abstract String getType();

    public double weightedScore() {
        return obtainedMarks; // default; overridden by subclasses if weighting differs
    }

    @Override
    public String toString() {
        return String.format("%-12s: %.1f / %.1f", getType(), obtainedMarks, maxMarks);
    }
}
