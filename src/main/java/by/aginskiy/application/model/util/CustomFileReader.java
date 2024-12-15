package by.aginskiy.application.model.util;

import by.aginskiy.application.controller.FileUploadingServlet;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Class to work with file reading.
 *
 * @author Dzmitry Ahinski
 */
public class CustomFileReader {
    private static final Logger logger = LogManager.getLogger();

    /**
     * Reads the file.
     *
     * @return Optional object with binary code of file, if file not exists - empty optional object.
     */
    public static Optional<byte[]> readFile(String fileName) {
        Optional<byte[]> result = Optional.empty();
        byte[] file;
        String fileUri = FileUploadingServlet.UPLOAD_DIR + fileName;
        Path filePath = Paths.get(fileUri);
        if (Files.exists(filePath)) {
            try {
                file = Files.readAllBytes(filePath);
                result = Optional.of(file);
            } catch (IOException exception) {
                logger.log(Level.ERROR, "Cannot read file", exception);
            }
        } else {
            logger.log(Level.ERROR, "File does not exists");
        }
        return result;
    }
}
