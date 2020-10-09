package ru.geekbrains.advancedjava.lesson1.balls;

import ru.geekbrains.advancedjava.lesson1.ColorUtils;
import ru.geekbrains.advancedjava.lesson1.common.GameCanvas;
import ru.geekbrains.advancedjava.lesson1.common.GameObject;

import java.awt.*;

public class Background implements GameObject {

    private static final int UPDATE_COLOR_SEC = 5;
    private Color color;
    private float deltaTimer;

    public Background() {
        this.color = ColorUtils.getRandomColor();
        this.deltaTimer = 0f;
    }

    @Override
    public void update(GameCanvas canvas, float deltaTime) {
        deltaTimer += deltaTime;
        if (deltaTimer > UPDATE_COLOR_SEC) {
            this.color = ColorUtils.getRandomColor();
            deltaTimer = 0f;
        }
    }

    @Override
    public void render(GameCanvas canvas, Graphics g) {
        canvas.setBackground(color);
    }
}
