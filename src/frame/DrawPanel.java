package frame;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import binaryOperations.Operator;
import binaryOperations.UnionOperator;
import draw.Drawer;
import draw.ScreenPolygonDrawer;
import rasterization.buffer.DepthBuffer;
import rasterization.lineDrawers.BresenhamLineDrawer;
import rasterization.lineDrawers.LineDrawer;
import math.Vector3;
import models.Circle;
import models.Parallelepiped;
import models.Sphere;
import rasterization.pixelDrawers.BufferedImagePixelDrawer;
import rasterization.pixelDrawers.PixelDrawer;
import rasterization.polygonDrawers.MyPolygonDrawer;
import rasterization.polygonDrawers.PolygonDrawer;
import screen.ScreenConverter;
import third.Camera;
import third.IModel;
import third.Light;
import third.Scene;

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

//    float coef = 0.1f;

    private Light light;

    public DrawPanel() {
        super();
        screenConverter = new ScreenConverter(-5, 5, 10, 10, getWidth(), getHeight());
        camera = new Camera();
        cameraController = new CameraController(camera, screenConverter);


        light = new Light(camera);
        scene = new Scene(Color.DARK_GRAY.getRGB());
        scene.showAxes();

        IModel lighting = new Parallelepiped(
                new Vector3(100, 100, 100),
                new Vector3(110, 110, 110),
                Color.WHITE);

        scene.getModelsList().add(lighting);
        Parallelepiped p1 = new Parallelepiped(
                new Vector3(10f, -10f, 50f),
                new Vector3(20, 10f, 0f),
                Color.RED);

        Parallelepiped p2 = new Parallelepiped(
                new Vector3(-40f, -40f, -40f),
                new Vector3(10f, 10f, 10f),
                Color.WHITE
        );

        Operator unionOperator = new UnionOperator();
        IModel circle = new Circle(new Vector3(0, 0, 0), 20, Color.RED);
        IModel circle1 = new Circle(new Vector3(30, 0, 0), 20, Color.GREEN);
        Sphere s1 = new Sphere(10);
        Sphere s2 = new Sphere(10, new Vector3(10, 0, 0));

        scene.getModelsList().add(s2);
        scene.getModelsList().add(s1);
//        scene.getModelsList().add(p1);
//        scene.getModelsList().add(p2);
//        scene.getModelsList().add(circle);
//        scene.getModelsList().add(circle1);

//        scene.getModelsList().add(unionOperator.operate(circle, circle1));


//        scene.getModelsList().add(operator.operate(p1,p2));
//            scene.getModelsList().add(
//                    new Triangle(
//                            new Vector3(0, 0, 20),
//                            new Vector3(-20, 10, 10),
//                            new Vector3(20,10,10)));
//        scene.getModelsList().add(
//                new Quadrangle(
//                        new Vector3(10, 10, 10),
//                        new Vector3(5,5,5)
//                ));
//        Ellipsoid s2 = new Sphere(30, new Vector3(20,0,0));


//        scene.getModelsList().add(s2);
//        scene.getModelsList().add(binaryOperator.intersection(s1, s2));
//        scene.getModelsList().remove(s1);
//        scene.getModelsList().remove(s2);
        cameraController.addRepaintListener(this);
        addMouseListener(cameraController);
        addMouseMotionListener(cameraController);
        addMouseWheelListener(cameraController);
    }

    @Override
    public void paint(Graphics g) {
        screenConverter.setScreenSize(getWidth(), getHeight());
        BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
        PixelDrawer pixelDrawer = new BufferedImagePixelDrawer(bufferedImage);
        LineDrawer lineDrawer = new BresenhamLineDrawer(pixelDrawer);
        PolygonDrawer polygonDrawer = new MyPolygonDrawer(pixelDrawer,lineDrawer, new DepthBuffer(getWidth(), getHeight()));
        Drawer drawer = new ScreenPolygonDrawer(screenConverter, polygonDrawer, light);
        scene.drawScene(drawer, camera);
        g.drawImage(bufferedImage, 0, 0, null);
        graphics2D.dispose();
    }

    @Override
    public void shouldRepaint() {
        repaint();
    }
}
