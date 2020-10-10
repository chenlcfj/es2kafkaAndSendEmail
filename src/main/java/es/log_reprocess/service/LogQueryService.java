package es.log_reprocess.service;


import java.util.List;
import java.util.Map;

/**
 * @author chenlc
 * @date 2020/9/14
 */
public interface LogQueryService {

    void getSsoLogFromEs2Topic(String startTime,String endTime);

}
