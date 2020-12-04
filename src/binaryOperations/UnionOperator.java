package binaryOperations;

import math.Vector3;
import models.Model;
import third.IModel;
import third.Polygon;

import java.util.LinkedList;
import java.util.List;

public class UnionOperator implements Operator {
    private Operator intersect = new IntersectionOperator();

    @Override
    public IModel operate(IModel first, IModel second) {
        List<Polygon> intersectPolygons;
        List<Polygon> firstPolygons = first.getPolygons();
        List<Polygon> secondPolygons = second.getPolygons();
        List<Polygon> newPolygons = new LinkedList<>();

        intersectPolygons = intersect.operate(first,second).getPolygons();

        for (Polygon polygon : firstPolygons) {
            if (!intersectPolygons.contains(polygon)) {
                newPolygons.add(polygon);
            }
        }

        for (Polygon polygon : secondPolygons) {
            if (!intersectPolygons.contains(polygon)) {
                newPolygons.add(polygon);
            }
        }


        return new Model(newPolygons);
    }

}
