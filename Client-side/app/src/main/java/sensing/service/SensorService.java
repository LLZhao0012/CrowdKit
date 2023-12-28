package sensing.service;

import sensing.helper.SenseHelper;

public class SensorService {
    private SenseHelper senseHelper;

    public SensorService(SenseHelper senseHelper) {
        this.senseHelper = senseHelper;
    }

    public void performSensorAction() {
        senseHelper.startAllSensors();
    }
}