package binaryOperations;

import math.Axis;
import modelOperations.ModelOperable;
import modelOperations.ModelOperator;
import models.Model;
import third.IModel;
import third.Polygon;

import java.util.LinkedList;
import java.util.List;

public class IntersectionOperator implements Operator {

    private ModelOperable modelOperator = new ModelOperator();

    @Override
    public IModel operate(IModel first, IModel second) {
        List<Polygon> newPolygons = new LinkedList<>();
        List<Polygon> firstPolygons = first.getPolygons();
        List<Polygon> secondPolygons = second.getPolygons();

        for (Polygon polygonSecond : secondPolygons) {
            if (isInnerPolygon(first, second, polygonSecond))
                newPolygons.add(polygonSecond);
        }

        for (Polygon polygonFirst : firstPolygons) {
            if (isInnerPolygon(first, second, polygonFirst))
                newPolygons.add(polygonFirst);
        }

        return new Model(newPolygons);
    }

    private boolean isInnerPolygon(IModel first, IModel second, Polygon polygon) {
        float maxX = maximum(first, second, Axis.X);
        float minX = minimum(first, second, Axis.X);

        float maxY = maximum(first, second, Axis.Y);
        float minY = minimum(first, second, Axis.Y);


        float maxZ = maximum(first, second, Axis.Z);
        float minZ = minimum(first, second, Axis.Z);


        return isInnerCoordinate(polygon.avgX(), maxX, minX)
                && isInnerCoordinate(polygon.avgY(), maxY, minY)
                && isInnerCoordinate(polygon.avgZ(), maxZ, minZ);
    }

    private boolean isInnerCoordinate(float coordinate, float max, float min) {
        return coordinate <= max && coordinate >= min;
    }

    private float maximum(IModel model1, IModel model2, Axis axis) {
        return Math.min(modelOperator.getMaxCoordinate(model1, axis), modelOperator.getMaxCoordinate(model2, axis));
    }

    private float minimum(IModel model1, IModel model2, Axis axis) {
        return Math.max(modelOperator.getMinCoordinate(model1, axis), modelOperator.getMinCoordinate(model2, axis));
    }
}
