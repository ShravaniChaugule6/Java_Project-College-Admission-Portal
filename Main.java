import javax.swing.SwingUtilities;   // Swing thread handle karne ke liye

// Main class – application ka entry point
public class Main {

    // main method – program yahin se start hota hai
    public static void main(String[] args) {

        // SwingUtilities.invokeLater ka use
        // taaki Swing GUI Event Dispatch Thread (EDT) par run ho
        SwingUtilities.invokeLater(() ->

            // Sabse pehle SignupScreen open hoti hai
            new SignupScreen().setVisible(true)
        );
    }
}
