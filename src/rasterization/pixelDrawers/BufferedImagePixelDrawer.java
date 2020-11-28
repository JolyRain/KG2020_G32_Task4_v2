package rasterization.pixelDrawers;

import rasterization.buffer.DepthBuffer;
import screen.ScreenPoint;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BufferedImagePixelDrawer implements PixelDrawer {
    private BufferedImage bi;
    private DepthBuffer depthBuffer;

    public BufferedImagePixelDrawer(BufferedImage bi) {
        this.bi = bi;
    }

    @Override
    public void drawPixel(int x, int y, Color c) {
        if (x >= 0 && y >= 0 && x < bi.getWidth() && y < bi.getHeight()) {
            bi.setRGB(x, y, c.getRGB());
        }
    }

    @Override
    public boolean isVisiblePoint(ScreenPoint point) {
        return false;
    }


}
