package com.sydowma.dataprocess.framework.engine;

import com.lmax.disruptor.EventFactory;

public class DisruptorFactory implements EventFactory<EventWrapper> {
    @Override
    public EventWrapper newInstance() {
        return null;
    }
}
