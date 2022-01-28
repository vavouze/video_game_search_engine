package fr.lernejo.search.api;

import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.io.IOException;

class GameInfoListenerTest {

    @Test
    void try_to_catch_message() {
        try (AbstractApplicationContext springContext = new AnnotationConfigApplicationContext(Launcher.class)) {
            RabbitTemplate template = springContext.getBean(RabbitTemplate.class);
            template.setMessageConverter(new Jackson2JsonMessageConverter());
            template.convertAndSend("", "game_info", "{]", m -> {
                m.getMessageProperties().getHeaders().put("game_id", "1");
                return m;
            });
        }
    }
}
