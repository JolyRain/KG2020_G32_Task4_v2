/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package third;

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

    List<Polygon> getPolygons();

    Color getColor();

}
