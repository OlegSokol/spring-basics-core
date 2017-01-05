package com.yet.spring.core.logger;

import com.yet.spring.core.bean.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Service
public class CacheFileEventLogger extends FileEventLogger {

    @Value("1")
    private int cacheSize;

    private List<Event> cache;

    public CacheFileEventLogger() {
        cache = new ArrayList<>();
    }

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

    @PreDestroy
    public void destroy() {
        if(!cache.isEmpty())
            writeEventsFromCache();
    }
}
