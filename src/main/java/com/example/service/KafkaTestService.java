package com.example.service;

/**
 * @author moubin.mo
 * @date: 2021/1/29 14:46
 */

public interface KafkaTestService {
	void sendMsg(String data, String topic, Integer partition, String key);
}
