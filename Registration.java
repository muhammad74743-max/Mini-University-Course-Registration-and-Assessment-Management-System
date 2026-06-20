import java.io.Serializable;
import java.time.LocalDate;

// Registration is a Composition: a Registration cannot exist without a Student and Course (created together, destroyed together logically)
public class Registration implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int counter = 1000;

    private String registrationId;
    private Student student;
    private Course course;
    private LocalDate registrationDate;
    private Result result;

    public Registration(Student student, Course course) {
        this.registrationId = "REG" + (++counter);
        this.student = student;
        this.course = course;
        this.registrationDate = LocalDate.now();
        this.result = new Result();
    }

    public String getRegistrationId() { return registrationId; }
    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    public Result getResult() { return result; }

    @Override
    public String toString() {
        return String.format("%s | %s registered in %s (%s) on %s",
                registrationId, student.getName(), course.getCourseCode(), course.getCourseTitle(), registrationDate);
    }
}
