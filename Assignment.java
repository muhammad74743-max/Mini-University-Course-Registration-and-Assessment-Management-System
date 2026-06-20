public class Assignment extends Assessment {
    private static final long serialVersionUID = 1L;
    private int assignmentNumber;

    public Assignment(double maxMarks, int assignmentNumber) {
        super(maxMarks);
        this.assignmentNumber = assignmentNumber;
    }

    public int getAssignmentNumber() { return assignmentNumber; }

    @Override
    public String getType() { return "Assignment " + assignmentNumber; }
}
