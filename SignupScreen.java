import java.awt.*;              // Layouts, Color, Font ke liye
import java.io.*;               // Serializable interface ke liye
import java.util.ArrayList;     // Users list store karne ke liye
import javax.swing.*;           // Swing GUI components

// SignupScreen class – new user registration ke liye
public class SignupScreen extends JFrame {

    // Input fields declaration
    private JTextField nameField, emailField;
    private JPasswordField passwordField;

    // Constructor – Signup UI create karta hai
    public SignupScreen() {

        // Frame basic settings
        setTitle("Signup - College Admission Portal");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(230, 240, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ===== TITLE =====
        JLabel title = new JLabel("Signup", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(0, 102, 204));
        mainPanel.add(title, BorderLayout.NORTH);

        // ===== FORM PANEL =====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        // GridBagConstraints – component alignment ke liye
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ===== USERNAME FIELD =====
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);

        nameField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        // ===== EMAIL FIELD =====
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Email:"), gbc);

        emailField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        // ===== PASSWORD FIELD =====
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Password:"), gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        // Form panel ko center me add karna
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        // Signup & Login buttons
        JButton signupBtn = new JButton("Signup");
        JButton loginBtn = new JButton("Login");

        // Signup button styling
        signupBtn.setBackground(new Color(0, 153, 76));
        signupBtn.setForeground(Color.WHITE);
        signupBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        signupBtn.setFocusPainted(false);

        // Login button styling
        loginBtn.setBackground(new Color(0, 102, 204));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setFocusPainted(false);

        // Buttons add karna
        buttonPanel.add(signupBtn);
        buttonPanel.add(loginBtn);

        // Button panel ko bottom me add karna
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Main panel frame me add karna
        add(mainPanel);

        // ===== ACTION LISTENERS =====

        // Signup button click
        signupBtn.addActionListener(e -> signup());

        // Login button click – Login screen open
        loginBtn.addActionListener(e -> {
            new LoginScreen().setVisible(true);
            dispose(); // Signup window close
        });
    }

    // ===== SIGNUP LOGIC METHOD =====
    private void signup() {

        // User input read karna
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Empty field validation
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        // ===== GMAIL VALIDATION =====
        // Sirf @gmail.com allowed
        if (!email.matches("^[A-Za-z0-9+_.-]+@gmail\\.com$")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Email must be a valid Gmail (example@gmail.com)"
            );
            return;
        }

        // Existing users load karna
        ArrayList<User> users = DataAccess.loadAllUsers();

        // ===== DUPLICATE CHECK =====
        for (User u : users) {

            // Username already exists
            if (u.getUsername().equalsIgnoreCase(name)) {
                JOptionPane.showMessageDialog(this, "Username already exists!");
                return;
            }

            // Email already registered
            if (u.getEmail().equalsIgnoreCase(email)) {
                JOptionPane.showMessageDialog(this, "Email already registered!");
                return;
            }
        }

        // ===== SAVE NEW USER =====
        User newUser = new User(name, email, password);
        users.add(newUser);
        DataAccess.saveAllUsers(users);

        // Success message
        JOptionPane.showMessageDialog(this, "Signup successful! Please login.");

        // Signup ke baad direct Login screen open
        new LoginScreen().setVisible(true);
        dispose();
    }

    // ===== INNER USER CLASS =====
    // Serializable – object file me store karne ke liye
    public static class User implements Serializable {

        // User data members
        private String username;
        private String email;
        private String password;

        // User constructor
        public User(String username, String email, String password) {
            this.username = username;
            this.email = email;
            this.password = password;
        }

        // Getter methods
        public String getUsername() { return username; }
        public String getEmail() { return email; }
        public String getPassword() { return password; }
    }
}
