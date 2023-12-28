package sensing.helper;

import sensing.sensors.EnvironmentSensor;
import sensing.sensors.MotionSensor;
import sensing.sensors.PositionSensor;
import sensing.storage.SensorData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SenseHelper {
    private MotionSensor motionSensor;
    private EnvironmentSensor environmentSensor;
    private PositionSensor positionSensor;

    public SenseHelper(MotionSensor motionSensor, EnvironmentSensor environmentSensor, PositionSensor positionSensor) {
        this.motionSensor = motionSensor;
        this.environmentSensor = environmentSensor;
        this.positionSensor = positionSensor;
    }

    public void startAllSensors() {
        motionSensor.startListening();
        environmentSensor.startListening();
        positionSensor.startListening();
    }

    public void stopAllSensors() {
        motionSensor.stopListening();
        environmentSensor.stopListening();
        positionSensor.stopListening();
    }

    public SensorData getSensorData(String sensorType) {
        switch (sensorType) {
            case "Motion":
                return motionSensor.getMotionData();
            case "Environment":
                return environmentSensor.getEnvironmentData();
            case "Position":
                return positionSensor.getPositionData();
            default:
                return new SensorData();
        }
    }

    public void storeData(SensorData data,String filePath) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(data.toString() + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}