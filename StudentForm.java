import java.awt.*;                       // Layouts, Color, Font ke liye
import javax.swing.*;                    // Swing GUI components
import javax.swing.border.EmptyBorder;   // Padding / margin ke liye

// StudentForm class – student admission form fill karne ke liye
public class StudentForm extends JFrame {

    // Text fields for student details
    private JTextField nameField, ageField, emailField, phoneField, addressField;
    private JTextField tenthField, twelfthField, birthField;

    // Dropdowns for gender & course selection
    private JComboBox<String> genderBox, courseBox;

    // Constructor – Student Form UI create karta hai
    public StudentForm() {

        // Frame basic settings
        setTitle("Student Admission Form");
        setSize(650, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        // ===== TITLE =====
        JLabel title = new JLabel("Student Admission Form", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(0, 102, 204));
        mainPanel.add(title, BorderLayout.NORTH);

        // ===== FORM PANEL =====
        // GridLayout: 10 rows, 2 columns, gap 10px
        JPanel formPanel = new JPanel(new GridLayout(10, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);

        // Initialize text fields
        nameField = new JTextField();
        ageField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        addressField = new JTextField();
        tenthField = new JTextField();
        twelfthField = new JTextField();
        birthField = new JTextField();

        // Initialize combo boxes
        genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        courseBox = new JComboBox<>(new String[]{"BSc CS","BSc IT","BCom","BA","BBA"});

        // Add labels & fields to form panel
        formPanel.add(new JLabel("Name:")); formPanel.add(nameField);
        formPanel.add(new JLabel("Age:")); formPanel.add(ageField);
        formPanel.add(new JLabel("Gender:")); formPanel.add(genderBox);
        formPanel.add(new JLabel("Course:")); formPanel.add(courseBox);
        formPanel.add(new JLabel("Email:")); formPanel.add(emailField);
        formPanel.add(new JLabel("Phone:")); formPanel.add(phoneField);
        formPanel.add(new JLabel("Address:")); formPanel.add(addressField);
        formPanel.add(new JLabel("10th %:")); formPanel.add(tenthField);
        formPanel.add(new JLabel("12th %:")); formPanel.add(twelfthField);
        formPanel.add(new JLabel("Birthdate (DD/MM/YYYY):")); formPanel.add(birthField);

        // Form panel ko center me add karna
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        // Buttons
        JButton submitBtn = new JButton("Submit");
        JButton viewStatusBtn = new JButton("View Status");
        JButton logoutBtn = new JButton("Logout");

        // Submit button styling
        submitBtn.setBackground(new Color(0,153,76));
        submitBtn.setForeground(Color.WHITE);

        // View Status button styling
        viewStatusBtn.setBackground(new Color(0,102,204));
        viewStatusBtn.setForeground(Color.WHITE);

        // Logout button styling
        logoutBtn.setBackground(new Color(153,153,153));
        logoutBtn.setForeground(Color.WHITE);

        // Buttons add karna
        buttonPanel.add(submitBtn);
        buttonPanel.add(viewStatusBtn);
        buttonPanel.add(logoutBtn);

        // Button panel ko bottom me add karna
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // ===== ACTION LISTENERS =====

        // Submit form
        submitBtn.addActionListener(e -> submitForm());

        // View application status
        viewStatusBtn.addActionListener(e -> viewStatusPopup());

        // Logout – Signup screen open
        logoutBtn.addActionListener(e -> {
            new SignupScreen().setVisible(true);
            dispose();
        });
    }

    // ================= SUBMIT FORM =================
    private void submitForm() {
        try {
            // Read input values
            String name = nameField.getText().trim();
            int age = Integer.parseInt(ageField.getText().trim());
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            double tenth = Double.parseDouble(tenthField.getText().trim());
            double twelfth = Double.parseDouble(twelfthField.getText().trim());
            String birth = birthField.getText().trim();

            // Empty field validation
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || birth.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!");
                return;
            }

            // ===== EMAIL VALIDATION =====
            // Only Gmail allowed
            if (!email.matches("^[A-Za-z0-9+_.-]+@gmail\\.com$")) {
                JOptionPane.showMessageDialog(this, "Email must be valid Gmail (example@gmail.com)");
                return;
            }

            // ===== PHONE VALIDATION =====
            // Exactly 10 digits
            if (!phone.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(this, "Phone number must be exactly 10 digits!");
                return;
            }

            // ===== CREATE STUDENT OBJECT =====
            // Status automatically "Pending"
            Student s = new Student(
                    name,
                    age,
                    (String) genderBox.getSelectedItem(),
                    (String) courseBox.getSelectedItem(),
                    email,
                    phone,
                    addressField.getText(),
                    tenth,
                    twelfth,
                    birth
            );

            // Save student data
            DataAccess.saveStudent(s);

            // Success message
            JOptionPane.showMessageDialog(this, "Application Submitted Successfully!");

            // Clear form for next entry
            clearForm();

        } catch (Exception ex) {
            // Invalid number format or other error
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }

    // ================= VIEW STATUS =================
    private void viewStatusPopup() {

        // Email ya phone input lena
        String input = JOptionPane.showInputDialog(
                this,
                "Enter your Email OR Phone Number:"
        );

        // Cancel ya empty input
        if (input == null || input.trim().isEmpty()) return;

        // Student list me search karna
        for (Student s : DataAccess.getAllStudents()) {
            if (s.verify(input.trim())) {

                // Status show karna
                JOptionPane.showMessageDialog(
                        this,
                        "Your Application Status: " + s.getStatus()
                );
                return;
            }
        }

        // Agar koi record nahi mila
        JOptionPane.showMessageDialog(this, "No application found!");
    }

    // ================= CLEAR FORM =================
    // Submit ke baad fields reset karne ke liye
    private void clearForm() {
        nameField.setText("");
        ageField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressField.setText("");
        tenthField.setText("");
        twelfthField.setText("");
        birthField.setText("");
        genderBox.setSelectedIndex(0);
        courseBox.setSelectedIndex(0);
    }
}
