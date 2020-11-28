package rasterization.lineDrawers;

import screen.ScreenPoint;

import java.awt.*;

public interface LineDrawer {

    void drawLine(int x1, int y1, int x2, int y2, Color color);

    default int countStep(int delta) {
        return (delta > 0) ? 1 : -1;
    }


    default int swap(int first, int second) {
        return first;
    }


    default void drawLine(ScreenPoint point1, ScreenPoint point2, Color color) {
        drawLine(point1.getI(), point1.getJ(), point2.getI(), point2.getJ(), color);
    }
}
