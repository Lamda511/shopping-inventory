package com.lambda511.model.services.query;

import com.lambda511.util.file.ImageProcessor;
import com.lambda511.util.file.ImageResourcesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by blitzer on 05.05.2016.
 */

@Component
public class QueryProductImageServiceImpl {

    @Autowired
    private ImageResourcesUtil imageResourcesUtil;

    @Autowired
    private ImageProcessor imageProcessor;

    public InputStream getProductImageFull(String imageName) {
        return imageResourcesUtil.getImageAsStream(imageName);
    }

    public void writeProductImageFullToOutputStream(String imageName, OutputStream outputStream) {
        imageResourcesUtil.writeFileToOutputStream(imageName, outputStream);
    }

    public InputStream getProductImageScaled(String imageName, int targetWidth, int targetHeight, String scaleQuality) {
        BufferedImage scaledBufferedImage = getScaledImage(imageName, targetWidth, targetHeight, scaleQuality);
        return imageProcessor.bufferedImageToInputStream(scaledBufferedImage);
    }

    public void writeProductImageScaledToOutputStream(String imageName, int targetWidth, int targetHeight, String scaleQuality, OutputStream outputStream) {
        BufferedImage scaledBufferedImage = getScaledImage(imageName, targetWidth, targetHeight, scaleQuality);
        imageProcessor.writeBufferedImageToOutputStream(scaledBufferedImage, outputStream);
    }

    private BufferedImage getScaledImage(String imageName, int targetWidth, int targetHeight, String scaleQuality) {
        File imageFile = imageResourcesUtil.getImageFile(imageName);
        BufferedImage bufferedImage = imageProcessor.getBufferedImageFromFile(imageFile);
        return getScaledImage(bufferedImage, targetWidth, targetHeight, scaleQuality);
    }

    //TODO: maybe find a better way to avoid this switch. Maybe use a map of Java 8 functions or
    private BufferedImage getScaledImage(BufferedImage bufferedImage, int targetWidth, int targetHeight, String scaleQuality) {
        switch (scaleQuality) {
            case ImageProcessor.SCALE_QUALITY_FAST:
                return imageProcessor.scaleImageFastQuality(bufferedImage, targetWidth, targetHeight);
            case ImageProcessor.SCALE_QUALITY_MEDIUM:
                return imageProcessor.scaleImageMediumQuality(bufferedImage, targetWidth, targetHeight);
            case ImageProcessor.SCALE_QUALITY_HIGH:
                return imageProcessor.scaleImageHighQuality(bufferedImage, targetWidth, targetHeight);
            case ImageProcessor.SCALE_QUALITY_VERY_HIGH:
                return imageProcessor.scaleImageVeryHighQuality(bufferedImage, targetWidth, targetHeight);
            default:
                return imageProcessor.scaleImageFastQuality(bufferedImage, targetWidth, targetHeight);
        }
    }

}
