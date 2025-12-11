package edu.example.app;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * JpgConverter - converts input image to JPG.
 */
public class JpgConverter implements Converter {
    @Override
    public Path convert(Path inputFile, String targetFormat) throws ImageProcessingException {
        try {
            BufferedImage img = ImageIO.read(inputFile.toFile());
            Path out = Files.createTempFile("conv-", ".jpg");
            ImageIO.write(img, "jpg", out.toFile());
            return out;
        } catch (Exception e) {
            throw new ImageProcessingException("Unable to convert to JPG: " + e.getMessage(), e);
        }
    }
}
