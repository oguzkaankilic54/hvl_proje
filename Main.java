public class Main {
    public static void main(String[] args) {
        String topicName = "sensor-data";
        Sensor sensor1 = new Sensor(1, 100, 200, topicName);
        Sensor sensor2 = new Sensor(2, 500, 800, topicName);

        CentralUnit centralUnit = new CentralUnit(topicName);
        centralUnit.startProcessing();

        // Sensorlerden tespitleri gonderme
        sensor1.sendDetection(700, 300);
        sensor2.sendDetection(200, 600);
    }
}
