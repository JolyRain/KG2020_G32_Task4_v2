package screen;

public class ScreenDepthPoint {
    private ScreenPoint screenPoint;
    private float z;

    public ScreenDepthPoint(ScreenPoint screenPoint, float z) {
        this.screenPoint = screenPoint;
        this.z = z;
    }


    public ScreenPoint getScreenPoint() {
        return screenPoint;
    }

    public void setScreenPoint(ScreenPoint screenPoint) {
        this.screenPoint = screenPoint;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
}
