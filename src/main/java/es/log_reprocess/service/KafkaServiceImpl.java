package es.log_reprocess.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.Future;

/**
 * @author chenlc
 * @date 2020/9/14
 */
@Slf4j
@Service
public class KafkaServiceImpl implements KafkaService {

    /**
     * kafka producer 初始化变量
     */
    private KafkaProducer<String, String> producer;

    private Long producerOffset = 0L;


    @Autowired
    private KafkaProperties kafkaProperties;


    @PostConstruct
    public void init() {
        this.producer = new KafkaProducer<>(kafkaProperties.buildProducerProperties());
    }

    @Override
    public void push(String topic, String value) {

        try {
            Future<RecordMetadata> result = this.producer.send(new ProducerRecord<>(topic,value));
            if (result.isDone()) {
                this.producerOffset++;
                log.debug("ManagerApplication generate {} records succ", this.producerOffset);
                log.debug("Kafka Producer push message = {}", value);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            this.producer.close();
        }
    }
}
