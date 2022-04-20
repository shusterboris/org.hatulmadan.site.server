package org.hatulmadan.site.server.application.services;

import org.hatulmadan.site.server.application.AppConfig;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    public void logError(Exception e){
        AppConfig.log.error(e.getMessage());
        e.printStackTrace();
    }
}
