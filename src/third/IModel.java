/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package third;

import javafx.scene.shape.Polyline;
import math.Vector3;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Описывает в общем виде любую модель
 *
 * @author Alexey
 */
public interface IModel {
    /**
     * Любая модель - это набор полилиний.
     *
     * @return Списко полилиний модели.
     */
    default List<PolyLine> getLines() {
        List<PolyLine> lines = new LinkedList<>();
        for (Polygon polygon : getPolygons()) {
            lines.add(polygon.getBorder());
        }
        return lines;
    }

    default List<Vector3> getPoints() {
        List<PolyLine> polyLines = getLines();
        List<Vector3> points = new LinkedList<>();
        for (PolyLine polyLine : polyLines) {
            points.addAll(polyLine.getPoints());
        }
        return points;
    }

    List<Polygon> getPolygons();

    Color getColor();

}
