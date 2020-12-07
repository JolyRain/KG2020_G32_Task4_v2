package frame;

import math.Vector3;
import models.Sphere;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

class Input extends JPanel {

    private final String[] NAME_COLORS = {"Белый", "Желтый", "Оранжевый", "Синий", "Красный",
            "Циан", "Серый", "Зеленый", "Пурпурный", "Розовый"};
    private JTextField xLabel = new JTextField(5);
    private JTextField yLabel = new JTextField(5);
    private JTextField zLabel = new JTextField(5);
    private JTextField rLabel = new JTextField(5);
    private JComboBox<String> colorComboBox = new JComboBox<>(NAME_COLORS);


    Input() {
        this.add(new JLabel("x0:"));
        this.add(xLabel);
        this.add(new JLabel("y0:"));
        this.add(yLabel);
        this.add(new JLabel("z0:"));
        this.add(zLabel);
        this.add(new JLabel("radius:"));
        this.add(rLabel);

        this.add(colorComboBox);
    }

    private void clear() {
        xLabel.setText("");
        yLabel.setText("");
        zLabel.setText("");
        rLabel.setText("");
    }

    Sphere getSphere() {
        clear();
        float x, y, z, radius;
        Color color;
        this.setVisible(true);
        int result = JOptionPane.showConfirmDialog(null, this,
                "Enter something", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                x = Float.parseFloat(xLabel.getText());
                y = Float.parseFloat(yLabel.getText());
                z = Float.parseFloat(zLabel.getText());
                radius = Float.parseFloat(rLabel.getText());
                if (radius <= 0) throw new NumberFormatException();
                color = Defaults.COLORS.get(colorComboBox.getSelectedItem());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Enter something valid",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } else return null;
        return new Sphere(radius, new Vector3(x, y, z), color);
    }

    private Color random() {
        return new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
    }
}

