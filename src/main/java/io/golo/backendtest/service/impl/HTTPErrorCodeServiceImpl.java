package io.golo.backendtest.service.impl;

import io.golo.backendtest.entity.MonitorLog;
import io.golo.backendtest.service.MonitorLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Service
public class HTTPErrorCodeServiceImpl implements ResponseErrorHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(HTTPErrorCodeServiceImpl.class);

    @Autowired
    public MonitorLogService monitorLogService;

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        LOGGER.error("Status code " + clientHttpResponse.getStatusCode());
        return (clientHttpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
                        || clientHttpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        monitorLogService.save(new MonitorLog(clientHttpResponse.getStatusCode().value(), clientHttpResponse.getStatusText()));
    }
}
