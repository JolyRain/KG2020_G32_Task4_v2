package rasterization.polygonDrawers;

import rasterization.buffer.DepthBuffer;
import rasterization.buffer.ZPlaneResolver;
import rasterization.buffer.ZValueResolver;
import rasterization.lineDrawers.LineDrawer;
import rasterization.pixelDrawers.PixelDrawer;
import screen.ScreenDepthPoint;
import screen.ScreenPoint;
import screen.ScreenPolygon;

public class MyPolygonDrawer implements PolygonDrawer {
    private PixelDrawer pixelDrawer;
    private LineDrawer lineDrawer;
    private DepthBuffer depthBuffer;

    //
    public MyPolygonDrawer(PixelDrawer pixelDrawer, LineDrawer lineDrawer, DepthBuffer depthBuffer) {
        this.pixelDrawer = pixelDrawer;
        this.lineDrawer = lineDrawer;
        this.depthBuffer = depthBuffer;
    }

//    public MyPolygonDrawer(PixelDrawer pixelDrawer, LineDrawer lineDrawer) {
//        this.pixelDrawer = pixelDrawer;
//        this.lineDrawer = lineDrawer;
//    }

    @Override
    public void drawPolygon(ScreenPolygon screenPolygon) {
        lineDrawer.drawLine(screenPolygon.getScreenPoint1(), screenPolygon.getScreenPoint2(), screenPolygon.getColor());
        lineDrawer.drawLine(screenPolygon.getScreenPoint2(), screenPolygon.getScreenPoint3(), screenPolygon.getColor());
        lineDrawer.drawLine(screenPolygon.getScreenPoint3(), screenPolygon.getScreenPoint1(), screenPolygon.getColor());
    }

    @Override
    public void fillPolygon(ScreenPolygon screenPolygon) {
        ScreenDepthPoint sdp1 = screenPolygon.getDepthPoint1();
        ScreenDepthPoint sdp2 = screenPolygon.getDepthPoint2();
        ScreenDepthPoint sdp3 = screenPolygon.getDepthPoint3();

        ScreenPoint sp1 = screenPolygon.getScreenPoint1();
        ScreenPoint sp2 = screenPolygon.getScreenPoint2();
        ScreenPoint sp3 = screenPolygon.getScreenPoint3();
        ZValueResolver resolver = new ZPlaneResolver(screenPolygon.getPolygon());
        ScreenPoint topLeft = topLeft(sp1, sp2, sp3);
        ScreenPoint bottomRight = bottomRight(sp1, sp2, sp3);
        for (int y = topLeft.getJ(); y <= bottomRight.getJ(); y++) {
            for (int x = topLeft.getI(); x <= bottomRight.getI(); x++) {
//                float xa = sp1.getI() + (sp2.getI() - sp1.getI()) * (float) ((y - sp1.getJ()) );
//                float xb = sp1.getI() + (sp3.getI() - sp1.getI()) * (float) ((y - sp1.getJ()) );
//                float za = sdp1.getZ() + (sdp2.getZ() - sdp1.getZ()) * (float) ((y - sp1.getJ()));
//                float zb = sdp1.getZ() + (sdp3.getZ() - sdp1.getZ()) * (float) ((y - sp1.getJ()));
//                float z = za + (zb - za) * ((x - xa) / (xb - xa));
                ScreenPoint point = new ScreenPoint(x, y);
                float z = resolver.resolve(point);
                if (isInnerPoint(new ScreenPoint(x, y), sp1, sp2, sp3)) {
                    if (depthBuffer.checkDepth(point, z)) {
                        pixelDrawer.drawPixel(x, y, screenPolygon.getColor());
                    }
                }
            }
        }
    }

    //определяем лежит ли точка внутри треугольника
    private boolean isInnerPoint(ScreenPoint point, ScreenPoint a, ScreenPoint b, ScreenPoint c) {
        int aSide = getSide(point, a, b);
        int bSide = getSide(point, b, c);
        int cSide = getSide(point, c, a);
        return (aSide >= 0 && bSide >= 0 && cSide >= 0) || (aSide < 0 && bSide < 0 && cSide < 0);
    }

    //уравнение стороны треугольника
    private int getSide(ScreenPoint point, ScreenPoint p1, ScreenPoint p2) {
        return (p1.getJ() - p2.getJ()) * point.getI()
                + (p2.getI() - p1.getI()) * point.getJ()
                + (p1.getI() * p2.getJ() - p2.getI() * p1.getJ());
    }

    //верхняя левая точка прямоугольника, описывающего треугольник
    private ScreenPoint topLeft(ScreenPoint point1, ScreenPoint point2, ScreenPoint point3) {
        int xMin = Math.min(point1.getI(), Math.min(point2.getI(), point3.getI()));
        int yMin = Math.min(point1.getJ(), Math.min(point2.getJ(), point3.getJ()));
        return new ScreenPoint(xMin, yMin);
    }

    //нижняя правая точка прямоугольника, описывающего треугольник
    private ScreenPoint bottomRight(ScreenPoint point1, ScreenPoint point2, ScreenPoint point3) {
        int xMax = Math.max(point1.getI(), Math.max(point2.getI(), point3.getI()));
        int yMax = Math.max(point1.getJ(), Math.max(point2.getJ(), point3.getJ()));
        return new ScreenPoint(xMax, yMax);
    }

    public LineDrawer getLineDrawer() {
        return lineDrawer;
    }

    public void setLineDrawer(LineDrawer lineDrawer) {
        this.lineDrawer = lineDrawer;
    }

    @Override
    public PixelDrawer getPixelDrawer() {
        return pixelDrawer;
    }

    public void setPixelDrawer(PixelDrawer pixelDrawer) {
        this.pixelDrawer = pixelDrawer;
    }
}
