package communication;

import android.os.AsyncTask;
import android.util.Log;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RabbitMQClient extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "RabbitMQClient";
    private static final String EXCHANGE_NAME = "topic_exchange";
    private static final String ROUTING_KEY = "specific_routing_key";

    private String deviceID;

    public RabbitMQClient(String deviceID) {
        this.deviceID = deviceID;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("your_rabbitmq_server_address");

            factory.setUsername("guest");
            factory.setPassword("guest");

            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            String queueName = channel.queueDeclare(deviceID, false, false, false, null).getQueue();

            channel.queueBind(queueName, EXCHANGE_NAME, ROUTING_KEY);

            channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, com.rabbitmq.client.AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, StandardCharsets.UTF_8);
                    Log.d(TAG, "Received message: " + message);
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());
        }
        return null;
    }
}
