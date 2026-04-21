import java.util.ArrayList;
import java.util.List;

/**
 * Main.java
 * Entry point for the Student Enrolment System.
 *
 * Demonstrates:
 *  1. Creating Student and Course objects
 *  2. Normal enrolment (seats available)
 *  3. WAITLIST system when a course is full (extension feature — odd student ID)
 *  4. Automatic promotion from waitlist when a student drops a course
 *  5. Displaying all enrolment information
 */
public class Main {

    public static void main(String[] args) {

        separator("STUDENT ENROLMENT SYSTEM — STARTUP");

        // ── 1. Create Students ─────────────────────────────────────────────
        System.out.println("\n[1] Creating students...\n");

        Student s1 = new Student("S1000001", "Alice Johnson",   "alice@uni.ac.uk");
        Student s2 = new Student("S1000002", "Bob Martinez",    "bob@uni.ac.uk");
        Student s3 = new Student("S1000003", "Clara Nguyen",    "clara@uni.ac.uk");
        Student s4 = new Student("S1000005", "David Okafor",    "david@uni.ac.uk");

        s1.displayStudentInfo();
        s2.displayStudentInfo();
        s3.displayStudentInfo();
        s4.displayStudentInfo();

        // ── 2. Create Courses ──────────────────────────────────────────────
        // Note: CS101 has a maxCapacity of 2 to demonstrate the waitlist feature
        separator("COURSE SETUP");

        Course cs101 = new Course("CS101", "Intro to Computer Science", 2);
        Course math201 = new Course("MATH201", "Calculus II",            3);

        System.out.println();
        cs101.displayCourseDetails();
        math201.displayCourseDetails();

        // ── 3. Normal Enrolments ────────────────────────────────────────────
        separator("ENROLMENT — AVAILABLE SEATS");

        List<Enrolment> enrolments = new ArrayList<>();

        // Alice → CS101 (seat 1 of 2)
        Enrolment e1 = Enrolment.createEnrolment(s1, cs101);
        if (e1 != null) enrolments.add(e1);

        // Bob → CS101 (seat 2 of 2 — course now FULL)
        Enrolment e2 = Enrolment.createEnrolment(s2, cs101);
        if (e2 != null) enrolments.add(e2);

        // Alice → MATH201
        Enrolment e3 = Enrolment.createEnrolment(s1, math201);
        if (e3 != null) enrolments.add(e3);

        // Bob → MATH201
        Enrolment e4 = Enrolment.createEnrolment(s2, math201);
        if (e4 != null) enrolments.add(e4);

        // ── 4. WAITLIST FEATURE ─────────────────────────────────────────────
        separator("EXTENSION: WAITLIST SYSTEM");

        System.out.println("  CS101 is now full (capacity: 2/2).");
        System.out.println("  Attempting to enrol Clara and David — they will be waitlisted.\n");

        // Clara → CS101 (FULL → goes on waitlist, position 1)
        Enrolment e5 = Enrolment.createEnrolment(s3, cs101);
        if (e5 != null) enrolments.add(e5);

        // David → CS101 (FULL → goes on waitlist, position 2)
        Enrolment e6 = Enrolment.createEnrolment(s4, cs101);
        if (e6 != null) enrolments.add(e6);

        System.out.println();
        cs101.displayCourseDetails();

        // ── 5. Drop Course → Automatic Waitlist Promotion ──────────────────
        separator("WAITLIST PROMOTION — BOB DROPS CS101");

        System.out.printf("  Dropping %s from %s...%n%n", s2.getName(), cs101.getCourseCode());
        cs101.dropCourse(s2);   // Clara should be auto-promoted

        System.out.println();
        cs101.displayCourseDetails();

        // Add Clara's promoted enrolment to our list
        enrolments.add(new Enrolment(s3, cs101));

        // ── 6. Clara → MATH201 ─────────────────────────────────────────────
        separator("ADDITIONAL ENROLMENT");

        Enrolment e7 = Enrolment.createEnrolment(s3, math201);
        if (e7 != null) enrolments.add(e7);

        // ── 7. Display All Confirmed Enrolment Records ─────────────────────
        separator("ALL ENROLMENT RECORDS");

        System.out.printf("  Total confirmed enrolments: %d%n%n", enrolments.size());
        for (Enrolment e : enrolments) {
            e.displayEnrolmentDetails();
        }

        // ── 8. Final Course Summaries ──────────────────────────────────────
        separator("FINAL COURSE STATUS");

        cs101.displayCourseDetails();
        math201.displayCourseDetails();

        separator("SYSTEM DEMONSTRATION COMPLETE");
    }

    /** Prints a visual separator with a centred title. */
    private static void separator(String title) {
        System.out.println();
        System.out.println("═══════════════════════════════════════════════════");
        System.out.printf( "  %s%n", title);
        System.out.println("═══════════════════════════════════════════════════");
    }
}
