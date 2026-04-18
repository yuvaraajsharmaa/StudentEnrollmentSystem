import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Enrolment.java
 * Links a Student to a Course and records the date the enrolment was created.
 *
 * OOP concepts applied:
 *  - Composition: holds references to Student and Course objects
 *  - Encapsulation: private fields with public getters/setters
 */
public class Enrolment {

    // ── Private attributes ─────────────────────────────────────────────────
    private Student   student;
    private Course    course;
    private LocalDate enrolmentDate;

    /**
     * Constructor — creates an enrolment record for today's date.
     * @param student The student being enrolled
     * @param course  The course they are enrolling in
     */
    public Enrolment(Student student, Course course) {
        this.student       = student;
        this.course        = course;
        this.enrolmentDate = LocalDate.now();   // Auto-stamp with today's date
    }

    /**
     * Constructor — creates an enrolment with a specific date (useful for testing).
     * @param student       The student being enrolled
     * @param course        The course they are enrolling in
     * @param enrolmentDate Explicit date for the enrolment record
     */
    public Enrolment(Student student, Course course, LocalDate enrolmentDate) {
        this.student       = student;
        this.course        = course;
        this.enrolmentDate = enrolmentDate;
    }

    // ── Getters ────────────────────────────────────────────────────────────

    public Student   getStudent()       { return student; }
    public Course    getCourse()        { return course; }
    public LocalDate getEnrolmentDate() { return enrolmentDate; }

    // ── Setters ────────────────────────────────────────────────────────────

    public void setStudent(Student student)             { this.student = student; }
    public void setCourse(Course course)               { this.course = course; }
    public void setEnrolmentDate(LocalDate date)       { this.enrolmentDate = date; }

    /**
     * Factory-style method: attempts to create an enrolment for a student in a course.
     * Delegates seat-or-waitlist logic to Course.enrolOrWaitlist().
     *
     * @param student The student to enrol
     * @param course  The target course
     * @return A new Enrolment object, or null if the student ended up on the waitlist
     */
    public static Enrolment createEnrolment(Student student, Course course) {
        boolean confirmed = course.enrolOrWaitlist(student);

        if (confirmed) {
            Enrolment enrolment = new Enrolment(student, course);
            System.out.printf("  [✓] %s successfully enrolled in %s (%s).%n",
                    student.getName(), course.getCourseName(), course.getCourseCode());
            return enrolment;
        } else {
            // Only print the waitlist message if the student was actually added
            if (course.isOnWaitlist(student)) {
                System.out.printf("  [~] %s added to WAITLIST for %s (%s). Position: %d%n",
                        student.getName(), course.getCourseName(),
                        course.getCourseCode(), course.getWaitlistSize());
            }
            return null;  // No confirmed enrolment record created
        }
    }

    /**
     * Displays a formatted summary of this enrolment record.
     */
    public void displayEnrolmentDetails() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMM yyyy");
        System.out.println("┌─────────────────────────────────────────────┐");
        System.out.println("│            ENROLMENT RECORD                  │");
        System.out.println("├─────────────────────────────────────────────┤");
        System.out.printf( "│  Student   : %-31s│%n", student.getName());
        System.out.printf( "│  Student ID: %-31s│%n", student.getStudentId());
        System.out.printf( "│  Course    : %-31s│%n", course.getCourseName());
        System.out.printf( "│  Code      : %-31s│%n", course.getCourseCode());
        System.out.printf( "│  Date      : %-31s│%n", enrolmentDate.format(fmt));
        System.out.println("└─────────────────────────────────────────────┘");
    }

    @Override
    public String toString() {
        return String.format("Enrolment[%s -> %s on %s]",
                student.getName(), course.getCourseCode(), enrolmentDate);
    }
}
