package com.yet.spring.core;

import com.yet.spring.core.bean.Client;
import com.yet.spring.core.bean.Event;
import com.yet.spring.core.bean.EventType;
import com.yet.spring.core.logger.EventLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class App {
    @Autowired
    private Client client;

    @Autowired
    @Qualifier("consoleEventLogger")
    private EventLogger eventLogger;

    @Autowired
    @Qualifier("loggerMap")
    private Map<EventType, EventLogger> loggers;

    public static void main(String[] args) {
        @SuppressWarnings("resource") // We will remove this suppress in further lessons
        /*ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        App app = (App) ctx.getBean("app");*/
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.scan("com.yet.spring.core");
        ctx.refresh();

        App app = (App)ctx.getBean("app");

        Event eventInfo = ctx.getBean(Event.class);
        eventInfo.setMsg("info msg");
        Event eventError = ctx.getBean(Event.class);
        eventError.setMsg("error msg");

        app.logEvent(EventType.INFO, eventInfo);
        app.logEvent(EventType.ERROR, eventError);

    }

    public App() {
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
