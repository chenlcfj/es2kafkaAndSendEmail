package es.log_reprocess.service;

/**
 * @author chenlc
 * @date 2020/9/14
 */
public interface KafkaService {

    //push message
    void push(String topic, String value);
}
