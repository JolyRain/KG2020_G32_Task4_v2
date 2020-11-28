/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package third;

import java.awt.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import math.Vector3;

/**
 * Полилиния в трёхмерном пространстве.
 * Описывает ломанную в трёхмерном пространстве по опорным точкам
 * @author Alexey
 */
public class PolyLine {
    private List<Vector3> points;
    private boolean closed;
    private Color color;

    /**
     * Создаёт новую полилинию на основе трёхмерных точек.
     * @param points список точек-вершин ломанной
     * @param closed признак замкнутостит линии
     */
    public PolyLine(Collection<Vector3> points, Color color, boolean closed) {
        this.points = new LinkedList<>(points);
        this.closed = closed;
        this.color = color;
    }

    public Color getColor() {
        return color;

    }

    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Признак закрытости
     * @return возвращает истину, если линия замкнута, иначе - ложь.
     */
    public boolean isClosed() {
        return closed;
    }

    /**
     * Список вершин линии
     * @return возвращает список точек.
     */
    public List<Vector3> getPoints() {
        return points;
    }
    
    /**
     * Вычисляет среднее арифметическое по оси Z.
     * @return среднее по Z для полилинии.
     */
    public float avgZ() {
        if (points == null || points.size() == 0)
            return 0;
        float sum = 0;
        for (Vector3 v : points)
            sum += v.getZ();
        return sum / points.size();
    }
    
}
