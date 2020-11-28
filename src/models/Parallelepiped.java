/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import math.Vector3;
import third.IModel;
import third.PolyLine;
import third.Polygon;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Описывает параллелипипед по двум диагональным точкам
 *
 * @author Alexey
 */
public class Parallelepiped implements IModel {
    private Vector3 LTF, RBN;
    private Color color = Color.RED;

    /**
     * Создаёт экземпляр параллелипипеда
     *
     * @param LTF Левая Верхняя Дальняя точка (Left Top Far)
     * @param RBN Правая Нижняя Ближняя точка (Right Bottom Near)
     */
    public Parallelepiped(Vector3 LTF, Vector3 RBN) {
        this.LTF = LTF;
        this.RBN = RBN;
    }

    public Parallelepiped(Vector3 LTF, Vector3 RBN, Color color) {
        this.LTF = LTF;
        this.RBN = RBN;
        this.color = color;
    }

    @Override
    public List<PolyLine> getLines() {
        LinkedList<PolyLine> lines = new LinkedList<>();
        for (Polygon polygon : getPolygons()) {
            lines.add(polygon.getBorder());
        }
        return lines;
    }

    @Override
    public List<Polygon> getPolygons() {
        List<Polygon> polygons = new LinkedList<>();
        Vector3 v1, v2, v3, v4;

        /*Дальняя сторона (Z фиксирован и вязт у LTF)*/
//        lines.add(new PolyLine(Arrays.asList(
        v1 = new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()); //1
        v2 = new Vector3(LTF.getX(), RBN.getY(), LTF.getZ()); //2
        v3 = new Vector3(RBN.getX(), RBN.getY(), LTF.getZ()); //3
        v4 = new Vector3(RBN.getX(), LTF.getY(), LTF.getZ()); //4
        polygons.add(new Polygon(v1, v2, v3, color));
        polygons.add(new Polygon(v1, v3, v4, color));

        /*Ближняя сторона  (Z фиксирован и вязт у RBN)*/
//        lines.add(new PolyLine(Arrays.asList(
        v1 = new Vector3(LTF.getX(), LTF.getY(), RBN.getZ()); //5
        v2 = new Vector3(LTF.getX(), RBN.getY(), RBN.getZ()); //6
        v3 = new Vector3(RBN.getX(), RBN.getY(), RBN.getZ()); //7
        v4 = new Vector3(RBN.getX(), LTF.getY(), RBN.getZ()); //8
        polygons.add(new Polygon(v1, v2, v3, color));
        polygons.add(new Polygon(v1, v3, v4, color));

        //

        /*Верхняя сторона (Y фиксирован и вязт у LTF)*/
//        lines.add(new PolyLine(Arrays.asList(
        v1 = new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()); //9
        v2 = new Vector3(LTF.getX(), LTF.getY(), RBN.getZ()); //10
        v3 = new Vector3(RBN.getX(), LTF.getY(), RBN.getZ()); //11
        v4 = new Vector3(RBN.getX(), LTF.getY(), LTF.getZ()); //12
        polygons.add(new Polygon(v1, v2, v3, color));
        polygons.add(new Polygon(v1, v3, v4, color));


        /*Нижняя сторона (Y фиксирован и вязт у RBN)*/

        v1 = new Vector3(LTF.getX(), RBN.getY(), LTF.getZ()); //13
        v2 = new Vector3(LTF.getX(), RBN.getY(), RBN.getZ()); //14
        v3 = new Vector3(RBN.getX(), RBN.getY(), RBN.getZ()); //15
        v4 = new Vector3(RBN.getX(), RBN.getY(), LTF.getZ());  //16
        polygons.add(new Polygon(v1, v2, v3, color));
        polygons.add(new Polygon(v1, v3, v4, color));
        /*Левая сторона (X фиксирован и вязт у LTF)*/

        v1 = new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()); //17
        v2 = new Vector3(LTF.getX(), LTF.getY(), RBN.getZ()); //18
        v3 = new Vector3(LTF.getX(), RBN.getY(), RBN.getZ()); //19
        v4 = new Vector3(LTF.getX(), RBN.getY(), LTF.getZ()); //20
        polygons.add(new Polygon(v1, v2, v3, color));
        polygons.add(new Polygon(v1, v3, v4, color));
        /*Правая сторона (X фиксирован и вязт у RBN)*/


        v1 = new Vector3(RBN.getX(), LTF.getY(), LTF.getZ()); //21
        v2 = new Vector3(RBN.getX(), LTF.getY(), RBN.getZ()); //22
        v3 = new Vector3(RBN.getX(), RBN.getY(), RBN.getZ()); //23
        v4 = new Vector3(RBN.getX(), RBN.getY(), LTF.getZ());//24

        polygons.add(new Polygon(v1, v2, v3, color));
        polygons.add(new Polygon(v1, v3, v4, color));
        return polygons;

    }

    @Override
    public Color getColor() {
        return color;
    }
}
