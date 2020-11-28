package binaryOperations;

import math.Vector3;
import models.Model;
import third.IModel;
import third.Polygon;

public class UnionOperator implements Operator {

    @Override
    public IModel operate(IModel first, IModel second) {
        IModel newModel = new Model(first.getPolygons(), first.getColor());
        for (Polygon polygon : second.getPolygons()) {
            polygon.setColor(newModel.getColor());
            newModel.getPolygons().add(polygon);
        }
        return newModel;
    }

    private float maxZ(IModel model) {
        float maxZ = Float.MIN_VALUE;
        for (Polygon polygon : model.getPolygons()) {
            for (Vector3 point : polygon.getBorder().getPoints()) {
                if (point.getZ() > maxZ)
                    maxZ = point.getZ();
            }
        }
        return maxZ;
    }

    private float minZ(IModel model) {
        float minZ = Float.MAX_VALUE;
        for (Polygon polygon : model.getPolygons()) {
            for (Vector3 point : polygon.getBorder().getPoints()) {
                if (point.getZ() < minZ)
                    minZ = point.getZ();
            }
        }
        return minZ;
    }

    private float maxZ(IModel first, IModel second) {
        return Math.max(maxZ(first), maxZ(second));
    }

    private float minZ(IModel first, IModel second) {
        return Math.min(minZ(first), minZ(second));
    }
}
