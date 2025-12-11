package edu.example.app;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Parent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.util.*;

/**
 * Controller - handles the JavaFX UI and ties model (ImageManager) with view.
 * - Upload images
 * - Display thumbnails (100x100)
 * - Show properties and metadata
 * - Convert, apply filters, download
 */
public class Controller {
    private Stage stage;
    private BorderPane root;

    private TilePane tilePane;
    private VBox rightPane;
    private ImageManager imageManager;
    private Label lblInfo;
    private ListView<String> metadataList;
    private Button btnConvert, btnDownload, btnGrayscale, btnTint;

    public Controller(Stage stage) {
        this.stage = stage;
        this.imageManager = new ImageManager();
        createUI();
    }

    public Parent getRoot() {
        return root;
    }

    private void createUI() {
        root = new BorderPane();
        // Top controls
        HBox top = new HBox(10);
        top.setPadding(new Insets(10));
        Button btnUpload = new Button("Upload");
        btnUpload.setOnAction(e -> onUpload());
        top.getChildren().addAll(btnUpload);
        root.setTop(top);

        // Center thumbnails
        tilePane = new TilePane();
        tilePane.setHgap(10);
        tilePane.setVgap(10);
        tilePane.setPadding(new Insets(10));
        root.setCenter(new ScrollPane(tilePane));

        // Right pane for details
        rightPane = new VBox(10);
        rightPane.setPadding(new Insets(10));
        lblInfo = new Label("Select an image to see details.");
        metadataList = new ListView<>();
        btnConvert = new Button("Convert to PNG");
        btnConvert.setOnAction(e -> onConvert("png"));
        btnDownload = new Button("Download Converted");
        btnDownload.setOnAction(e -> onDownload());
        btnGrayscale = new Button("Apply Grayscale");
        btnGrayscale.setOnAction(e -> onApplyGrayscale());
        btnTint = new Button("Apply Red Tint");
        btnTint.setOnAction(e -> onApplyTint());
        rightPane.getChildren().addAll(lblInfo, new Label("Metadata:"), metadataList, btnConvert, btnDownload, btnGrayscale, btnTint);

        root.setRight(rightPane);
    }

    private void onUpload() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Images");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp"));
        List<File> files = chooser.showOpenMultipleDialog(stage);
        if (files == null) return;
        for (File f : files) {
            try {
                ImageFile imgFile = imageManager.addImage(f.toPath());
                ImageView iv = createThumbnailView(imgFile);
                tilePane.getChildren().add(iv);
            } catch (ImageProcessingException ex) {
                showError("Failed to load image: " + ex.getMessage());
            }
        }
    }

    private ImageView createThumbnailView(ImageFile imgFile) throws ImageProcessingException {
        Image thumb = ThumbnailGenerator.generateThumbnail(imgFile.getPath(), 100, 100);
        imgFile.setThumbnail(thumb);
        ImageView iv = new ImageView(thumb);
        iv.setUserData(imgFile);
        iv.setFitWidth(100);
        iv.setFitHeight(100);
        iv.setPreserveRatio(false);
        iv.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> onThumbnailClicked(iv));
        return iv;
    }

    private void onThumbnailClicked(ImageView iv) {
        ImageFile f = (ImageFile) iv.getUserData();
        lblInfo.setText(String.format("File: %s\nSize: %dx%d", f.getFilename(), f.getWidth(), f.getHeight()));
        metadataList.getItems().clear();
        if (f.getMetadata() != null && !f.getMetadata().isEmpty()) {
            f.getMetadata().forEach((k,v) -> metadataList.getItems().add(k + ": " + v));
        } else {
            metadataList.getItems().add("No metadata available");
        }
    }

    private void onConvert(String format) {
        try {
            Optional<ImageFile> opt = imageManager.getSelected();
            if (opt.isEmpty()) {
                showError("No image selected");
                return;
            }
            ImageFile selected = opt.get();
            Converter converter;
            switch(format.toLowerCase()) {
                case "png": converter = new PngConverter(); break;
                case "jpg": converter = new JpgConverter(); break;
                default: converter = new PngConverter(); break;
            }
            Path out = imageManager.convert(selected, converter, format);
            showInfo("Converted and saved to: " + out.toString());
        } catch (ImageProcessingException ex) {
            showError("Conversion failed: " + ex.getMessage());
        }
    }

    private void onDownload() {
        showInfo("Converted files are saved in temp directory. Please check the path shown after conversion.");
    }

    private void onApplyGrayscale() {
        Optional<ImageFile> opt = imageManager.getSelected();
        if (opt.isEmpty()) { showError("No image selected"); return;}
        ImageFile f = opt.get();
        try {
            Filter g = new GrayscaleFilter();
            imageManager.applyFilter(f, g);
            // refresh thumbnail
            tilePane.getChildren().removeIf(node -> ((ImageView)node).getUserData()==f);
            ImageView iv = new ImageView(f.getThumbnail());
            iv.setUserData(f);
            iv.setFitWidth(100); iv.setFitHeight(100); iv.setPreserveRatio(true);
            iv.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> onThumbnailClicked(iv));
            tilePane.getChildren().add(iv);
            showInfo("Grayscale applied");
        } catch (ImageProcessingException e) {
            showError("Filter failed: " + e.getMessage());
        }
    }

    private void onApplyTint() {
        Optional<ImageFile> opt = imageManager.getSelected();
        if (opt.isEmpty()) { showError("No image selected"); return;}
        ImageFile f = opt.get();
        try {
            Filter t = new TintFilter();
            imageManager.applyFilter(f, t);
            tilePane.getChildren().removeIf(node -> ((ImageView)node).getUserData()==f);
            ImageView iv = new ImageView(f.getThumbnail());
            iv.setUserData(f);
            iv.setFitWidth(100); iv.setFitHeight(100); iv.setPreserveRatio(true);
            iv.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> onThumbnailClicked(iv));
            tilePane.getChildren().add(iv);
            showInfo("Tint applied");
        } catch (ImageProcessingException e) {
            showError("Filter failed: " + e.getMessage());
        }
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg);
        a.showAndWait();
    }
    private void showInfo(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg);
        a.showAndWait();
    }
}
