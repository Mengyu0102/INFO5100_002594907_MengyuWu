package edu.example.app;

import java.nio.file.Path;
import java.util.*;

/**
 * ImageManager - manages images loaded into the application.
 * Provides methods to add images, convert using a Converter strategy,
 * and apply filters.
 */
public class ImageManager {
    private List<ImageFile> images = new ArrayList<>();
    private ImageFile selected = null;

    public ImageFile addImage(Path path) throws ImageProcessingException {
        try {
            // Use ThumbnailGenerator to get size
            javafx.scene.image.Image img = ThumbnailGenerator.readImage(path);
            ImageFile f = new ImageFile(path.getFileName().toString(), path, (int)img.getWidth(), (int)img.getHeight());
            Map<String,String> meta = MetadataUtil.extractMetadata(path);
            f.setMetadata(meta);
            images.add(f);
            selected = f;
            return f;
        } catch (Exception e) {
            throw new ImageProcessingException("Failed to add image: " + e.getMessage(), e);
        }
    }

    public Optional<ImageFile> getSelected() {
        return Optional.ofNullable(selected);
    }

    // ImageManager.java (convert)

    public void convert(ImageFile input, Converter converter) throws ImageProcessingException {
        javafx.scene.image.Image converted = converter.convert(input.getPath());
        input.setConvertedImage(converted);
    }

    public void applyFilter(ImageFile input, Filter filter) throws ImageProcessingException {
        filter.apply(input);
    }
}
