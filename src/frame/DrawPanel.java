package frame;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import binaryOperations.IntersectionOperator;
import binaryOperations.Operator;
import binaryOperations.SubtractionOperator;
import binaryOperations.UnionOperator;
import draw.Drawer;
import draw.ScreenPolygonDrawer;
import math.Vector3;
import models.Circle;
import models.Parallelepiped;
import models.Sphere;
import rasterization.buffer.DepthBuffer;
import rasterization.lineDrawers.BresenhamLineDrawer;
import rasterization.lineDrawers.LineDrawer;
import rasterization.pixelDrawers.BufferedImagePixelDrawer;
import rasterization.pixelDrawers.PixelDrawer;
import rasterization.polygonDrawers.MyPolygonDrawer;
import rasterization.polygonDrawers.PolygonDrawer;
import screen.ScreenConverter;
import third.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author Alexey
 */
public class DrawPanel extends JPanel implements CameraController.RepaintListener {
    private Scene scene;
    private ScreenConverter screenConverter;
    private Camera camera;
    private CameraController cameraController;


    private Light light;

    public DrawPanel() {
        super();
        screenConverter = new ScreenConverter(-5, 5, 10, 10, getWidth(), getHeight());
        camera = new Camera();
        cameraController = new CameraController(camera, screenConverter);
        light = new Light(camera);
        scene = new Scene(Color.DARK_GRAY.getRGB());
        scene.showAxes();
        cameraController.addRepaintListener(this);
        addMouseListener(cameraController);
        addMouseMotionListener(cameraController);
        addMouseWheelListener(cameraController);
        Operator intersect = new IntersectionOperator();
        Sphere s1 = new Sphere(10, new Vector3(0,0,0), Color.RED);
        Sphere s2 = new Sphere(10, new Vector3(5,0,0), Color.BLUE);

//        getScene().getModelsList().add(s1);
//        getScene().getModelsList().add(s2);

        getScene().getModelsList().add(intersect.operate(s1,s2));

    }

    @Override
    public void paint(Graphics g) {
        screenConverter.setScreenSize(getWidth(), getHeight());
        BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
        PixelDrawer pixelDrawer = new BufferedImagePixelDrawer(bufferedImage);
        LineDrawer lineDrawer = new BresenhamLineDrawer(pixelDrawer);
        PolygonDrawer polygonDrawer = new MyPolygonDrawer(pixelDrawer, lineDrawer, new DepthBuffer(getWidth(), getHeight()));
        Drawer drawer = new ScreenPolygonDrawer(screenConverter, polygonDrawer, light);
        scene.drawScene(drawer, camera);
        g.drawImage(bufferedImage, 0, 0, null);
        graphics2D.dispose();
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public ScreenConverter getScreenConverter() {
        return screenConverter;
    }

    public void setScreenConverter(ScreenConverter screenConverter) {
        this.screenConverter = screenConverter;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public CameraController getCameraController() {
        return cameraController;
    }

    public void setCameraController(CameraController cameraController) {
        this.cameraController = cameraController;
    }

    public Light getLight() {
        return light;
    }

    public void setLight(Light light) {
        this.light = light;
    }

    @Override
    public void shouldRepaint() {
        repaint();
    }
}
