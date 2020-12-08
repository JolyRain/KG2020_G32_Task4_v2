package rasterization.polygonDrawers;

import rasterization.pixelDrawers.GraphicsPixelDrawer;
import rasterization.pixelDrawers.PixelDrawer;
import screen.ScreenCoordinates;
import screen.ScreenPoint;
import screen.ScreenPolygon;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class GraphicsPolygonDrawer implements PolygonDrawer {
    private Graphics2D graphics2D;

    public GraphicsPolygonDrawer(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
    }

    @Override
    public void drawPolygon(ScreenPolygon screenPolygon) {
        ScreenCoordinates screenCoordinates = getScreenCoordinates(screenPolygon);
        graphics2D.setColor(screenPolygon.getColor());
        graphics2D.drawPolygon(screenCoordinates.getXx(), screenCoordinates.getYy(), screenCoordinates.size());
    }

    @Override
    public void fillPolygon(ScreenPolygon screenPolygon) {
        ScreenCoordinates screenCoordinates = getScreenCoordinates(screenPolygon);
        graphics2D.setColor(screenPolygon.getColor());
        graphics2D.fillPolygon(screenCoordinates.getXx(), screenCoordinates.getYy(), screenCoordinates.size());
    }

    private ScreenCoordinates getScreenCoordinates(ScreenPolygon screenPolygon) {
        List<ScreenPoint> points = new LinkedList<>();
        points.add(screenPolygon.getScreenPoint1());
        points.add(screenPolygon.getScreenPoint2());
        points.add(screenPolygon.getScreenPoint3());
        return new ScreenCoordinates(points);
    }

    @Override
    public PixelDrawer getPixelDrawer() {
        return new GraphicsPixelDrawer(graphics2D);
    }

    public void clear(int color, int ws, int hs) {
        Color c = graphics2D.getColor();
        graphics2D.setColor(new Color(color));
        graphics2D.fillRect(0, 0, ws, hs);
        graphics2D.setColor(c);
    }

    public Graphics2D getGraphics2D() {
        return graphics2D;
    }

    public void setGraphics2D(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
    }
}
