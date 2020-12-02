package modelOperations;

import math.Axis;
import math.Matrix4Factories;
import math.Vector3;
import third.IModel;

public interface ModelOperable {

    boolean isInnerPoint(IModel model, Vector3 point);

    Vector3 getMaxPoint(IModel model, Axis axis);

    Vector3 getMinPoint(IModel model, Axis axis);

    default float getMaxCoordinate(IModel model, Axis axis) {
        Vector3 maxPoint = getMaxPoint(model, axis);
        return getCoordinate(maxPoint, axis);
    }

    default float getMinCoordinate(IModel model, Axis axis) {
        Vector3 minPoint = getMinPoint(model, axis);
        return getCoordinate(minPoint, axis);
    }

//    default int getAxisIndex(Axis axis) {
//        return axis.equals(Axis.X) ? 0 : axis.equals(Axis.Y) ? 1 : 2;
//    }

    default float getCoordinate(Vector3 point, Axis axis) {
        return axis.equals(Axis.X) ? point.getX() : axis.equals(Axis.Y) ? point.getY() : point.getZ();
    }



}

