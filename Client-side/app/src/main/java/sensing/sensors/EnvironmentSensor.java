package sensing.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import sensing.storage.SensorData;

public class EnvironmentSensor {
    private SensorManager sensorManager;
    private Sensor environmentSensor;
    private boolean isListening;
    private SensorData lastEnvironmentData;

    public EnvironmentSensor(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        environmentSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        isListening = false;
        lastEnvironmentData = new SensorData(0, 0, 0);
    }

    public void startListening() {
        if (!isListening) {
            sensorManager.registerListener(sensorEventListener, environmentSensor, SensorManager.SENSOR_DELAY_NORMAL);
            isListening = true;
        }
    }

    public void stopListening() {
        if (isListening) {
            sensorManager.unregisterListener(sensorEventListener);
            isListening = false;
        }
    }

    public SensorData getEnvironmentData() {
        return lastEnvironmentData;
    }

    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
                float[] values = event.values;
                lastEnvironmentData = new SensorData(values[0], values[1], values[2]);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // 处理传感器精度变化事件
        }
    };
}
