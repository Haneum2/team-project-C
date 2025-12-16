package kr.ac.mnu.c_team.breakout.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import kr.ac.mnu.c_team.breakout.engine.Vector2D;

public abstract class GameObject implements Collidable {
    // protected는 다른 패키지(engine)에서 접근 불가하므로 Getter가 필요함
    protected Vector2D position;
    protected double width, height;

    public GameObject(double x, double y, double width, double height) {
        this.position = new Vector2D(x, y);
        this.width = width;
        this.height = height;
    }

    public abstract void update();
    public abstract void draw(Graphics2D g);

    // [수정] Collidable 인터페이스의 onCollision을 추상 메서드로 선언합니다.
    @Override
    public abstract void onCollision(Collidable other); // <--- 이 줄을 추가합니다.

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)position.x, (int)position.y, (int)width, (int)height);
    }
    
    // ★ [팀원 코드 호환용] Getter 추가
    public Vector2D getPosition() {
        return position;
    }
    
    public double getWidth() { return width; }
    public double getHeight() { return height; }
}