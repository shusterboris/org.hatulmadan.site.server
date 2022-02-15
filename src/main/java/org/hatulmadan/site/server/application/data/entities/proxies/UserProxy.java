package org.hatulmadan.site.server.application.data.entities.proxies;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserProxy {
    private String userName;
    private String employeeName;
    private Long employeeId;
    private String jobTitle;
    private Long orgUnitId;
    private String orgUnit;
    private List<String> authorities = new ArrayList<>();
    private LocalTime coming;
    private LocalTime leaving;
    private String password;

    public UserProxy(String userName, String employeeName) {
        this.userName = userName;
        this.employeeName = employeeName;
    }

    public UserProxy(String userName, String employeeName, Long employeeId, String jobTitle, Long orgUnitId) {
        this.userName = userName;
        this.employeeName = employeeName;
        this.jobTitle = jobTitle;
        this.orgUnitId = orgUnitId;
        this.employeeId = employeeId;
    }
}
