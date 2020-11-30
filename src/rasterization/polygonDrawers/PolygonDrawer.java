package rasterization.polygonDrawers;

import rasterization.pixelDrawers.PixelDrawer;
import screen.ScreenPoint;
import screen.ScreenPolygon;

import java.awt.*;

public interface PolygonDrawer {

    void drawPolygon(ScreenPolygon screenPolygon);

    void fillPolygon(ScreenPolygon screenPolygon);

    PixelDrawer getPixelDrawer();
}
