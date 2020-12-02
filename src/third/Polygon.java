package third;

import math.Vector3;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Polygon {
    private Vector3 point1;
    private Vector3 point2;
    private Vector3 point3;
    private Color color;

    public Polygon(Vector3 point1, Vector3 point2, Vector3 point3, Color color) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
        this.color = color;
    }

    public Polygon() {
    }

    public Polygon(Vector3 point1, Vector3 point2, Vector3 point3) {
        this.point1 = point1;
        this.point2 = point2;
        this.point3 = point3;
    }

    public Vector3 getNormal() {
        Vector3 u = point3.minus(point1);   //вычисляем вектора лежащие на плоскости
        Vector3 v = point2.minus(point1);
        Vector3 w = point2.minus(point3);
        return (u.getNormal(v).plus(u.getNormal(w).plus(v.getNormal(w)))).mul(1f / 3f); //находим нормаль полигона
    }

    public boolean isInnerPoint(Vector3 point) {
        Vector3 normal = getNormal();
        float a = normal.getX(), b = normal.getY(), c = normal.getZ();
        return a * point.getX() + b * point.getY() + c * point.getZ() == 0;
    }

    public float avgZ() {
        List<Vector3> points = getBorder().getPoints();
        if (points == null || points.size() == 0)
            return 0;
        float sum = 0;
        for (Vector3 v : points)
            sum += v.getZ();
        return sum / points.size();
    }

    public float avgX() {
        List<Vector3> points = getBorder().getPoints();
        if (points == null || points.size() == 0)
            return 0;
        float sum = 0;
        for (Vector3 v : points)
            sum += v.getX();
        return sum / points.size();
    }

    public float avgY() {
        List<Vector3> points = getBorder().getPoints();
        if (points == null || points.size() == 0)
            return 0;
        float sum = 0;
        for (Vector3 v : points)
            sum += v.getY();
        return sum / points.size();
    }

    public float getMaxX() {
        return Math.max(point1.getX(), Math.max(point2.getX(), point3.getX()));
    }

    public float getMaxY() {
        return Math.max(point1.getY(), Math.max(point2.getY(), point3.getY()));

    }

    public float getMaxZ() {
        return Math.max(point1.getZ(), Math.max(point2.getZ(), point3.getZ()));

    }

    public float getMinX() {
        return Math.min(point1.getX(), Math.min(point2.getX(), point3.getX()));
    }

    public float getMinY() {
        return Math.min(point1.getY(), Math.min(point2.getY(), point3.getY()));

    }

    public float getMinZ() {
        return Math.min(point1.getZ(), Math.min(point2.getZ(), point3.getZ()));

    }

    public boolean isLine() {
        return point1.equals(point2) || (point1.equals(point3) || point2.equals(point3));
    }

    public float avg() {
        return (avgX() + avgY() + avgZ());
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public PolyLine getBorder() {
        return new PolyLine(Arrays.asList(point1, point2, point3), color, true);
    }

    public Vector3 getPoint1() {
        return point1;
    }

    public void setPoint1(Vector3 point1) {
        this.point1 = point1;
    }

    public Vector3 getPoint2() {
        return point2;
    }

    public void setPoint2(Vector3 point2) {
        this.point2 = point2;
    }

    public Vector3 getPoint3() {
        return point3;
    }

    public void setPoint3(Vector3 point3) {
        this.point3 = point3;
    }
}
