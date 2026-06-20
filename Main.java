import java.util.*;

public class Main {
    static UniversitySystem system = new UniversitySystem();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        loadSampleData();
        boolean running = true;
        while (running) {
            printMenu();
            String choice = sc.nextLine().trim();
            try {
                switch (choice) {
                    case "1": addStudentMenu(); break;
                    case "2": updateStudentMenu(); break;
                    case "3": displayAllStudents(); break;
                    case "4": addCourseMenu(); break;
                    case "5": displayAllCourses(); break;
                    case "6": assignInstructorMenu(); break;
                    case "7": registerCourseMenu(); break;
                    case "8": dropCourseMenu(); break;
                    case "9": displayStudentRegistrations(); break;
                    case "10": recordMarksMenu(); break;
                    case "11": searchStudentMenu(); break;
                    case "12": searchCourseMenu(); break;
                    case "13": coursewiseStudentsMenu(); break;
                    case "14": passFailReportMenu(); break;
                    case "15": studentResultReportMenu(); break;
                    case "16": topPerformerMenu(); break;
                    case "17": saveDataMenu(); break;
                    case "18": loadDataMenu(); break;
                    case "0": running = false; System.out.println("Exiting system. Goodbye!"); break;
                    default: System.out.println("Invalid choice. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
            System.out.println();
        }
    }

    static void printMenu() {
        System.out.println("=================================================");
        System.out.println(" MINI UNIVERSITY COURSE REGISTRATION SYSTEM");
        System.out.println("=================================================");
        System.out.println(" 1.  Add Student");
        System.out.println(" 2.  Update Student");
        System.out.println(" 3.  Display All Students");
        System.out.println(" 4.  Add Course");
        System.out.println(" 5.  Display All Courses");
        System.out.println(" 6.  Assign Instructor to Course");
        System.out.println(" 7.  Register Student in Course");
        System.out.println(" 8.  Drop Course");
        System.out.println(" 9.  Display Student's Registered Courses");
        System.out.println(" 10. Record Assessment Marks");
        System.out.println(" 11. Search Student by ID/Name");
        System.out.println(" 12. Search Course by Code/Title");
        System.out.println(" 13. Course-wise Registered Students Report");
        System.out.println(" 14. Passed/Failed Students Report");
        System.out.println(" 15. Student Result Report");
        System.out.println(" 16. Top Performer Report");
        System.out.println(" 17. Save Data to File");
        System.out.println(" 18. Load Data from File");
        System.out.println(" 0.  Exit");
        System.out.print("Enter choice: ");
    }

    static void addStudentMenu() {
        System.out.print("Student ID: "); String id = sc.nextLine();
        System.out.print("Name: "); String name = sc.nextLine();
        System.out.print("Program: "); String program = sc.nextLine();
        System.out.print("Semester: "); int sem = Integer.parseInt(sc.nextLine());
        System.out.print("Contact: "); String contact = sc.nextLine();
        system.addStudent(new Student(id, name, contact, program, sem));
        System.out.println("Student added successfully.");
    }

    static void updateStudentMenu() throws RecordNotFoundException {
        System.out.print("Student ID to update: "); String id = sc.nextLine();
        System.out.print("New Name: "); String name = sc.nextLine();
        System.out.print("New Program: "); String program = sc.nextLine();
        System.out.print("New Semester: "); int sem = Integer.parseInt(sc.nextLine());
        System.out.print("New Contact: "); String contact = sc.nextLine();
        system.updateStudent(id, name, program, sem, contact);
        System.out.println("Student updated successfully.");
    }

    static void displayAllStudents() {
        System.out.println("---- All Students ----");
        for (Student s : system.getAllStudents()) System.out.println(s);
    }

    static void addCourseMenu() {
        System.out.print("Course Code: "); String code = sc.nextLine();
        System.out.print("Course Title: "); String title = sc.nextLine();
        System.out.print("Credit Hours: "); int credits = Integer.parseInt(sc.nextLine());
        system.addCourse(new Course(code, title, credits));
        System.out.println("Course added successfully.");
    }

    static void displayAllCourses() {
        System.out.println("---- All Courses ----");
        for (Course c : system.getAllCourses()) System.out.println(c);
    }

    static void assignInstructorMenu() throws RecordNotFoundException {
        System.out.print("Course Code: "); String code = sc.nextLine();
        System.out.print("Instructor ID: "); String iid = sc.nextLine();
        system.assignInstructorToCourse(code, iid);
        System.out.println("Instructor assigned successfully.");
    }

    static void registerCourseMenu() {
        System.out.print("Student ID: "); String sid = sc.nextLine();
        System.out.print("Course Code: "); String cid = sc.nextLine();
        try {
            Registration r = system.registerStudentInCourse(sid, cid);
            System.out.println("Registration successful: " + r);
        } catch (RecordNotFoundException | DuplicateRegistrationException e) {
            System.out.println("Error message displayed: " + e.getMessage());
        }
    }

    static void dropCourseMenu() throws RecordNotFoundException {
        System.out.print("Student ID: "); String sid = sc.nextLine();
        System.out.print("Course Code: "); String cid = sc.nextLine();
        system.dropCourse(sid, cid);
        System.out.println("Course dropped successfully.");
    }

    static void displayStudentRegistrations() throws RecordNotFoundException {
        System.out.print("Student ID: "); String sid = sc.nextLine();
        for (Registration r : system.getRegistrationsForStudent(sid)) {
            System.out.println(r);
        }
    }

    static void recordMarksMenu() throws RecordNotFoundException, InvalidMarksException {
        System.out.print("Student ID: "); String sid = sc.nextLine();
        System.out.print("Course Code: "); String cid = sc.nextLine();
        System.out.println("Assessment Type (1-Quiz 2-Assignment 3-Midterm 4-Final): ");
        String type = sc.nextLine();
        System.out.print("Max Marks: "); double max = Double.parseDouble(sc.nextLine());
        System.out.print("Obtained Marks: "); double marks = Double.parseDouble(sc.nextLine());
        Assessment a;
        switch (type) {
            case "1": a = new Quiz(max, 1); break;
            case "2": a = new Assignment(max, 1); break;
            case "3": a = new Exam(max, "Midterm"); break;
            default: a = new Exam(max, "Final");
        }
        a.setMarks(marks);
        system.recordMarks(sid, cid, a);
        System.out.println("Marks recorded successfully.");
    }

    static void searchStudentMenu() {
        System.out.print("Enter ID or Name: "); String q = sc.nextLine();
        try {
            Student s = system.findStudentById(q);
            System.out.println("Student record displayed: " + s);
        } catch (RecordNotFoundException e) {
            List<Student> results = system.searchStudentByName(q);
            if (results.isEmpty()) System.out.println("Student not found message: No student matches '" + q + "'");
            else results.forEach(System.out::println);
        }
    }

    static void searchCourseMenu() {
        System.out.print("Enter Code or Title: "); String q = sc.nextLine();
        try {
            Course c = system.findCourseByCode(q);
            System.out.println("Course record displayed: " + c);
        } catch (RecordNotFoundException e) {
            List<Course> results = system.searchCourseByTitle(q);
            if (results.isEmpty()) System.out.println("Course not found.");
            else results.forEach(System.out::println);
        }
    }

    static void coursewiseStudentsMenu() throws RecordNotFoundException {
        System.out.print("Course Code: "); String cid = sc.nextLine();
        Course c = system.findCourseByCode(cid);
        System.out.print(c.generateReport());
    }

    static void passFailReportMenu() throws RecordNotFoundException {
        System.out.print("Course Code: "); String cid = sc.nextLine();
        System.out.println("Passed Students:");
        system.getPassedStudents(cid).forEach(s -> System.out.println("   " + s.getName()));
        System.out.println("Failed Students:");
        system.getFailedStudents(cid).forEach(s -> System.out.println("   " + s.getName()));
    }

    static void studentResultReportMenu() throws RecordNotFoundException {
        System.out.print("Student ID: "); String sid = sc.nextLine();
        System.out.print(system.studentWiseReport(sid));
    }

    static void topPerformerMenu() throws RecordNotFoundException {
        System.out.print("Course Code: "); String cid = sc.nextLine();
        Registration top = system.getTopPerformer(cid);
        System.out.printf("Top Performer: %s with %.2f%%\n", top.getStudent().getName(), top.getResult().percentage());
    }

    static void saveDataMenu() {
        try {
            system.saveData();
            System.out.println("Data saved successfully to file.");
        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    static void loadDataMenu() {
        try {
            system.loadData();
            System.out.println("Data loaded successfully from file.");
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    // ---------- Sample / Mock Data ----------
    static void loadSampleData() {
        system.addStudent(new Student("S001", "Ali Raza", "0300-1111111", "BS-CS", 3));
        system.addStudent(new Student("S002", "Sara Khan", "0300-2222222", "BS-CS", 3));
        system.addStudent(new Student("S003", "Bilal Ahmed", "0300-3333333", "BS-SE", 2));
        system.addStudent(new Student("S004", "Hina Tariq", "0300-4444444", "BS-CS", 5));
        system.addStudent(new Student("S005", "Usman Javed", "0300-5555555", "BS-AI", 1));

        system.addInstructor(new Instructor("I001", "Dr. Ahsan Malik", "0321-1000000", "Computer Science"));
        system.addInstructor(new Instructor("I002", "Ms. Faiza Sheikh", "0321-2000000", "Computer Science"));

        system.addCourse(new Course("CS101", "Object Oriented Programming", 3));
        system.addCourse(new Course("CS102", "Data Structures", 3));
        system.addCourse(new Course("CS103", "Database Systems", 3));

        try {
            system.assignInstructorToCourse("CS101", "I001");
            system.assignInstructorToCourse("CS102", "I002");
            system.assignInstructorToCourse("CS103", "I001");

            system.registerStudentInCourse("S001", "CS101");
            system.registerStudentInCourse("S001", "CS102");
            system.registerStudentInCourse("S002", "CS101");
            system.registerStudentInCourse("S003", "CS101");
            system.registerStudentInCourse("S004", "CS103");
            system.registerStudentInCourse("S005", "CS102");

            system.recordMarks("S001", "CS101", quiz(10, 9));
            system.recordMarks("S001", "CS101", assignment(10, 8));
            system.recordMarks("S001", "CS101", exam(30, "Midterm", 26));
            system.recordMarks("S001", "CS101", exam(50, "Final", 44));

            system.recordMarks("S002", "CS101", quiz(10, 7));
            system.recordMarks("S002", "CS101", assignment(10, 6));
            system.recordMarks("S002", "CS101", exam(30, "Midterm", 18));
            system.recordMarks("S002", "CS101", exam(50, "Final", 22));

            system.recordMarks("S003", "CS101", quiz(10, 4));
            system.recordMarks("S003", "CS101", assignment(10, 5));
            system.recordMarks("S003", "CS101", exam(30, "Midterm", 10));
            system.recordMarks("S003", "CS101", exam(50, "Final", 12));
        } catch (Exception e) {
            System.out.println("Error loading sample data: " + e.getMessage());
        }
    }

    static Assessment quiz(double max, double marks) {
        Assessment a = new Quiz(max, 1);
        try { a.setMarks(marks); } catch (InvalidMarksException e) { }
        return a;
    }
    static Assessment assignment(double max, double marks) {
        Assessment a = new Assignment(max, 1);
        try { a.setMarks(marks); } catch (InvalidMarksException e) { }
        return a;
    }
    static Assessment exam(double max, String type, double marks) {
        Assessment a = new Exam(max, type);
        try { a.setMarks(marks); } catch (InvalidMarksException e) { }
        return a;
    }
}
