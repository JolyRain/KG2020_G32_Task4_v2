package rasterization.polygonDrawers;

import rasterization.lineDrawers.LineDrawer;
import rasterization.pixelDrawers.PixelDrawer;
import screen.ScreenPoint;

import java.awt.*;

public class OtherPolygonDrawer implements PolygonDrawer {
    private PixelDrawer pixelDrawer;
    private LineDrawer lineDrawer;

    public OtherPolygonDrawer(PixelDrawer pixelDrawer, LineDrawer lineDrawer) {
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
        if (point2.getJ() < point1.getJ()) point2 = swap(point1, point1 = point2);
        if (point3.getJ() < point1.getJ()) point3 = swap(point1, point1 = point3);
        if (point3.getJ() < point2.getJ()) point3 = swap(point2, point2 = point3);

    }

    private ScreenPoint swap(ScreenPoint first, ScreenPoint second) {
        return first;
    }

    @Override
    public PixelDrawer getPixelDrawer() {
        return pixelDrawer;
    }
}
