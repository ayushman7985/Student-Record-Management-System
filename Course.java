/**
 * Course class representing academic courses
 * Demonstrates encapsulation and data management
 */
public class Course {
    private String courseId;
    private String courseName;
    private String courseDescription;
    private int creditHours;
    private String department;
    private String instructor;
    private String semester;
    private int maxCapacity;
    private int currentEnrollment;
    private String[] prerequisites;
    
    // Constructor
    public Course(String courseId, String courseName, String courseDescription, 
                  int creditHours, String department, String instructor, 
                  String semester, int maxCapacity) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.creditHours = creditHours;
        this.department = department;
        this.instructor = instructor;
        this.semester = semester;
        this.maxCapacity = maxCapacity;
        this.currentEnrollment = 0;
        this.prerequisites = new String[0];
    }
    
    // Getters
    public String getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public String getCourseDescription() { return courseDescription; }
    public int getCreditHours() { return creditHours; }
    public String getDepartment() { return department; }
    public String getInstructor() { return instructor; }
    public String getSemester() { return semester; }
    public int getMaxCapacity() { return maxCapacity; }
    public int getCurrentEnrollment() { return currentEnrollment; }
    public String[] getPrerequisites() { return prerequisites.clone(); }
    
    // Setters
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public void setCourseDescription(String courseDescription) { this.courseDescription = courseDescription; }
    public void setCreditHours(int creditHours) { this.creditHours = creditHours; }
    public void setInstructor(String instructor) { this.instructor = instructor; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }
    public void setPrerequisites(String[] prerequisites) { this.prerequisites = prerequisites.clone(); }
    
    // Business methods
    public boolean hasAvailableSeats() {
        return currentEnrollment < maxCapacity;
    }
    
    public boolean enrollStudent() {
        if (hasAvailableSeats()) {
            currentEnrollment++;
            return true;
        }
        return false;
    }
    
    public boolean dropStudent() {
        if (currentEnrollment > 0) {
            currentEnrollment--;
            return true;
        }
        return false;
    }
    
    public int getAvailableSeats() {
        return maxCapacity - currentEnrollment;
    }
    
    public double getEnrollmentPercentage() {
        return (double) currentEnrollment / maxCapacity * 100;
    }
    
    public boolean isFull() {
        return currentEnrollment >= maxCapacity;
    }
    
    public void displayCourseInfo() {
        System.out.println("=== COURSE INFORMATION ===");
        System.out.println("Course ID: " + courseId);
        System.out.println("Course Name: " + courseName);
        System.out.println("Description: " + courseDescription);
        System.out.println("Credit Hours: " + creditHours);
        System.out.println("Department: " + department);
        System.out.println("Instructor: " + instructor);
        System.out.println("Semester: " + semester);
        System.out.println("Enrollment: " + currentEnrollment + "/" + maxCapacity);
        System.out.println("Available Seats: " + getAvailableSeats());
        System.out.println("Enrollment %: " + String.format("%.1f", getEnrollmentPercentage()) + "%");
        if (prerequisites.length > 0) {
            System.out.print("Prerequisites: ");
            for (int i = 0; i < prerequisites.length; i++) {
                System.out.print(prerequisites[i]);
                if (i < prerequisites.length - 1) System.out.print(", ");
            }
            System.out.println();
        }
        System.out.println("=========================");
    }
    
    @Override
    public String toString() {
        return "Course{" +
                "ID='" + courseId + '\'' +
                ", Name='" + courseName + '\'' +
                ", Credits=" + creditHours +
                ", Instructor='" + instructor + '\'' +
                ", Enrollment=" + currentEnrollment + "/" + maxCapacity +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        return courseId.equals(course.courseId);
    }
    
    @Override
    public int hashCode() {
        return courseId.hashCode();
    }
}
