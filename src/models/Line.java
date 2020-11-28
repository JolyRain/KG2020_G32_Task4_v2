/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import math.Vector3;
import third.IModel;
import third.PolyLine;
import third.Polygon;

/**
 * Описывает трёхмерный отрезок
 * @author Alexey
 */
public class Line implements IModel {
    private Vector3 p1, p2;
    private Color color = Color.BLACK;

    public Line(Vector3 p1, Vector3 p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Line(Vector3 p1, Vector3 p2, Color color) {
        this.p1 = p1;
        this.p2 = p2;
        this.color = color;
    }

    @Override
    public List<PolyLine> getLines() {
        return Collections.singletonList(new PolyLine(Arrays.asList(p1, p2), color, false));
    }

    @Override
    public List<Polygon> getPolygons() {
        return Collections.singletonList(new Polygon(p2,p2,p1, color));
    }

    @Override
    public Color getColor() {
        return color;
    }
}
