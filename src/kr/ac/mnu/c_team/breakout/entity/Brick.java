package kr.ac.mnu.c_team.breakout.entity;

import java.awt.Color;
import java.awt.Graphics2D;

// 추상 클래스 (abstract)여야 합니다.
public abstract class Brick extends GameObject {
    
    public int hp;          // 내구도
    public int scoreValue;  // 점수
    public Color color;     // 색상
    public boolean isDestroyed = false; // 파괴 여부

    // ★ 이 생성자가 있어야 NormalBrick에서 에러가 안 납니다!
    public Brick(double x, double y, double width, double height, int hp, Color color) {
        super(x, y, width, height); // GameObject의 생성자 호출
        this.hp = hp;
        this.color = color;
        this.scoreValue = 100 * hp;
    }

    // 공에 맞았을 때 로직
    public void hit() {
        hp--;
        if (hp <= 0) {
            isDestroyed = true;
        }
    }

    @Override
    public void update() {
        // 움직이지 않으므로 비워둠
    }

    @Override
    public void draw(Graphics2D g) {
        if (!isDestroyed) {
            g.setColor(color);
            g.fillRect((int)position.x, (int)position.y, (int)width, (int)height);
            g.setColor(Color.BLACK);
            g.drawRect((int)position.x, (int)position.y, (int)width, (int)height);
        }
    }
}