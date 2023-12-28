package sensing.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import sensing.storage.SensorData;

public class MotionSensor {
    private SensorManager sensorManager;
    private Sensor motionSensor;
    private boolean isListening;
    private SensorData lastMotionData;

    public MotionSensor(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        motionSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        isListening = false;
        lastMotionData = new SensorData();
    }

    public void startListening() {
        if (!isListening) {
            sensorManager.registerListener(sensorEventListener, motionSensor, SensorManager.SENSOR_DELAY_NORMAL);
            isListening = true;
        }
    }

    public void stopListening() {
        if (isListening) {
            sensorManager.unregisterListener(sensorEventListener);
            isListening = false;
        }
    }

    public SensorData getMotionData() {
        return lastMotionData;
    }

    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float[] values = event.values;
                lastMotionData = new SensorData(values[0], values[1], values[2]);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
}
