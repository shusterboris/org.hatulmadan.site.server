package org.hatulmadan.site.server.application.data.proxies;

import lombok.Getter;
import lombok.Setter;

public class UploadedFileProxy {
    private String fileName;
    private String blob;

    public String getBlob() {return blob; }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setBlob(String blob) {
        this.blob = blob;
    }
}
