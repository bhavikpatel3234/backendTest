package io.golo.backendtest.service;

import io.golo.backendtest.entity.MonitorLog;
import java.util.List;

public interface MonitorLogService {

    List<MonitorLog> findAll();
    void removeAll();
    MonitorLog save(MonitorLog monitorLog);
}
