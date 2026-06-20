import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Result implements Serializable, Reportable {
    private static final long serialVersionUID = 1L;
    private List<Assessment> assessments;
    private double totalMarks;
    private double maxTotal;
    private char grade;

    public Result() {
        assessments = new ArrayList<>();
        totalMarks = 0;
        maxTotal = 0;
    }

    public void addAssessment(Assessment a) {
        assessments.add(a);
        calculate();
    }

    public List<Assessment> getAssessments() { return assessments; }
    public double getTotalMarks() { return totalMarks; }
    public double getMaxTotal() { return maxTotal; }
    public char getGrade() { return grade; }

    private void calculate() {
        totalMarks = 0;
        maxTotal = 0;
        for (Assessment a : assessments) {
            totalMarks += a.weightedScore();
            maxTotal += a.getMaxMarks();
        }
        grade = computeGrade(percentage());
    }

    public double percentage() {
        if (maxTotal == 0) return 0;
        return (totalMarks / maxTotal) * 100.0;
    }

    private char computeGrade(double pct) {
        if (pct >= 85) return 'A';
        else if (pct >= 75) return 'B';
        else if (pct >= 65) return 'C';
        else if (pct >= 50) return 'D';
        else return 'F';
    }

    public boolean isPassed() { return grade != 'F'; }

    @Override
    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        for (Assessment a : assessments) {
            sb.append("   ").append(a.toString()).append("\n");
        }
        sb.append(String.format("   Total: %.1f / %.1f  | Percentage: %.2f%%  | Grade: %c\n",
                totalMarks, maxTotal, percentage(), grade));
        return sb.toString();
    }
}
