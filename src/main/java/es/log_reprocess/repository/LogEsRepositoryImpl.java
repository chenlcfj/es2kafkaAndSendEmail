package es.log_reprocess.repository;


import es.log_reprocess.exception.LogException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author chenlc
 * @date 2020/9/14
 */
@Slf4j
@Repository
public class LogEsRepositoryImpl implements LogEsRepository {
    private final RestHighLevelClient restClient;

    public LogEsRepositoryImpl(RestHighLevelClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public List<Map<String, Object>> getSsoLogFromEs(String index, int startIndex, int pageSize) {
        SearchRequest searchRequest = new SearchRequest();
        List<Map<String, Object>> listMap = new LinkedList<>();
        searchRequest.indices(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().trackTotalHits(true);
        searchSourceBuilder.from(startIndex).size(pageSize);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse;
        try {
            searchResponse = restClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new LogException("elasticsearch日志查询异常", e);
        }
        SearchHits searchHits = searchResponse.getHits();
        long totalHits = searchHits.totalHits;
        SearchHit[] hits = searchHits.getHits();
        if (ArrayUtils.isEmpty(hits)) {
            log.error("查询es索引【{}】,第【{}】,每次查询【{}】,", index, startIndex / pageSize + 1, pageSize);
//                throw new LogNotFoundException("未查询到符合条件的日志");
        }
        int currentPageSize = hits.length;
        log.info("查询es索引【{}】,第【{}】页,每页查询【{}】,当前页有【{}】条数据", index, startIndex / pageSize + 1, pageSize, currentPageSize);
        for (SearchHit hit : hits) {
            Map<String, Object> logData = hit.getSourceAsMap();
            listMap.add(logData);
        }
        return listMap;
    }

    @Override
    public long getSsoLogTotalFromEs(String index) {
        log.info("查询es索引【{}】", index);
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().trackTotalHits(true);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse;
        try {
            searchResponse = restClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new LogException("elasticsearch日志查询异常", e);
        }
        SearchHits searchHits = searchResponse.getHits();
        long totalHits = searchHits.totalHits;
        return totalHits;
    }
}
