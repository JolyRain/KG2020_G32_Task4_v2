package frame;

import models.Sphere;

import javax.swing.*;
import java.awt.event.ActionListener;

import static frame.Defaults.*;

public class App {
    private JFrame frame;
    private DrawPanel drawPanel;
    private JPanel leftPanel;
    private Input input = new Input();

    public App() {
        createFrame();
        initElements();
    }

    private void createFrame() {
        frame = new JFrame("Binary operations");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setFocusable(true);
    }

    public void show() {
        frame.setVisible(true);
    }

    private void initElements() {
        drawPanel = new DrawPanel();
        createLeftPanel();
        leftPanel.setBounds(0, 0, frame.getWidth() / 5, frame.getHeight());
        drawPanel.setLayout(null);
        drawPanel.setBounds(frame.getWidth() / 5, 0, frame.getWidth() - frame.getWidth() / 5, frame.getHeight());
        frame.add(drawPanel);
        frame.add(leftPanel);
    }

    private void createLeftPanel() {
        leftPanel = new JPanel();
//        leftPanel.setLayout(null);
        addButton("Sphere 1", e -> {
            Sphere sphere = input.getSphere();
            if (sphere == null) return;
            drawPanel.getScene().addModel1(sphere);
            drawPanel.shouldRepaint();
        });
        addButton("Sphere 2", e -> {
            Sphere sphere = input.getSphere();
            if (sphere == null) return;
            drawPanel.getScene().addModel2(sphere);
            drawPanel.shouldRepaint();
        });

        addButton("Union", e -> {
            drawPanel.getScene().setShowUnion(true);
            drawPanel.shouldRepaint();
        });
        addButton("Intersection", e -> {
            drawPanel.getScene().setShowIntersect(true);
            drawPanel.shouldRepaint();
        });
        addButton("Subtraction", e -> {
            drawPanel.getScene().setShowSubtract(true);
            drawPanel.shouldRepaint();
        });

        addButton("Clear", e -> {
            drawPanel.getScene().clear();
            drawPanel.shouldRepaint();
        });
    }

    private void addButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(FONT_LABEL);
        button.addActionListener(actionListener);
        leftPanel.add(button);
    }

    private JLabel getLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_LABEL);
        return label;
    }

    private ActionListener addSphere() {
        return e -> {
            Sphere sphere = input.getSphere();
            if (sphere == null) return;
            drawPanel.getScene().getBinaryOperations().setModel1(sphere);
            drawPanel.shouldRepaint();
        };
    }

}



