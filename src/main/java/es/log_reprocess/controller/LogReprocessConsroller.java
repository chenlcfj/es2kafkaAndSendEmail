package es.log_reprocess.controller;

import es.log_reprocess.service.LogQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author chenlc
 * @date 2020/9/14
 */
@RestController
@Slf4j
public class LogReprocessConsroller {
    @Autowired
    private LogQueryService logQueryService;

    @GetMapping(path = "/reprocess")
    public void produce(@RequestParam String startTime, @RequestParam String endTime) {
        logQueryService.getSsoLogFromEs2Topic(startTime, endTime);
    }
}
