package ru.geekbrains.advancedjava.lesson1.bricks;

import ru.geekbrains.advancedjava.lesson1.ColorUtils;
import ru.geekbrains.advancedjava.lesson1.common.GameCanvas;
import ru.geekbrains.advancedjava.lesson1.common.Sprite;

import java.awt.*;

public class Brick extends Sprite {
    private final Color color;
    private float vX;
    private float vY;

    public Brick() {
        halfHeight = 20 + (float) (Math.random() * 50f);
        halfWidth = halfHeight;
        color = ColorUtils.getRandomColor();
        vX = (float) (100f + (Math.random() * 200f));
        vY = (float) (100f + (Math.random() * 200f));
    }

    public Brick(Point point) {
        this();
        this.setX((float) point.getX());
        this.setY((float) point.getY());
    }

    @Override
    public void update(GameCanvas canvas, float deltaTime) {
        x += vX * deltaTime;//S = vt;
        y += vY * deltaTime;
        if (getLeft() < canvas.getLeft()) {
            setLeft(canvas.getLeft());
            vX = -vX;
        }
        if (getRight() > canvas.getRight()) {
            setRight(canvas.getRight());
            vX = -vX;
        }
        if (getTop() < canvas.getTop()) {
            setTop(canvas.getTop());
            vY = -vY;
        }
        if (getBottom() > canvas.getBottom()) {
            setBottom(canvas.getBottom());
            vY = -vY;
        }
    }

    @Override
    public void render(GameCanvas canvas, Graphics g) {
        g.setColor(color);
        g.fillRect((int) getLeft(), (int) getTop(),
                (int) getWidth(), (int) getHeight()
        );
    }
}