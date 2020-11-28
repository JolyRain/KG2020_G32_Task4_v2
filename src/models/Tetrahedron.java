package models;

import math.Vector3;
import third.IModel;
import third.PolyLine;
import third.Polygon;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Tetrahedron implements IModel {

    private Vector3 topPoint;
    private Vector3 leftPoint;
    private Vector3 rightPoint;
    private Color color = Color.BLACK;

    @Override
    public Color getColor() {
        return color;
    }
    public Tetrahedron(Vector3 topPoint, Vector3 leftPoint, Vector3 rightPoint, Color color) {
        this.topPoint = topPoint;
        this.leftPoint = leftPoint;
        this.rightPoint = rightPoint;
        this.color = color;
    }

    public Tetrahedron(Vector3 topPoint, Vector3 leftPoint, Vector3 rightPoint) {
        this.topPoint = topPoint;
        this.leftPoint = leftPoint;
        this.rightPoint = rightPoint;
    }

    @Override
    public List<PolyLine> getLines() {
        List<PolyLine> lines =new LinkedList<>();
        Vector3 bottomPoint = new Vector3(topPoint.getX(), topPoint.getY(), topPoint.getZ() - leftPoint.getZ());
        lines.add(new PolyLine(Arrays.asList(topPoint, bottomPoint, leftPoint, rightPoint), color, true));
        return lines;
    }

    @Override
    public List<Polygon> getPolygons() {
        return null;
    }

}
