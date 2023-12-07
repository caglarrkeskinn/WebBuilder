package filemanager;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class AddingPageGUI extends JFrame implements ActionListener {
    JLabel label;
    JTextField pageCountField;
    JButton saveButton;

    public AddingPageGUI() {
        setTitle("Create Pages");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new java.awt.FlowLayout());

        label = new JLabel("How many pages do you want?");
        add(label);

        pageCountField = new JTextField(10);
        add(pageCountField);

        saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        add(saveButton);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            String pageCountText = pageCountField.getText();

            try {
                int pageCount = Integer.parseInt(pageCountText);

                JFileChooser fileChooser = new JFileChooser("Choose Directory");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int option = fileChooser.showSaveDialog(this);

                if (option == JFileChooser.APPROVE_OPTION) {
                    File selectedFolder = fileChooser.getSelectedFile();
                    String folderPath = selectedFolder.getAbsolutePath();

                    JFileChooser appFileChooser = new JFileChooser();
                    appFileChooser.setDialogTitle("Select The App.js File");
                    appFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    int appOption = appFileChooser.showSaveDialog(this);
                    
                    JFileChooser navbarChooser = new JFileChooser();
                    navbarChooser.setDialogTitle("Select The Header File");
                    navbarChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    int navbarOption = navbarChooser.showSaveDialog(this);

                    if (appOption == JFileChooser.APPROVE_OPTION) {
                        File appFile = appFileChooser.getSelectedFile();
                        String appFilePath = appFile.getAbsolutePath();
                        
                        
                        

                        List<String> fileNames = new ArrayList<>();

                        for (int i = 1; i <= pageCount; i++) {
                            String fileName = JOptionPane.showInputDialog(this, "Please enter the name of the page: " + i);
                            if (fileName == null || fileName.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Please enter a valid file name!");
                                return;
                            } else {
                            	// Converting Turkish characters to English characters
                                fileName = convertToEnglishCharacters(fileName); 
                                fileName = fileName.substring(0, 1).toUpperCase() + fileName.substring(1); 

                                String fileContent = "import React from 'react';\n\n" +
                                        "function " + fileName + "() {\n" +
                                        "    return (\n" +
                                        "        <h1 style={{ marginTop: '120px',}}>" + fileName + "</h1>\n" +
                                        "    );\n" +
                                        "}\n\n" +
                                        "export default " + fileName + ";";

                                fileNames.add(fileName);

                                Path filePath = Paths.get(folderPath, fileName + ".js");
                                Files.write(filePath, fileContent.getBytes());
                                
                                
                            }
                        }

                        // Update the App.js file.
                        try {
                            StringBuilder appFileContent = new StringBuilder(Files.readString(Paths.get(appFilePath)));

                            for (String fileName : fileNames) {
                                String importStatement = "import " + fileName + " from './components/" + fileName + "';\n";
                                String routeElement = "<Route exact path=\"/" + fileName + "\" element={<" + fileName + " />} />\n";

                                if (!appFileContent.toString().contains(importStatement)) {
                                    appFileContent.insert(appFileContent.indexOf("import './App.css';") + "import './App.css';".length() + 1, importStatement);
                                }

                                if (!appFileContent.toString().contains(routeElement)) {
                                    appFileContent.insert(appFileContent.indexOf("<Routes>") + "<Routes>".length() + 1, routeElement);
                                }
                            }

                            Files.write(Paths.get(appFilePath), appFileContent.toString().getBytes());
                            JOptionPane.showMessageDialog(this, "App.js has been successfully updated!");
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(this, "Error: App.js could not be updated!");
                            ex.printStackTrace();
                        }
                        
                        if(navbarOption == JFileChooser.APPROVE_OPTION) {
                        	File navbarFile = navbarChooser.getSelectedFile();
                            String navbarFilePath = navbarFile.getAbsolutePath();
                            
                            
                         // Update the Header file.
    						try {
                                StringBuilder navbarFileContent = new StringBuilder(Files.readString(Paths.get(navbarFilePath)));

                                for (String fileName : fileNames) {
                                	// <Nav.Link href="/home">Ana Sayfa</Nav.Link>
                                    String linkElement = "<Nav.Link href=\"/" + fileName + "\">"+fileName+"</Nav.Link>\n";

                                    if (!navbarFileContent.toString().contains(linkElement)) {
                                    	navbarFileContent.insert(navbarFileContent.indexOf("<Nav className=\"me-auto\">") + "<Nav className=\"me-auto\">".length() + 1, linkElement);
                                    }
                                }

                                Files.write(Paths.get(navbarFilePath), navbarFileContent.toString().getBytes());
                                JOptionPane.showMessageDialog(this, "Navbar has been successfully updated!");
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(this, "Error: Navbar could not be updated!");
                                ex.printStackTrace();
                            }
                        }
                        

                        JOptionPane.showMessageDialog(this, pageCount + " page(s) have been successfully created!");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error: Pages could not be created!");
                ex.printStackTrace();
            }
        }
    }

    private String convertToEnglishCharacters(String input) {
        input = input.replaceAll("ı", "i").replaceAll("İ", "I").replaceAll("ş", "s").replaceAll("Ş", "S")
                .replaceAll("ğ", "g").replaceAll("Ğ", "G").replaceAll("ü", "u").replaceAll("Ü", "U")
                .replaceAll("ö", "o").replaceAll("Ö", "O").replaceAll("ç", "c").replaceAll("Ç", "C");
        return input;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AddingPageGUI app = new AddingPageGUI();
            app.setVisible(true);
        });
    }
}
