import java.awt.*;               // Layouts, Color, Font ke liye
import java.util.ArrayList;      // Users list ke liye
import javax.swing.*;            // Swing components

// LoginScreen class – Admin aur Student dono ke login ke liye
public class LoginScreen extends JFrame {

    // Username input field
    private JTextField usernameField;

    // Password input field (hidden characters)
    private JPasswordField passwordField;

    // Constructor – Login UI banata hai
    public LoginScreen() {

        // Frame properties
        setTitle("Login - College Admission Portal");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(230, 240, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ===== TITLE =====
        JLabel title = new JLabel("Login", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(0, 102, 204));
        mainPanel.add(title, BorderLayout.NORTH);

        // ===== FORM PANEL =====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        // GridBagConstraints – component positioning ke liye
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ===== USERNAME FIELD =====
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        // ===== PASSWORD FIELD =====
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Password:"), gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        // Form panel ko center me add karna
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // ===== BUTTON PANEL =====
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);

        // Login & Signup buttons
        JButton loginBtn = new JButton("Login");
        JButton signupBtn = new JButton("Signup");

        // Login button styling
        loginBtn.setBackground(new Color(0, 153, 76));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setFocusPainted(false);

        // Signup button styling
        signupBtn.setBackground(new Color(0, 102, 204));
        signupBtn.setForeground(Color.WHITE);
        signupBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        signupBtn.setFocusPainted(false);

        // Buttons add karna
        buttonPanel.add(loginBtn);
        buttonPanel.add(signupBtn);

        // Button panel ko bottom me add karna
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Main panel frame me add karna
        add(mainPanel);

        // ===== ACTION LISTENERS =====

        // Login button click
        loginBtn.addActionListener(e -> login());

        // Signup button click – Signup screen open
        signupBtn.addActionListener(e -> {
            new SignupScreen().setVisible(true);
            dispose(); // current login window close
        });
    }

    // ===== LOGIN LOGIC METHOD =====
    private void login() {

        // Username aur password read karna
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Empty field validation
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password!");
            return;
        }

        // ===== ADMIN LOGIN =====
        if (username.equals("admin") && password.equals("admin123")) {
            new AdminDashboard().setVisible(true); // Admin dashboard open
            dispose(); // Login screen close
            return;
        }

        // ===== STUDENT / USER LOGIN =====
        ArrayList<SignupScreen.User> users = DataAccess.loadAllUsers();

        // Sabhi users me check karna
        for (SignupScreen.User u : users) {

            // Username & password match
            if (u.getUsername().equals(username) &&
                u.getPassword().equals(password)) {

                new StudentForm().setVisible(true); // Student form open
                dispose(); // Login screen close
                return;
            }
        }

        // Agar koi match nahi mila
        JOptionPane.showMessageDialog(this, "Invalid username or password!");
    }
}
