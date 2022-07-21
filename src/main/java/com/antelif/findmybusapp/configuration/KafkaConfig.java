package com.antelif.findmybusapp.configuration;

import com.antelif.findmybusapp.domain.BusLocation;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

/** Kafka configuration. */
@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

  private final KafkaProperties kafkaProperties;

  // Consumer configuration.
  @Bean
  public ConsumerFactory<String, BusLocation> consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(
        consumerConfigs(), new StringDeserializer(), new JsonDeserializer<>(BusLocation.class));
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, BusLocation>
      kafkaListenerContainerFactory() {

    var factory = new ConcurrentKafkaListenerContainerFactory<String, BusLocation>();
    factory.setConsumerFactory(consumerFactory());

    return factory;
  }

  @Bean
  public Map<String, Object> consumerConfigs() {
    var props = new HashMap<String, Object>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
    props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaProperties.getConsumer().getGroupId());
    props.put(
        ConsumerConfig.MAX_POLL_RECORDS_CONFIG, kafkaProperties.getConsumer().getMaxPollRecords());
    props.put(
        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
        kafkaProperties.getConsumer().getAutoOffsetReset());
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);

    return props;
  }

  // Producer configuration.
  @Bean
  public ProducerFactory<String, BusLocation> producerFactory() {
    return new DefaultKafkaProducerFactory<>(produceConfigs());
  }

  @Bean
  public KafkaTemplate<String, BusLocation> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }

  @Bean
  public Map<String, Object> produceConfigs() {
    var props = new HashMap<String, Object>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    return props;
  }
}
