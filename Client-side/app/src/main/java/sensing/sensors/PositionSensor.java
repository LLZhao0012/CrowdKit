package sensing.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import sensing.storage.SensorData;

public class PositionSensor {
    private SensorManager sensorManager;
    private Sensor positionSensor;
    private boolean isListening;
    private SensorData lastPositionData;

    public PositionSensor(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        positionSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        isListening = false;
        lastPositionData = new SensorData(0, 0, 0);
    }

    public void startListening() {
        if (!isListening) {
            sensorManager.registerListener(sensorEventListener, positionSensor, SensorManager.SENSOR_DELAY_NORMAL);
            isListening = true;
        }
    }

    public void stopListening() {
        if (isListening) {
            sensorManager.unregisterListener(sensorEventListener);
            isListening = false;
        }
    }

    public SensorData getPositionData() {
        return lastPositionData;
    }

    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
                float[] values = event.values;
                lastPositionData = new SensorData(values[0], values[1], values[2]);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
}