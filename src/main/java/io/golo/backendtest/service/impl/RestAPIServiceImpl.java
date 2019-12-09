package io.golo.backendtest.service.impl;

import io.golo.backendtest.entity.MonitorLog;
import io.golo.backendtest.service.MonitorLogService;
import io.golo.backendtest.service.RestAPIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestAPIServiceImpl implements RestAPIService{

    private static final Logger LOGGER = LoggerFactory.getLogger(RestAPIService.class);
    private static final int HTTP_SUCCESS = 200;
    private static final int HTTP_INTERNAL_ERROR = 500;
    private RestTemplate restTemplate;

    @Autowired
    MonitorLogService monitorLogService;

    @Autowired
    public RestAPIServiceImpl(RestTemplateBuilder restTemplateBuilder, HTTPErrorCodeServiceImpl httpErrorCodeService) {
        restTemplate =  restTemplateBuilder.errorHandler(httpErrorCodeService).build();
    }

    public void monitorTheServer(String url) {
        try {
            String str = (String) restTemplate.getForObject(url, String.class);
            if (str.contains("READY")) {
                monitorLogService.save(new MonitorLog(HTTP_SUCCESS, url));
            }
        } catch (Exception exception) {
            monitorLogService.save(new MonitorLog(HTTP_INTERNAL_ERROR, url));
        }
    }
}
