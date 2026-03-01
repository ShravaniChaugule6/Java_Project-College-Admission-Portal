import java.awt.*;                  // AWT classes for layout, color, font
import java.util.ArrayList;         // To store list of students
import javax.swing.*;               // Swing components like JFrame, JButton, JTable
import javax.swing.table.DefaultTableModel; // Table model for JTable

// AdminDashboard class – Admin ka main screen
public class AdminDashboard extends JFrame {

    private JTable table;                  // Table to display students
    private DefaultTableModel tableModel;  // Model to manage table data
    private JTextField searchField;        // Text field for name search
    private JComboBox<String> filterBox;   // Dropdown for status filter

    // Constructor – UI initialize hota hai
    public AdminDashboard() {

        // Frame basic settings
        setTitle("Admin Dashboard - College Admission Portal");
        setSize(1300, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        // ===== TITLE SECTION =====
        JLabel title = new JLabel("Admin Dashboard", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(new Color(0, 102, 204));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // ===== TOP PANEL (Search & Filter) =====
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        topPanel.setBackground(new Color(230, 240, 255));

        // Search label
        topPanel.add(new JLabel("Search Name:"));

        // Search text field
        searchField = new JTextField(15);
        topPanel.add(searchField);

        // Search button
        JButton searchBtn = new JButton("Search");
        topPanel.add(searchBtn);

        // Filter label
        topPanel.add(new JLabel("Filter Status:"));

        // Status dropdown
        filterBox = new JComboBox<>(new String[]{"All", "Pending", "Approved", "Rejected"});
        topPanel.add(filterBox);

        // Filter button
        JButton filterBtn = new JButton("Apply Filter");
        topPanel.add(filterBtn);

        add(topPanel, BorderLayout.BEFORE_FIRST_LINE);

        // ===== TABLE SECTION =====
        // INDEX column hidden rehta hai but actual student index store karta hai
        tableModel = new DefaultTableModel(
                new String[]{
                        "INDEX",      // hidden index
                        "Name","Age","Gender","Course","Email","Phone",
                        "Address","10th %","12th %","Birthdate","Status"
                }, 0
        );

        // JTable create
        table = new JTable(tableModel);
        table.setRowHeight(28);

        // INDEX column hide karna
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== BOTTOM PANEL (Buttons) =====
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        bottomPanel.setBackground(new Color(230, 240, 255));

        // Buttons create
        JButton approveBtn = new JButton("Approve");
        JButton rejectBtn = new JButton("Reject");
        JButton pendingBtn = new JButton("Pending");
        JButton deleteBtn = new JButton("Delete");
        JButton refreshBtn = new JButton("Refresh");
        JButton logoutBtn = new JButton("Logout");

        // Buttons array
        JButton[] buttons = {approveBtn, rejectBtn, pendingBtn, deleteBtn, refreshBtn, logoutBtn};

        // Button colors
        Color[] colors = {
                new Color(0,153,0),    // Approve - Green
                new Color(204,0,0),    // Reject - Red
                new Color(255,204,0),  // Pending - Yellow
                new Color(160,0,0),    // Delete - Dark Red
                new Color(255,153,0),  // Refresh - Orange
                new Color(96,96,96)    // Logout - Grey
        };

        // Buttons styling loop
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setFont(new Font("Segoe UI", Font.BOLD, 14));
            buttons[i].setBackground(colors[i]);
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setFocusPainted(false);
            bottomPanel.add(buttons[i]);
        }

        add(bottomPanel, BorderLayout.SOUTH);

        // ===== LOAD STUDENT DATA =====
        loadData();

        // ===== BUTTON ACTIONS =====
        approveBtn.addActionListener(e -> changeStatus("Approved"));
        rejectBtn.addActionListener(e -> changeStatus("Rejected"));
        pendingBtn.addActionListener(e -> changeStatus("Pending"));
        deleteBtn.addActionListener(e -> deleteStudent());
        refreshBtn.addActionListener(e -> loadData());

        // Logout – Signup screen open
        logoutBtn.addActionListener(e -> {
            new SignupScreen().setVisible(true);
            dispose();
        });

        // Search & Filter actions
        searchBtn.addActionListener(e -> searchByName());
        filterBtn.addActionListener(e -> filterByStatus());
    }

    // ===== LOAD ALL STUDENTS INTO TABLE =====
    private void loadData() {

        // Table clear
        tableModel.setRowCount(0);

        // All students from file
        ArrayList<Student> students = DataAccess.getAllStudents();

        // Add students to table
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            tableModel.addRow(new Object[]{
                    i,                        // hidden index
                    s.getName(),
                    s.getAge(),
                    s.getGender(),
                    s.getCourse(),
                    s.getEmail(),
                    s.getPhone(),
                    s.getAddress(),
                    s.getTenthPercent(),
                    s.getTwelfthPercent(),
                    s.getBirthDate(),
                    s.getStatus()
            });
        }
    }

    // ===== CHANGE STUDENT STATUS =====
    private void changeStatus(String newStatus) {

        // Selected row check
        int viewRow = table.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student!");
            return;
        }

        // Actual model row
        int modelRow = table.convertRowIndexToModel(viewRow);

        // Real index from hidden column
        int realIndex = (int) tableModel.getValueAt(modelRow, 0);

        // Student list
        ArrayList<Student> students = DataAccess.getAllStudents();

        // Status update
        students.get(realIndex).setStatus(newStatus);

        // Save back to file
        DataAccess.saveAllStudents(students);

        // Reload table
        loadData();
    }

    // ===== DELETE STUDENT =====
    private void deleteStudent() {

        int viewRow = table.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student!");
            return;
        }

        // Confirmation dialog
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this student?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        int modelRow = table.convertRowIndexToModel(viewRow);
        int realIndex = (int) tableModel.getValueAt(modelRow, 0);

        ArrayList<Student> students = DataAccess.getAllStudents();

        // Remove student
        students.remove(realIndex);

        // Save changes
        DataAccess.saveAllStudents(students);

        loadData();
    }

    // ===== SEARCH BY NAME =====
    private void searchByName() {

        String key = searchField.getText().trim().toLowerCase();
        tableModel.setRowCount(0);

        ArrayList<Student> students = DataAccess.getAllStudents();

        // Name match logic
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            if (s.getName().toLowerCase().contains(key)) {
                tableModel.addRow(new Object[]{
                        i,
                        s.getName(),
                        s.getAge(),
                        s.getGender(),
                        s.getCourse(),
                        s.getEmail(),
                        s.getPhone(),
                        s.getAddress(),
                        s.getTenthPercent(),
                        s.getTwelfthPercent(),
                        s.getBirthDate(),
                        s.getStatus()
                });
            }
        }
    }

    // ===== FILTER BY STATUS =====
    private void filterByStatus() {

        String status = (String) filterBox.getSelectedItem();
        tableModel.setRowCount(0);

        ArrayList<Student> students = DataAccess.getAllStudents();

        // Status filter logic
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            if (status.equals("All") || s.getStatus().equals(status)) {
                tableModel.addRow(new Object[]{
                        i,
                        s.getName(),
                        s.getAge(),
                        s.getGender(),
                        s.getCourse(),
                        s.getEmail(),
                        s.getPhone(),
                        s.getAddress(),
                        s.getTenthPercent(),
                        s.getTwelfthPercent(),
                        s.getBirthDate(),
                        s.getStatus()
                });
            }
        }
    }
}