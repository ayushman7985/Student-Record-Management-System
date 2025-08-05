import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for Student Record Management System
 * Provides a console-based user interface for managing student records
 */
public class StudentManagementSystem {
    private StudentDatabase database;
    private Scanner scanner;
    private DateTimeFormatter dateFormatter;
    
    public StudentManagementSystem() {
        this.database = new StudentDatabase();
        this.scanner = new Scanner(System.in);
        this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }
    
    public void start() {
        System.out.println("=================================================");
        System.out.println("    STUDENT RECORD MANAGEMENT SYSTEM");
        System.out.println("=================================================");
        
        while (true) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewStudent();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    searchStudents();
                    break;
                case 6:
                    displayAllStudents();
                    break;
                case 7:
                    displaySortedStudents();
                    break;
                case 8:
                    database.displayStatistics();
                    break;
                case 9:
                    manageSubjects();
                    break;
                case 0:
                    System.out.println("Thank you for using Student Management System!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
            
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }
    
    private void displayMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Add New Student");
        System.out.println("2. View Student Details");
        System.out.println("3. Update Student Information");
        System.out.println("4. Delete Student");
        System.out.println("5. Search Students");
        System.out.println("6. Display All Students");
        System.out.println("7. Display Sorted Students");
        System.out.println("8. View Statistics");
        System.out.println("9. Manage Student Subjects");
        System.out.println("0. Exit");
        System.out.println("==================");
    }
    
    private void addStudent() {
        System.out.println("\n=== ADD NEW STUDENT ===");
        
        System.out.print("First Name: ");
        String firstName = scanner.nextLine().trim();
        
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine().trim();
        
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        
        System.out.print("Phone Number: ");
        String phoneNumber = scanner.nextLine().trim();
        
        LocalDate dateOfBirth = getDateInput("Date of Birth (dd/MM/yyyy): ");
        if (dateOfBirth == null) return;
        
        System.out.print("Address: ");
        String address = scanner.nextLine().trim();
        
        System.out.print("Course: ");
        String course = scanner.nextLine().trim();
        
        int semester = getIntInput("Semester: ");
        
        database.addStudent(firstName, lastName, email, phoneNumber, 
                          dateOfBirth, address, course, semester);
    }
    
    private void viewStudent() {
        System.out.println("\n=== VIEW STUDENT DETAILS ===");
        int studentId = getIntInput("Enter Student ID: ");
        
        Student student = database.getStudent(studentId);
        if (student != null) {
            System.out.println("\n" + student.toString());
        } else {
            System.out.println("Student not found!");
        }
    }
    
    private void updateStudent() {
        System.out.println("\n=== UPDATE STUDENT INFORMATION ===");
        int studentId = getIntInput("Enter Student ID to update: ");
        
        Student student = database.getStudent(studentId);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }
        
        System.out.println("Current Information:");
        System.out.println(student.toString());
        
        System.out.println("\nEnter new information (press Enter to keep current value):");
        
        System.out.print("First Name [" + student.getFirstName() + "]: ");
        String firstName = getStringOrDefault(student.getFirstName());
        
        System.out.print("Last Name [" + student.getLastName() + "]: ");
        String lastName = getStringOrDefault(student.getLastName());
        
        System.out.print("Email [" + student.getEmail() + "]: ");
        String email = getStringOrDefault(student.getEmail());
        
        System.out.print("Phone Number [" + student.getPhoneNumber() + "]: ");
        String phoneNumber = getStringOrDefault(student.getPhoneNumber());
        
        System.out.print("Address [" + student.getAddress() + "]: ");
        String address = getStringOrDefault(student.getAddress());
        
        System.out.print("Course [" + student.getCourse() + "]: ");
        String course = getStringOrDefault(student.getCourse());
        
        System.out.print("Semester [" + student.getSemester() + "]: ");
        int semester = getIntOrDefault(student.getSemester());
        
        System.out.print("GPA [" + String.format("%.2f", student.getGpa()) + "]: ");
        double gpa = getDoubleOrDefault(student.getGpa());
        
        database.updateStudent(studentId, firstName, lastName, email, phoneNumber, 
                             address, course, semester, gpa);
    }
    
    private void deleteStudent() {
        System.out.println("\n=== DELETE STUDENT ===");
        int studentId = getIntInput("Enter Student ID to delete: ");
        
        Student student = database.getStudent(studentId);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }
        
        System.out.println("Student to be deleted:");
        System.out.println(student.toString());
        
        System.out.print("Are you sure you want to delete this student? (yes/no): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();
        
        if (confirmation.equals("yes") || confirmation.equals("y")) {
            database.deleteStudent(studentId);
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
    
    private void searchStudents() {
        System.out.println("\n=== SEARCH STUDENTS ===");
        System.out.println("1. Search by Name");
        System.out.println("2. Search by Course");
        System.out.println("3. Search by Semester");
        
        int choice = getIntInput("Enter search type: ");
        List<Student> results = null;
        
        switch (choice) {
            case 1:
                System.out.print("Enter name to search: ");
                String name = scanner.nextLine().trim();
                results = database.searchByName(name);
                break;
            case 2:
                System.out.print("Enter course to search: ");
                String course = scanner.nextLine().trim();
                results = database.searchByCourse(course);
                break;
            case 3:
                int semester = getIntInput("Enter semester to search: ");
                results = database.searchBySemester(semester);
                break;
            default:
                System.out.println("Invalid search type!");
                return;
        }
        
        if (results != null && !results.isEmpty()) {
            System.out.println("\nSearch Results (" + results.size() + " found):");
            System.out.println("=".repeat(50));
            for (Student student : results) {
                System.out.println("ID: " + student.getStudentId() + 
                                 " | Name: " + student.getFullName() + 
                                 " | Course: " + student.getCourse() + 
                                 " | Semester: " + student.getSemester());
            }
        } else {
            System.out.println("No students found matching the search criteria.");
        }
    }
    
    private void displayAllStudents() {
        System.out.println("\n=== ALL STUDENTS ===");
        List<Student> students = database.getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("No students in database.");
            return;
        }
        
        System.out.println("Total Students: " + students.size());
        System.out.println("=".repeat(80));
        
        for (Student student : students) {
            System.out.printf("ID: %-6d | Name: %-25s | Course: %-15s | Semester: %-2d | GPA: %.2f%n",
                            student.getStudentId(),
                            student.getFullName(),
                            student.getCourse(),
                            student.getSemester(),
                            student.getGpa());
        }
    }
    
    private void displaySortedStudents() {
        System.out.println("\n=== DISPLAY SORTED STUDENTS ===");
        System.out.println("1. Sort by Name");
        System.out.println("2. Sort by GPA (Highest to Lowest)");
        System.out.println("3. Sort by Student ID");
        
        int choice = getIntInput("Enter sorting option: ");
        List<Student> students = null;
        
        switch (choice) {
            case 1:
                students = database.getStudentsSortedByName();
                System.out.println("\nStudents sorted by Name:");
                break;
            case 2:
                students = database.getStudentsSortedByGPA();
                System.out.println("\nStudents sorted by GPA (Highest to Lowest):");
                break;
            case 3:
                students = database.getStudentsSortedById();
                System.out.println("\nStudents sorted by Student ID:");
                break;
            default:
                System.out.println("Invalid sorting option!");
                return;
        }
        
        if (students.isEmpty()) {
            System.out.println("No students in database.");
            return;
        }
        
        System.out.println("=".repeat(80));
        for (Student student : students) {
            System.out.printf("ID: %-6d | Name: %-25s | Course: %-15s | Semester: %-2d | GPA: %.2f%n",
                            student.getStudentId(),
                            student.getFullName(),
                            student.getCourse(),
                            student.getSemester(),
                            student.getGpa());
        }
    }
    
    private void manageSubjects() {
        System.out.println("\n=== MANAGE STUDENT SUBJECTS ===");
        int studentId = getIntInput("Enter Student ID: ");
        
        Student student = database.getStudent(studentId);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }
        
        System.out.println("Student: " + student.getFullName());
        System.out.println("Current Subjects: " + student.getSubjects());
        
        System.out.println("\n1. Add Subject");
        System.out.println("2. Remove Subject");
        
        int choice = getIntInput("Enter choice: ");
        
        switch (choice) {
            case 1:
                System.out.print("Enter subject to add: ");
                String subjectToAdd = scanner.nextLine().trim();
                student.addSubject(subjectToAdd);
                System.out.println("Subject added successfully!");
                break;
            case 2:
                if (student.getSubjects().isEmpty()) {
                    System.out.println("No subjects to remove.");
                    return;
                }
                System.out.print("Enter subject to remove: ");
                String subjectToRemove = scanner.nextLine().trim();
                student.removeSubject(subjectToRemove);
                System.out.println("Subject removed successfully!");
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
    
    // Helper methods for input validation
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    private double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    
    private LocalDate getDateInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return LocalDate.parse(input, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.println("Please enter date in dd/MM/yyyy format.");
                System.out.print("Try again or type 'cancel' to cancel: ");
                String retry = scanner.nextLine().trim();
                if (retry.equalsIgnoreCase("cancel")) {
                    return null;
                }
            }
        }
    }
    
    private String getStringOrDefault(String defaultValue) {
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? defaultValue : input;
    }
    
    private int getIntOrDefault(int defaultValue) {
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number, keeping current value.");
            return defaultValue;
        }
    }
    
    private double getDoubleOrDefault(double defaultValue) {
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number, keeping current value.");
            return defaultValue;
        }
    }
    
    public static void main(String[] args) {
        StudentManagementSystem system = new StudentManagementSystem();
        system.start();
    }
}
