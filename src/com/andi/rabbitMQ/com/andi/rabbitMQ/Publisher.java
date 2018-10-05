package com.andi.rabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class Publisher {

    public static void main(String[] args) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, InterruptedException {

        // create connection
        ConnectionFactory factory = new ConnectionFactory();

        // set Uri, guest & guest
        factory.setUri("amqp://guest:guest@localhost");
        factory.setConnectionTimeout(300000);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // declare a queue, declare a name and have it stick around
        channel.queueDeclare("my-queue", true, false, false, null);

        int count = 0;

        while (count < 50) {
            String message = "Message number " + count;

            // publish it, message.getBytes() => serialize it
            channel.basicPublish("", "my-queue", null, message.getBytes());
            count++;
            System.out.println("Published message: " + message);

            Thread.sleep(5000);
        }
    }
}
