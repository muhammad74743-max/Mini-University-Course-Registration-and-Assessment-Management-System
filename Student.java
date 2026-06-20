import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    private static final long serialVersionUID = 1L;
    private String program;
    private int semester;
    private List<Registration> registrations;

    public Student(String id, String name, String contactNumber, String program, int semester) {
        super(id, name, contactNumber);
        this.program = program;
        this.semester = semester;
        this.registrations = new ArrayList<>();
    }

    public String getProgram() { return program; }
    public int getSemester() { return semester; }
    public void setProgram(String program) { this.program = program; }
    public void setSemester(int semester) { this.semester = semester; }
    public List<Registration> getRegistrations() { return registrations; }

    public void addRegistration(Registration r) { registrations.add(r); }
    public void removeRegistration(Registration r) { registrations.remove(r); }

    public boolean isAlreadyRegistered(Course course) {
        for (Registration r : registrations) {
            if (r.getCourse().getCourseCode().equalsIgnoreCase(course.getCourseCode())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRole() { return "Student"; }

    @Override
    public String toString() {
        return super.toString() + String.format(" Program: %-10s Semester: %d", program, semester);
    }
}
