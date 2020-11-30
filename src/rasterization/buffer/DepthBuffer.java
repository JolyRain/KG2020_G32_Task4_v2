package rasterization.buffer;

import screen.ScreenPoint;

import java.util.Arrays;

public class DepthBuffer {
    private float[][] depthBuffer;

    public DepthBuffer(int width, int height) {
        depthBuffer = new float[width][height];
        initialize();
    }

    private void initialize() {
        for (float[] floats : depthBuffer) {
            Arrays.fill(floats, Float.MIN_VALUE);
        }
    }

    public boolean checkDepth(ScreenPoint point, float depth) {
        try {
            if (depth >= depthBuffer[point.getI()][point.getJ()]) {
                depthBuffer[point.getI()][point.getJ()] = depth;
                return true;
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }

    public void clear() {
        for (float[] floats : depthBuffer) {
            Arrays.fill(floats, 0);
        }
    }

    public float[][] getDepthBuffer() {
        return depthBuffer;
    }

    public void setDepthBuffer(float[][] depthBuffer) {
        this.depthBuffer = depthBuffer;
    }
}
