package kr.ac.mnu.c_team.breakout.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import kr.ac.mnu.c_team.breakout.engine.Vector2D; // 벡터 클래스 임포트
import kr.ac.mnu.c_team.breakout.view.GamePanel;

public class Ball extends GameObject {
    
    // ★ 변경점: velX, velY 대신 Vector2D 사용 (한흠 님 코드와 호환)
    private Vector2D velocity;

    public Ball(double x, double y) {
        super(x, y, 20, 20);
        // 초기 속도 설정
        this.velocity = new Vector2D(4.0, -4.0);
    }

    // ★ [팀원 코드 호환용] Getter 추가 (CollisionDetector에서 호출함)
    public Vector2D getVelocity() {
        return velocity;
    }

    @Override
    public void update() {
        // 벡터 덧셈으로 위치 이동
        position.x += velocity.x;
        position.y += velocity.y;
        
        // 주의: 벽 충돌 처리는 이제 CollisionDetector가 할 수도 있지만,
        // 일단 안전장치로 GamePanel 경계 체크는 유지하거나 제거해도 됨.
        // 여기서는 바닥(Game Over) 체크만 남김.
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillOval((int)position.x, (int)position.y, (int)width, (int)height);
    }

    @Override
    public void onCollision(Collidable other) {
        // CollisionDetector가 물리 반사를 처리해주므로,
        // 여기서는 소리를 재생하거나 특수 이펙트를 넣는 용도로 쓰면 됨.
    }
}