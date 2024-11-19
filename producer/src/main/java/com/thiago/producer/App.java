package com.thiago.producer;

import org.eclipse.paho.client.mqttv3.*;

import java.util.UUID;

public class App {
    public static void main(String[] args) throws Exception {
        final String publisherId = UUID.randomUUID().toString();
        final var client = new MqttClient("tcp://mqtt_server:1883", publisherId);
        final var options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setConnectionTimeout(10);
        client.connect(options);
        final var f = new EngineTemperatureSensor(client);
        for (int i = 0; i < 10; i++) {
            f.call();
            Thread.sleep(1000);
        }
        client.disconnect();
        client.close();
    }
}
