package edu.example.app;

import javafx.scene.image.Image;
import java.nio.file.Path;

/**
 * Converter - Strategy interface for converting images to different formats.
 */
public interface Converter {
    Image convert(Path inputPath) throws ImageProcessingException;
}