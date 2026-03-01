import java.io.Serializable;   // Object ko file me save karne ke liye

// Student class – ek student ka complete data represent karti hai
// Serializable implement kiya hai taaki object ko .dat file me store kar sake
public class Student implements Serializable {

    // Student personal & academic details
    private String name;          // Student name
    private String gender;        // Male / Female
    private String course;        // Selected course
    private String email;         // Student email
    private String phone;         // Student phone number
    private String address;       // Student address
    private String status;        // Admission status (Pending / Approved / Rejected)
    private String birthDate;     // Date of birth

    // Academic percentage details
    private double tenthPercent;      // 10th percentage
    private double twelfthPercent;    // 12th percentage

    // Student age
    private int age;

    // ===== CONSTRUCTOR =====
    // Student object create karte time data initialize hota hai
    // Default status "Pending" set hota hai
    public Student(String name, int age, String gender, String course,
                   String email, String phone, String address,
                   double tenthPercent, double twelfthPercent, String birthDate) {

        this.name = name;                     // Name set
        this.age = age;                       // Age set
        this.gender = gender;                 // Gender set
        this.course = course;                 // Course set
        this.email = email;                   // Email set
        this.phone = phone;                   // Phone set
        this.address = address;               // Address set
        this.tenthPercent = tenthPercent;     // 10th % set
        this.twelfthPercent = twelfthPercent; // 12th % set
        this.birthDate = birthDate;            // Birthdate set
        this.status = "Pending";               // Default admission status
    }

    // ===== GETTER METHODS =====
    // Data ko safely access karne ke liye

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getCourse() { return course; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public double getTenthPercent() { return tenthPercent; }
    public double getTwelfthPercent() { return twelfthPercent; }
    public String getBirthDate() { return birthDate; }
    public String getStatus() { return status; }

    // ===== SETTER METHOD =====
    // Admin ke through student ka status change karne ke liye
    public void setStatus(String status) {
        this.status = status;
    }

    // ===== VERIFY METHOD =====
    // Email ya phone number ke base par student verify karta hai
    // Login ya search jaise features me use ho sakta hai
    public boolean verify(String input) {

        // Agar input email ya phone se match karta ho to true return
        return email.equalsIgnoreCase(input) || phone.equals(input);
    }
}
