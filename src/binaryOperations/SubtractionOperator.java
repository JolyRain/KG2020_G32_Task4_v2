package binaryOperations;

import models.Model;
import third.IModel;
import third.Polygon;

import java.util.LinkedList;
import java.util.List;

public class SubtractionOperator implements Operator {
    private Operator intersectionOperator = new IntersectionOperator();

    @Override
    public IModel operate(IModel first, IModel second) {
        List<Polygon> newPolygons;
        List<Polygon> firstPolygons = first.getPolygons();

        newPolygons = intersectionOperator.operate(first,second).getPolygons();

        for (Polygon newPolygon : newPolygons) {
            firstPolygons.remove(newPolygon);
        }

        return new Model(firstPolygons);
    }
}
