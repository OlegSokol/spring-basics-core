package com.yet.spring.core;

import com.yet.spring.core.bean.Client;
import com.yet.spring.core.bean.Event;
import com.yet.spring.core.bean.EventType;
import com.yet.spring.core.logger.EventLogger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class App {
    private Client client;
    private EventLogger eventLogger;
    private Map<EventType, EventLogger> loggers;

    public static void main(String[] args) {
        @SuppressWarnings("resource") // We will remove this suppress in further lessons
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        App app = (App) ctx.getBean("app");

        Event eventInfo = ctx.getBean(Event.class);
        eventInfo.setMsg("info msg");
        Event eventError = ctx.getBean(Event.class);
        eventError.setMsg("error msg");

        app.logEvent(EventType.INFO, eventInfo);
        app.logEvent(EventType.ERROR, eventError);

    }

    public App(Client client, EventLogger eventLogger, Map<EventType, EventLogger> loggers) {
        super();
        this.client = client;
        this.eventLogger = eventLogger;
        this.loggers = loggers;
    }

    private void logEvent(EventType eventType, Event event) {
        EventLogger logger = this.loggers.get(eventType);
        if(logger == null) {
            logger = eventLogger;
        }

        logger.logEvent(event);
    }
}
