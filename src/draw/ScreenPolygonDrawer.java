/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package draw;

import math.Vector3;
import rasterization.polygonDrawers.PolygonDrawer;
import screen.ScreenConverter;
import screen.ScreenPoint;
import third.Light;
import third.Polygon;

import java.awt.*;
import java.util.Comparator;

public class ScreenPolygonDrawer extends MyDrawer {

    public ScreenPolygonDrawer(ScreenConverter screenConverter, PolygonDrawer polygonDrawer, Light light) {
        super(screenConverter, polygonDrawer, light);
    }

    @Override
    protected void drawPolygon(Polygon polygon) {
        ScreenConverter screenConverter = getScreenConverter();
        PolygonDrawer polygonDrawer = getPolygonDrawer();
        /*переводим все точки в экранные*/
        ScreenPoint p1 = screenConverter.realToScreen(polygon.getPoint1());
        ScreenPoint p2 = screenConverter.realToScreen(polygon.getPoint2());
        ScreenPoint p3 = screenConverter.realToScreen(polygon.getPoint3());
        //освещение
        Light light = getLight();
        Vector3 lightPosition = light.getPosition();

        Vector3 lightDir = lightPosition.minus(polygon.getPoint1()).normalize(); //вектор между источником света и полигоном
        light.setDiffuse(polygon.getNormal(), lightDir); // коэффициент диффузного освещения
        Color polygonColor = polygon.getColor();
        Color newColor = light.getObjectColor(polygonColor);

        if (polygon.isLine())
            polygonDrawer.drawPolygon(p1, p2, p3, polygon.getColor());
        else {
            polygonDrawer.fillPolygon(p1,p2,p3, newColor);
            polygonDrawer.drawPolygon(p1, p2, p3, Color.BLACK);

        }
    }


    /**
     * В данной реализации возвращаем фильтр, который одобряет все полилинии.
     *
     * @return фильтр полилиний
     */

    @Override
    protected Filter<Polygon> getPolygonFilter() {
        return new Filter<Polygon>() {
            @Override
            public boolean permit(Polygon value) {
                return true;
            }
        };
    }

    /**
     * Сравниваем полилинии по среднему Z.
     *
     * @return компаратор
     */

    @Override
    protected Comparator<Polygon> getPolygonComparator() {
        return new Comparator<Polygon>() {
            private static final float EPSILON = 1e-10f;

            @Override
            public int compare(Polygon o1, Polygon o2) {
                float d = o1.avgZ() - o2.avgZ();
                if (-EPSILON < d && d < EPSILON)
                    return 0;
                return d < 0 ? -1 : 1;
            }
        };
    }
}
