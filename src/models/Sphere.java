package models;

import math.Vector3;
import third.IModel;
import third.Polygon;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.*;


public class Sphere implements IModel {

    private float radius;
    private final float APPROXIMATION = 12;
    private Vector3 center = new Vector3(0, 0, 0);
    private Color color;

    public Sphere(float radius, Vector3 center) {
        this.radius = radius;
        this.center = center;
    }

    public Sphere(float radius) {
        this.radius = radius;
    }

    public Sphere(float radius, Vector3 center, Color color) {
        this.radius = radius;
        this.center = center;
        this.color = color;
    }

    private float getLevelRadius(float level) {
        float cathetus = center.getZ() - level;
        return (float) sqrt(radius * radius - cathetus * cathetus);
    }

    @Override
    public List<Polygon> getPolygons() {
        List<Polygon> polygons = new LinkedList<>();
        float x, y, step;
        float x1, y1, nextAlpha, nextZ;

        step = (float) ((float) 2 * PI / APPROXIMATION);
        float topLevel = center.getZ() + radius;
        float bottomLevel = center.getZ() - radius;
        float levelStep = 2 * radius / (APPROXIMATION);

        Vector3 p1, p2, p3, p4;
        for (float level = topLevel - levelStep / 2; level > bottomLevel + levelStep; level -= levelStep) {
            float levelRadius = getLevelRadius(level);
            float nextLevelRadius = getLevelRadius(level - levelStep);
            for (float alpha = 0; alpha < 2 * PI - step; alpha += step) {
                nextAlpha = alpha + step;
                nextZ = level - levelStep;

                x = getX(levelRadius, alpha); //current level point
                y = getY(levelRadius, alpha);

                x1 = getX(nextLevelRadius, alpha); //next level point
                y1 = getY(nextLevelRadius, alpha);

                p1 = new Vector3(x, y, level);
                p2 = new Vector3(x1, y1, nextZ);

                x = getX(levelRadius, nextAlpha);  //current level next point
                y = getY(levelRadius, nextAlpha);

                p3 = (new Vector3(x, y, level));
                polygons.add(new Polygon(p1, p2, p3, color));

                x1 = getX(nextLevelRadius, nextAlpha); //next level next point
                y1 = getY(nextLevelRadius, nextAlpha);

                p4 = new Vector3(x1, y1, nextZ);
                polygons.add(new Polygon(p2, p4, p3, color));
            }
        }
        polygons.addAll(getPoles(topLevel, step, levelStep));
        polygons.addAll(getPoles(bottomLevel, step, -levelStep));

        return polygons;
    }

    @Override
    public Color getColor() {
        return color;
    }

    private List<Polygon> getPoles(float level, float step, float levelStep) {
        List<Polygon> polygons = new LinkedList<>();
        float x, y;
        float x1, y1;
        Vector3 p1, p2, p3;
        levelStep /= 2;
        float levelRadius = getLevelRadius(level);
        float nextLevelRadius = getLevelRadius(level - levelStep);
        for (float alpha = 0; alpha < 2 * PI - step; alpha += step) {

            x = getX(levelRadius, alpha); //current level point
            y = getY(levelRadius, alpha);
            p1 = new Vector3(x, y, level);

            x1 = getX(nextLevelRadius, alpha); //next level point
            y1 = getY(nextLevelRadius, alpha);
            p2 = new Vector3(x1, y1, level - levelStep);


            x1 = getX(nextLevelRadius, alpha + step); //next level next point
            y1 = getY(nextLevelRadius, alpha + step);
            p3 = new Vector3(x1, y1, level - levelStep);

            polygons.add(
                    level == center.getZ() + radius ?
                            new Polygon(p1, p2, p3, color) :
                            new Polygon(p1, p3, p2, color));
        }
        return polygons;
    }

    private float getX(float radius, float angle) {
        return (float) (radius * cos(angle) + center.getX());
    }

    private float getY(float radius, float angle) {
        return (float) (radius * sin(angle) + center.getY());
    }

    public Vector3 getCenter() {
        return center;
    }

    public void setCenter(Vector3 center) {
        this.center = center;
    }
}
