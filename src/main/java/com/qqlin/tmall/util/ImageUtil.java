package com.qqlin.tmall.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

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

    /**
     * Converts an image file to JPEG format.
     *
     * <p>Handles various input formats (PNG, GIF, BMP, etc.) by reading pixel data
     * and writing it as a standard RGB BufferedImage in JPEG format.</p>
     *
     * <p>Implementation uses PixelGrabber to extract pixel data from the source image
     * and creates a new BufferedImage with DirectColorModel for RGB output.</p>
     *
     * @param f the source image file to convert
     * @return a BufferedImage in JPEG-compatible format, or {@code null} if conversion fails
     */
    public static BufferedImage change2jpg(File f) {
        try {
            Image i = Toolkit.getDefaultToolkit().createImage(f.getAbsolutePath());
            PixelGrabber pg = new PixelGrabber(i, 0, 0, -1, -1, true);
            pg.grabPixels();
            int width = pg.getWidth(), height = pg.getHeight();
            final int[] RGB_MASKS = {0xFF0000, 0xFF00, 0xFF};
            final ColorModel RGB_OPAQUE = new DirectColorModel(32, RGB_MASKS[0], RGB_MASKS[1], RGB_MASKS[2]);
            DataBuffer buffer = new DataBufferInt((int[]) pg.getPixels(), pg.getWidth() * pg.getHeight());
            WritableRaster raster = Raster.createPackedRaster(buffer, width, height, width, RGB_MASKS, null);
            BufferedImage img = new BufferedImage(RGB_OPAQUE, raster, false, null);
            return img;
        } catch (InterruptedException e) {
            e.printStackTrace();
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
            if (!destFile.getParentFile().exists())
                destFile.getParentFile().mkdirs();
            Image i = ImageIO.read(srcFile);
            i = resizeImage(i, width, height);
            ImageIO.write((RenderedImage) i, "jpg", destFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Resizes an Image object to specified dimensions.
     *
     * <p>Creates a new BufferedImage with the target dimensions and draws the
     * source image scaled to fit using {@link Image#SCALE_SMOOTH} algorithm.</p>
     *
     * <p>Note: The returned Image is actually a BufferedImage and can be used
     * with ImageIO.write() for saving to disk.</p>
     *
     * @param srcImage the source image to resize
     * @param width    the target width in pixels
     * @param height   the target height in pixels
     * @return a resized Image, or {@code null} if an error occurs
     */
    public static Image resizeImage(Image srcImage, int width, int height) {
        try {

            BufferedImage buffImg = null;
            buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            buffImg.getGraphics().drawImage(srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);

            return buffImg;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
