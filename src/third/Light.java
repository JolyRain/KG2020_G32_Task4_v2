package third;

import math.Vector3;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Light{
    private static final float AMBIENT_LIGHTING = 0.2f;
    private Vector3 color = new Vector3(1f, 1f, 1f); //здесь вектор - это набор из rgb компонентов
    private Vector3 diffuse;
    private IPositionProvider positionProvider;


    public Light(IPositionProvider positionProvider) {
        this.positionProvider = positionProvider;
    }

    public Color getObjectColor(Color objectColor) {

        float r = (toFloat(objectColor.getRed()) * (AMBIENT_LIGHTING + diffuse.getX()));
        float g = (toFloat(objectColor.getGreen()) * (AMBIENT_LIGHTING + diffuse.getY()));
        float b = (toFloat(objectColor.getBlue()) * (AMBIENT_LIGHTING + diffuse.getZ()));
        return new Color(correct(r), correct(g), correct(b));
    }

    //отсекает значения с погрешностью, например когда r = 1.00033
    private float correct(float component) {
        return component > 1 ? 1 : component < 0 ? 0 : component;
    }

    private float diffuseCoefficient(Vector3 normal, Vector3 lightDir) {
        return Math.max(normal.scalar(lightDir), 0f);
    }

    public void setDiffuse(Vector3 normal, Vector3 lightDir) {
        float diffuse = diffuseCoefficient(normal, lightDir);
        float r = color.getX() * diffuse;
        float g = color.getY() * diffuse;
        float b = color.getZ() * diffuse;
        this.diffuse = new Vector3(r, g, b);
    }

    private float toFloat(int component) {
        return (float) (component / 255);
    }

    public Vector3 getPosition() {
        return positionProvider.getPosition();
    }



}
