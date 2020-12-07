/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package third;

import draw.Drawer;
import math.Vector3;
import models.Line;

import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * Описывает трёхмерную со всеми объектами на ней
 *
 * @author Alexey
 */
public class Scene {
    private static final List<Line> axes = Arrays.asList(
            new Line(new Vector3(0, 0, 0), new Vector3(100, 0, 0), Color.RED),
            new Line(new Vector3(0, 0, 0), new Vector3(0, 100, 0), Color.GREEN),
            new Line(new Vector3(0, 0, 0), new Vector3(0, 0, 100), Color.BLUE)
    );
    private List<IModel> models = new ArrayList<>();
    private BinaryOperations binaryOperations;
    private int backgroundColor;
    private boolean showAxes;
    private boolean showUnion;
    private boolean showIntersect;
    private boolean showSubtract;
    private IModel unionResult;
    private IModel intersectResult;
    private IModel subtractResult;
    /**
     * Создаём сцену с заданным фоном
     *
     * @param backgroundColorRGB цвет фона.
     */
    public Scene(int backgroundColorRGB) {
        this.backgroundColor = backgroundColorRGB;
        this.showAxes = false;
        binaryOperations = new BinaryOperations();
    }

    public void addModel1(IModel model) {
        models.remove(binaryOperations.getModel1());
        binaryOperations.setModel1(model);
        models.add(model);
        if (!binaryOperations.nullOperation()) {
            setResults();
        }
    }

    public void addModel2(IModel model) {
        models.remove(binaryOperations.getModel2());
        binaryOperations.setModel2(model);
        models.add(model);
        if (!binaryOperations.nullOperation()) {
            setResults();
        }
    }

    public void setResults() {
        unionResult = binaryOperations.union();
        intersectResult = binaryOperations.intersect();
        subtractResult = binaryOperations.subtract();
    }

    private void drop() {
        showIntersect = false;
        showSubtract = false;
        showUnion = false;
        models.clear();
//        models.removeIf(model ->
//                model.equals(intersectResult)
//                        || model.equals(unionResult)
//                        || model.equals(subtractResult));
    }

    public void setShowUnion(boolean showUnion) {
        this.showUnion = showUnion;
    }

    public void setShowIntersect(boolean showIntersect) {
        this.showIntersect = showIntersect;
    }

    public void setShowSubtract(boolean showSubtract) {
        this.showSubtract = showSubtract;
    }

    public List<IModel> getModelsList() {
        return models;
    }

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



    /**
     * Рисуем сцену со всеми моделями
     *
     * @param drawer то, с помощью чего будем рисовать
     * @param cam    камера для преобразования координат
     */

    public void drawScene(Drawer drawer, ICamera cam) {
        List<Polygon> polygons = new LinkedList<>();
        LinkedList<Collection<? extends IModel>> allModels = new LinkedList<>();
        if (showUnion && unionResult != null) {
            drop();
            models.add(unionResult);
        }
        if (showSubtract && subtractResult != null) {
            drop();
            models.add(subtractResult);
        }
        if (showIntersect && intersectResult != null) {
            drop();
            models.add(intersectResult);
        }

        allModels.add(models);
        /*Если требуется, то добавляем оси координат*/
        if (isShowAxes()) {
            allModels.add(axes);
        }
        /*перебираем все полилинии во всех моделях*/
        for (Collection<? extends IModel> mc : allModels)
            for (IModel m : mc) {
                if (m.getPolygons().size() == 0 || m.getPolygons() == null) continue;
                for (Polygon polygon : m.getPolygons()) {
                     /*Все точки конвертируем с помощью камеры*/
                    List<Vector3> points = new LinkedList<>();
                    points.add(cam.worldToScreen(polygon.getPoint1()));
                    points.add(cam.worldToScreen(polygon.getPoint2()));
                    points.add(cam.worldToScreen(polygon.getPoint3()));

                    /*Создаём на их сонове новые полилинии, но в том виде, в котором их видит камера*/
                    polygons.add(new Polygon(points.get(0), points.get(1), points.get(2), polygon.getColor()));
                }
            }
        /*Закрашиваем фон*/
        drawer.clear(backgroundColor);
        /*Рисуем все линии*/
        drawer.drawPolygons(polygons);
    }

    public void clear() {
        models.clear();
        intersectResult = null;
        unionResult = null;
        subtractResult = null;
        binaryOperations.clear();
    }

    public BinaryOperations getBinaryOperations() {
        return binaryOperations;
    }

    public void setBinaryOperations(BinaryOperations binaryOperations) {
        this.binaryOperations = binaryOperations;
    }
}
