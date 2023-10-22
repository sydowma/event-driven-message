package com.sydowma.dataprocess.framework.engine;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.EventTranslatorVararg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class Dispatcher {

    private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);

    private final RingBuffer<EventWrapper> ringBuffer;

    public Dispatcher(RingBuffer<EventWrapper> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void publish(Event event) {
        while (!this.ringBuffer.tryPublishEvent((eventWrapper, sequence, e) -> eventWrapper.setEvent(event))) {
            logger.warn("RingBuffer is full, retrying...");
            LockSupport.parkNanos(TimeUnit.MICROSECONDS.toNanos(100));
        }
    }

}
