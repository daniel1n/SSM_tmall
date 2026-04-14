package com.qqlin.tmall.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * Image processing utility class for thumbnail generation and format conversion.
 *
 * <p>Provides static methods for:</p>
 * <ul>
 *   <li>Converting various image formats (PNG, GIF, etc.) to JPEG</li>
 *   <li>Resizing images to specified dimensions for thumbnails</li>
 * </ul>
 *
 * <p>Uses Java AWT and ImageIO for image manipulation. The resize methods use
 * {@link Image#SCALE_SMOOTH} algorithm for quality thumbnail generation.</p>
 *
 * @author qqlin
 * @date 2020-5-18
 */
public class ImageUtil {

    private static final Logger log = LoggerFactory.getLogger(ImageUtil.class);

    private ImageUtil() {
        // Utility class, prevent instantiation
    }

    /**
     * Converts an image file to JPEG format.
     *
     * <p>Handles various input formats (PNG, GIF, BMP, etc.) by reading pixel data
     * and writing it as a standard RGB BufferedImage in JPEG format.</p>
     *
     * <p>Uses ImageIO directly for better compatibility with modern image formats.</p>
     *
     * @param f the source image file to convert
     * @return a BufferedImage in JPEG-compatible format, or {@code null} if conversion fails
     */
    public static BufferedImage change2jpg(File f) {
        try (ImageInputStream iis = ImageIO.createImageInputStream(f)) {
            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
            if (!readers.hasNext()) {
                log.warn("No image reader found for file: {}", f.getAbsolutePath());
                return null;
            }
            ImageReader reader = readers.next();
            reader.setInput(iis);
            BufferedImage image = reader.read(0);
            reader.dispose();

            // Convert to RGB if necessary
            if (image.getType() != BufferedImage.TYPE_INT_RGB) {
                BufferedImage rgbImage = new BufferedImage(
                        image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
                rgbImage.getGraphics().drawImage(image, 0, 0, null);
                return rgbImage;
            }
            return image;
        } catch (IOException e) {
            log.error("Failed to convert image to JPEG: {}", f.getAbsolutePath(), e);
            return null;
        }
    }

    /**
     * Resizes an image file and saves it to a destination file in JPEG format.
     *
     * <p>Creates parent directories of destFile if they do not exist.
     * Uses {@link #resizeImage(Image, int, int)} for the actual scaling.</p>
     *
     * @param srcFile  the source image file to resize
     * @param width    the target width in pixels
     * @param height   the target height in pixels
     * @param destFile the destination file to write the resized image
     */
    public static void resizeImage(File srcFile, int width, int height, File destFile) {
        try {
            File parentDir = destFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            BufferedImage srcImage = ImageIO.read(srcFile);
            if (srcImage == null) {
                log.warn("Failed to read image file: {}", srcFile.getAbsolutePath());
                return;
            }
            BufferedImage resizedImage = resizeImage(srcImage, width, height);
            if (resizedImage != null) {
                ImageIO.write(resizedImage, "jpg", destFile);
            }
        } catch (IOException e) {
            log.error("Failed to resize image: {} -> {}", srcFile.getAbsolutePath(), destFile.getAbsolutePath(), e);
        }
    }

    /**
     * Resizes a BufferedImage to specified dimensions.
     *
     * <p>Creates a new BufferedImage with the target dimensions and draws the
     * source image scaled to fit using {@link Image#SCALE_SMOOTH} algorithm.</p>
     *
     * @param srcImage the source image to resize
     * @param width    the target width in pixels
     * @param height   the target height in pixels
     * @return a resized BufferedImage, or {@code null} if an error occurs
     */
    public static BufferedImage resizeImage(Image srcImage, int width, int height) {
        if (srcImage == null) {
            return null;
        }
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = buffImg.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
        g2d.dispose();
        return buffImg;
    }
}
