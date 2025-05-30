package com.doc.reader;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import javafx.scene.control.ScrollPane;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button openButton = new Button("Open PDF");
        openButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open PDF File");
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                try {
                    PDDocument document = PDDocument.load(selectedFile);
                    PDFTextStripper stripper = new PDFTextStripper();
                    String text = stripper.getText(document);
                    document.close();
                    pdfTextArea.setText(text);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    pdfTextArea.setText("Error loading PDF: " + ex.getMessage());
                }
            }
        });

        pdfTextArea = new TextArea();
        pdfTextArea.setEditable(false);
        ScrollPane scrollPane = new ScrollPane(pdfTextArea);

        StackPane root = new StackPane(scrollPane);
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("PDF Reader");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TextArea pdfTextArea;

    public static void main(String[] args) {
        launch(args);
    }
}