import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.util.Collections;
import java.util.Properties;

public class CentralUnit {
    private String topicName;

    public CentralUnit(String topicName) {
        this.topicName = topicName;
    }

    public void startProcessing() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "central-unit");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topicName));

        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    String[] data = record.value().split(",");
                    int sensorId = Integer.parseInt(data[0]);
                    int sensorX = Integer.parseInt(data[1]);
                    int sensorY = Integer.parseInt(data[2]);
                    int targetX = Integer.parseInt(data[3]);
                    int targetY = Integer.parseInt(data[4]);

                    // Hedefin konumunun hesaplanmasi
                    int targetPositionX = calculateTargetPositionX(sensorId, sensorX, sensorY, targetX);
                    int targetPositionY = calculateTargetPositionY(sensorId, sensorX, sensorY, targetY);

                    System.out.println("Hedef konumu: (" + targetPositionX + ", " + targetPositionY + ")");
                }
                consumer.commitSync();
            }
        } finally {
            consumer.close();
        }
    }

    private int calculateTargetPositionX(int sensorId, int sensorX, int sensorY, int targetX) {
        // Ornek bir hesaplama islemi: Hedefin X koordinatini sensorun X koordinati ile farkini alarak buluyoruz
		return sensorX - (targetX - sensorX);
    }

    private int calculateTargetPositionY(int sensorId, int sensorX, int sensorY, int targetY) {
        // Ornek bir hesaplama islemi: Hedefin Y koordinatini sensorun Y koordinati ile farkini alarak buluyoruz
		return sensorY - (targetY - sensorY);
    }
}
