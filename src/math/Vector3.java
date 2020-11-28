/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package math;

import java.util.Arrays;

/**
 * Класс, хранящий трёхмерный вектор / точку в трёхмерном пространстве.
 *
 * @author Alexey
 */
public class Vector3 {
    private static final float EPSILON = 1e-10f;
    private float[] values; /*Значения хранятся в виде массива из трёх элементов*/

    /**
     * Создаёт экземпляр вектора на основе значений трёх составляющих
     *
     * @param x первая составляющая, описывающая X-координату
     * @param y вторая составляющая, описывающая Y-координату
     * @param z третья составляющая, описывающая Z-координату
     */
    public Vector3(float x, float y, float z) {
        values = new float[]{x, y, z};
    }

    private Vector3(float[] array) {
        values = array;
    }

    public Vector3 getNormal(Vector3 vector) {
        return this.cross(vector).normalize();
    }

    public Vector3 cross(Vector3 other) {
        return new Vector3(
                this.getY() * other.getZ() - this.getZ() * other.getY(),
                this.getZ() * other.getX() - this.getX() * other.getZ(),
                this.getX() * other.getY() - this.getY() * other.getX());
    }

    public float scalar(Vector3 other) {
        return this.getX() * other.getX() + this.getY() * other.getY() + this.getZ() * other.getZ();
    }

    public Vector3 plus(Vector4 other) {
        float[] array = new float[3];
        for (int i = 0; i < array.length; i++)
            array[i] = this.at(i) + other.at(i);
        return new Vector3(array);
    }

    public Vector3 minus(Vector3 other) {
        float[] array = new float[3];
        for (int i = 0; i < array.length; i++)
            array[i] = this.at(i) - other.at(i);
        return new Vector3(array);
    }

    public Vector3 mul(float number) {
        float[] array = new float[3];
        for (int i = 0; i < array.length; i++)
            array[i] = number * this.at(i);
        return new Vector3(array);
    }

    public Vector3 normalize() {
        return this.mul(1 / this.length());
    }

    /**
     * X-составляющая вектора
     *
     * @return X-составляющая вектора
     */
    public float getX() {
        return values[0];
    }

    /**
     * Y-составляющая вектора
     *
     * @return Y-составляющая вектора
     */
    public float getY() {
        return values[1];
    }

    /**
     * Z-составляющая вектора
     *
     * @return Z-составляющая вектора
     */
    public float getZ() {
        return values[2];
    }

    /**
     * Метод, возвращающий составляющую вектора по порядковому номеру
     *
     * @param idx порядковый номер
     * @return значение составляющей вектора
     */
    public float at(int idx) {
        return values[idx];
    }

    /**
     * Метод, возвращающий длину вектора
     *
     * @return длина вектора
     */
    public float length() {
        float lenSqr = values[0] * values[0] + values[1] * values[1] + values[2] * values[2];
        if (lenSqr < EPSILON)
            return 0;
        return (float) Math.sqrt(lenSqr);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3 vector3 = (Vector3) o;
        return Arrays.equals(values, vector3.values);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }

    @Override
    public String toString() {
        return "Vector3{" + values[0] + ", " + values[1] + ", " + values[2] + "}";
    }
}
