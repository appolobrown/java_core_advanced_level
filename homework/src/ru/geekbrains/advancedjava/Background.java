package ru.geekbrains.advancedjava;

import java.awt.*;

public class Background {

    private static final int UPDATE_COLOR_SEC = 5;
    private Color color;
    private float deltaTimer;

    public Background() {
        this.color = ColorUtils.getRandomColor();
        this.deltaTimer = 0f;
    }

    public void update(GameCanvas canvas, float deltaTime) {
        deltaTimer += deltaTime;
        if (deltaTimer > UPDATE_COLOR_SEC) {
            this.color = ColorUtils.getRandomColor();
            deltaTimer = 0f;
        }
    }

    public void render(GameCanvas canvas, Graphics g) {
        canvas.setBackground(color);
    }
}
