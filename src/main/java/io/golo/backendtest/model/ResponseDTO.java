package io.golo.backendtest.model;

import io.golo.backendtest.entity.MonitorLog;

import java.util.List;

public class ResponseDTO {

    int statusCode;
    List<MonitorLog> logs;

    public ResponseDTO() {
    }

    public ResponseDTO(int statusCode, List<MonitorLog> logs) {
        this.statusCode = statusCode;
        this.logs = logs;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<MonitorLog> getLogs() {
        return logs;
    }

    public void setLogs(List<MonitorLog> logs) {
        this.logs = logs;
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "statusCode=" + statusCode +
                ", logs=" + logs +
                '}';
    }
}
