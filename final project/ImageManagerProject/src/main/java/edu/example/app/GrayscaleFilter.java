package edu.example.app;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * GrayscaleFilter - converts image to grayscale and updates thumbnail.
 */
public class GrayscaleFilter implements Filter {
    @Override
    public void apply(ImageFile input) throws ImageProcessingException {
        try {
            BufferedImage src = ImageIO.read(input.getPath().toFile());
            BufferedImage gray = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
            for (int y=0;y<src.getHeight();y++){
                for (int x=0;x<src.getWidth();x++){
                    int rgb = src.getRGB(x,y);
                    int a = (rgb>>24)&0xff;
                    int r = (rgb>>16)&0xff;
                    int g = (rgb>>8)&0xff;
                    int b = rgb&0xff;
                    int lum = (r+g+b)/3;
                    int gr = (a<<24) | (lum<<16) | (lum<<8) | lum;
                    gray.setRGB(x,y,gr);
                }
            }
            Image fx = SwingFXUtils.toFXImage(gray, null);
            WritableImage thumb = new WritableImage(100,100);
            javafx.scene.SnapshotParameters params = new javafx.scene.SnapshotParameters();
            javafx.scene.image.ImageView iv = new javafx.scene.image.ImageView(fx);
            iv.setFitWidth(100); iv.setFitHeight(100); iv.setPreserveRatio(true);
            iv.snapshot(params, thumb);
            input.setThumbnail(thumb);
            // Optionally overwrite original - skip to avoid data loss
        } catch (Exception e) {
            throw new ImageProcessingException("Failed to apply grayscale: " + e.getMessage(), e);
        }
    }
}
