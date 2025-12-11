package edu.example.app;

import java.nio.file.Path;

/**
 * Converter - Strategy interface for converting images to different formats.
 */
public interface Converter {
    Path convert(Path inputFile, String targetFormat) throws ImageProcessingException;
}
