import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Course.java
 * Represents an academic course, including enrolment capacity management
 * and a WAITLIST for when the course is full (extension feature).
 *
 * OOP concepts applied:
 *  - Encapsulation: all fields private, accessed through getters/setters
 *  - Abstraction: internal waitlist/enrolment logic is hidden from callers
 */
public class Course {

    // ── Core attributes ───────────────────────────────────────────────────
    private String courseCode;
    private String courseName;
    private int    maxCapacity;

    // ── Runtime state ─────────────────────────────────────────────────────
    /** Students currently enrolled (confirmed seats). */
    private List<Student> enrolledStudents;

    /**
     * EXTENSION FEATURE — Waitlist System
     * When the course reaches maxCapacity, further enrolment attempts are
     * placed on a FIFO queue. If an enrolled student drops the course, the
     * first student on the waitlist is automatically offered the vacated seat.
     */
    private Queue<Student> waitlist;

    /**
     * Constructor — initialises a course with the given code, name, and cap.
     * @param courseCode  Short unique identifier (e.g. "CS101")
     * @param courseName  Full descriptive name of the course
     * @param maxCapacity Maximum number of students that can be enrolled
     */
    public Course(String courseCode, String courseName, int maxCapacity) {
        this.courseCode       = courseCode;
        this.courseName       = courseName;
        this.maxCapacity      = maxCapacity;
        this.enrolledStudents = new ArrayList<>();
        this.waitlist         = new LinkedList<>();
    }

    // ── Getters ────────────────────────────────────────────────────────────

    public String getCourseCode()           { return courseCode; }
    public String getCourseName()           { return courseName; }
    public int    getMaxCapacity()          { return maxCapacity; }
    public int    getCurrentEnrolment()     { return enrolledStudents.size(); }
    public int    getWaitlistSize()         { return waitlist.size(); }
    public List<Student>  getEnrolledStudents() { return enrolledStudents; }
    public Queue<Student> getWaitlist()     { return waitlist; }

    // ── Setters ────────────────────────────────────────────────────────────

    public void setCourseCode(String courseCode)   { this.courseCode = courseCode; }
    public void setCourseName(String courseName)   { this.courseName = courseName; }
    public void setMaxCapacity(int maxCapacity)    { this.maxCapacity = maxCapacity; }

    // ── Business logic ─────────────────────────────────────────────────────

    /** Returns true when the course has no available seats. */
    public boolean isFull() {
        return enrolledStudents.size() >= maxCapacity;
    }

    /**
     * Attempts to enrol a student.
     * If the course is full, the student is added to the waitlist instead.
     * @param student The student to enrol or waitlist
     * @return true if the student was given a confirmed seat,
     *         false if they were placed on the waitlist
     */
    public boolean enrolOrWaitlist(Student student) {
        // Prevent duplicate enrolment
        if (isAlreadyEnrolled(student)) {
            System.out.printf("  [!] %s is already enrolled in %s.%n",
                    student.getName(), courseCode);
            return false;
        }
        // Prevent duplicate waitlist entry
        if (isOnWaitlist(student)) {
            System.out.printf("  [!] %s is already on the waitlist for %s.%n",
                    student.getName(), courseCode);
            return false;
        }

        if (!isFull()) {
            enrolledStudents.add(student);
            return true;        // Confirmed enrolment
        } else {
            waitlist.add(student);
            return false;       // Placed on waitlist
        }
    }

    /**
     * Removes a student from the course.
     * If students are on the waitlist, the first one is automatically enrolled.
     * @param student The student dropping the course
     * @return true if the student was found and removed
     */
    public boolean dropCourse(Student student) {
        boolean removed = enrolledStudents.remove(student);
        if (removed && !waitlist.isEmpty()) {
            // Promote the first waiting student to a confirmed seat
            Student promoted = waitlist.poll();
            enrolledStudents.add(promoted);
            System.out.printf("  [✓] Waitlist: %s has been promoted from the waitlist into %s.%n",
                    promoted.getName(), courseCode);
        }
        return removed;
    }

    /** Checks whether a specific student is currently enrolled. */
    public boolean isAlreadyEnrolled(Student student) {
        return enrolledStudents.stream()
                .anyMatch(s -> s.getStudentId().equals(student.getStudentId()));
    }

    /** Checks whether a specific student is currently on the waitlist. */
    public boolean isOnWaitlist(Student student) {
        return waitlist.stream()
                .anyMatch(s -> s.getStudentId().equals(student.getStudentId()));
    }

    /**
     * Displays full course details including current enrolment and waitlist status.
     */
    public void displayCourseDetails() {
        System.out.println("┌─────────────────────────────────────┐");
        System.out.println("│           COURSE DETAILS             │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.printf( "│  Code     : %-23s│%n", courseCode);
        System.out.printf( "│  Name     : %-23s│%n", courseName);
        System.out.printf( "│  Capacity : %d/%d%-20s│%n",
                enrolledStudents.size(), maxCapacity, "");
        System.out.printf( "│  Waitlist : %-23d│%n", waitlist.size());
        System.out.printf( "│  Status   : %-23s│%n", isFull() ? "FULL" : "OPEN");
        System.out.println("└─────────────────────────────────────┘");
    }

    @Override
    public String toString() {
        return String.format("Course[%s | %s | %d/%d seats]",
                courseCode, courseName, enrolledStudents.size(), maxCapacity);
    }
}
