package com.sydowma.dataprocess.framework.engine;


public class EventWrapper {
    private long offerTimeNs;
    private String traceId;
    private Event event;

    public void setEvent(Event event) {
        this.event = event;
        this.offerTimeNs = System.nanoTime();
    }
}
