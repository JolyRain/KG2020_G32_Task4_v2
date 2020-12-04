package binaryOperations;

import math.Vector3;
import modelOperations.ModelOperable;
import modelOperations.ModelOperator;
import models.Line;
import models.Model;
import models.Ray;
import third.IModel;
import third.IPositionProvider;
import third.Polygon;

import java.util.LinkedList;
import java.util.List;

public class IntersectionOperator implements Operator {

    private final float TRIANGLE_EPSILON = 1e-10f;
    private ModelOperable modelOperator = new ModelOperator();

    private IPositionProvider positionProvider;

    public IntersectionOperator(IPositionProvider positionProvider) {
        this.positionProvider = positionProvider;
    }

    public IntersectionOperator() {
    }

    @Override
    public IModel operate(IModel first, IModel second) {
        List<Polygon> newPolygons = new LinkedList<>();
        List<Polygon> firstPolygons = first.getPolygons();
        List<Polygon> secondPolygons = second.getPolygons();

        for (Polygon polygonFirst : firstPolygons) {
            if (isInnerPolygon(second, polygonFirst)) {
                newPolygons.add(polygonFirst);
            } else if (isIntersectionPolygon(second, polygonFirst)) {
                newPolygons.add(polygonFirst);
            }
        }
        for (Polygon polygonSecond : secondPolygons) {
            if (isInnerPolygon(first, polygonSecond)) {
                newPolygons.add(polygonSecond);
            } else if (isIntersectionPolygon(first, polygonSecond)) {
                newPolygons.add(polygonSecond);
            }
        }
        return new Model(newPolygons);
    }

    private boolean isInnerPolygon(IModel model, Polygon polygon) {
        return isInnerPoint(model, polygon.getPoint1())
                && isInnerPoint(model, polygon.getPoint2())
                && isInnerPoint(model, polygon.getPoint3());
    }

    private boolean isIntersectionPolygon(IModel model, Polygon polygon) {
        return isInnerPoint(model, polygon.getPoint1())
                || isInnerPoint(model, polygon.getPoint2())
                || isInnerPoint(model, polygon.getPoint3());
    }

    private boolean isInnerPoint(IModel model, Vector3 point) {
        int counter = 0;

        Line ray = new Line(point, new Vector3(1000,1000,1000));
        for (Polygon polygon : model.getPolygons()) {

            Vector3 p = pointIntersect(new Ray(point), polygon); //находим точку пересечения луча и плоскости, в которой лежит полигон

            if (p != null && barycentric(p, polygon)) { //проверяем находится ли точка внутри треугольника
                counter++;
            }
        }
        return counter % 2 != 0;
    }

    private boolean barycentric(Vector3 point, Polygon polygon) {
        Vector3 ab = polygon.getPoint2().minus(polygon.getPoint1());
        Vector3 ac = polygon.getPoint3().minus(polygon.getPoint1());
        float areaABC = ab.cross(ac).length() / 2;
        Vector3 pb = polygon.getPoint2().minus(point);
        Vector3 pc = polygon.getPoint3().minus(point);
        Vector3 pa = polygon.getPoint1().minus(point);
        float alpha = pb.cross(pc).length() / (2 * areaABC);
        float beta = pc.cross(pa).length() / (2 * areaABC);
        float gamma = 1 - alpha - beta;
        return isCorrect(alpha) && isCorrect(beta) && isCorrect(gamma);
    }

    private boolean isCorrect(float number) {
        return number >= 0 && number <= 1;
    }

    private Vector3 pointIntersect(Ray ray, Polygon polygon) {
        Vector3 dir = ray.getDirection();
        Vector3 start = ray.getStart();
        Vector3 n = polygon.normal();
        Vector3 p = polygon.getPoint1();
        float d = -(n.getX() * p.getX() + n.getY() * p.getY() + n.getZ() * p.getZ());
        float t = (-d - n.scalar(start)) / n.scalar(dir);
        if (t < 0) return null;
        return start.plus(dir.mul(t));
    }

    /////////////////////////////////////////////////////////////
    //useless methods


    // возвращает расстояние по лучу от rayPos до точки пересечения
// отрицательное расстояние значит пересечение не найдено (луч уходит)
// N должно быть нормировано
    private float RayPlaneIntersect(Vector3 rayPos, Vector3 rayDir, Vector3 N, Vector3 p) {
        float eps = 1.0e-5f;
        float ratio = N.scalar(rayDir);   // косинус нормали с лучом
        if (Math.abs(ratio) < eps) return -1.0f;  // луч параллелен плоскости
        float d = N.scalar(p.minus(rayPos));  // расстояние от плоскости до rayPos по нормали
        return d / ratio;     // возвращаем расстояние по лучу
    }

    private Vector3 pointIntersect(Line ray, Polygon polygon) {
        Vector3 rayDir = ray.getP2().minus(ray.getP1());
        float t = RayPlaneIntersect(ray.getP1(), rayDir, polygon.normal(), polygon.getPoint1());
        if (t >= 0f) {
            return ray.getP1().plus(rayDir.mul(t));
        }
        return null;
    }

    private boolean isInTriangle(Vector3 p, Polygon polygon) {
        Vector3 b = polygon.getPoint2().minus(polygon.getPoint1());
        Vector3 c = polygon.getPoint3().minus(polygon.getPoint1());
        Vector3 n = b.cross(c);
        return false;
    }


    private boolean someMethod(Vector3 point, Polygon polygon) {
        Ray ray = new Ray(point);
        Vector3 intersect = pointIntersect(ray, polygon);
        if (intersect == null) {
            return false;
        }
        return isInsideTriangle(intersect, polygon.getPoint1(), polygon.getPoint2(), polygon.getPoint3(), polygon.normal());
    }


    private boolean isInsideTriangle(Vector3 pt, Vector3 v0, Vector3 v1, Vector3 v2, Vector3 normal) {
        Vector3 a = v1.minus(v0);
        Vector3 b = pt.minus(v0);
        Vector3 c = v2.minus(v0);
        Vector3 d = v1.minus(pt);
        Vector3 e = v2.minus(pt);
        if (a.cross(b).scalar(normal) < TRIANGLE_EPSILON)
            return false;
        if (b.cross(c).scalar(normal) < TRIANGLE_EPSILON)
            return false;
        if (d.cross(e).scalar(normal) < TRIANGLE_EPSILON)
            return false;
        return true;
    }


    /*
     * более менее рабочий метод, но с косяками
     *
     * */

    // intersect3D_RayTriangle(): find the 3D intersection of a ray with a triangle
//    Input:  a ray R, and a triangle T
//    Output: *I = intersection point (when it exists)
//    Return: -1 = triangle is degenerate (a segment or point)
//             0 =  disjoint (no intersect)
//             1 =  intersect in unique point I1
//             2 =  are in the same plane

    private boolean intersect3D_RayTriangle(Line ray, Polygon T) {
        Vector3 u, v, n;              // triangle vectors
        Vector3 dir, w0, w;           // ray vectors
        float r, a, b;              // params to calc ray-plane intersect

        // get triangle edge vectors and plane normal
        u = T.getPoint2().minus(T.getPoint1());
        v = T.getPoint3().minus(T.getPoint1());
        n = u.cross(v);              // cross product
        if (n.getX() == 0 && n.getY() == 0 && n.getZ() == 0)             // triangle is degenerate
            return false;                  // do not deal with this case

        dir = ray.getP2().minus(ray.getP1());              // ray direction vector
        w0 = ray.getP1().minus(T.getPoint1());
        a = -n.scalar(w0);
        b = n.scalar(dir);
        if (Math.abs(b) < TRIANGLE_EPSILON) {     // ray is  parallel to triangle plane
            if (a == 0)                 // ray lies in triangle plane
                return false;
            else return false;              // ray disjoint from plane
        }

        // get intersect point of ray with triangle plane
        r = a / b;
        if (r < 0)                    // ray goes away from triangle
            return false;                   // => no intersect
        // for a segment, also test if (r > 1.0) => no intersect

        Vector3 point = ray.getP1().plus(dir.mul(r));            // intersect point of ray and plane

        // is I inside T?
        float uu, uv, vv, wu, wv, D;
        uu = u.scalar(u);
        uv = u.scalar(v);
        vv = v.scalar(v);
        w = point.minus(T.getPoint1());
        wu = w.scalar(u);
        wv = w.scalar(v);
        D = uv * uv - uu * vv;

        // get and test parametric coords
        float s, t;
        s = (uv * wv - vv * wu) / D;
        if (s < 0 || s > 1)         // I is outside T
            return false;
        t = (uv * wu - uu * wv) / D;
        if (t < 0 || (s + t) > 1)  // I is outside T
            return false;

        return true;                       // I is in T
    }


//
//    Будем обозначать A,B,C - точки плоскости, X,Y - точки прямой(концы отрезка),
//    SP - скалярное произведение, VP - векторное произведение. O - искомое множество точек пересечения
//
//    N = VP ( B - A, C - A )
//    N = N / | N |  - нормаль к плоскости  // в принципе это можно и не делать
//            V = A - X
//      // расстояние до плоскости по нормали
//    d = SP ( N, V )
//    W = Y - X
//      // приближение к плоскости по нормали при прохождении отрезка
//            e = SP ( N, W )
//
//      if( e!=0 )
//            O = X + W * d/e;          // одна точка
//      else if( d==0)
//          O =X + W * (anything)     // прямая принадлежит плоскости
//            else
//    O = empty;                // прямая параллельна плоскости


}
