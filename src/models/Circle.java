package models;

import math.Vector3;
import third.IModel;
import third.PolyLine;
import third.Polygon;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.*;

public class Circle implements IModel {
    private Vector3 center;
    private float radius;
    private Color color = Color.RED;


    public Circle(Vector3 center, float radius, Color color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

    public Circle(Vector3 center, float radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public List<Polygon> getPolygons() {
        List<Polygon> polygons = new LinkedList<>();

        float step = (float) (PI / 24);
        float x,y, nextX, nextY;

        for (float alpha = 0; alpha < 2 * PI; alpha += step) {
            x = (float) (radius * cos(alpha)) + center.getX();
            y = (float) (radius * sin((alpha))) + center.getY();

            nextX = (float) (radius * cos(alpha + step)) + center.getX();
            nextY = (float) (radius * sin((alpha + step))) + center.getY();

            polygons.add(new Polygon(center, new Vector3(x,y,0), new Vector3(nextX,nextY,0), color));
        }

        return polygons;
    }

    @Override
    public Color getColor() {
        return color;
    }
}
