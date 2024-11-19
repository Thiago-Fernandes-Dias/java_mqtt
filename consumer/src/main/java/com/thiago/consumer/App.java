package com.thiago.consumer;

import org.eclipse.paho.client.mqttv3.*;

import java.util.UUID;

public class App {
    public static void main(String[] args) throws Exception {
        final String subscriberId = UUID.randomUUID().toString();
        final var uri = "tcp://mqtt_server:1883";
        final var subscriber = new MqttClient(uri, subscriberId);
        final var options = new MqttConnectOptions();
        subscriber.connect(options);
        final var topics = new String[] { "engine/temperature" };
        final var qoss = new int[] { 0 };
        final IMqttMessageListener listener = (mTopic, mContent) -> {
            byte[] payload = mContent.getPayload();
            System.out.println("Received message: " + new String(payload));
        };
        var listeners = new IMqttMessageListener[] { listener };
        subscriber.subscribe(topics, qoss, listeners);
        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
        }
        subscriber.disconnect();
        subscriber.close();
    }
}
