import java.io.*;              // File handling & Object streams ke liye
import java.util.ArrayList;    // Dynamic list ke liye

// DataAccess class – file ke through data save & load karta hai
public class DataAccess {

    // Student data store karne wali file
    private static final String STUDENT_FILE = "students.dat";

    // User (login/signup) data store karne wali file
    private static final String USER_FILE = "users.dat";

    // ================= STUDENT METHODS =================

    // Single student ko save karne ka method
    public static void saveStudent(Student student) {

        // Pehle existing students load karo
        ArrayList<Student> students = getAllStudents();

        // Naya student list me add karo
        students.add(student);

        // Puri updated list ko file me save karo
        saveAllStudents(students);
    }

    // Sabhi students ko ek sath file me save karna
    // (status update, delete ke time use hota hai)
    public static void saveAllStudents(ArrayList<Student> students) {

        // ObjectOutputStream use karke object file me write hota hai
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(STUDENT_FILE))) {

            // ArrayList<Student> ko file me write karna
            oos.writeObject(students);

        } catch (IOException e) {

            // Agar koi IO error aaye to print karo
            e.printStackTrace();
        }
    }

    // File se sabhi students load karna
    public static ArrayList<Student> getAllStudents() {

        // Empty list create
        ArrayList<Student> students = new ArrayList<>();

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(STUDENT_FILE))) {

            // File se object read karke cast karna
            students = (ArrayList<Student>) ois.readObject();

        } catch (FileNotFoundException e) {

            // First time run me file nahi milegi
            // Isliye kuch nahi kar rahe

        } catch (IOException | ClassNotFoundException e) {

            // Agar read ya casting error aaye
            e.printStackTrace();
        }

        // Students list return
        return students;
    }

    // ================= USER METHODS =================

    // Signup ke baad new user ko save karna
    public static void saveUser(SignupScreen.User user) {

        // Pehle saare users load karo
        ArrayList<SignupScreen.User> users = loadAllUsers();

        // Naya user add karo
        users.add(user);

        // Updated list file me save karo
        saveAllUsers(users);
    }

    // Sabhi users ko file me save karna
    public static void saveAllUsers(ArrayList<SignupScreen.User> users) {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(USER_FILE))) {

            // Users list ko file me write karna
            oos.writeObject(users);

        } catch (IOException e) {

            // Agar file write me error aaye
            e.printStackTrace();
        }
    }

    // File se sabhi users load karna
    public static ArrayList<SignupScreen.User> loadAllUsers() {

        // Empty user list
        ArrayList<SignupScreen.User> users = new ArrayList<>();

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(USER_FILE))) {

            // File se users read karna
            users = (ArrayList<SignupScreen.User>) ois.readObject();

        } catch (FileNotFoundException e) {

            // First time koi user nahi hoga
            // Isliye ignore

        } catch (IOException | ClassNotFoundException e) {

            // Read ya class error
            e.printStackTrace();
        }

        // Users list return
        return users;
    }
}
