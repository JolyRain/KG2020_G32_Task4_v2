package models;

import third.IModel;
import third.PolyLine;
import third.Polygon;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Model implements IModel {

    private List<Polygon> polygons;
    private Color color;

    public Model(List<Polygon> polygons) {
        this.polygons = polygons;
    }

    public Model(List<Polygon> polygons, Color color) {
        this.polygons = polygons;
        this.color = color;
    }

    public Model() {
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setPolygons(List<Polygon> polygons) {
        this.polygons = polygons;
    }

    @Override
    public List<PolyLine> getLines() {
        List<PolyLine> polyLines = new LinkedList<>();
        for (Polygon polygon :  polygons)
            polyLines.add(polygon.getBorder());
        return polyLines;
    }

    @Override
    public List<Polygon> getPolygons() {
        return polygons;
    }
}
