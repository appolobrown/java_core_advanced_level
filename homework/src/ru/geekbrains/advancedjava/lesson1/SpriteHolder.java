package ru.geekbrains.advancedjava.lesson1;

import java.awt.*;
import java.util.Arrays;

public class SpriteHolder {

    private int capacity = 10;
    private Sprite[] sprites;

    public SpriteHolder() {
        sprites = new Sprite[capacity];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = new Ball();
        }
    }

    public void update(GameCanvas canvas, float deltaTime) {
        for (Sprite sprite : sprites) {
            sprite.update(canvas, deltaTime);
        }
    }

    public void render(GameCanvas canvas, Graphics g) {
        for (Sprite sprite : sprites) {
            sprite.render(canvas, g);
        }
    }

    public void addSprite(Point point) {
        capacity++;
        Sprite[] cache = Arrays.copyOf(sprites, capacity);
        cache[cache.length - 1] = new Ball(point);
        sprites = cache;
    }

    public void removeLast() {
        if (capacity > 0) {
            capacity--;
            sprites = Arrays.copyOf(sprites, capacity);
        }
    }
}
