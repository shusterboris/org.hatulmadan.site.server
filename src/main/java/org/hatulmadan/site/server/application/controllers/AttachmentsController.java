package org.hatulmadan.site.server.application.controllers;

import org.hatulmadan.site.server.application.AppConfig;
import org.hatulmadan.site.server.application.data.proxies.UploadedFileProxy;
import org.hatulmadan.site.server.application.services.AttachmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class AttachmentsController {
    @Autowired
    AttachmentsService attService;

    @PostMapping(value = "/file/save")
    public ResponseEntity<String> uploadAttachment(@RequestBody UploadedFileProxy proxy) {
        String result = attService.writeFile(proxy);
        if (!result.startsWith("Ошибка")) {
            return new ResponseEntity<String>(result, HttpStatus.OK);
        } else
            return new ResponseEntity<String>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
