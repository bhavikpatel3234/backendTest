package io.golo.backendtest.service.impl;

import io.golo.backendtest.entity.MonitorLog;
import io.golo.backendtest.repository.MonitorLogRepository;
import io.golo.backendtest.service.MonitorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitorLogServiceImpl implements MonitorLogService {

    @Autowired
    MonitorLogRepository monitorLogRepository;

    public List<MonitorLog> findAll() {
        return monitorLogRepository.findAll();
    }

    @Override
    public void removeAll() {
        monitorLogRepository.deleteAll();
    }

    @Override
    public MonitorLog save(MonitorLog monitorLog) {
        return monitorLogRepository.save(monitorLog);
    }
}
