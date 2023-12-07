package filemanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class CreatingReactProjectGUI extends JFrame implements ActionListener {
    JLabel label;
    JTextField projectNameField;
    JButton createButton;

    public CreatingReactProjectGUI() {
        setTitle("React Project Creator");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new java.awt.FlowLayout());

        label = new JLabel("Enter the project name:");
        add(label);

        projectNameField = new JTextField(15);
        add(projectNameField);

        createButton = new JButton("Create New Project");
        createButton.addActionListener(this);
        add(createButton);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            String projectName = projectNameField.getText();

            if (projectName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a project name!");
            } else {
            	try {
                    String os = System.getProperty("os.name").toLowerCase();

                    if (os.contains("mac")) {
                        
                     // Command to open Terminal on Mac
                        ProcessBuilder openTerminal = new ProcessBuilder("open", "-a", "Terminal");
                        openTerminal.start();
                        
                        ProcessBuilder pb = new ProcessBuilder("osascript", "-e", "tell application \"Terminal\" to do script \"npx create-react-app " + projectName + "\"");
                        Process process = pb.start();
                        
                     
                        
                        Thread.sleep(60000);
                        
                        ProcessBuilder closeTerminal = new ProcessBuilder("osascript", "-e", "tell application \"Terminal\" to quit");
                        closeTerminal.start();
                        
                    

                    } else {
                        JOptionPane.showMessageDialog(null, "This operating system is not supported.");
                    }
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            	
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CreatingReactProjectGUI app = new CreatingReactProjectGUI();
            app.setVisible(true);
        });
    }
}
