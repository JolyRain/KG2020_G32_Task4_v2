package models;

import math.Vector3;
import third.IModel;
import third.PolyLine;
import third.Polygon;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Triangle implements IModel {
    private Vector3 pointA;
    private Vector3 pointB;
    private Vector3 pointC;
    private Color color = Color.BLACK;

    public Triangle(Vector3 pointA, Vector3 pointB, Vector3 pointC) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.pointC = pointC;
    }

    public Triangle(Vector3 pointA, Vector3 pointB, Vector3 pointC, Color color) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.pointC = pointC;
        this.color = color;
    }

    public Triangle() {

    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public List<PolyLine> getLines() {
        List<PolyLine> lines = new LinkedList<>();
        lines.add(new PolyLine(Arrays.asList(pointA, pointB, pointC), color, true));
        return lines;
    }

    @Override
    public List<Polygon> getPolygons() {
        return Collections.singletonList(new Polygon(pointA, pointB, pointC, color));
    }

    public Vector3 getPointA() {
        return pointA;
    }

    public void setPointA(Vector3 pointA) {
        this.pointA = pointA;
    }

    public Vector3 getPointB() {
        return pointB;
    }

    public void setPointB(Vector3 pointB) {
        this.pointB = pointB;
    }

    public Vector3 getPointC() {
        return pointC;
    }

    public void setPointC(Vector3 pointC) {
        this.pointC = pointC;
    }
}
