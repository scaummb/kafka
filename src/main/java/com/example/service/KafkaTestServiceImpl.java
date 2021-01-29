package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author moubin.mo
 * @date: 2021/1/29 14:46
 */
@Component
public class KafkaTestServiceImpl implements KafkaTestService, ApplicationListener<ApplicationReadyEvent> {

	Logger LOGGER = LoggerFactory.getLogger(KafkaTestServiceImpl.class);

	private final static ExecutorService pool = Executors.newSingleThreadExecutor();

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
		startSendingMessages();
	}

	private void startSendingMessages() {
		pool.submit(new Runnable() {
			@Override
			public void run() {
				for (;;){
					try {
						Thread.sleep(10000);
						kafkaTemplate.send("test", 1, "1","111111");
					} catch (InterruptedException e) {
						LOGGER.debug("KafkaTestServiceImpl error, ", e);
					}
				}
			}
		});
	}

	@Override
	public void sendMsg(String data, String topic, Integer partition, String key) {
		kafkaTemplate.send(topic, partition, key, data);
	}
}
