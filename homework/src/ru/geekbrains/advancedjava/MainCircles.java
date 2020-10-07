package ru.geekbrains.advancedjava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static java.awt.event.MouseEvent.BUTTON1;
import static java.awt.event.MouseEvent.BUTTON3;

public class MainCircles extends JFrame implements MouseListener {
    private static final int POS_X = 400;
    private static final int POS_Y = 200;
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;

    private Background background;
    private SpriteHolder spriteHolder;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainCircles::new);
    }

    private MainCircles() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
        GameCanvas canvas = new GameCanvas(this);
        add(canvas);
        initApplication();
        setTitle("Circles");
        setVisible(true);

    }

    private void initApplication() {
        background = new Background();
        spriteHolder = new SpriteHolder();
    }

    public void onDrawFrame(GameCanvas canvas, Graphics g, float deltaTime) {
        update(canvas, deltaTime);
        render(canvas, g);
    }

    private void update(GameCanvas canvas, float deltaTime) {
        spriteHolder.update(canvas, deltaTime);
        background.update(canvas, deltaTime);
    }

    private void render(GameCanvas canvas, Graphics g) {
        spriteHolder.render(canvas, g);
        background.render(canvas, g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (e.getButton()) {
            case BUTTON1 -> spriteHolder.addSprite(e.getPoint());
            case BUTTON3 -> spriteHolder.removeLast();
            default -> System.out.println(e.getButton());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
