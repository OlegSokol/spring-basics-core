package com.yet.spring.core.logger;

import com.yet.spring.core.bean.Event;

import java.util.ArrayList;
import java.util.List;

public class CacheFileEventLogger extends FileEventLogger {
    private int cacheSize;
    private List<Event> cache;

    public CacheFileEventLogger(String fileName, int cacheSize) {
        super(fileName);
        this.cacheSize = cacheSize;
        cache = new ArrayList<>();
    }

    public void logEvent(Event event) {
        cache.add(event);
        if(cache.size() == cacheSize) {
            writeEventsFromCache();
            cache.clear();
        }
    }

    public void writeEventsFromCache() {
        for (Event event : cache) {
            super.logEvent(event);
        }
    }

    public void destroy() {
        if(!cache.isEmpty())
            writeEventsFromCache();
    }
}
