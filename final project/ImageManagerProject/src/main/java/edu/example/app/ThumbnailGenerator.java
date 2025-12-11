package edu.example.app;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

/**
 * ThumbnailGenerator - utility class to create thumbnails and read images.
 */
public class ThumbnailGenerator {
    public static Image generateThumbnail(Path path, int w, int h) throws ImageProcessingException {
        try {
            Image fx = readImage(path);
            // create scaled thumbnail preserving ratio by using Image constructor
            Image thumb = new Image(path.toUri().toString(), w, h, true, true);
            return thumb;
        } catch (Exception e) {
            throw new ImageProcessingException("Failed to create thumbnail", e);
        }
    }

    public static Image readImage(Path path) throws IOException {
        return new Image(path.toUri().toString());
    }
}
