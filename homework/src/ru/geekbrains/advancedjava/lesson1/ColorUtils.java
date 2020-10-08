package ru.geekbrains.advancedjava.lesson1;

import java.awt.*;

public class ColorUtils {
    public static Color getRandomColor() {
        return new Color(
                (int) (Math.random() * 255), // R
                (int) (Math.random() * 255), // G
                (int) (Math.random() * 255)); // B
    }
}
