package com.thiago.consumer;

import com.thiago.producer.EngineTemperatureSensor;
import org.eclipse.paho.mqttv5.client.*;
import org.eclipse.paho.mqttv5.common.MqttSubscription;

import java.util.UUID;

public class App {
    public static void main(String[] args) throws Exception {
        String subscriberId = UUID.randomUUID().toString();
        var uri = "tcp://localhost:1883";
        var subscriber = new MqttClient(uri, subscriberId);
        var options = new MqttConnectionOptions();
        subscriber.connect(options);
        var topic = EngineTemperatureSensor.TOPIC;
        var mqttSubscrition = new MqttSubscription(topic, 0);
        var subs = new MqttSubscription[] { mqttSubscrition };
        IMqttMessageListener listener = (mTopic, mContent) -> {
            byte[] payload = mContent.getPayload();
            System.out.println("Received message: " + new String(payload));
        };
        var listeners = new IMqttMessageListener[] { listener };
        IMqttToken token = subscriber.subscribe(subs, listeners);
        token.waitForCompletion();
    }
}
