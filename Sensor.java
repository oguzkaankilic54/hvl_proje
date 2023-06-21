import org.apache.kafka.clients.producer.*;

import java.util.Properties;

public class Sensor {
    private int sensorId;
    private int x;
    private int y;
    private String topicName;

    public Sensor(int sensorId, int x, int y, String topicName) {
        this.sensorId = sensorId;
        this.x = x;
        this.y = y;
        this.topicName = topicName;
    }

    public void sendDetection(int targetX, int targetY) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        String message = sensorId + "," + x + "," + y + "," + targetX + "," + targetY;

        producer.send(new ProducerRecord<>(topicName, message), new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception != null) {
                    exception.printStackTrace();
                }
            }
        });

        producer.close();
    }
}
