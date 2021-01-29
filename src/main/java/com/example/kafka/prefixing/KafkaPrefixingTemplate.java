package com.example.kafka.prefixing;

import com.example.base.util.EnvUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;

/**
 * @author moubin.mo
 * @date: 2021/1/29 15:09
 */

public class KafkaPrefixingTemplate<K,Y> extends KafkaTemplate<K,Y> {

	public KafkaPrefixingTemplate(ProducerFactory<K, Y> producerFactory) {
		super(producerFactory);
	}

	/**
	 * <p>覆盖主题，统一添加前缀</p>
	 */
	@Override
	public void setDefaultTopic(String defaultTopic) {
		super.setDefaultTopic(prefixing(defaultTopic));
	}

	@Override
	public List<PartitionInfo> partitionsFor(String topic) {
		return super.partitionsFor(prefixing(topic));
	}

	@Override
	public <T> T execute(ProducerCallback<K, Y, T> callback) {
		throw new UnsupportedOperationException("Not implemented");
	}

	/** 发消息，topic前缀加工 */
	@Override
	protected ListenableFuture<SendResult<K, Y>> doSend(ProducerRecord<K, Y> r) {
		return super.doSend(new ProducerRecord<>(prefixing(r.topic()), r.partition(), r.timestamp(), r.key(), r.value(), r.headers()));
	}

	public static String prefixing(String topic) {
		if (EnvUtils.CLUSTER.isEmpty()){
			return topic;
		}
		return EnvUtils.CLUSTER + "__" + topic;
	}
}
