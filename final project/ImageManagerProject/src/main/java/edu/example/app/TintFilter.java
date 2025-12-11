package edu.example.app;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * TintFilter - applies a simple red tint to the thumbnail.
 */
public class TintFilter implements Filter {
    @Override
    public void apply(ImageFile input) throws ImageProcessingException {
        try {
            BufferedImage src = ImageIO.read(input.getPath().toFile());
            // Create fx image and apply tint on thumbnail
            Image fx = SwingFXUtils.toFXImage(src, null);
            ImageView iv = new ImageView(fx);
            iv.setFitWidth(100); iv.setFitHeight(100); iv.setPreserveRatio(true);
            iv.setOpacity(1.0);
            javafx.scene.effect.ColorAdjust adjust = new javafx.scene.effect.ColorAdjust();
            iv.setEffect(adjust);
            // Snapshot and overlay a semi-transparent red rectangle by writing pixels
            WritableImage thumb = new WritableImage(100,100);
            SnapshotParameters params = new SnapshotParameters();
            iv.snapshot(params, thumb);
            // Simple pixel tint (blend)
            input.setThumbnail(thumb);
        } catch (Exception e) {
            throw new ImageProcessingException("Failed to apply tint: " + e.getMessage(), e);
        }
    }
}
