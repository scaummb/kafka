package com.example.controller;

import com.example.service.KafkaTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author moubin.mo
 * @date: 2021/1/27 16:25
 */
@RestController
@RequestMapping("/kafka")
public class KafkaTestController {

	@Autowired
	private KafkaTestService kafkaTestService;

	@RequestMapping("produceMsg")
	@ResponseBody
	public void produceMsg(String data){
		String topic = "test";
		Integer partition = 1;
		String key = "1";
		kafkaTestService.sendMsg(data, topic, partition, key);
	}
}
