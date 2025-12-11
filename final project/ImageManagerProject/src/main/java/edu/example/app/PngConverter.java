package edu.example.app;

import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * PngConverter - converts any supported input image to PNG.
 */
public class PngConverter implements Converter {
    @Override
    public Path convert(Path inputFile, String targetFormat) throws ImageProcessingException {
        try {
            BufferedImage img = ImageIO.read(inputFile.toFile());
            Path out = Files.createTempFile("conv-", ".png");
            ImageIO.write(img, "png", out.toFile());
            return out;
        } catch (Exception e) {
            throw new ImageProcessingException("Unable to convert to PNG: " + e.getMessage(), e);
        }
    }
}
