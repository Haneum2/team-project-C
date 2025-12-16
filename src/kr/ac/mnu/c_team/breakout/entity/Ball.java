package kr.ac.mnu.c_team.breakout.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import kr.ac.mnu.c_team.breakout.engine.Vector2D;

/**
 * 벽돌 깨기 게임의 공 오브젝트.
 */
public class Ball extends GameObject {

    private Vector2D velocity;
    private double radius;

    public Ball(double x, double y, double radius, Vector2D initialVelocity) {
        super(x, y, radius * 2, radius * 2);
        this.radius = radius;
        this.velocity = initialVelocity;
    }

    @Override
    public void update() {
        if (!active) return;

        // 위치 = 위치 + 속도
        position.add(velocity);
        // 실제 벽 충돌, 패들/벽돌 충돌 처리는
        // CollisionDetector 쪽에서 담당 예정
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (!active) return;

        g2d.setColor(Color.WHITE);
        g2d.fillOval(
            (int) position.x,
            (int) position.y,
            (int) (radius * 2),
            (int) (radius * 2)
        );
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }
}