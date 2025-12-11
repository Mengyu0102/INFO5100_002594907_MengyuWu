package edu.example.app;

/**
 * Filter - interface for image filters (Strategy).
 */
public interface Filter {
    void apply(ImageFile input) throws ImageProcessingException;
}
