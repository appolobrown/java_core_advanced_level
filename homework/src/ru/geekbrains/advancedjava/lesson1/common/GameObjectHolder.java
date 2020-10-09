package ru.geekbrains.advancedjava.lesson1.common;

import java.awt.*;
import java.util.Arrays;

public class GameObjectHolder {

    private int capacity = 10;
    private GameObject[] gameObjects;

    public GameObjectHolder() {
        gameObjects = new GameObject[capacity];
    }

    public void update(GameCanvas canvas, float deltaTime) {
        for (GameObject gameObject : gameObjects) {
            gameObject.update(canvas, deltaTime);
        }
    }

    public void render(GameCanvas canvas, Graphics g) {
        for (GameObject gameObject : gameObjects) {
            gameObject.render(canvas, g);
        }
    }

    public void addGameObject(GameObject gameObject) {
        capacity++;
        GameObject[] cache = Arrays.copyOf(gameObjects, capacity);
        cache[cache.length - 1] = gameObject;
        gameObjects = cache;
    }

    public void removeLast() {
        if (capacity > 0) {
            capacity--;
            gameObjects = Arrays.copyOf(gameObjects, capacity);
        }
    }
}
