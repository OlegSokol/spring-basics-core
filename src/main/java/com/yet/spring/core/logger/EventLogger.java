package com.yet.spring.core.logger;

import com.yet.spring.core.bean.Event;

public interface EventLogger {
    void logEvent(Event event);
}
