package edu.example.app;

/**
 * ImageProcessingException - custom exception for image processing errors.
 */
public class ImageProcessingException extends Exception {
    public ImageProcessingException(String msg) { super(msg); }
    public ImageProcessingException(String msg, Throwable cause) { super(msg, cause); }
}
