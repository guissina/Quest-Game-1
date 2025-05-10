package com.quest.config.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class HelloController {

    public static class HelloMessage {
        public String name;
    }

    public static class Greeting {
        public String content;
        public Greeting(String c) { this.content = c; }
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage msg) {
        return new Greeting("Hello, " + msg.name + "!");
    }
}
