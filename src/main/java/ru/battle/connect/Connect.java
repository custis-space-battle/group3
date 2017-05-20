package ru.battle.connect;

import com.rabbitmq.client.*;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Created by onotole on 20/05/2017.
 */
@Slf4j
public class Connect {
    private final String URL = "amqp://group3:TpRjMQ@91.241.45.69/debug";
    private ConnectionFactory factory;
    private Connection connection;
    private Channel inputChannel;
    private Channel outputChannel;
    private String inptuQueue = "to_group3";
    private String outputQueue = "group3";
    private Consumer consumer;
    private MessageProcessor processor = new MessageProcessor();
    public Connect() {
        prepare();
    }

    @SneakyThrows
    private void prepare() {
        factory = new ConnectionFactory();
        factory.setUri(URL);
        connection = factory.newConnection();
        inputChannel = connection.createChannel();
        inputChannel.queueDeclare(inptuQueue, false, false, true, null);
        inputChannel.queueBind(inptuQueue, inptuQueue, inptuQueue);

        outputChannel = connection.createChannel();

        consumer = new DefaultConsumer(inputChannel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                log.info(" [x] Received '" + message + "'");
                String outputMessage =  processor.processMessage(message);
                if (! outputMessage.isEmpty()) {
                    send(outputMessage);
                }
            }
        };
        inputChannel.basicConsume(inptuQueue, true, consumer);

    }

    @SneakyThrows
    public void send(String message) {
        log.info("[x] Send: " + message);
        outputChannel.basicPublish(outputQueue, outputQueue, null, message.getBytes());
    }

}
