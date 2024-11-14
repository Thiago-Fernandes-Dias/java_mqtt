package com.thiago.producer;

import org.eclipse.paho.mqttv5.client.*;

import java.util.UUID;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        String publisherId = UUID.randomUUID().toString();
        MqttClient client = new MqttClient("tcp://localhost:1883", publisherId);
        MqttConnectionOptions options = new MqttConnectionOptions();
        options.setAutomaticReconnect(true);
        options.setConnectionTimeout(10);
        client.connect(options);
        var f = new EngineTemperatureSensor(client);
        while (true) {
            f.call();
            Thread.sleep(1000);
        }
    }
}
