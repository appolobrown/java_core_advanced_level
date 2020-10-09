package ru.geekbrains.advancedjava.lesson1.bricks;

import ru.geekbrains.advancedjava.lesson1.common.GameObjectHolder;
import ru.geekbrains.advancedjava.lesson1.balls.Background;
import ru.geekbrains.advancedjava.lesson1.common.GameCanvas;
import ru.geekbrains.advancedjava.lesson1.common.GameCanvasListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static java.awt.event.MouseEvent.BUTTON1;
import static java.awt.event.MouseEvent.BUTTON3;

public class MainBricks extends JFrame implements MouseListener, GameCanvasListener {
    private static final int POS_X = 400;
    private static final int POS_Y = 200;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;

    private Background background;
    private GameObjectHolder gameObjectHolder;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainBricks::new);
    }

    private MainBricks() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        GameCanvas canvas = new GameCanvas(this);
        add(canvas);
        initApplication();
        setTitle("Circles");
        setVisible(true);
        canvas.addMouseListener(this);

    }

    private void initApplication() {
        background = new Background();
        gameObjectHolder = new GameObjectHolder();
    }

    @Override
    public void onDrawFrame(GameCanvas canvas, Graphics g, float deltaTime) {
        update(canvas, deltaTime);
        render(canvas, g);
    }

    private void update(GameCanvas canvas, float deltaTime) {
        gameObjectHolder.update(canvas, deltaTime);
        background.update(canvas, deltaTime);
    }

    private void render(GameCanvas canvas, Graphics g) {
        gameObjectHolder.render(canvas, g);
        background.render(canvas, g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (e.getButton()) {
            case BUTTON1 -> gameObjectHolder.addGameObject(new Brick(e.getPoint()));
            case BUTTON3 -> gameObjectHolder.removeLast();
            default -> System.out.println(e.getButton());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }


}
