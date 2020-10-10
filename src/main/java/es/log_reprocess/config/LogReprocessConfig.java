package es.log_reprocess.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author chenlc
 * @date 2020/9/14
 */
@Setter
@Getter
@Component
@Slf4j
@ConfigurationProperties(prefix = "project.log-reprocess")
public class LogReprocessConfig {
    private String indexPrefix;
    private String topic;
    private String startTime;
    private String endTime;
    private Integer pageSize;

}
