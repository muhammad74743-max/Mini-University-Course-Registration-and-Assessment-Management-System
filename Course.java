import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable, Reportable {
    private static final long serialVersionUID = 1L;
    private String courseCode;
    private String courseTitle;
    private int creditHours;
    private Instructor instructor; // aggregation: course can exist without instructor assigned yet
    private List<Registration> registrations;

    public Course(String courseCode, String courseTitle, int creditHours) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.creditHours = creditHours;
        this.registrations = new ArrayList<>();
    }

    public String getCourseCode() { return courseCode; }
    public String getCourseTitle() { return courseTitle; }
    public int getCreditHours() { return creditHours; }
    public Instructor getInstructor() { return instructor; }
    public List<Registration> getRegistrations() { return registrations; }

    public void assignInstructor(Instructor instructor) { this.instructor = instructor; }
    public void addRegistration(Registration r) { registrations.add(r); }
    public void removeRegistration(Registration r) { registrations.remove(r); }

    @Override
    public String generateReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("Course: ").append(courseCode).append(" - ").append(courseTitle).append("\n");
        sb.append("Registered Students: ").append(registrations.size()).append("\n");
        for (Registration r : registrations) {
            sb.append("   -> ").append(r.getStudent().getName()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        String instr = (instructor == null) ? "Not Assigned" : instructor.getName();
        return String.format("Code: %-8s Title: %-25s Credits: %d Instructor: %s",
                courseCode, courseTitle, creditHours, instr);
    }
}
