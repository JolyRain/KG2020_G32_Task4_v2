package rasterization.lineDrawers;


import java.awt.*;

public class GraphicsLineDrawer implements LineDrawer {
    private Graphics graphics;

    public GraphicsLineDrawer(Graphics graphics) {
        this.graphics = graphics;
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2, Color color) {
        graphics.setColor(color);
        graphics.drawLine(x1, y1, x2, y2);
    }

}
