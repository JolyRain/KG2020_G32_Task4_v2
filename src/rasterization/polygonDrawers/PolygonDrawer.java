package rasterization.polygonDrawers;

import rasterization.pixelDrawers.PixelDrawer;
import screen.ScreenPoint;

import java.awt.*;

public interface PolygonDrawer {

    void drawPolygon(ScreenPoint point1, ScreenPoint point2, ScreenPoint point3, Color color);

    void fillPolygon(ScreenPoint point1, ScreenPoint point2, ScreenPoint point3, Color color);

    PixelDrawer getPixelDrawer();
}
