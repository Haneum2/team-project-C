package kr.ac.mnu.c_team.breakout.entity;

import java.awt.Color;
import java.awt.Graphics2D;

import kr.ac.mnu.c_team.breakout.engine.Vector2D;

/**
 * 플레이어가 조종하는 패들.
 */
public class Paddle extends GameObject {

    private double speed = 5.0;

    public Paddle(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public void update() {
        if (!active) return;

        // 실제 이동 로직은 InputManager 상태를 보고
        // GamePanel 쪽에서 position.x를 조정하는 식으로 사용할 수 있다.
        // 여기서는 기본적으로 아무것도 하지 않음.
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (!active) return;

        g2d.setColor(Color.GREEN);
        g2d.fillRect(
            (int) position.x,
            (int) position.y,
            (int) width,
            (int) height
        );
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}