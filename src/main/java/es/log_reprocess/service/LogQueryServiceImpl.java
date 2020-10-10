package es.log_reprocess.service;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.log_reprocess.config.LogReprocessConfig;
import es.log_reprocess.pojo.SsoLogModel;
import es.log_reprocess.repository.LogEsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author chenlc
 * @date 2020/9/14
 */
@Slf4j
@Service
public class LogQueryServiceImpl implements LogQueryService {
    @Autowired
    private LogReprocessConfig config;
    private final LogEsRepository logEsRepository;
    private final KafkaService kafkaService;
    private final static String DEFAULT_LOG = "default";


    public LogQueryServiceImpl(LogEsRepository logEsRepository, KafkaService kafkaService) {
        this.logEsRepository = logEsRepository;
        this.kafkaService = kafkaService;
    }


    @Override
    public void getSsoLogFromEs2Topic(String startTime, String endTime) {
        long produceNum = 0;
        int startIndex = 1;
        int pageSize = config.getPageSize();
        int pages = 1;
        String indexPrefix = config.getIndexPrefix();
//        String startTime = config.getStartTime();
//        String endTime = config.getEndTime();
        String topic = config.getTopic();
        List<String> timeList = getTime(startTime, endTime);
        for (String time : timeList) {
            ObjectMapper objectMapper = new ObjectMapper();
            String index = indexPrefix + "-" + time + "*";
            long total = logEsRepository.getSsoLogTotalFromEs(index);
            if (total == 0) {
                continue;
            }
            //分页去es查询
            if (total % pageSize == 0) {
                pages = (int) total / pageSize;
            } else {
                pages = (int) total / pageSize + 1;
            }
            for (int i = 1; i <= pages; i++) {
                startIndex = (int) Math.min((i - 1) * pageSize, total);
                List<Map<String, Object>> msgFromEs = logEsRepository.getSsoLogFromEs(index, startIndex, pageSize);
                for (Map<String, Object> map : msgFromEs) {
                    try {
                        String ssoLogMsg = objectMapper.writeValueAsString(map);
                        SsoLogModel ssoLogModel = JSON.parseObject(ssoLogMsg, SsoLogModel.class);
                        //对null值处理"null"
                        resetNull(ssoLogModel);
                        kafkaService.push(topic, ssoLogModel.toString());
                        produceNum++;
                    } catch (JsonProcessingException e) {
                        log.error("json cast String failed", e);
                    }
                }
            }
        }
        log.info("kafka producer already produced:【{}】recordes", produceNum);
    }

    public static List<String> getTime(String startTimeStr, String endTimeStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        List<String> timestampList = new LinkedList<>();
        try {
            long startTime = format1.parse(startTimeStr).getTime();
            long endTime = format1.parse(endTimeStr).getTime();
            long longInterval = 1000 * 60 * 60 * 24;
            //截止时间多加一天
//            endTime = endTime + longInterval;
            long q = (endTime - startTime) / longInterval;
            for (int i = 0; i <= q + 1; i++) {
                if (startTime + i * longInterval < endTime) {
                    String f = format.format(new Date(startTime + i * longInterval));
                    timestampList.add(f);
                } else {
                    String f = format.format(new Date(endTime));
                    timestampList.add(f);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestampList;
    }

    private static SsoLogModel resetNull(SsoLogModel ssoLogModel) {
        if (ssoLogModel.getAuthMethod() == null) {
            ssoLogModel.setAuthMethod("");
        }
        if (ssoLogModel.getAction() == null) {
            ssoLogModel.setAction("");
        }
        if (ssoLogModel.getApplication() == null) {
            ssoLogModel.setApplication("");
        }
        if (ssoLogModel.getClientBrowser() == null) {
            ssoLogModel.setClientBrowser("");
        }
        if (ssoLogModel.getMethod() == null) {
            ssoLogModel.setMethod("");
        }
        if (ssoLogModel.getResult() == null) {
            ssoLogModel.setResult("");
        }
        if (ssoLogModel.getResultDetail() == null) {
            ssoLogModel.setResultDetail("");
        }
        if (ssoLogModel.getServerIp() == null) {
            ssoLogModel.setServerIp("");
        }
        if (ssoLogModel.getClientOs() == null) {
            ssoLogModel.setClientOs("");
        }
        if (ssoLogModel.getTime() == null) {
            ssoLogModel.setTime("");
        }
        if (ssoLogModel.getTimeUsed() == null) {
            ssoLogModel.setTimeUsed("");
        }
        if (ssoLogModel.getUrl() == null) {
            ssoLogModel.setUrl("");
        }
        if (ssoLogModel.getUserAgent() == null) {
            ssoLogModel.setUserAgent("");
        }
        if (ssoLogModel.getServiceId() == null) {
            ssoLogModel.setServiceId("");
        }
        if (ssoLogModel.getUserUinqueId() == null) {
            ssoLogModel.setUserUinqueId("");
        }
        if (ssoLogModel.getUserId() == null) {
            ssoLogModel.setUserId("");
        }
        if (ssoLogModel.getResultDetail() == null) {
            ssoLogModel.setResultDetail("");
        }
        if (ssoLogModel.getUserName() == null) {
            ssoLogModel.setUserName("");
        }
        if (ssoLogModel.getWho() == null) {
            ssoLogModel.setWho("");
        }
        return ssoLogModel;
    }
}
