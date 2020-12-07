package frame;


import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Defaults {

    public static final int FRAME_WIDTH = 1200;
    public static final int FRAME_HEIGHT = 1000;
    public static final double REAL_SIZE = 10;
    public static final double CORNER_X = -REAL_SIZE / 2;
    public static final double CORNER_Y = REAL_SIZE / 2;
    public static final double COEFFICIENT_INCREASE = 1.05;
    public static final double COEFFICIENT_DECREASE = 0.95;
    public static final Font FONT_LABEL = new Font(Font.SANS_SERIF, Font.BOLD, 18);
    public static final Font FONT_SIGNATURES = new Font(Font.DIALOG, Font.BOLD, 15);
    public static final Color MAIN_GRID_COLOR = new Color(150, 150, 150);
    public static final Color SMALL_GRID_COLOR = new Color(200, 200, 200);
    public static final Color LIGHT_GRAY = new Color(220, 220, 220);
    public static final Color VIOLET = new Color(100, 10, 255);
    public static final Color BLUE = new Color(0, 100, 255);
    public static final double LOWER_LIMIT_NUMBER = 0.001;
    public static final double UPPER_LIMIT_NUMBER = 10000;
    public static final int SMALL_GRID_DIVIDER = 5;
    public static final Map<String, Color> COLORS = new HashMap<>();

    static {
        COLORS.put("Белый", Color.WHITE);
        COLORS.put("Желтый", Color.YELLOW);
        COLORS.put("Оранжевый", Color.ORANGE);
        COLORS.put("Синий", Color.BLUE);
        COLORS.put("Красный", Color.RED);
        COLORS.put("Циан", Color.CYAN);
        COLORS.put("Серый", Color.GRAY);
        COLORS.put("Зеленый", Color.GREEN);
        COLORS.put("Пурпурный", Color.MAGENTA);
        COLORS.put("Розовый", Color.PINK);
    }
}
