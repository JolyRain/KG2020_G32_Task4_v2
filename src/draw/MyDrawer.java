package draw;

import rasterization.pixelDrawers.PixelDrawer;
import rasterization.polygonDrawers.PolygonDrawer;
import screen.ScreenConverter;
import third.Light;
import third.Polygon;

import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class MyDrawer implements Drawer {
    private ScreenConverter screenConverter;
    private PolygonDrawer polygonDrawer;
    private Light light;


    public MyDrawer(ScreenConverter screenConverter, PolygonDrawer polygonDrawer, Light light) {
        this.screenConverter = screenConverter;
        this.polygonDrawer = polygonDrawer;
        this.light = light;
    }


    public ScreenConverter getScreenConverter() {
        return screenConverter;
    }

    public Light getLight() {
        return light;
    }


    @Override
    public void drawPolygons(Collection<Polygon> polygons) {
        List<Polygon> planes = new LinkedList<>();
        Filter<Polygon> filter = getPolygonFilter();
        for (Polygon polygon : polygons) {
            if (filter.permit(polygon))
                planes.add(polygon);
        }
        Polygon[] planesArray = planes.toArray(new Polygon[0]);
        Arrays.sort(planesArray, getPolygonComparator());
        for (Polygon polygon : planesArray) {
            drawPolygon(polygon);
        }
    }

    @Override
    public void clear(int color) {
        polygonDrawer.clear(color, screenConverter.getWs(), screenConverter.getHs());
    }

    /**
     * Метод, умеющий рсовать одну полилинию
     * @param polygon полилиния, которую требуется нарисовать
     */


    protected abstract void drawPolygon(Polygon polygon);


    /**
     * Должен возвращать фильтр рисуемых полилиний.
     * С помощью него можно оставить только те из них, которые следует рисовать.
     * Например, можно исключить те линии, которые находятся "позади"
     * @return фильтр
     */


    protected abstract Filter<Polygon> getPolygonFilter();

    /**
     * Должен возвращать компаратор полилиний для упорядочивания их.
     * @return компаратор
     */


    protected abstract Comparator<Polygon> getPolygonComparator();

    public PolygonDrawer getPolygonDrawer() {
        return polygonDrawer;
    }

    public void setPolygonDrawer(PolygonDrawer polygonDrawer) {
        this.polygonDrawer = polygonDrawer;
    }
}
