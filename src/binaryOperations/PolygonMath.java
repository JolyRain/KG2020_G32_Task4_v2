package binaryOperations;

import math.Vector3;
import models.Line;
import third.Polygon;

public class PolygonMath implements IPolygonMath {

    private static final int EPSILON = 10000;

    @Override
    public Vector3 intersectPoint(Line line, Polygon polygon) {
        Vector3 dir = line.getP2().minus(line.getP1());
        Vector3 start = line.getP1();
        Vector3 n = polygon.normal();
        Vector3 p = polygon.getPoint1();
        float d = -(n.getX() * p.getX() + n.getY() * p.getY() + n.getZ() * p.getZ());
        float t = (-d - n.scalar(start)) / n.scalar(dir);
        if (t < 0) return null;
        return start.plus(dir.mul(t));
    }

    public float triangleSquare(float a, float b, float c) {
        float p = (a + b + c) / 2;
        return (float) Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    @Override
    public boolean isInsideTriangle(Vector3 point, Polygon polygon) {
        Vector3 a = polygon.getPoint1();
        Vector3 b = polygon.getPoint2();
        Vector3 c = polygon.getPoint3();

        float AB = sideLength(a, b);
        float BC = sideLength(b, c);
        float CA = sideLength(c, a);

        float AP = sideLength(a, point);
        float BP = sideLength(b, point);
        float CP = sideLength(c, point);

        float diff = (triangleSquare(AP, BP, AB)
                + triangleSquare(AP, CP, CA)
                + triangleSquare(BP, CP, BC))
                - triangleSquare(AB, BC, CA);

        return Math.abs(diff) < triangleSquare(AB, BC, CA) / EPSILON;
    }

    public float sideLength(Vector3 p1, Vector3 p2) {
        return p2.minus(p1).length();
    }
}
