package fr.lernejo.fileinjector;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
public class Launcher {

    public static void main(String[] args) throws IOException {
        try (AbstractApplicationContext springContext = new AnnotationConfigApplicationContext(Launcher.class)) {
            if (args.length >= 1) {
                ObjectMapper mapper = new ObjectMapper();
                List<FileMapper> games_map = Arrays.asList(mapper.readValue(Paths.get(args[0]).toFile(), FileMapper[].class));
                RabbitTemplate template = springContext.getBean(RabbitTemplate.class);
                for (FileMapper game : games_map) {
                    template.setMessageConverter(new Jackson2JsonMessageConverter());
                    template.convertAndSend("", "game_info", game, m -> {
                        m.getMessageProperties().getHeaders().put("game_id", game.id);
                        return m;
                    });
                }
            } else {
                System.out.println("you must provide a JSON file");
            }
        }
    }
}
