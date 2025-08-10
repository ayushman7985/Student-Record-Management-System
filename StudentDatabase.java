import java.util.*;
import java.time.LocalDate;
import java.io.*;

/**
 * StudentDatabase class to manage all student records
 * Provides CRUD operations and search functionality
 */
public class StudentDatabase {
    private Map<Integer, Student> students;
    private int nextStudentId;
    private static final String DATA_FILE = "students.dat";
    
    public StudentDatabase() {
        this.students = new HashMap<>();
        this.nextStudentId = 1001; // Starting ID
        loadFromFile();
    }
    
    // Add a new student
    public boolean addStudent(String firstName, String lastName, String email, 
                             String phoneNumber, LocalDate dateOfBirth, String address, 
                             String course, int semester) {
        // Check if email already exists
        if (isEmailExists(email)) {
            System.out.println("Error: Email already exists!");
            return false;
        }
        
        Student student = new Student(nextStudentId++, firstName, lastName, email, 
                                    phoneNumber, dateOfBirth, address, course, semester);
        students.put(student.getStudentId(), student);
        saveToFile();
        System.out.println("Student added successfully with ID: " + student.getStudentId());
        return true;
    }
    
    // Get student by ID
    public Student getStudent(int studentId) {
        return students.get(studentId);
    }
    
    // Update student information
    public boolean updateStudent(int studentId, String firstName, String lastName, 
                               String email, String phoneNumber, String address, 
                               String course, int semester, double gpa) {
        Student student = students.get(studentId);
        if (student == null) {
            System.out.println("Student not found!");
            return false;
        }
        
        // Check if new email conflicts with existing students
        if (!student.getEmail().equals(email) && isEmailExists(email)) {
            System.out.println("Error: Email already exists!");
            return false;
        }
        
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setPhoneNumber(phoneNumber);
        student.setAddress(address);
        student.setCourse(course);
        student.setSemester(semester);
        student.setGpa(gpa);
        
        saveToFile();
        System.out.println("Student updated successfully!");
        return true;
    }
    
    // Delete student
    public boolean deleteStudent(int studentId) {
        Student removed = students.remove(studentId);
        if (removed != null) {
            saveToFile();
            System.out.println("Student deleted successfully!");
            return true;
        } else {
            System.out.println("Student not found!");
            return false;
        }
    }
    
    // Search students by name
    public List<Student> searchByName(String name) {
        List<Student> results = new ArrayList<>();
        String searchName = name.toLowerCase();
        
        for (Student student : students.values()) {
            if (student.getFullName().toLowerCase().contains(searchName) ||
                student.getFirstName().toLowerCase().contains(searchName) ||
                student.getLastName().toLowerCase().contains(searchName)) {
                results.add(student);
            }
        }
        return results;
    }
    
    // Search students by course
    public List<Student> searchByCourse(String course) {
        List<Student> results = new ArrayList<>();
        String searchCourse = course.toLowerCase();
        
        for (Student student : students.values()) {
            if (student.getCourse().toLowerCase().contains(searchCourse)) {
                results.add(student);
            }
        }
        return results;
    }
    
    // Search students by semester
    public List<Student> searchBySemester(int semester) {
        List<Student> results = new ArrayList<>();
        
        for (Student student : students.values()) {
            if (student.getSemester() == semester) {
                results.add(student);
            }
        }
        return results;
    }
    
    // Get all students
    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }
    
    // Get students sorted by name
    public List<Student> getStudentsSortedByName() {
        List<Student> sortedList = getAllStudents();
        sortedList.sort((s1, s2) -> s1.getFullName().compareToIgnoreCase(s2.getFullName()));
        return sortedList;
    }
    
    // Get students sorted by GPA
    public List<Student> getStudentsSortedByGPA() {
        List<Student> sortedList = getAllStudents();
        sortedList.sort((s1, s2) -> Double.compare(s2.getGpa(), s1.getGpa())); // Descending order
        return sortedList;
    }
    
    // Get students sorted by ID
    public List<Student> getStudentsSortedById() {
        List<Student> sortedList = getAllStudents();
        sortedList.sort((s1, s2) -> Integer.compare(s1.getStudentId(), s2.getStudentId()));
        return sortedList;
    }
    
    // Check if email exists
    private boolean isEmailExists(String email) {
        for (Student student : students.values()) {
            if (student.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }
    
    // Get total number of students
    public int getTotalStudents() {
        return students.size();
    }
    
    // Get statistics
    public void displayStatistics() {
        if (students.isEmpty()) {
            System.out.println("No students in database.");
            return;
        }
        
        System.out.println("\n=== DATABASE STATISTICS ===");
        System.out.println("Total Students: " + getTotalStudents());
        
        // Course distribution
        Map<String, Integer> courseCount = new HashMap<>();
        double totalGPA = 0;
        int studentsWithGPA = 0;
        
        for (Student student : students.values()) {
            String course = student.getCourse();
            courseCount.put(course, courseCount.getOrDefault(course, 0) + 1);
            
            if (student.getGpa() > 0) {
                totalGPA += student.getGpa();
                studentsWithGPA++;
            }
        }
        
        System.out.println("\nCourse Distribution:");
        for (Map.Entry<String, Integer> entry : courseCount.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " students");
        }
        
        if (studentsWithGPA > 0) {
            double averageGPA = totalGPA / studentsWithGPA;
            System.out.println("\nAverage GPA: " + String.format("%.2f", averageGPA));
        }
        
        System.out.println("Next Student ID: " + nextStudentId);
    }
    
    // Save data to file
    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(students);
            oos.writeInt(nextStudentId);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }
    
    // Load data from file
    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return; // No data file exists yet
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            students = (Map<Integer, Student>) ois.readObject();
            nextStudentId = ois.readInt();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
            students = new HashMap<>();
            nextStudentId = 1001;
        }
    }
}
