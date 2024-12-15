package by.aginskiy.application.controller;

import by.aginskiy.application.controller.path.UrlPath;
import by.aginskiy.application.exception.ServiceException;
import by.aginskiy.application.model.entity.User;
import by.aginskiy.application.model.service.impl.UserServiceImpl;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;

/**
 * Controller used for uploading files
 *
 * @author Dzmitry Ahinski
 */
@WebServlet(urlPatterns = { "*.upload" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class FileUploadingServlet extends HttpServlet {

    public static final String UPLOAD_DIR = "C:" + File.separator + "Users" + File.separator + "37529"
            + File.separator + "IdeaProjects" + File.separator + "WebApplication" + File.separator + "src"
            + File.separator + "main" + File.separator + "webapp" + File.separator + "img" + File.separator;
    private static final String PART_USER_PHOTO = "user_photo";
    private static final String DOT_SYMBOL = ".";
    private static final String PNG_TYPE = "png";


    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        try {
            Part part = request.getPart(PART_USER_PHOTO);
            String path = part.getSubmittedFileName();
            if (path != null && !path.isEmpty()) {
                String randomFilename = UUID.randomUUID() + path.substring(path.lastIndexOf(DOT_SYMBOL));
                try (InputStream inputStream = part.getInputStream()) {
                    byte[] file = zoomImage(inputStream);
                    if(file.length != 1) {
                        boolean isUploaded = uploadFile(file, UPLOAD_DIR + randomFilename);
                        if(isUploaded) {
                            UserServiceImpl.INSTANCE.updateUserPhoto(user.getId(), randomFilename);
                            user.setPhotoName(randomFilename);
                            session.setAttribute(SessionAttribute.USER, user);
                        }
                    }
                }
            }
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        response.sendRedirect(request.getContextPath() + UrlPath.USER_ARTICLE_LIST_DO);
    }

    /**
     * Uploads user file.
     * @param bytes as byte[] contains user file
     * @param path as String contains file path
     * @return
     */
    private boolean uploadFile(byte[] bytes, String path) {
        boolean result = false;
        try (FileOutputStream stream = new FileOutputStream(path)) {
            stream.write(bytes);
            result = true;
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return result;
    }

    /**
     * Centers and trims a custom photo
     *
     * @param inputStream as InputStream object
     * @return Byte[] with a binary file code, otherwise byte[] with containing 0
     */
    private byte[] zoomImage(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] result = new byte[1];
        try {
            BufferedImage image = ImageIO.read(inputStream);
            if(image != null) {
                int width = image.getWidth();
                int height = image.getHeight();
                int startX = 0;
                int startY = 0;
                double difference = 0;
                if(width > height) {
                    difference = (double)height / width;
                    width = height;
                    startX = width - (int)(width * difference);
                } else if(width < height) {
                    difference = (double)width / height;
                    height = width;
                    startY = height - (int)(height * difference);
                }
                image = image.getSubimage(startX, startY, width, height);
                ImageIO.write(image, PNG_TYPE, outputStream);
                result = outputStream.toByteArray();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return result;
    }
}
