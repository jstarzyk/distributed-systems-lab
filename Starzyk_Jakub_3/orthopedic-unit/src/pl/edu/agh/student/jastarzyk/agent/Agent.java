package pl.edu.agh.student.jastarzyk.agent;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import pl.edu.agh.student.jastarzyk.examination.Exchange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public abstract class Agent {

//    public Connection getConnection() {
//        return connection;
//    }
//
//    public Channel getChannel() {
//        return channel;
//    }
//
    Connection connection;
    Channel channel;
    List<String> localQueues;

    Agent() throws IOException, TimeoutException {
        this.connection = Exchange.getConnection();
        this.channel = connection.createChannel();
        this.localQueues = new ArrayList<>();
    }

    String createQueue() throws IOException {
        String queue = channel.queueDeclare().getQueue();
        localQueues.add(queue);
        return queue;
    }

    void bindQueue(String queue, String... patterns) throws IOException {
        for (String pattern : patterns) {
            channel.queueBind(queue, Exchange.EXCHANGE_NAME, pattern);
        }
        Exchange.queueCreated(queue, patterns);
    }

//    void listen(String queue, Consumer consumer) throws IOException {
//        channel.basicConsume(queue, true, consumer);
//    }

//    void send(Object object, String routingKey) throws IOException {
//        byte[] bytes = Exchange.serialize(object);
//        channel.basicPublish(Exchange.EXCHANGE_NAME, routingKey, null, bytes);
//        Exchange.sent(object.toString());
//    }

//    public static Object receive(byte[] bytes) throws IOException, ClassNotFoundException {
//        Object object = Exchange.deserialize(bytes);
//        Exchange.received(object.toString());
//        return object;
//    }

}
