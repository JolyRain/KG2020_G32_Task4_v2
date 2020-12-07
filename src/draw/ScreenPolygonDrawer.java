/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package draw;

import math.Vector3;
import rasterization.polygonDrawers.PolygonDrawer;
import screen.ScreenConverter;
import screen.ScreenPoint;
import screen.ScreenPolygon;
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
        Color newColor = getNewColor(polygon);
        ScreenPolygon screenPolygon = screenConverter.realToScreen(polygon);
        screenPolygon.setColor(newColor);
        Filter<ScreenPolygon> filter = getScreenPolygonFilter();
        if (filter.permit(screenPolygon)) {
            if (polygon.isLine()) {
                screenPolygon.setColor(polygon.getColor());
                polygonDrawer.drawPolygon(screenPolygon);
            } else {
                polygonDrawer.fillPolygon(screenPolygon);
//                screenPolygon.setColor(Color.BLACK);
//                polygonDrawer.drawPolygon(screenPolygon);
            }
        }
    }

    private Color getNewColor(Polygon polygon) {
        Light light = getLight();
        Vector3 lightPosition = light.getPosition();
        Vector3 lightDir = lightPosition.minus(polygon.getPoint1()).normalize(); //вектор между источником света и полигоном
        light.setDiffuse(polygon.normal(), lightDir); // коэффициент диффузного освещения
        Color polygonColor = polygon.getColor();
        return light.getObjectColor(polygonColor);
    }

    private Filter<ScreenPolygon> getScreenPolygonFilter() {
        return new Filter<ScreenPolygon>() {
            @Override
            public boolean permit(ScreenPolygon value) {
                   return isValidPoint(value.getScreenPoint1())
                           || isValidPoint(value.getScreenPoint2())
                           || isValidPoint(value.getScreenPoint3());
            }
            private boolean isValidPoint(ScreenPoint point) {
                return point.getI() >= 0
                        && point.getJ() >= 0
                        && point.getI() <= getScreenConverter().getWs()
                        && point.getJ() <= getScreenConverter().getHs();
            }
        };
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
            public boolean permit(Polygon polygon) {
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
