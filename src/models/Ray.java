package models;

import math.Vector3;

public class Ray extends Line {
    private static final Vector3 END = new Vector3(1000, 1000, 1000);
    private Vector3 start;
    private Vector3 direction;

    public Ray(Vector3 start) {
        super(start, END);
        this.start = start;
    }

    public Vector3 getStart() {
        return start;
    }

    public void setStart(Vector3 start) {
        this.start = start;
    }

    public Vector3 getEND() {
        return END;
    }

    public Vector3 getDirection() {
        return direction;
    }

    public void setDirection(Vector3 direction) {
        this.direction = direction;
    }
}
