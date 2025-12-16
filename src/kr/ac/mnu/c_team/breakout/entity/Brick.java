package kr.ac.mnu.c_team.breakout.entity;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * 기본 벽돌 오브젝트.
 */
public class Brick extends GameObject {

    protected int hitPoints = 1;
    protected Color color = Color.ORANGE;

    public Brick(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public void update() {
        // 벽돌은 보통 움직이지 않으므로 비워둔다.
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (!active) return;

        g2d.setColor(color);
        g2d.fillRect(
            (int) position.x,
            (int) position.y,
            (int) width,
            (int) height
        );
    }

    @Override
    public void onCollision(Collidable other) {
        hitPoints--;
        if (hitPoints <= 0) {
            active = false; // 화면/충돌에서 제거
        }
    }

    public int getHitPoints() {
        return hitPoints;
    }
}