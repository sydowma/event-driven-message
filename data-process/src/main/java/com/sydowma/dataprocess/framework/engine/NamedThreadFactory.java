package com.sydowma.dataprocess.framework.engine;

import java.util.concurrent.ThreadFactory;

public class NamedThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, "engine");
    }
}
