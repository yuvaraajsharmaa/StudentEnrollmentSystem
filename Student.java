/**
 * Student.java
 * Represents a student in the enrolment system.
 * Applies encapsulation: all fields are private, accessed via getters/setters.
 */
public class Student {

    // Private attributes — encapsulated from direct external access
    private String studentId;
    private String name;
    private String email;

    /**
     * Constructor — initialises a new Student with all required attributes.
     * @param studentId Unique identifier for the student (e.g. "S1000001")
     * @param name      Full name of the student
     * @param email     Contact email address
     */
    public Student(String studentId, String name, String email) {
        this.studentId = studentId;
        this.name      = name;
        this.email     = email;
    }

    // ── Getters ────────────────────────────────────────────────────────────

    public String getStudentId() { return studentId; }
    public String getName()      { return name; }
    public String getEmail()     { return email; }

    // ── Setters ────────────────────────────────────────────────────────────

    public void setStudentId(String studentId) { this.studentId = studentId; }
    public void setName(String name)           { this.name = name; }
    public void setEmail(String email)         { this.email = email; }

    /**
     * Displays a formatted summary of the student's information.
     */
    public void displayStudentInfo() {
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│           STUDENT DETAILS            │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.printf( "│  ID    : %-27s│%n", studentId);
        System.out.printf( "│  Name  : %-27s│%n", name);
        System.out.printf( "│  Email : %-27s│%n", email);
        System.out.println("└─────────────────────────────────────┘");
    }

    /** Returns a compact one-line representation — useful for waitlist display. */
    @Override
    public String toString() {
        return String.format("Student[%s | %s | %s]", studentId, name, email);
    }
}
