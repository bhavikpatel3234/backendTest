package io.golo.backendtest.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class MonitorLog {

    @Id
    @GeneratedValue
    private Long id;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    private Integer httpStatus;
    private String message;

    public MonitorLog() { }

    public MonitorLog(Integer httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
