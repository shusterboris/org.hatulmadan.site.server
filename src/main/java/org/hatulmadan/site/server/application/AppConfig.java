package org.hatulmadan.site.server.application;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.core.env.Environment;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@SpringBootConfiguration
public class AppConfig {
    public final static ZoneId tz = ZoneId.of("Asia/Jerusalem");
    public final static DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yy");
    public final static DateTimeFormatter dfy4 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
    public final static DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm");
    public final static DateTimeFormatter dtfISO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public final static DateTimeFormatter dfISO = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final String fileSeparator = FileSystems.getDefault().getSeparator();
    public final static String appPath = getAppFolder();
    public final static Gson gson = new Gson();
    public static final Logger log = LoggerFactory.getLogger(Starter.class);
    public static String getAppFolder() {
        URL fileURL = AppConfig.class.getClassLoader().getResource("");
        if (fileURL == null)
            return "";
        try {
            URI uri;
            if (fileURL.toString().startsWith("jar")) {
                String urlStr = fileURL.toString();
                int illegalCharPos = urlStr.indexOf("!");
                urlStr = urlStr.substring(4, illegalCharPos);
                uri = new URI(urlStr);
            } else
                uri = fileURL.toURI();
            Path path = Paths.get(uri);
            return path.getParent().toString().concat(fileSeparator);
        } catch (URISyntaxException e) {
            return "";
        }
    }

}
