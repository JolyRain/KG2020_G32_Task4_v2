package rasterization.polygonDrawers;

import rasterization.buffer.DepthBuffer;
import rasterization.lineDrawers.LineDrawer;
import rasterization.pixelDrawers.PixelDrawer;
import screen.ScreenPoint;

import java.awt.*;

public class MyPolygonDrawer implements PolygonDrawer {
    private PixelDrawer pixelDrawer;
    private LineDrawer lineDrawer;
//    private DepthBuffer depthBuffer;

//    public MyPolygonDrawer(PixelDrawer pixelDrawer, LineDrawer lineDrawer, DepthBuffer depthBuffer) {
//        this.pixelDrawer = pixelDrawer;
//        this.lineDrawer = lineDrawer;
//        this.depthBuffer = depthBuffer;
//    }

    public MyPolygonDrawer(PixelDrawer pixelDrawer, LineDrawer lineDrawer) {
        this.pixelDrawer = pixelDrawer;
        this.lineDrawer = lineDrawer;
    }

    @Override
    public void drawPolygon(ScreenPoint point1, ScreenPoint point2, ScreenPoint point3, Color color) {
        lineDrawer.drawLine(point1, point2, color);
        lineDrawer.drawLine(point2, point3, color);
        lineDrawer.drawLine(point3, point1, color);
    }

    @Override
    public void fillPolygon(ScreenPoint point1, ScreenPoint point2, ScreenPoint point3, Color color) {
        ScreenPoint topLeft = topLeft(point1, point2, point3);
        ScreenPoint bottomRight = bottomRight(point1, point2, point3);
        for (int y = topLeft.getJ(); y <= bottomRight.getJ(); y++) {
            for (int x = topLeft.getI(); x <= bottomRight.getI(); x++) {
                if (isInnerPoint(new ScreenPoint(x, y), point1, point2, point3)) {
                    pixelDrawer.drawPixel(x, y, color);
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
