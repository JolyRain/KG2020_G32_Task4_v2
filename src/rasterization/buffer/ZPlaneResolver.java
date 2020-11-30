package rasterization.buffer;

import screen.ScreenPoint;
import third.Polygon;

public class ZPlaneResolver implements ZValueResolver{
    private Polygon polygon;

    public ZPlaneResolver(Polygon polygon) {
        this.polygon = polygon;
    }

    private float[] createPlane() {
        float[] plane = new float[4];
        float x1 = polygon.getPoint1().getX(), x2 = polygon.getPoint2().getX(), x3 = polygon.getPoint3().getX();
        float y1 = polygon.getPoint1().getY(), y2 = polygon.getPoint2().getY(), y3 = polygon.getPoint3().getY();
        float z1 = polygon.getPoint1().getZ(), z2 = polygon.getPoint2().getZ(), z3 = polygon.getPoint3().getZ();

        //A
        plane[0] = y1 * (z2 - z3) + y2 * (z3 - z1) + y3 * (z1 - z2);

        //B
        plane[1] = z1 * (x2 - x3) + z2 * (x3 - x1) + z3 * (x1 - x2);

        //C
        plane[2] = x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2);

        //D
        plane[3] = -(x1 * (y2 * z3 - y3 * z2) + x2 * (y3 * z1 - y1 * z3) + x3 * (y1 * z2 - y2 * z1));

        return plane;
    }

    @Override
    public float resolve(ScreenPoint point) {
        float[] plane = createPlane();
        return - (plane[0] * point.getI() + plane[1] * point.getJ() + plane[3]) / plane[2];
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }
}
