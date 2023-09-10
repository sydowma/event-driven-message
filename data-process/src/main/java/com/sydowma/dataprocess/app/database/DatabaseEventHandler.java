package com.sydowma.dataprocess.app.database;

import com.lmax.disruptor.EventHandler;

public class DatabaseEventHandler implements EventHandler<DatabaseEvent> {
    @Override
    public void onEvent(DatabaseEvent databaseEvent, long l, boolean b) throws Exception {

        System.out.println("DatabaseEventHandler Event: " + databaseEvent);
    }
}
