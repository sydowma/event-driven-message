package com.sydowma.dataprocess.framework.engine;

import com.lmax.disruptor.EventFactory;

public class DisruptorFactory implements EventFactory<Event> {
    @Override
    public Event newInstance() {
        return null;
    }
}
