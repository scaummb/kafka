package com.example.kafka.prefixing;

import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerEndpoint;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.TopicPartitionOffset;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class KafkaPrefixingListenerContainerFactory<K, V>
        extends ConcurrentKafkaListenerContainerFactory<K, V> {

    /**
     *<p>
     *  通过配置MessageListenerContainer和提供消息侦听器或使用 @KafkaListener 批注来接收消息。
     * MessageListenerContainer提供了两种实现：
     * KafkaMessageListenerContainer
     * ConcurrentMessageListenerContainer
     *
     * 该KafkaMessageListenerContainer接收在单个线程从所有的主题或分区上的所有消息。所述ConcurrentMessageListenerContainer的一个或多个代表KafkaMessageListenerContainer实例，以提供多线程消耗。
     *</p>
     *
     */
    @Override
    protected ConcurrentMessageListenerContainer<K, V> createContainerInstance(KafkaListenerEndpoint endpoint) {
        //2.6.0 版本没有这个属性了
        TopicPartitionOffset[] topicPartitions = endpoint.getTopicPartitionsToAssign();
        if (topicPartitions.length > 0) {
            // prefixing with env
            List<TopicPartitionOffset> offsetList = prefixingTopicPartitionInitialOffsets(topicPartitions);
            TopicPartitionOffset[] offsets = offsetList.toArray(new TopicPartitionOffset[0]);
            ContainerProperties properties = new ContainerProperties(offsets);
            return new ConcurrentMessageListenerContainer<K, V>(getConsumerFactory(), properties);
        } else {
            Collection<String> topics = endpoint.getTopics();
            if (!topics.isEmpty()) {
                // prefixing with env
                topics = prefixingTopics(topics);

                ContainerProperties properties = new ContainerProperties(topics.toArray(new String[0]));
                return new ConcurrentMessageListenerContainer<K, V>(getConsumerFactory(), properties);
            } else {
                Pattern topicPattern = endpoint.getTopicPattern();
                // prefixing with env
                topicPattern = prefixingPattern(topicPattern);

                ContainerProperties properties = new ContainerProperties(topicPattern);
                return new ConcurrentMessageListenerContainer<K, V>(getConsumerFactory(), properties);
            }
        }
    }

    private Pattern prefixingTopicPartitionInitialOffsets(Pattern endpointTopicPattern) {


        return endpointTopicPattern;
    }

    private Pattern prefixingPattern(Pattern topicPattern) {
        return Pattern.compile(prefixing(topicPattern.pattern()));
    }

    private Collection<String> prefixingTopics(Collection<String> topics) {
        return topics.stream().map(this::prefixing).collect(Collectors.toList());
    }

    private List<TopicPartitionOffset> prefixingTopicPartitionInitialOffsets(
            TopicPartitionOffset[] topicPartitions) {

        List<TopicPartitionOffset> offsetList = Arrays.asList(topicPartitions);
        return offsetList.stream().map(of -> {
            return new TopicPartitionOffset(prefixing(of.getTopic()), of.getPartition(), of.getOffset(), of.isRelativeToCurrent());
        }).collect(Collectors.toList());
    }

    private String prefixing(String topic) {
        return KafkaPrefixingTemplate.prefixing(topic);
    }
}
