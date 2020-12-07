package binaryOperations;

import models.Model;
import third.IModel;
import third.Polygon;

import java.util.List;

public class SubtractionOperator implements Operator {
    private IntersectionOperator intersectionOperator = new IntersectionOperator();

    @Override
    public IModel operate(IModel first, IModel second) {
        List<Polygon> intersectPolygons;
        List<Polygon> firstPolygons = first.getPolygons();

//        intersectPolygons = intersectionOperator.getIntersect(first, second).getAInnerPolygons();
//        intersectPolygons.addAll(intersectionOperator.getIntersect(first, second).getIntersectPolygons());


//        for (Polygon newPolygon : intersectPolygons) {
//            firstPolygons.remove(newPolygon);
//        }

        return new Model(firstPolygons);
    }
}
