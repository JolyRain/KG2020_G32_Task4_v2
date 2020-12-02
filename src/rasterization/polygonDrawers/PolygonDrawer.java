package rasterization.polygonDrawers;

import rasterization.pixelDrawers.PixelDrawer;
import screen.ScreenPolygon;

public interface PolygonDrawer {

    void drawPolygon(ScreenPolygon screenPolygon);

    void fillPolygon(ScreenPolygon screenPolygon);

    PixelDrawer getPixelDrawer();
}
