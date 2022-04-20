package org.hatulmadan.site.server.application.services;

import org.hatulmadan.site.server.application.AppConfig;
import org.hatulmadan.site.server.application.data.proxies.UploadedFileProxy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

@Service
public class AttachmentsService {
    public long maxAttSize = 1024 * 1024 * 5;
    String attStoragePath = AppConfig.getAppFolder().concat("attstorage").concat(AppConfig.fileSeparator);


    public String writeFile(UploadedFileProxy fileData) {
        String fileName = "";
        String fullName = "";
        if (fileData.getBlob().length() > maxAttSize) {
            return "Ошибка: слишком большой файл, размер не должен быть более " + String.valueOf(Math.ceil(maxAttSize/1024/1024)) +  " Мб";
        }
        String[] encodedFile = fileData.getBlob().split(",");
        byte[] data = Base64.getMimeDecoder().decode(encodedFile[1]);
        try {
            String[] fileNameParts = fileData.getFileName().split("\\.");
            String fileExt = fileNameParts.length>1 ? fileNameParts[fileNameParts.length-1] : "";
            fileName = UUID.randomUUID().toString().concat(".").concat(fileExt);
            AppConfig.log.error(attStoragePath);
            fullName = attStoragePath.concat(fileName);
            if (encodedFile[0].contains("image")) {
                File imgFile = new File(fullName);
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                ImageIO.write(img, fileExt, imgFile);
            } else {
                FileOutputStream fos = new FileOutputStream(fullName);
                fos.write(data);
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            AppConfig.log.error(e.getMessage());
            System.out.println("Не могу записать файл " + fullName);
            return "Ошибка: невозможно сохранить отправленный файл";
        }
    }

    public byte[] readImage(String fileName) throws Exception {
        String fullPath = attStoragePath.concat(fileName);
        return Files.readAllBytes(Paths.get(fullPath).toAbsolutePath());
    }



    private void createAttStorage() {
        try {
            File directory = new File(attStoragePath);
            if (!directory.exists()) {
                directory.mkdir();
            }
        } catch (Exception e) {
            AppConfig.log.error(e.getMessage());
            e.printStackTrace();
        }
    }

}
