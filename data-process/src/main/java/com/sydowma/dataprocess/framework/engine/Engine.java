package com.sydowma.dataprocess.framework.engine;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

public class Engine {

    private final Disruptor<EventWrapper> disruptor;
    private final RingBuffer<EventWrapper> ringBuffer;
    private static final int bufferSize = 2 >> 16;

    public Engine(RingBuffer<EventWrapper> ringBuffer) {
        this.ringBuffer = ringBuffer;
        DisruptorFactory disruptorFactory = new DisruptorFactory();
        Disruptor<EventWrapper> disruptor =
                new Disruptor<>(disruptorFactory, bufferSize, new NamedThreadFactory());
        this.disruptor = disruptor;
        disruptor.start();
    }


}
