package io.golo.backendtest.service;

public interface MonitorControlService {

    void startMonitor (final long interval, final String url);
    void stopMonitor ();
}
