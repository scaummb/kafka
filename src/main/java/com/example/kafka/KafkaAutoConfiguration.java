package com.example.kafka;

import com.example.kafka.noop.KafkaNoopListenerContainerFactory;
import com.example.kafka.noop.KafkaNoopTemplate;
import com.example.kafka.prefixing.KafkaPrefixingListenerContainerFactory;
import com.example.kafka.prefixing.KafkaPrefixingTemplate;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author moubin.mo
 * @date: 2021/1/29 15:26
 */
@Configuration
@ConditionalOnClass(KafkaTemplate.class)
@EnableConfigurationProperties(KafkaExtendProperties.class)
public class KafkaAutoConfiguration {
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaAutoConfiguration.class);

	private final KafkaExtendProperties properties;

	public KafkaAutoConfiguration(KafkaExtendProperties properties) {
		this.properties = properties;
	}

	public KafkaTemplate<?,?> kafkaTemplate(
			ProducerFactory<Object, Object> kafkaProducerFactory,
			ProducerListener<Object, Object> kafkaProducerListener,
			Collection<NewTopic> newTopicCollection){
		if (properties.isEnabled()){
			KafkaPrefixingTemplate<Object, Object> kafkaTemplate = new KafkaPrefixingTemplate<>(kafkaProducerFactory);
			kafkaTemplate.setProducerListener(kafkaProducerListener);
			kafkaTemplate.setDefaultTopic(this.properties.getTemplate().getDefaultTopic());

			createTopicIfNeeded(newTopicCollection);
			return kafkaTemplate;
		}
		return new KafkaNoopTemplate();
	}

	private void createTopicIfNeeded(Collection<NewTopic> newTopicCollection) {
		AdminClient.create(properties.buildAdminProperties());
		newTopicCollection = prefixingNewTopics(newTopicCollection);
	}

	private List<NewTopic> prefixingNewTopics(Collection<NewTopic> newTopicCollection) {
		return newTopicCollection.stream()
				.map(t -> {
					if (t.replicasAssignments() != null) {
						return new NewTopic(prefixing(t), t.replicasAssignments());
					}
					return new NewTopic(prefixing(t), t.numPartitions(), t.replicationFactor());
				})
				.collect(Collectors.toList());
	}

	private String prefixing(NewTopic t) {
		return KafkaPrefixingTemplate.prefixing(t.name());
	}

	@Bean
	public KafkaListenerContainerFactory<?> kafkaListenerContainerFactory(ConcurrentKafkaListenerContainerFactoryConfigurer configurer, ConsumerFactory<Object, Object> kafkaConsumerFactory){
		if (properties.isEnabled()){
			KafkaPrefixingListenerContainerFactory<Object, Object> factory = new KafkaPrefixingListenerContainerFactory<>();
			configurer.configure(factory, kafkaConsumerFactory);
			return factory;
		}
		return new KafkaNoopListenerContainerFactory();
	}

	@Bean
	public NewTopic systemEventTopic(){
		return new NewTopic("system-event", 100, (short)1);
	}
}

