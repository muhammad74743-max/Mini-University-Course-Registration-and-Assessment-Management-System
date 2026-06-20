public class Instructor extends Person {
    private static final long serialVersionUID = 1L;
    private String department;

    public Instructor(String id, String name, String contactNumber, String department) {
        super(id, name, contactNumber);
        this.department = department;
    }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public String getRole() { return "Instructor"; }

    @Override
    public String toString() {
        return super.toString() + String.format(" Department: %s", department);
    }
}
