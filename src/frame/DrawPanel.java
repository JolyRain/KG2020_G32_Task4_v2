package frame;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import binaryOperations.*;
import draw.Drawer;
import draw.ScreenPolygonDrawer;
import math.Vector3;
import models.*;
import rasterization.buffer.DepthBuffer;
import rasterization.lineDrawers.BresenhamLineDrawer;
import rasterization.lineDrawers.LineDrawer;
import rasterization.pixelDrawers.BufferedImagePixelDrawer;
import rasterization.pixelDrawers.PixelDrawer;
import rasterization.polygonDrawers.GraphicsPolygonDrawer;
import rasterization.polygonDrawers.MyPolygonDrawer;
import rasterization.polygonDrawers.PolygonDrawer;
import screen.ScreenConverter;
import third.*;
import third.Polygon;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @author Alexey
 */
public class DrawPanel extends JPanel implements CameraController.RepaintListener {
    private Scene scene;
    private ScreenConverter screenConverter;
    private Camera camera;
    private CameraController cameraController;
    //problem
//    Triangle triangle = new Triangle(
//            new Vector3(5.728219f,3.3071887f,7.5000005f),
//            new Vector3(3.3071885f, 5.728219f, 7.5000005f),
//            new Vector3(1.9982625f, 3.4610925f, 9.166667f),
//            new Color(255,0,0,50));
//    Triangle triangle1 = new Triangle(
//            new Vector3(4.9999995f, 6.6143775f, 7.5000005f),  //one inner {10,2,5}   all inner {10,0,5}  all inside {10,-15,5}
//            new Vector3(1.6928109f, 5.7282186f, 7.5000005f), //one inner {10,15,5}  all inner {10,10,5}    all inside {10,15,5}
//            new Vector3(3.001737f, 3.4610922f, 9.166667f),  //one inner {-5,3,5}   all inner {-5,1,5}     all inside {-5,3,5}
//            new Color(0,0,255,50));

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
        Triangle triangle = new Triangle(
                new Vector3(0,0,0),
                new Vector3(0,0,10),
                new Vector3(0,10,0),
                new Color(255,0,0,50));
        Triangle triangle1 = new Triangle(
                new Vector3(10,2,5),  //one inner {10,2,5}   all inner {10,0,5}  all inside {10,-15,5}
                new Vector3(5,15,5), //one inner {10,15,5}  all inner {10,10,5}    all inside {10,15,5}
                new Vector3(-5,3,5),  //one inner {-5,3,5}   all inner {-5,1,5}     all inside {-5,3,5}
                new Color(0,0,255,50));
//        scene.getModelsList().add(triangle);
//        scene.getModelsList().add(triangle1);


//
//                getScene().getModelsList().add(s1);
//        getScene().getModelsList().add(s2);
                PolygonSeparator separator = new PolygonSeparator();

        List<Line> lines = separator.lines(triangle1.getPolygons().get(0));

//        for (Line line : lines) {
//            scene.getModelsList().add(new Line(line.getP1(), new PolygonMath().intersectPoint(line, triangle.getPolygons().get(0)), Color.GREEN));
//        }
        PolygonMath polygonMath = new PolygonMath();
        System.out.println(polygonMath.isIntersectTriangles(triangle.getPolygons().get(0), triangle1.getPolygons().get(0)));

        IModel model = new Model(separator.separate(triangle.getPolygons().get(0), triangle1.getPolygons().get(0)));
//        scene.getModelsList().add(model);
        IModel inter = intersect.operate(s1, s2);
        scene.getModelsList().add(inter);


        //        getScene().getModelsList().add(intersect.operate(s1,s2));
        System.out.println("a");
    }

    @Override
    public void paint(Graphics g) {
        screenConverter.setScreenSize(getWidth(), getHeight());
        BufferedImage bufferedImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
        PixelDrawer pixelDrawer = new BufferedImagePixelDrawer(bufferedImage);
        LineDrawer lineDrawer = new BresenhamLineDrawer(pixelDrawer);
//        PolygonDrawer polygonDrawer = new MyPolygonDrawer(pixelDrawer, lineDrawer, new DepthBuffer(getWidth(), getHeight()));
        PolygonDrawer polygonDrawer = new GraphicsPolygonDrawer(graphics2D);

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
