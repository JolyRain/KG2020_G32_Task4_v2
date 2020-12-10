package binaryOperations;

import math.Vector3;
import models.Line;
import third.Polygon;

public class PolygonMath implements IPolygonMath {

    private static final int EPSILON = 100000;
    private static final float SMALL = 1e-10f;


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

    private float dCoefficient(Polygon polygon) {
        Vector3 n = polygon.normal();
        Vector3 p = polygon.getPoint1();
        return -(n.getX() * p.getX() + n.getY() * p.getY() + n.getZ() * p.getZ());
    }

    public float getPointSign(Vector3 point, Polygon polygon) {
        Vector3 n = polygon.normal();
        float d = dCoefficient(polygon);
        return Math.signum(
                n.getX() * point.getX()
                        + n.getY() * point.getY()
                        + n.getZ() * point.getZ()
                        + d);
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

        float small1 = triangleSquare(AP, BP, AB);
        float small2 = triangleSquare(AP, CP, CA);
        float small3 = triangleSquare(BP, CP, BC);
        float big = triangleSquare(AB, BC, CA);

        float eps = 0.01f;

//        if (small1 < eps || small2 < eps || small3 < eps) return true;

        float diff = (small1 + small2 + small3) - big;

        return Math.abs(diff) < big / EPSILON;
    }


    public boolean isIntersectTriangles(Polygon mainPolygon, Polygon intersectPolygon) {
        Vector3[] v0 = new Vector3[3];
        Vector3[] v1 = new Vector3[3];

        for (int i = 0; i < 3; i++) {
            v0[i] =  mainPolygon.getBorder().getPoints().get(i);
            v1[i] =  intersectPolygon.getBorder().getPoints().get(i);
        }
        return Moller1997b(v0,v1);
    }



    public float sideLength(Vector3 p1, Vector3 p2) {
        return p2.minus(p1).length();
    }


    public boolean PointInTriangle2D(float[][] t, float[] p) {
        boolean inside = false;
        int j = 2; // = 3 - 1
        for (int i = 0; i < 3; i++) {
            if ((t[i][1] < p[1] && t[j][1] >= p[1] || t[j][1] < p[1] && t[i][1] >= p[1]) &&
                    (t[i][0] + (p[1] - t[i][1]) / (t[j][1] - t[i][1]) * (t[j][0] - t[i][0]) < p[0]))
                inside = !inside;
            j = i;
        }
        return inside;
    }

    public boolean Moller1997b(Vector3[] V0, Vector3[] V1) {
        Vector3[][] array = {V0, V1};
        return Moller1997b(array);
    }

    public boolean Moller1997b(Vector3[][] V) {
        float[][] dv = new float[2][];
        int[][] sign = new int[2][];
        float[][] pv = new float[2][];
        float[][] t = new float[2][];
        float[][] triange2D = new float[3][];
        float[] point2D = new float[2];
        for (int j = 0; j < 3; ++j)
            triange2D[j] = new float[2];
        for (int j = 0; j < 2; ++j) {
            dv[j] = new float[3];
            sign[j] = new int[3];
            pv[j] = new float[3];
            t[j] = new float[2];
        }

        //Находим вектор-нормаль ко второму треугольнику
        Vector3 N1 = (V[1][1].minus(V[1][0]).cross(V[1][2].minus(V[1][0])));
        //и коэфициент плоскости второго треугольника
        float d1 = -N1.scalar(V[1][0]);
        //Уровнение плоскости второго треугольника будет выглядеть
        //π2 : N2*X+d2=0, где X-любая точка плоскости (одна из точек v2)
        //Но нам это в данной задаче не важно
        //Проверяем с какой стороны плоскости лежат точки первого треугольника
        for (int i = 0; i < 3; ++i) {
            dv[0][i] = N1.scalar(V[0][i]) + d1;
            sign[0][i] = Math.abs(dv[0][i]) > SMALL ? (int) Math.signum(dv[0][i]) : 0;
        }
        //Треугольник 0 не пересекается с плоскостью другого треугольника
        if (sign[0][0] == (sign[0][1]) && sign[0][1] == (sign[0][2]) && sign[0][0] != (0))
            return false;
        //Треугольники в одной плоскости
        //Как показала практика такие случаи оооочень большая редкость
        if (sign[0][0] == sign[0][1] && sign[0][1] == sign[0][2]) {
            //Теперь выберем на какую плоскость (xy, yz, xz) спроецируем треугольники
            float aNX = Math.abs(N1.getX());
            float aNY = Math.abs(N1.getY());
            float aNZ = Math.abs(N1.getZ());
            float maxN = Math.max(aNX, Math.max(aNY, aNZ));
            //Какую компоненту будем игнорить
            int ignoreIndex = (aNX == (maxN)) ? 0 : (aNY == (maxN)) ? 1 : 2;
            //Какие компоненты будем использовать для 2D координат
            int iX = ignoreIndex == 0 ? 2 : 0;
            int iY = ignoreIndex == 1 ? 2 : 1;
            //Проверка принадлежности хотябы одной точки треугольника 0 треугольнику 1
            //http://ru.stackoverflow.com/questions/464787/Точка-внутри-многоугольника
            for (int p = 0; p < 3; ++p) {
                for (int i = 0; i < 3; ++i) {
                    triange2D[i][0] = V[1][i].at(iX); //  ????????
                    triange2D[i][1] = V[1][i].at(iY);
                }
                point2D[0] = V[0][p].at(iX);
                point2D[1] = V[0][p].at(iY);
//                triange2D[i][0] = V[1][i][iX]; //  ????????
//                triange2D[i][1] = V[1][i][iY];
//            }
//            point2D[0] = V[0][p][iX];
//            point2D[1] = V[0][p][iY];
                if (PointInTriangle2D(triange2D, point2D))
                    return true;
            }
            //или центр масс второго треугольника 1 попадает в треугольник 0. Заодно включает
            //вопрос совподения (или очень близкого к совпадению) трех точек одного треугольника и трех точек другого треугольника
            for (int i = 0; i < 3; ++i) {
                triange2D[i][0] = V[0][i].at(iX);
                triange2D[i][1] = V[0][i].at(iY);
            }
            point2D[0] = (V[1][0].at(iX) + V[1][1].at(iX) + V[1][2].at(iX)) / 3f;
            point2D[1] = (V[1][0].at(iY) + V[1][1].at(iY) + V[1][2].at(iY)) / 3f;
            return PointInTriangle2D(triange2D, point2D);
        }
        Vector3 N0 = (V[0][1].minus(V[0][0]).cross(V[0][2].minus(V[0][0])));
        float d0 = -N0.scalar(V[0][0]);
        for (int i = 0; i < 3; ++i) {
            dv[1][i] = N0.scalar(V[1][i]) + d0;
            sign[1][i] = Math.abs(dv[1][i]) > SMALL ? (int) Math.signum(dv[1][i]) : 0;
        }
        //Треугольник 1 не пересекается с плоскостью другого треугольника
        if (sign[1][0] == (sign[1][1]) && sign[1][1] == (sign[1][2]) && sign[1][0] != (0))
            return false;
        //Треугольники на разных плоскостях
        Vector3 D = N0.cross(N1);
        float aDX = Math.abs(D.getX());
        float aDY = Math.abs(D.getY());
        float aDZ = Math.abs(D.getZ());
        float maxD = Math.max(aDX, Math.max(aDY, aDZ));
        int Tindex = (aDX == (maxD)) ? 0 : (aDY == (maxD)) ? 1 : 2;
        for (int j = 0; j < 2; ++j) {
            //l и r это индексы точек находящихся по одну из сторон плоскости j
            int c = 1, l = 0, r = 2;
            if (sign[j][0] == (sign[j][1])) {
                c = 2;
                r = 0;
                l = 1;
            } else if (sign[j][1] == (sign[j][2])) {
                c = 0;
                r = 2;
                l = 1;
            } //Иначе оставляем без изменений
            for (int i = 0; i < 3; ++i)
                pv[j][i] = pv[j][i] = V[j][i].at(Tindex);//D.ScalarProduct(V[j][i]); //pv[j][i] = V[j][i][Tindex];
            t[j][0] = pv[j][l] + (pv[j][c] - pv[j][l]) * dv[j][l] / (dv[j][l] - dv[j][c]);
            t[j][1] = pv[j][r] + (pv[j][c] - pv[j][r]) * dv[j][r] / (dv[j][r] - dv[j][c]);
            if (t[j][0] > t[j][1]) {
                float _t = t[j][0];
                t[j][0] = t[j][1];
                t[j][1] = _t;
            }
        }
        return (t[0][0] < t[1][1]) && (t[0][1] > t[1][0]);
    }
}
