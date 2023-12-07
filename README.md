# Web Automation Project

This project contains a Java Swing application that takes the number of pages to create and the names of these pages from the user, and generates page files for a React project. Although we currently do not require the photo upload interface, I aim to utilize this interface in the future of the project to integrate selected photos into chosen pages.

## Usage

1. **Determining the Number of Pages and Page Names:** Upon starting the application, the user is prompted to specify the number of pages to create and name these pages.
2. **Creating Files:** After the user enters the page names, files with .js extension are generated based on these names and saved to the specified directory (correct directory selection is expected from the user).
3. **Updating App.js:** The created files are added as Routes in the App.js file.
4. **Updating header.js:** The created files are added as Nav.Links in the header.js file.
## Technologies Used

- Java Swing

## How to Run

1. Clone this project.
2. Open and run `UploadPhotoGUI.java` and `PageNames.java` files using a Java IDE.

## Contributing

If you discover any issues or wish to propose improvements, please feel free to submit a pull request.

