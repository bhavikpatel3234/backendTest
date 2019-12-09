package io.golo.backendtest.service.impl;

import io.golo.backendtest.service.MonitorControlService;
import io.golo.backendtest.service.MonitorLogService;
import io.golo.backendtest.service.RestAPIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.time.Instant;
import java.util.Date;
import java.util.concurrent.ScheduledFuture;

@Configuration
@EnableScheduling
public class MonitorControlServiceImpl implements MonitorControlService, SchedulingConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonitorControlServiceImpl.class);
    ThreadPoolTaskScheduler threadPoolTaskScheduler;
    TaskScheduler taskScheduler;
    private ScheduledFuture<?> task;

    @Autowired
    RestAPIService restAPIService;

    @Autowired
    MonitorLogService monitorLogService;

    @Override
    public void startMonitor(final long interval, final String url) {
        threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(3);
        threadPoolTaskScheduler.initialize();
        scheduledFuture(threadPoolTaskScheduler, interval, url);
        this.taskScheduler = threadPoolTaskScheduler;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        if (threadPoolTaskScheduler != null) {
            scheduledTaskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
        }
    }

    private void scheduledFuture(TaskScheduler taskScheduler, final long interval, final String url) {
        try {
            task = taskScheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    LOGGER.info("Run at : " + Instant.now());
                    restAPIService.monitorTheServer(url);
                }
            }, new Trigger() {
                @Override
                public Date nextExecutionTime(TriggerContext triggerContext) {
                    String cronExp = "0/" + interval + " * * * * ?";
                    return new CronTrigger(cronExp).nextExecutionTime(triggerContext);
                }
            });
        } catch (Exception e) {
            LOGGER.error("Failed to start schedular. Possible cause - " + e.getMessage());
        }
    }

    @Override
    public void stopMonitor() {
        if (task != null) {
            task.cancel(true);
        }
    }
}
