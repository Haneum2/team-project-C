package kr.ac.mnu.c_team.breakout.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import kr.ac.mnu.c_team.breakout.engine.Vector2D;

/**
 * 벽돌깨기 게임에 등장하는 모든 오브젝트의 공통 부모 클래스.
 * 위치, 크기, 활성 상태를 가지고 있고, 충돌 영역을 제공한다.
 */
public abstract class GameObject implements Collidable {

    /** 왼쪽 위 기준 위치 */
    protected Vector2D position;

    /** 가로, 세로 크기 */
    protected double width;
    protected double height;

    /** 비활성화되면 화면/충돌에서 제외됨 */
    protected boolean active = true;

    public GameObject(double x, double y, double width, double height) {
        this.position = new Vector2D(x, y);
        this.width = width;
        this.height = height;
    }

    /** 매 프레임마다 상태를 갱신하는 메서드 */
    public abstract void update();

    /** 화면에 자신을 그리는 메서드 */
    public abstract void draw(Graphics2D g2d);

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(double x, double y) {
        this.position.x = x;
        this.position.y = y;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(
                (int) position.x,
                (int) position.y,
                (int) width,
                (int) height
        );
    }

    @Override
    public void onCollision(Collidable other) {
        // 기본 동작 없음. 필요하면 자식 클래스에서 override.
    }
}