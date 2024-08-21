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
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText(response);

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
