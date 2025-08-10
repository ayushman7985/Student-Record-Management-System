import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Student class representing a student record with all necessary details
 */
public class Student {
    private int studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String address;
    private String course;
    private int semester;
    private double gpa;
    private List<String> subjects;
    private LocalDate enrollmentDate;
    
    // Constructor
    public Student(int studentId, String firstName, String lastName, String email, 
                   String phoneNumber, LocalDate dateOfBirth, String address, 
                   String course, int semester) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.course = course;
        this.semester = semester;
        this.gpa = 0.0;
        this.subjects = new ArrayList<>();
        this.enrollmentDate = LocalDate.now();
    }
    
    // Getters
    public int getStudentId() { return studentId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getFullName() { return firstName + " " + lastName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public String getAddress() { return address; }
    public String getCourse() { return course; }
    public int getSemester() { return semester; }
    public double getGpa() { return gpa; }
    public List<String> getSubjects() { return subjects; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    
    // Setters
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setAddress(String address) { this.address = address; }
    public void setCourse(String course) { this.course = course; }
    public void setSemester(int semester) { this.semester = semester; }
    public void setGpa(double gpa) { this.gpa = gpa; }
    
    // Methods to manage subjects
    public void addSubject(String subject) {
        if (!subjects.contains(subject)) {
            subjects.add(subject);
        }
    }
    
    public void removeSubject(String subject) {
        subjects.remove(subject);
    }
    
    // Calculate age
    public int getAge() {
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }
    
    // Override toString for display
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();
        sb.append("Student ID: ").append(studentId).append("\n");
        sb.append("Name: ").append(getFullName()).append("\n");
        sb.append("Email: ").append(email).append("\n");
        sb.append("Phone: ").append(phoneNumber).append("\n");
        sb.append("Date of Birth: ").append(dateOfBirth.format(formatter)).append(" (Age: ").append(getAge()).append(")\n");
        sb.append("Address: ").append(address).append("\n");
        sb.append("Course: ").append(course).append("\n");
        sb.append("Semester: ").append(semester).append("\n");
        sb.append("GPA: ").append(String.format("%.2f", gpa)).append("\n");
        sb.append("Subjects: ").append(subjects.toString()).append("\n");
        sb.append("Enrollment Date: ").append(enrollmentDate.format(formatter)).append("\n");
        return sb.toString();
    }
    
    // Override equals and hashCode for proper comparison
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student student = (Student) obj;
        return studentId == student.studentId;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(studentId);
    }
}
