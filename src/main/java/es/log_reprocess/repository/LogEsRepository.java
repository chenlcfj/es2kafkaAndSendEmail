package es.log_reprocess.repository;

import java.util.List;
import java.util.Map;

/**
 * @author chenlc
 * @date 2020/9/14
 */
public interface LogEsRepository {
    List<Map<String, Object>> getSsoLogFromEs(String index, int startIndex, int pageSize);

    long getSsoLogTotalFromEs(String index);
}
