/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package third;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import draw.Drawer;
import math.Vector3;
import models.Line;

/**
 * Описывает трёхмерную со всеми объектами на ней
 * @author Alexey
 */
public class Scene {
    private List<IModel> models = new ArrayList<>();

    public List<IModel> getModelsList() {
        return models;
    }
    
    private int backgroundColor;

    /**
     * Создаём сцену с заданным фоном
     * @param backgroundColorRGB цвет фона.
     */
    public Scene(int backgroundColorRGB) {
        this.backgroundColor = backgroundColorRGB;
        this.showAxes = false;
    }
    
    private boolean showAxes;

    public boolean isShowAxes() {
        return showAxes;
    }

    public void setShowAxes(boolean showAxis) {
        this.showAxes = showAxis;
    }
    
    public void showAxes() {
        this.showAxes = true;
    }
    
    public void hideAxes() {
        this.showAxes = false;
    }
    

    private static final List<Line> axes = Arrays.asList(
            new Line(new Vector3(0, 0, 0), new Vector3(100, 0, 0), Color.RED),
            new Line(new Vector3(0, 0, 0), new Vector3(0, 100, 0), Color.GREEN),
            new Line(new Vector3(0, 0, 0), new Vector3(0, 0, 100), Color.BLUE)
    );
    /**
     * Рисуем сцену со всеми моделями
     * @param drawer то, с помощью чего будем рисовать
     * @param cam камера для преобразования координат
     */

    public void drawScene(Drawer drawer, ICamera cam) {
        List<Polygon> polygons = new LinkedList<>();
        LinkedList<Collection<? extends IModel>> allModels = new LinkedList<>();
        allModels.add(models);
        /*Если требуется, то добавляем оси координат*/
        if (isShowAxes())
            allModels.add(axes);

        /*перебираем все полилинии во всех моделях*/
        for (Collection<? extends IModel> mc : allModels)
            for (IModel m : mc) {
                for (Polygon polygon : m.getPolygons()) {
                    /*Все точки конвертируем с помощью камеры*/
                    List<Vector3> points = new LinkedList<>();
                    points.add(cam.worldToScreen(polygon.getPoint1()));
                    points.add(cam.worldToScreen(polygon.getPoint2()));
                    points.add(cam.worldToScreen(polygon.getPoint3()));

                    /*Создаём на их сонове новые полилинии, но в том виде, в котором их видит камера*/
                    polygons.add(new Polygon(points.get(0),points.get(1),points.get(2), polygon.getColor()));
                }
            }
        /*Закрашиваем фон*/
        drawer.clear(backgroundColor);
        /*Рисуем все линии*/
        drawer.drawPolygons(polygons);
    }
}
