package com.sydowma.dataprocess.app.database;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.nio.ByteBuffer;

public class DatabaseEventMain {


//    public static void main(String[] args) throws InterruptedException {
//
//        int bufferSize = 1024;
//        Disruptor<DatabaseEvent> disruptor = new Disruptor<>(
//                DatabaseEvent::new,
//                bufferSize,
//                DaemonThreadFactory.INSTANCE
//                );
//        DatabaseEventHandler databaseEventHandler = new DatabaseEventHandler();
//        disruptor.handleEventsWith(databaseEventHandler);
////        disruptor.handleEventsWith(((event, sequence, endOfBatch) -> {
////            System.out.println("Event: " + event);
////        }));
//        disruptor.start();
//
//        RingBuffer<DatabaseEvent> ringBuffer = disruptor.getRingBuffer();
//        ByteBuffer bb = ByteBuffer.allocate(8);
//        for (long l = 0; true; l++)
//        {
//            bb.putLong(0, l);
//            ringBuffer.publishEvent((event, sequence, buffer) -> event.set(buffer.getLong(0)), bb);
//            Thread.sleep(1000);
//        }
//    }

    public static void handleEvent(DatabaseEvent event, long sequence, boolean endOfBatch)
    {
        System.out.println(event);
    }

    public static void translate(DatabaseEvent event, long sequence, ByteBuffer buffer)
    {
        event.set(buffer.getLong(0));
    }

    public static void main(String[] args) throws Exception
    {
        int bufferSize = 1024;

        Disruptor<DatabaseEvent> disruptor =
                new Disruptor<>(DatabaseEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);
        disruptor.handleEventsWith(DatabaseEventMain::handleEvent);
        disruptor.start();

        RingBuffer<DatabaseEvent> ringBuffer = disruptor.getRingBuffer();
        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++)
        {
            bb.putLong(0, l);
            ringBuffer.publishEvent(DatabaseEventMain::translate, bb);
            Thread.sleep(1000);
        }
    }
}
