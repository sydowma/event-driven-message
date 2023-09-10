package com.sydowma.dataprocess.app.database;

import com.lmax.disruptor.EventFactory;

public class DatabaseEventFactory implements EventFactory<DatabaseEvent> {
    @Override
    public DatabaseEvent newInstance() {
        return new DatabaseEvent();
    }
}
