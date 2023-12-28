package sensing.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SPHelper {
    private static final String PREF_NAME = "sensor_prefs";
    private static final String KEY_SENSOR_DATA = "sensor_data";

    private SharedPreferences preferences;

    public SPHelper(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveData(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    public String loadData(String key) {
        return preferences.getString(key, "");
    }
}