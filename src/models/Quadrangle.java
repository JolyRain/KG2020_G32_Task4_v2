package models;

import math.Vector3;
import third.IModel;
import third.PolyLine;
import third.Polygon;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Quadrangle implements IModel {
    private Vector3 leftTopPoint;
    private Vector3 rightBottomPoint;
    private Color color = Color.BLACK;



    public Quadrangle(Vector3 leftTopPoint, Vector3 rightBottomPoint) {
        this.leftTopPoint = leftTopPoint;
        this.rightBottomPoint = rightBottomPoint;
    }

    public Quadrangle(Vector3 leftTopPoint, Vector3 rightBottomPoint, Color color) {
        this.leftTopPoint = leftTopPoint;
        this.rightBottomPoint = rightBottomPoint;
        this.color = color;
    }

    @Override
    public Color getColor() {
        return color;
    }
    @Override
    public List<PolyLine> getLines() {
        List<PolyLine> lines = new LinkedList<>();
        Vector3 rightTopPoint = new Vector3(rightBottomPoint.getX(), rightBottomPoint.getY(), leftTopPoint.getZ());
        Vector3 leftBottomPoint = new Vector3(leftTopPoint.getX(), leftTopPoint.getY(), rightBottomPoint.getZ());
        lines.add(new PolyLine(Arrays.asList(leftTopPoint, leftBottomPoint, rightBottomPoint, rightTopPoint), color, true));

        return lines;
    }

    @Override
    public List<Polygon> getPolygons() {
        List<Polygon> polygons = new LinkedList<>();
        Vector3 rightTopPoint = new Vector3(rightBottomPoint.getX(), leftTopPoint.getY(), leftTopPoint.getZ());
        Vector3 leftBottomPoint = new Vector3(leftTopPoint.getX(), rightBottomPoint.getY(), rightBottomPoint.getZ());
        polygons.add(new Polygon(leftTopPoint, leftBottomPoint, rightTopPoint, color));
        polygons.add(new Polygon(rightBottomPoint, leftBottomPoint, rightTopPoint, color));
        return polygons;
    }

    private float offset(float dx) {
        return dx > 0 ? -0.01f : 0.01f;
    }

}
