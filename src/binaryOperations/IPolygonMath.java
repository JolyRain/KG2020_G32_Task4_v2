package binaryOperations;

import math.Vector3;
import models.Line;
import third.Polygon;

public interface IPolygonMath {

    Vector3 intersectPoint(Line line, Polygon polygon);

    boolean isInsideTriangle(Vector3 point, Polygon polygon);

}
