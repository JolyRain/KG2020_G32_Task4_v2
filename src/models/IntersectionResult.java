package models;

import third.IModel;
import third.Polygon;

import java.awt.*;
import java.util.List;

public class IntersectionResult implements IModel {
    private List<Polygon> firstInnerPolygons;
    private List<Polygon> firstOwnPolygons;
    private List<Polygon> secondInnerPolygons;
    private List<Polygon> secondOwnPolygons;
    

    private Color color = Color.LIGHT_GRAY;

    public IntersectionResult(List<Polygon> firstInnerPolygons, List<Polygon> firstOwnPolygons, List<Polygon> secondInnerPolygons, List<Polygon> secondOwnPolygons) {
        this.firstInnerPolygons = firstInnerPolygons;
        this.firstOwnPolygons = firstOwnPolygons;
        this.secondInnerPolygons = secondInnerPolygons;
        this.secondOwnPolygons = secondOwnPolygons;
    }

    public List<Polygon> getFirstInnerPolygons() {
        return firstInnerPolygons;
    }

    public void setFirstInnerPolygons(List<Polygon> firstInnerPolygons) {
        this.firstInnerPolygons = firstInnerPolygons;
    }

    public List<Polygon> getFirstOwnPolygons() {
        return firstOwnPolygons;
    }

    public void setFirstOwnPolygons(List<Polygon> firstOwnPolygons) {
        this.firstOwnPolygons = firstOwnPolygons;
    }

    public List<Polygon> getSecondInnerPolygons() {
        return secondInnerPolygons;
    }

    public void setSecondInnerPolygons(List<Polygon> secondInnerPolygons) {
        this.secondInnerPolygons = secondInnerPolygons;
    }

    public List<Polygon> getSecondOwnPolygons() {
        return secondOwnPolygons;
    }

    public void setSecondOwnPolygons(List<Polygon> secondOwnPolygons) {
        this.secondOwnPolygons = secondOwnPolygons;
    }

    @Override
    public List<Polygon> getPolygons() {
//        firstInnerPolygons.addAll(firstOwnPolygons);
        firstInnerPolygons.addAll(secondInnerPolygons);
//        firstInnerPolygons.addAll(secondOwnPolygons);
        return firstInnerPolygons;
    }

    @Override
    public Color getColor() {
        return color;
    }
}
