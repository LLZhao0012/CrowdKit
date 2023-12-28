package sensing.export;

import sensing.storage.SensorData;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;

public class FileExport {
    public void exportDataToFile(Context context, SensorData data, String fileName) {
        try (FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            String dataString = "Sensor Data: " + data.toString();
            fos.write(dataString.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}