import java.io.*;
import java.util.*;

public class UniversitySystem {
    private Map<String, Student> students;
    private Map<String, Course> courses;
    private Map<String, Instructor> instructors;
    private List<Registration> registrations;

    private static final String DATA_FILE = "university_data.ser";

    public UniversitySystem() {
        students = new LinkedHashMap<>();
        courses = new LinkedHashMap<>();
        instructors = new LinkedHashMap<>();
        registrations = new ArrayList<>();
    }

    // ---------- Student Management ----------
    public void addStudent(Student s) {
        students.put(s.getId(), s);
    }

    // Method overloading: update student with full details OR just contact number
    public void updateStudent(String id, String name, String program, int semester, String contact) throws RecordNotFoundException {
        Student s = findStudentById(id);
        s.setName(name);
        s.setProgram(program);
        s.setSemester(semester);
        s.setContactNumber(contact);
    }

    public void updateStudent(String id, String contact) throws RecordNotFoundException {
        Student s = findStudentById(id);
        s.setContactNumber(contact);
    }

    public Student findStudentById(String id) throws RecordNotFoundException {
        Student s = students.get(id);
        if (s == null) throw new RecordNotFoundException("Student not found with ID: " + id);
        return s;
    }

    public List<Student> searchStudentByName(String name) {
        List<Student> result = new ArrayList<>();
        for (Student s : students.values()) {
            if (s.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(s);
            }
        }
        return result;
    }

    public Collection<Student> getAllStudents() { return students.values(); }

    // ---------- Instructor Management ----------
    public void addInstructor(Instructor i) { instructors.put(i.getId(), i); }
    public Collection<Instructor> getAllInstructors() { return instructors.values(); }

    // ---------- Course Management ----------
    public void addCourse(Course c) { courses.put(c.getCourseCode(), c); }

    public Course findCourseByCode(String code) throws RecordNotFoundException {
        Course c = courses.get(code);
        if (c == null) throw new RecordNotFoundException("Course not found with code: " + code);
        return c;
    }

    public List<Course> searchCourseByTitle(String title) {
        List<Course> result = new ArrayList<>();
        for (Course c : courses.values()) {
            if (c.getCourseTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(c);
            }
        }
        return result;
    }

    public void assignInstructorToCourse(String courseCode, String instructorId) throws RecordNotFoundException {
        Course c = findCourseByCode(courseCode);
        Instructor i = instructors.get(instructorId);
        if (i == null) throw new RecordNotFoundException("Instructor not found with ID: " + instructorId);
        c.assignInstructor(i);
    }

    public Collection<Course> getAllCourses() { return courses.values(); }

    // ---------- Registration Module ----------
    public Registration registerStudentInCourse(String studentId, String courseCode)
            throws RecordNotFoundException, DuplicateRegistrationException {
        Student s = findStudentById(studentId);
        Course c = findCourseByCode(courseCode);

        if (s.isAlreadyRegistered(c)) {
            throw new DuplicateRegistrationException(
                    "Student " + s.getName() + " is already registered in " + c.getCourseCode());
        }

        Registration r = new Registration(s, c);
        s.addRegistration(r);
        c.addRegistration(r);
        registrations.add(r);
        return r;
    }

    public void dropCourse(String studentId, String courseCode) throws RecordNotFoundException {
        Student s = findStudentById(studentId);
        Course c = findCourseByCode(courseCode);
        Registration toRemove = null;
        for (Registration r : s.getRegistrations()) {
            if (r.getCourse().getCourseCode().equalsIgnoreCase(courseCode)) {
                toRemove = r;
                break;
            }
        }
        if (toRemove == null) throw new RecordNotFoundException("Registration not found for drop request.");
        s.removeRegistration(toRemove);
        c.removeRegistration(toRemove);
        registrations.remove(toRemove);
    }

    public List<Registration> getRegistrationsForStudent(String studentId) throws RecordNotFoundException {
        Student s = findStudentById(studentId);
        return s.getRegistrations();
    }

    // ---------- Assessment Management ----------
    public void recordMarks(String studentId, String courseCode, Assessment assessment)
            throws RecordNotFoundException {
        Registration target = null;
        for (Registration r : registrations) {
            if (r.getStudent().getId().equals(studentId) && r.getCourse().getCourseCode().equalsIgnoreCase(courseCode)) {
                target = r;
                break;
            }
        }
        if (target == null) throw new RecordNotFoundException("Registration not found for marks entry.");
        target.getResult().addAssessment(assessment);
    }

    // ---------- Reports ----------
    public List<Student> getPassedStudents(String courseCode) throws RecordNotFoundException {
        List<Student> passed = new ArrayList<>();
        Course c = findCourseByCode(courseCode);
        for (Registration r : c.getRegistrations()) {
            if (r.getResult().isPassed()) passed.add(r.getStudent());
        }
        return passed;
    }

    public List<Student> getFailedStudents(String courseCode) throws RecordNotFoundException {
        List<Student> failed = new ArrayList<>();
        Course c = findCourseByCode(courseCode);
        for (Registration r : c.getRegistrations()) {
            if (!r.getResult().isPassed()) failed.add(r.getStudent());
        }
        return failed;
    }

    public Registration getTopPerformer(String courseCode) throws RecordNotFoundException {
        Course c = findCourseByCode(courseCode);
        Registration top = null;
        for (Registration r : c.getRegistrations()) {
            if (top == null || r.getResult().percentage() > top.getResult().percentage()) {
                top = r;
            }
        }
        if (top == null) throw new RecordNotFoundException("No registrations found for course: " + courseCode);
        return top;
    }

    public String studentWiseReport(String studentId) throws RecordNotFoundException {
        Student s = findStudentById(studentId);
        StringBuilder sb = new StringBuilder();
        sb.append("Result Report for ").append(s.getName()).append(" (").append(s.getId()).append(")\n");
        for (Registration r : s.getRegistrations()) {
            sb.append(" Course: ").append(r.getCourse().getCourseCode()).append(" - ").append(r.getCourse().getCourseTitle()).append("\n");
            sb.append(r.getResult().generateReport());
        }
        return sb.toString();
    }

    // ---------- File Handling / Serialization ----------
    @SuppressWarnings("unchecked")
    public void saveData() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(students);
            oos.writeObject(courses);
            oos.writeObject(instructors);
            oos.writeObject(registrations);
        }
    }

    @SuppressWarnings("unchecked")
    public void loadData() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            students = (Map<String, Student>) ois.readObject();
            courses = (Map<String, Course>) ois.readObject();
            instructors = (Map<String, Instructor>) ois.readObject();
            registrations = (List<Registration>) ois.readObject();
        }
    }
}
