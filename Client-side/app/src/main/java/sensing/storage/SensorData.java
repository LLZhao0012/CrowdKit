package sensing.storage;

public class SensorData {
    private float x;
    private float y;
    private float z;

    public SensorData() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
    }

    public SensorData(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
