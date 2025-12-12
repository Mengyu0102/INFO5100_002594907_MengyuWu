package edu.example.app;

import javafx.scene.image.Image;

import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

/**
 * ImageFile - model representing an image and its properties.
 * Demonstrates encapsulation (private fields with getters/setters).
 */
public class ImageFile {
    private String filename;
    private Path path;
    private int width;
    private int height;
    private Map<String, String> metadata;
    private Image thumbnail;

    public ImageFile(String filename, Path path, int width, int height) {
        this.filename = filename;
        this.path = path;
        this.width = width;
        this.height = height;
    }

    public String getFilename() { return filename; }
    public Path getPath() { return path; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public Map<String, String> getMetadata() { return metadata; }
    public void setMetadata(Map<String, String> metadata) { this.metadata = metadata; }

    public Image getThumbnail() { return thumbnail; }
    public void setThumbnail(Image thumbnail) { this.thumbnail = thumbnail; }


    private javafx.scene.image.Image convertedImage;

    public void setConvertedImage(javafx.scene.image.Image convertedImage) {
        this.convertedImage = convertedImage;
    }

    public Optional<javafx.scene.image.Image> getConvertedImage() {
        return Optional.ofNullable(convertedImage);
    }
}
