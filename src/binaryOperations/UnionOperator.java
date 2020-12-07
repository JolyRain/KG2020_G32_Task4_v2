package binaryOperations;

import models.Model;
import third.IModel;
import third.Polygon;

import java.util.LinkedList;
import java.util.List;

public class UnionOperator implements Operator {
    private IntersectionOperator intersect = new IntersectionOperator();

    @Override
    public IModel operate(IModel first, IModel second) {
        List<Polygon> innerPolygons;

        List<Polygon> firstPolygons = first.getPolygons();
        List<Polygon> secondPolygons = second.getPolygons();
        List<Polygon> newPolygons = new LinkedList<>();

//        innerPolygons = intersect.getIntersect(first, second).getAInnerPolygons();
//
//        for (Polygon polygon : firstPolygons) {
//            if (!innerPolygons.contains(polygon)) {
//                newPolygons.add(polygon);
//            }
//        }
//
//        for (Polygon polygon : secondPolygons) {
//            if (!innerPolygons.contains(polygon)) {
//                newPolygons.add(polygon);
//            }
//        }


        return new Model(newPolygons);
    }

}
