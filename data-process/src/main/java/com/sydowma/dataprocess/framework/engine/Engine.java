package com.sydowma.dataprocess.framework.engine;

import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.util.concurrent.ThreadFactory;

public class Engine {

    private final Disruptor<Event> disruptor;
    private static final int bufferSize = 2 >> 16;

    public Engine() {
        DisruptorFactory disruptorFactory = new DisruptorFactory();
        Disruptor<Event> disruptor =
                new Disruptor<>(disruptorFactory, bufferSize, new NamedThreadFactory());
        this.disruptor = disruptor;
        disruptor.start();
    }


}
