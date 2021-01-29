package com.example.kafka.noop;

import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpoint;
import org.springframework.kafka.support.TopicPartitionOffset;

import java.util.regex.Pattern;

public class KafkaNoopListenerContainerFactory
        implements KafkaListenerContainerFactory<KafkaNoopListenerContainer> {

    @Override
    public KafkaNoopListenerContainer createListenerContainer(KafkaListenerEndpoint endpoint) {
        return new KafkaNoopListenerContainer();
    }

    @Override
    public KafkaNoopListenerContainer createContainer(TopicPartitionOffset... topicPartitions) {
        return new KafkaNoopListenerContainer();
    }

    @Override
    public KafkaNoopListenerContainer createContainer(String... topics) {
        return new KafkaNoopListenerContainer();
    }

    @Override
    public KafkaNoopListenerContainer createContainer(Pattern topicPattern) {
        return new KafkaNoopListenerContainer();
    }
}
