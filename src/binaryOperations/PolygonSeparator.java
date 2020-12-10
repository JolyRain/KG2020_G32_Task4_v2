package binaryOperations;

import math.Vector3;
import models.Line;
import third.Polygon;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PolygonSeparator {

    private PolygonMath polygonMath = new PolygonMath();
    private Case aCase;
    private List<Vector3> intersectPoints = new LinkedList<>();
    private List<Vector3> innerPoints = new LinkedList<>();
    private List<Vector3> allPoints = new LinkedList<>();

    public List<Polygon> separate(Polygon mainPolygon, Polygon intersectPolygon) {
        clear();
        allPoints = intersectPoints(mainPolygon, intersectPolygon);
//        if (allPoints.size() == 0) return null;
//        if (!(innerPoints.size() != 1 && innerPoints.size() != 2)) return null;
        if (innerPoints.size() == 2) aCase = Case.ALL_INNERS;
        if (innerPoints.size() == 1 && intersectPoints.size() <=2) aCase = Case.ONE_INNER; //случай когда inner = 1, intersect = 2 не обработан; (допустим <= 2 работает)
        if (intersectPoints.size() <=3 && innerPoints.size() == 0) aCase = Case.ALL_OUTSIDE; //некорректный
        if (aCase == null) {
            return null;
        }
        switch (aCase) {
            case ALL_INNERS:
                return allInners(mainPolygon, intersectPolygon);
            case ONE_INNER:
                return oneInner(mainPolygon, intersectPolygon);
            case ALL_OUTSIDE:
                return allOutside(mainPolygon);
        }
        return null;
    }

    private List<Polygon> allOutside(Polygon mainPolygon) {


        Vector3 outsidePoint1 = intersectPoints.get(0);
        Vector3 outsidePoint2 = intersectPoints.get(1);

        Line intersectLine = new Line(outsidePoint1, outsidePoint2);

        Line nearSide1 = nearSide(outsidePoint1, mainPolygon);
        Line nearSide2 = nearSide(outsidePoint2, mainPolygon);

        Vector3 newPoint1 = intersectPoint(intersectLine, nearSide1);
        Vector3 newPoint2 = intersectPoint(intersectLine, nearSide2);

        Vector3 newMainPoint = generalPoint(nearSide1, nearSide2);

        Vector3 i = nearSide1.getP1().equals(newMainPoint) ? nearSide1.getP2() : nearSide1.getP1();
        Vector3 j = nearSide2.getP1().equals(newMainPoint) ? nearSide2.getP2() : nearSide2.getP1();


        return new LinkedList<>(Arrays.asList(
                new Polygon(newMainPoint, newPoint1, newPoint2, Color.GREEN),
                new Polygon(newPoint1, i, j, Color.GREEN),
                new Polygon(newPoint1, newPoint2, j, Color.GREEN)));
    }

    //исправить на методику с нормалью и положение точки
    private Line nearSide(Vector3 outsidePoint, Polygon polygon) {
        List<Line> lines = lines(polygon);

        float maxAngle = Float.MIN_VALUE;
        Line nearSide = lines.get(0);
        for (Line line : lines) {
            Vector3 vector1 = line.getP1().minus(outsidePoint);
            Vector3 vector2 = line.getP2().minus(outsidePoint);
            float angle = vector1.angle(vector2);
            if (Math.abs(angle) > maxAngle) {
                maxAngle = angle;
                nearSide = line;
            }
        }
        return nearSide;
    }

    private Vector3 generalPoint(Line line1, Line line2) {
        return line1.getP1().equals(line2.getP1()) ? line1.getP1() : line2.getP2();
    }

    private List<Polygon> allInners(Polygon mainPolygon, Polygon intersectPolygon) {

        if (newMainPoint(mainPolygon, intersectPolygon) == null) return null;
        Vector3 newPointMain = newMainPoint(mainPolygon, intersectPolygon);
        Vector3 newPoint2 = innerPoints.get(0);
        Vector3 newPoint3 = innerPoints.get(1);

        Line line1 = new Line(newPointMain, newPoint2);
        Line line2 = new Line(newPointMain, newPoint3);

        Vector3 mainPoint1 = null, mainPoint2 = null;
        if (!mainPolygon.getPoint1().equals(newPointMain)) {
            mainPoint1 = mainPolygon.getPoint1();
            mainPoint2 = !mainPolygon.getPoint2().equals(newPointMain) ? mainPolygon.getPoint2() : mainPolygon.getPoint3();
        } else if (!mainPolygon.getPoint2().equals(newPointMain)) {
            mainPoint1 = mainPolygon.getPoint2();
            mainPoint2 = mainPolygon.getPoint3();
        }

        Line intersectLine = new Line(mainPoint1, mainPoint2);

        Vector3 i = intersectPoint(line1, intersectLine);

        Vector3 j = intersectPoint(line2, intersectLine);

        return new LinkedList<>(Arrays.asList(
                new Polygon(newPointMain, newPoint2, newPoint3, Color.GREEN),
                new Polygon(newPoint3, j, i, Color.GREEN),
                new Polygon(newPoint2, i, newPoint3, Color.GREEN)));
    }

    private void clear() {
        allPoints.clear();
        innerPoints.clear();
        intersectPoints.clear();
        aCase = null;
    }

    private List<Polygon> oneInner(Polygon mainPolygon, Polygon intersectPolygon) {

            Vector3 newPoint1 = innerPoints.get(0);
            Vector3 intersectPoint = intersectPoints.get(0);

            Vector3 newPointMain = newMainPoint(mainPolygon, intersectPolygon); //возможный проблемный момент (неправильно определяется точка)
            Line intersectLine = new Line(newPoint1, intersectPoint);

            Line nearSide = nearSide(intersectPoint, mainPolygon);

            Vector3 newPoint2 = intersectPoint(intersectLine, nearSide);

            Line line1 = new Line(newPointMain, newPoint1);
            Vector3 mainPoint1 = null, mainPoint2 = null;
            if (!mainPolygon.getPoint1().equals(newPointMain)) {
                mainPoint1 = mainPolygon.getPoint1();
                mainPoint2 = !mainPolygon.getPoint2().equals(newPointMain) ? mainPolygon.getPoint2() : mainPolygon.getPoint3();
            } else if (!mainPolygon.getPoint2().equals(newPointMain)) {
                mainPoint1 = mainPolygon.getPoint2();
                mainPoint2 = mainPolygon.getPoint3();
            }
            Line farLine = new Line(mainPoint1, mainPoint2);

            Vector3 i = intersectPoint(farLine, line1);
            Vector3 j = !nearSide.getP1().equals(newPointMain) ? nearSide.getP1() : nearSide.getP2();

            return new LinkedList<>(Arrays.asList(
                    new Polygon(newPointMain, newPoint2, newPoint1, Color.GREEN),
                    new Polygon(newPoint1, i, j, Color.GREEN),
                    new Polygon(newPoint1, newPoint2, j, Color.GREEN)));
    }


    public List<Vector3> intersectPoints(Polygon mainPolygon, Polygon intersectPolygon) {
        List<Line> lines = lines(intersectPolygon);
        for (Line line : lines) {
            Vector3 intersectPoint = polygonMath.intersectPoint(line, mainPolygon);
            if (intersectPoint == null) continue;
            if (polygonMath.isInsideTriangle(intersectPoint, mainPolygon))
                innerPoints.add(intersectPoint);
            else intersectPoints.add(intersectPoint);
        }

        if (intersectPoints.size() == 3) {
//            intersectPoints.removeIf(point -> !polygonMath.isInsideTriangle(point, intersectPolygon));
        }
        allPoints.addAll(innerPoints);
        allPoints.addAll(intersectPoints);
//        for (Vector3 point : allPoints) {
//            if (!polygonMath.isInsideTriangle(point, intersectPolygon)) {
//                allPoints.clear();
//            }
//        }
        return allPoints;
    }
    private Vector3 farPoint(Vector3 innerPoint, List<Vector3> intersectPoints) {
        float maxLength = Float.MIN_VALUE;
        Vector3 farPoint = null;
        for (Vector3 point : intersectPoints) {
            float length = point.minus(innerPoint).length();
            if (length > maxLength) {
                maxLength = length;
                farPoint = point;
            }
        }
        return farPoint;
    }

    public Vector3 intersectPoint(Line line1, Line line2) {
        Vector3 p1 = line1.getP1(), p2 = line1.getP2();
        Vector3 p3 = line2.getP1(), p4 = line2.getP2();

        float d1343 = d(p1, p3, p4, p3),
                d4321 = d(p4, p3, p2, p1),
                d1321 = d(p1, p3, p2, p1),
                d4343 = d(p4, p3, p4, p3),
                d2121 = d(p2, p1, p2, p1);

        float mu = (d1343 * d4321 - d1321 * d4343) / (d2121 * d4343 - d4321 * d4321);
        return p1.plus(p2.minus(p1).mul(mu));
    }

    //    http://paulbourke.net/geometry/pointlineplane/
    private float d(Vector3 m, Vector3 n, Vector3 o, Vector3 p) {
        float xm = m.getX(), ym = m.getY(), zm = m.getZ();
        float xn = n.getX(), yn = n.getY(), zn = n.getZ();
        float xo = o.getX(), yo = o.getY(), zo = o.getZ();
        float xp = p.getX(), yp = p.getY(), zp = p.getZ();
        return (xm - xn) * (xo - xp) + (ym - yn) * (yo - yp) + (zm - zn) * (zo - zp);
    }

    //ищем точку из которой будут испускаться отрезки внутри полигона


    private Vector3 newMainPoint(Polygon mainPolygon, Polygon intersectPolygon) {
        float sign1 = polygonMath.getPointSign(mainPolygon.getPoint1(), intersectPolygon);
        float sign2 = polygonMath.getPointSign(mainPolygon.getPoint2(), intersectPolygon);
        float sign3 = polygonMath.getPointSign(mainPolygon.getPoint3(), intersectPolygon);

        if (sign1 != sign2 && sign1 != sign3) return mainPolygon.getPoint1();
        if (sign2 != sign1 && sign2 != sign3) return mainPolygon.getPoint2();
        if (sign3 != sign1 && sign3 != sign2) return mainPolygon.getPoint3();
        return null;
    }


    public List<Line> lines(Polygon polygon) {
        List<Line> lines = new LinkedList<>();
        lines.add(new Line(polygon.getPoint1(), polygon.getPoint2())); //ab
        lines.add(new Line(polygon.getPoint2(), polygon.getPoint3())); //bc
        lines.add(new Line(polygon.getPoint1(), polygon.getPoint3())); //ac
        return lines;
    }

    enum Case {
        ALL_INNERS, ONE_INNER, ALL_OUTSIDE
    }
}
