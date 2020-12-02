package rasterization.buffer;

import screen.ScreenPoint;
import screen.ScreenPolygon;

public class ZPlaneResolver implements ZValueResolver {
    private ScreenPolygon screenPolygon;

    public ZPlaneResolver(ScreenPolygon screenPolygon) {
        this.screenPolygon = screenPolygon;
    }

    private int[] createPlane() {
        int[] plane = new int[4];
        int x1 = screenPolygon.getScreenPoint1().getI();
        int x2 = screenPolygon.getScreenPoint2().getI();
        int x3 = screenPolygon.getScreenPoint3().getI();
        int y1 = screenPolygon.getScreenPoint1().getJ();
        int y2 = screenPolygon.getScreenPoint2().getJ();
        int y3 = screenPolygon.getScreenPoint3().getJ();
        float z1 = screenPolygon.getDepthPoint1().getZ();
        float z2 = screenPolygon.getDepthPoint2().getZ();
        float z3 = screenPolygon.getDepthPoint3().getZ();

        //A
        plane[0] = (int) (y1 * (z2 - z3) + y2 * (z3 - z1) + y3 * (z1 - z2));

        //B
        plane[1] = (int) (z1 * (x2 - x3) + z2 * (x3 - x1) + z3 * (x1 - x2));

        //C
        plane[2] = x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2);

        //D
        plane[3] = (int) -(x1 * (y2 * z3 - y3 * z2) + x2 * (y3 * z1 - y1 * z3) + x3 * (y1 * z2 - y2 * z1));

        return plane;
    }

    @Override
    public int resolve(ScreenPoint point) {
        int[] plane = createPlane();
        return  -((plane[0] * point.getI() + plane[1] * point.getJ() + plane[3]) / plane[2]);
    }

    public ScreenPolygon getScreenPolygon() {
        return screenPolygon;
    }

    public void setScreenPolygon(ScreenPolygon screenPolygon) {
        this.screenPolygon = screenPolygon;
    }
}
