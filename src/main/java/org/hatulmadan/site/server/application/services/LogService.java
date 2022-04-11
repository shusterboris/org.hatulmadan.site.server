package org.hatulmadan.site.server.application.services;

import org.springframework.stereotype.Service;

@Service
public class LogService {
    public void logError(Exception e){
        e.printStackTrace();
    }
}
