package crowdos.crowdkit.communication;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MsgProducer {
    public static void publishMsg(String exchange, BuiltinExchangeType exchangeType, String toutingKey, String message) throws IOException, TimeoutException, IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        //创建消息通道
        Channel channel = connection.createChannel();
        // 声明exchange中的消息为可持久化，不自动删除
        channel.exchangeDeclare(exchange, exchangeType, true, false, null);
        // 发布消息
        channel.basicPublish(exchange, toutingKey, null, message.getBytes());
        System.out.println("Sent '" + message + "'");
        channel.close();
        connection.close();
    }

    public static void publishMsg(String exchange, BuiltinExchangeType exchangeType, String toutingKey, String message, String queue) throws IOException, TimeoutException, IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        //创建消息通道
        Channel channel = connection.createChannel();
        // 声明exchange中的消息为可持久化，不自动删除
        channel.exchangeDeclare(exchange, exchangeType, true, false, null);
        // 声明消息队列
//        channel.queueDeclare(queue);
        // 发布消息
        channel.basicPublish(exchange, toutingKey, null, message.getBytes());
        System.out.println("Sent '" + message + "'");
        channel.close();
        connection.close();
    }
}
