package com.example.kafka.noop;

import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.converter.MessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureTask;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class KafkaNoopTemplate<K, V> extends KafkaTemplate<K, V> {

    public KafkaNoopTemplate() {
        super(new DefaultKafkaProducerFactory<>(Collections.emptyMap()));
    }

    @Override
    public String getDefaultTopic() {
        return super.getDefaultTopic();
    }

    @Override
    public void setDefaultTopic(String defaultTopic) {
    }

    @Override
    public void setProducerListener(ProducerListener<K, V> producerListener) {
    }

    @Override
    public MessageConverter getMessageConverter() {
        return super.getMessageConverter();
    }

    @Override
    public void setMessageConverter(RecordMessageConverter messageConverter) {
    }

    @Override
    public boolean isTransactional() {
        return false;
    }

    @Override
    public ListenableFuture<SendResult<K, V>> sendDefault(V data) {
        return new ListenableFutureTask<>(() -> null);
    }

    @Override
    public ListenableFuture<SendResult<K, V>> sendDefault(K key, V data) {
        return new ListenableFutureTask<>(() -> null);
    }

    @Override
    public ListenableFuture<SendResult<K, V>> sendDefault(Integer partition, K key, V data) {
        return new ListenableFutureTask<>(() -> null);
    }

    @Override
    public ListenableFuture<SendResult<K, V>> sendDefault(Integer partition, Long timestamp, K key, V data) {
        return new ListenableFutureTask<>(() -> null);
    }

    @Override
    public ListenableFuture<SendResult<K, V>> send(String topic, V data) {
        return new ListenableFutureTask<>(() -> null);
    }

    @Override
    public ListenableFuture<SendResult<K, V>> send(String topic, K key, V data) {
        return new ListenableFutureTask<>(() -> null);
    }

    @Override
    public ListenableFuture<SendResult<K, V>> send(String topic, Integer partition, K key, V data) {
        return new ListenableFutureTask<>(() -> null);
    }

    @Override
    public ListenableFuture<SendResult<K, V>> send(String topic, Integer partition, Long timestamp, K key, V data) {
        return new ListenableFutureTask<>(() -> null);
    }

    @Override
    public ListenableFuture<SendResult<K, V>> send(ProducerRecord<K, V> record) {
        return new ListenableFutureTask<>(() -> null);
    }

    @Override
    public ListenableFuture<SendResult<K, V>> send(Message<?> message) {
        return new ListenableFutureTask<>(() -> null);
    }

    @Override
    public List<PartitionInfo> partitionsFor(String topic) {
        return Collections.emptyList();
    }

    @Override
    public Map<MetricName, ? extends Metric> metrics() {
        return Collections.emptyMap();
    }

    @Override
    public <T> T execute(ProducerCallback<K, V, T> callback) {
        return null;
    }

    @Override
    public <T> T executeInTransaction(OperationsCallback<K, V, T> callback) {
        return null;
    }

    @Override
    public void flush() {
    }

    @Override
    public void sendOffsetsToTransaction(Map<TopicPartition, OffsetAndMetadata> offsets) {
    }

    @Override
    public void sendOffsetsToTransaction(Map<TopicPartition, OffsetAndMetadata> offsets, String consumerGroupId) {
    }

    @Override
    protected void closeProducer(Producer<K, V> producer, boolean inLocalTx) {
    }

    @Override
    protected ListenableFuture<SendResult<K, V>> doSend(ProducerRecord<K, V> producerRecord) {
        return new ListenableFutureTask<>(() -> null);
    }

    @Override
    public boolean inTransaction() {
        return false;
    }
}
