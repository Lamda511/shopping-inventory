package com.lambda511.util.file;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;

/**
 * Created by blitzer on 05.05.2016.
 */

@Component
public class FileUtil {

    public File getFile(File storageDirectory, String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            throw new FileNotFoundError("The searched file could not be found: [" + fileName + "]");
        }

        File file = new File(storageDirectory, fileName);
        if (!file.exists() || file.isDirectory()) {
            throw new FileNotFoundError("The searched file could not be found or the given name does not denote a file: [" + fileName + "]");
        }

        return file;
    }

    public InputStream getFileInputStream(File storageDirectory, String fileName) {
        try {
            return new FileInputStream(getFile(storageDirectory, fileName));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundError("The searched file could not be found: [" + fileName + "]");
        }

    }

    public void writeFileToOutputStream(File storageDirectory, String fileName, OutputStream outputStream) {
        try (InputStream inputStream = getFileInputStream(storageDirectory, fileName)) {
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException ex) {
            throw new FileIOError("There was an error while processing the file!", ex);
        }
    }

    public String storeToFile(InputStream inputStream, File storageDirectory) throws IOException {
        OutputStream outputStream = null;
        try {
            File resultingImageFile = Files.createTempFile(FileSystems.getDefault()
                    .getPath(storageDirectory.getCanonicalPath()), "img-", ".imgfile").toFile();

            outputStream = new BufferedOutputStream(new FileOutputStream(resultingImageFile));
            IOUtils.copy(inputStream, outputStream);

            return resultingImageFile.getName();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    //TODO: log error.
                }
            }
        }
    }

    public void deleteFile(File storageDirectory, String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            throw new FileNotFoundError("The searched file could not be found: [" + fileName + "]");
        }

        try {
            File fileToDelete = new File(storageDirectory, fileName);
            if (fileToDelete.isDirectory()) {
                throw new FileNotFoundError("The searched file could not be found or the given name does not denote a file: [" + fileName + "]");
            }

            Files.deleteIfExists(FileSystems.getDefault().getPath(fileToDelete.getCanonicalPath()));
        } catch (IOException e) {
            throw new FileIOError("Could not delete the old file", e);
        }
    }
}
