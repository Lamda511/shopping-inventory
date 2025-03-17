package com.lambda511.util.file;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by blitzer on 07.05.2016.
 */

@Component
public class ImageResourcesUtil {

    private static final String IMAGES_LOCATION_PROPERTY_NAME = "images.location";

    private static final String DEFAULT_IMAGES_LOCATION = ".";

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private Environment env;

    @Value("${images.location}")
    private String imagesLocation;

    private File imagesDirectory;

    @PostConstruct
    public void setUpImagesDirectory() {
        String imagesLocationPath = StringUtils.isNotEmpty(imagesLocation) ? imagesLocation : DEFAULT_IMAGES_LOCATION;
        imagesDirectory = new File(imagesLocationPath);
        if (!imagesDirectory.exists()) {
            imagesDirectory.mkdirs();
        }
    }

    public File getImageFile(String imageFileName) {
        return fileUtil.getFile(getImagesDirectory(), imageFileName);

    }

    public InputStream getImageAsStream(String imageFileName) {
        return fileUtil.getFileInputStream(getImagesDirectory(), imageFileName);
    }

    public void writeFileToOutputStream(String imageFileName, OutputStream outputStream) {
        fileUtil.writeFileToOutputStream(getImagesDirectory(), imageFileName, outputStream);
    }

    public String storeImageFromInputStream(InputStream inputStream) throws IOException {
        return fileUtil.storeToFile(inputStream, getImagesDirectory());
    }

    public void deleteImage(String imageFileName) {
        fileUtil.deleteFile(getImagesDirectory(), imageFileName);
    }

    private File getImagesDirectory() {
        return imagesDirectory;

    }

}
