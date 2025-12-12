package edu.example.app;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.File;
import javafx.embed.swing.SwingFXUtils;

/**
 * PngConverter - converts any supported input image to PNG.
 */
public class PngConverter implements Converter {
    @Override
    public javafx.scene.image.Image convert(Path inputPath) throws ImageProcessingException {
        try {
            BufferedImage img = ImageIO.read(inputPath.toFile());
            // 核心修改：将 BufferedImage 转换为 JavaFX Image 对象并返回
            return SwingFXUtils.toFXImage(img, null);
        } catch (Exception e) {
            throw new ImageProcessingException("Unable to convert to JPG/PNG: " + e.getMessage(), e);
        }
    }
}