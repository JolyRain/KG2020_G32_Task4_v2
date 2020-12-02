package modelOperations;

import math.Axis;
import math.Vector3;
import third.IModel;
import third.Polygon;

import java.util.List;

public class ModelOperator implements ModelOperable {

    @Override
    public boolean isInnerPoint(IModel model, Vector3 point) {
        return false;
    }

    @Override
    public Vector3 getMaxPoint(IModel model, Axis axis) {
        List<Vector3> points = model.getPoints();
        Vector3 maxPoint = points.get(0);
        for (Vector3 point : points) {
            if (getCoordinate(point, axis) > getCoordinate(maxPoint, axis)) {
                maxPoint = point;
            }
        }
        return maxPoint;
    }

    @Override
    public Vector3 getMinPoint(IModel model, Axis axis) {
        List<Vector3> points = model.getPoints();
        Vector3 minPoint = points.get(0);
        for (Vector3 point : points) {
            if (getCoordinate(point, axis) < getCoordinate(minPoint, axis)) {
                minPoint = point;
            }
        }
        return minPoint;
    }


}
