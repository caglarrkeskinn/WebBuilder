package filemanager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class UploadPhotoGUI extends JFrame implements ActionListener {
    JButton selectButton;
    JFileChooser fileChooser;
    
    public UploadPhotoGUI() {
    	setTitle("Upload Photo");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new java.awt.FlowLayout());

        selectButton = new JButton("Choose a Photo");
        selectButton.addActionListener(this);
        add(selectButton);

        fileChooser = new JFileChooser();
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == selectButton) {
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                saveToNewFolder(selectedFile);
            }
        }
    }
    
    private void saveToNewFolder(File selectedFile) {
        String destPath = System.getProperty("user.home") + "/website-template/src/assets/images";

        try {

            Path sourcePath = Paths.get(selectedFile.getAbsolutePath());
            Path destinationPath = Paths.get(destPath + "/" + selectedFile.getName());

            Files.copy(sourcePath, destinationPath);

            JOptionPane.showMessageDialog(this, "The photo has been saved successfully!");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: The photo could not be saved!");
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	UploadPhotoGUI app = new UploadPhotoGUI();
            app.setVisible(true);
        });
    }

}
