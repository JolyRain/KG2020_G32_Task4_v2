package rasterization.pixelDrawers;

import screen.ScreenPoint;

import java.awt.*;

public interface PixelDrawer {
    
    void drawPixel(int x, int y, Color color);

    boolean isVisiblePoint(ScreenPoint point);


}
