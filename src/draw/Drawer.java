/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package draw;

import java.util.Collection;

import third.PolyLine;
import third.Polygon;

/**
 * Интерфейс, описывающий в общем виде процесс рисования полилинии
 * @author Alexey
 */
public interface Drawer {
    /**
     * Очищает область заданным цветом
     * @param color цвет
     */
    void clear(int color);


    void drawPolygons(Collection<Polygon> polygons);

}
