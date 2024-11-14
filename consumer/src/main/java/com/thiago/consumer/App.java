package com.thiago.consumer;

import com.thiago.producer.EngineTemperatureSensor;
import org.eclipse.paho.mqttv5.client.*;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.*;

public class App {
    public static void main(String[] args) throws Exception {
        CountDownLatch receivedSignal = new CountDownLatch(10);
        String subscriberId = UUID.randomUUID().toString();
        IMqttClient subscriber = new MqttClient("tcp://localhost:1883", subscriberId);
        MqttConnectionOptions options = new MqttConnectionOptions();
        options.setAutomaticReconnect(true);
        options.setConnectionTimeout(10);
        subscriber.connect(options);
        subscriber.subscribe(EngineTemperatureSensor.TOPIC, 0, (topic, msg) -> {
            byte[] payload = msg.getPayload();
            // ... payload handling omitted
            System.out.println(Arrays.toString(payload));
            receivedSignal.countDown();
        });
        receivedSignal.await(1, TimeUnit.MINUTES);
    }
}
