package fr.lernejo.search.api;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GameInfoListener {
    private final RestHighLevelClient cli;
    private final Logger logger = LoggerFactory.getLogger(GameInfoListener.class);
    public GameInfoListener(RestHighLevelClient document){
        this.cli = document;
    }

    @RabbitListener(queues = AmqpConfiguration.GAME_INFO_QUEUE)
    public void onMessage(String document,@Header("game_id") String id) throws IOException {
        try {
            IndexRequest req = new IndexRequest("games").id(id).source(document, XContentType.JSON);
            logger.info(id);
            this.cli.index(req, RequestOptions.DEFAULT);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

