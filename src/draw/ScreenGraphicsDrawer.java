/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package draw;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import screen.ScreenConverter;
import third.Light;
import third.PolyLine;
import third.Polygon;

/**
 * Рисовальщик на графиксе экрана
 * @author Alexey
 */
public abstract class ScreenGraphicsDrawer implements Drawer {
    private ScreenConverter screenConverter;
    private Graphics2D graphics;
    private Light light;

    /**
     * Создаёт экземпляр рисвальщика
     * @param screenConverter преобразователь координат
     * @param graphics графикс
     */
    public ScreenGraphicsDrawer(ScreenConverter screenConverter, Graphics2D graphics, Light light) {
        this.screenConverter = screenConverter;
        this.graphics = graphics;
        this.light = light;
    }

    public Graphics2D getGraphics() {
        return graphics;
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
        Graphics2D g = getGraphics();
        Color c = g.getColor();
        g.setColor(new Color(color));
        g.fillRect(0, 0, screenConverter.getWs(), screenConverter.getHs());
        g.setColor(c);
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

}
