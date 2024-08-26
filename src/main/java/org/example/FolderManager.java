package org.example;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;


public class FolderManager {

    private final String PATH = "C:\\Users\\40764\\Desktop\\Nexus Curs\\GymRatAI\\Previous Meal Planned";
    File directory;
    String directoryName;

    public FolderManager(String directoryName) {
        this.directoryName = directoryName;
        directory = new File(PATH, directoryName);
        makeDirectory();
    }

    private void makeDirectory() {
        if (!directory.exists()) {
            directory.mkdir();
            return;
        }

        System.out.println("Directory already exists");
    }

    public void createDocxFile(String response) {
        String docxFileName = directoryName + ".docx";
        File docxFile = new File(directory, docxFileName);

        try (XWPFDocument document = new XWPFDocument()) {
            // Create a new paragraph in the document
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();

            // Split the response string by newline characters to preserve paragraph structure
            String[] lines = response.split("\n");

            for (String line : lines) {
                run.setText(line);
                run.addBreak(); // Adds a new line for each line in the original string
            }

            // Write the document to the file
            try (FileOutputStream out = new FileOutputStream(docxFile)) {
                document.write(out);
            }
            System.out.println(docxFileName + " created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FolderManager folderManager = new FolderManager("user.dir");
    }
}
