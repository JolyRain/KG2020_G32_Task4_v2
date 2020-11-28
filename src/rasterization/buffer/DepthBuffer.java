package rasterization.buffer;

import java.util.Arrays;

public class DepthBuffer {
    private float[][] depthBuffer;

    public DepthBuffer(int width, int height) {
        depthBuffer = new float[width][height];
        clear();
    }

    private void initialize() {
        for (float[] floats : depthBuffer) {
            Arrays.fill(floats, Float.MIN_VALUE);
        }
    }

    public boolean checkDepth(int x, int y, float depth) {
        if (depth >= depthBuffer[x][y]) {
            depthBuffer[x][y] = depth;
            return true;
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
