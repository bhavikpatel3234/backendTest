package io.golo.backendtest.controller;

import io.golo.backendtest.Constants;
import io.golo.backendtest.entity.MonitorLog;
import io.golo.backendtest.model.RequestDTO;
import io.golo.backendtest.model.ResponseDTO;
import io.golo.backendtest.service.MonitorControlService;
import io.golo.backendtest.service.MonitorLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller exposing all API endpoints
 */
@RestController
public class BackendTestRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BackendTestRestController.class);

    @Autowired
    MonitorControlService monitorControlService;

    @Autowired
    MonitorLogService monitorLogService;

    /**
     * Returns API resource
     *
     * @return The response containing the logs with timestamp.
     */
    @GetMapping(value = "/overview")
    public ResponseEntity<ResponseDTO> getOverview() {
        List<MonitorLog> logs = monitorLogService.findAll();

        if (!logs.isEmpty())
            return ResponseEntity.ok().body(new ResponseDTO(HttpStatus.OK.value() , logs));
        else
            return ResponseEntity.badRequest().body(new ResponseDTO(HttpStatus.NO_CONTENT.value() , null));
    }

    /**
     * Returns API resource
     *
     * @param requestData
     *
     * @return The response is message about monitor status start
     */
    @PostMapping(value = "/monitor/start", consumes = "application/json")
    public ResponseEntity startMonitorServer(@RequestBody RequestDTO requestData) {
        monitorLogService.removeAll();
        monitorControlService.startMonitor(requestData.getTimeInterval(), requestData.getUrl());
        return ResponseEntity.ok().body(Constants.MONITOR_START);
    }

    /**
     * Returns API resource
     *
     * @return The response is message about monitor status stop
     */
    @GetMapping(value = "/monitor/stop")
    public ResponseEntity stopMonitorServer() {
        monitorControlService.stopMonitor();
        return ResponseEntity.ok().body(Constants.MONITOR_STOP);
    }
}
