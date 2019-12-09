package io.golo.backendtest.model;

public class RequestDTO {
    private long timeInterval;
    private String url;

    public RequestDTO(long timeInterval, String url) {
        this.timeInterval = timeInterval;
        this.url = url;
    }

    public long getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(long timeInterval) {
        this.timeInterval = timeInterval;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "RequestData{" +
                "timeInterval=" + timeInterval +
                ", url='" + url + '\'' +
                '}';
    }
}
