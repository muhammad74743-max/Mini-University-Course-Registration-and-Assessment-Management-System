public class Quiz extends Assessment {
    private static final long serialVersionUID = 1L;
    private int quizNumber;

    public Quiz(double maxMarks, int quizNumber) {
        super(maxMarks);
        this.quizNumber = quizNumber;
    }

    public int getQuizNumber() { return quizNumber; }

    @Override
    public String getType() { return "Quiz " + quizNumber; }
}
