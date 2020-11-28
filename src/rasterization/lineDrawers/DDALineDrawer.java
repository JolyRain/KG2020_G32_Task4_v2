package rasterization.lineDrawers;

import rasterization.pixelDrawers.PixelDrawer;

import java.awt.*;

public class DDALineDrawer implements LineDrawer {

    private PixelDrawer pixelDrawer;

    public DDALineDrawer(PixelDrawer pixelDrawer) {
        this.pixelDrawer = pixelDrawer;
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2, Color color) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        if (Math.abs(dx) > Math.abs(dy)) {
            double k = dy / dx;
            if (x1 > x2) {
                x1 = swap(x2, x2 = x1);
                y1 = swap(y2, y2 = y1);
            }
            for (int j = x1; j < x2; j++) {
                double i = k * (j - x1) + y1;
                pixelDrawer.drawPixel(j, (int) i, color);
            }
        } else {
            double kObr = dx / dy;
            if (y1 > y2) {
                x1 = swap(x2, x2 = x1);
                y1 = swap(y2, y2 = y1);
            }
            for (int i = y1; i <= y2; i++) {
                double j = kObr * (i - y1) + x1;
                pixelDrawer.drawPixel((int) j, i, color);
            }
        }
    }


}

