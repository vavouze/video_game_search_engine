package fr.lernejo.search.api;

import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class GameInfoListenerTest {

    @Test
    void try_to_catch_message() {
        String message = "{}";
        String id = "1";
        ElasticSearchConfiguration config = new ElasticSearchConfiguration();
        RestHighLevelClient cli = config.bean("localhost",9200,"elastic","admin");
        GameInfoListener listener = new GameInfoListener(cli);
        try {
            listener.onMessage(message,id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
