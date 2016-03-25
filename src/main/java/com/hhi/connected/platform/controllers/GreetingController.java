package com.hhi.connected.platform.controllers;

import com.hhi.connected.platform.models.HelloMessage;
import com.hhi.connected.platform.services.MyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingController.class);

    @Autowired
    MyService myService;

////    @Autowired
//    PushService pushService;

    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private SimpMessagingTemplate template;

    private TaskScheduler scheduler = new ConcurrentTaskScheduler();

    @RequestMapping(value = "/greeting", method = RequestMethod.GET )
    public Object greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return myService.sayHello(name);
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Object hello(HelloMessage message) throws Exception {
        return myService.sayHello("WS, " + message.getName() + "!");
    }

    @PostConstruct
    private void broadcastTimePeriodically() {
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override public void run() {
                tictocBroadcast();
            }
        }, 60000L);
    }

    private void tictocBroadcast() {
        template.convertAndSend("/topic/greetings", myService.tick());
    }
}
