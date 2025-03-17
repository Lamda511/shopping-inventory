package com.lambda511.util.file;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by blitzer on 05.05.2016.
 */

@Component
public class ImageProcessor {

    public static final String SCALE_QUALITY_FAST = "fast";
    public static final String SCALE_QUALITY_MEDIUM = "medium";
    public static final String SCALE_QUALITY_HIGH = "high";
    public static final String SCALE_QUALITY_VERY_HIGH = "veryhigh";

    public static final String IMAGE_FILE_FORMAT = "jpeg";

    public BufferedImage getBufferedImageFromFile(File file) {
        try {
            return ImageIO.read(file);
        } catch (IOException ex) {
            throw new FileIOError(String.format("Can not read image file"), ex);
        }
    }

    public InputStream bufferedImageToInputStream(BufferedImage bufferedImage) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            writeBufferedImageToOutputStream(bufferedImage, byteArrayOutputStream);
            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } finally {
            IOUtils.closeQuietly(byteArrayOutputStream);
        }
    }

    public void writeBufferedImageToOutputStream(BufferedImage bufferedImage, OutputStream outputStream) {
        try {
            ImageIO.write(bufferedImage, IMAGE_FILE_FORMAT, outputStream);
        } catch (IOException ex) {
            throw new FileIOError(String.format("Can not get stream for processed file"), ex);
        }
    }

    public BufferedImage scaleImageVeryHighQuality(BufferedImage bufferedImage, int targetWidth, int targetHeight) {
        return getScaledInstanceWithAspectRatio(bufferedImage, targetWidth, targetHeight, RenderingHints.VALUE_INTERPOLATION_BICUBIC, true);
    }

    public BufferedImage scaleImageHighQuality(BufferedImage bufferedImage, int targetWidth, int targetHeight) {
        return getScaledInstanceWithAspectRatio(bufferedImage, targetWidth, targetHeight, RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);
    }

    public BufferedImage scaleImageMediumQuality(BufferedImage bufferedImage, int targetWidth, int targetHeight) {
        return getScaledInstanceWithAspectRatio(bufferedImage, targetWidth, targetHeight, RenderingHints.VALUE_INTERPOLATION_BILINEAR, false);
    }

    public BufferedImage scaleImageFastQuality(BufferedImage bufferedImage, int targetWidth, int targetHeight) {
        return getScaledInstanceWithAspectRatio(bufferedImage, targetWidth, targetHeight, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR, false);
    }

    /**
     * Convenience method that returns a scaled instance of the provided {@code BufferedImage}.
     * @param img the original image to be scaled
     * @param targetWidth the desired width of the scaled instance in pixels
     * @param targetHeight the desired height of the scaled instance, in pixels
     * @param hint one of the rendering hints that corresponds to {@code RenderingHints.KEY_INTERPOLATION}
     *             (e.g. {@code RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR},
     *             {@code RenderingHints.VALUE_INTERPOLATION_BILINEAR},
     *             {@code RenderingHints.VALUE_INTERPOLATION_BICUBIC})
     * @param higherQuality if true, this method will use a multi-step scaling technique that provides higher quality
     *                      than the usual one-step technique (only useful in downscaling cases, where {@code targetWidth}
     *                      or {@code targetHeight} is smaller than the original dimensions, and generally only when
     *                      the {@code BILINEAR} hint is specified)
     @return a scaled version of the original {@code BufferedImage}
     */
    private BufferedImage getScaledInstanceWithAspectRatio(BufferedImage img, int targetWidth, int targetHeight, Object hint, boolean higherQuality) {
        int requiredWidth = targetWidth;
        int requiredHeight = targetHeight;
        double targetRatio = ((double)targetWidth) / ((double)targetHeight);
        double sourceRatio = ((double)img.getWidth()) / ((double)img.getHeight());
        if(sourceRatio >= targetRatio){
            // source is wider than target in proportion
            requiredHeight = (int) (requiredWidth / sourceRatio);
        } else {
            // source is higher than target in proportion
            requiredWidth = (int) (requiredHeight * sourceRatio);
        }

        int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = (BufferedImage) img;
        int w, h;
        if (higherQuality) {
            // Use multi-step technique: start with original size, then
            // scale down in multiple passes with drawImage()
            // until the target size is reached
            w = img.getWidth();
            h = img.getHeight();
        } else {
            // Use one-step technique: scale directly from original
            // size to target size with a single drawImage() call
            w = requiredWidth;
            h = requiredHeight;
        }
        do {
            if (higherQuality && w > requiredWidth) {
                w /= 2;
                if (w < requiredWidth) {
                    w = requiredWidth;
                }
            }
            if (higherQuality && h > requiredHeight) {
                h /= 2;
                if (h < requiredHeight) {
                    h = requiredHeight;
                }
            }
            BufferedImage tmp = new BufferedImage(w, h, type);
            Graphics2D g2 = tmp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(ret, 0, 0, w, h, null);
            g2.dispose();
            ret = tmp;
        } while (w != requiredWidth || h != requiredHeight);

        return ret;
    }
}
