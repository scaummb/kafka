package com.example.kafka.noop;

import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.springframework.kafka.listener.MessageListenerContainer;

import java.util.Map;

public class KafkaNoopListenerContainer implements MessageListenerContainer {

    @Override
    public void setupMessageListener(Object messageListener) {
        // NOOP
    }

    @Override
    public Map<String, Map<MetricName, ? extends Metric>> metrics() {
        return null;
    }

    @Override
    public boolean isAutoStartup() {
        return false;
    }

    @Override
    public void stop(Runnable callback) {
        // NOOP
    }

    @Override
    public void start() {
        // NOOP
    }

    @Override
    public void stop() {
        // NOOP
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public int getPhase() {
        return 0;
    }
}
