package com.yet.spring.core.logger;

import com.yet.spring.core.bean.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service("combinedEventLogger")
public class CombinedEventLogger implements EventLogger {

    @Autowired
    @Qualifier("loggersForCombinedLogger")
    private Collection<EventLogger> loggers;

    public CombinedEventLogger() {
    }

    public CombinedEventLogger(Collection<EventLogger> loggers) {
        this.loggers = loggers;
    }

    @Override
    public void logEvent(Event event) {
        for (EventLogger eventLogger : loggers) {
            eventLogger.logEvent(event);
        }
    }
}
