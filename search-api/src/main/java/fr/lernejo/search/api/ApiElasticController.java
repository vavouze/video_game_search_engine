package fr.lernejo.search.api;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
class ApiElasticController {
    private final RestHighLevelClient cli;

    ApiElasticController(RestHighLevelClient cli) {
        this.cli = cli;
    }

    @GetMapping("/api/games")
    ArrayList<Object> getGames(@RequestParam(name = "query") String query) throws IOException {
        SearchRequest searchRequest = new SearchRequest().source(SearchSourceBuilder.searchSource().query(new QueryStringQueryBuilder(query)));
        SearchResponse response = this.cli.search(searchRequest, RequestOptions.DEFAULT);
        ArrayList res = new ArrayList();
        for (SearchHit hit :response.getHits()){
            res.add(hit.getSourceAsMap());
        }
        return res;
    }
}
