package edu.example.app;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * MetadataUtil - reads EXIF and other metadata using metadata-extractor library.
 */
public class MetadataUtil {
    public static Map<String, String> extractMetadata(Path path) {
        Map<String,String> map = new HashMap<>();
        try {
            File f = path.toFile();
            Metadata metadata = ImageMetadataReader.readMetadata(f);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    map.put(tag.getTagName(), tag.getDescription());
                }
            }
        } catch (Exception e) {
            // Gracefully handle missing/unsupported metadata
            // Return empty map
        }
        return map;
    }
}
