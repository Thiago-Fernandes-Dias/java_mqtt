package com.thiago.producer;

import org.eclipse.paho.mqttv5.client.*;
import org.eclipse.paho.mqttv5.common.MqttMessage;

import java.util.Random;
import java.util.concurrent.Callable;

public class EngineTemperatureSensor implements Callable<Void> {
    public EngineTemperatureSensor(IMqttClient client) {
        this.client = client;
    }

    private final IMqttClient client;

    public static final String TOPIC = "engine/temperature";

    private static final Random rnd = new Random();

    @Override
    public Void call() throws Exception {
        if (!client.isConnected()) {
            return null;
        }
        MqttMessage msg = readEngineTemp();
        msg.setQos(1);
        msg.setRetained(true);
        client.publish(TOPIC, msg);
        System.out.println("Message published: " + msg);
        return null;
    }

    private MqttMessage readEngineTemp() {
        double temp = 80 + rnd.nextDouble() * 20.0;
        byte[] payload = String.format("T:%04.2f", temp)
                .getBytes();
        return new MqttMessage(payload);
    }
}