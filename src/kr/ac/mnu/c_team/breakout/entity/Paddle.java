package kr.ac.mnu.c_team.breakout.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import kr.ac.mnu.c_team.breakout.manager.InputManager;
import kr.ac.mnu.c_team.breakout.view.GamePanel;

public class Paddle extends GameObject {
    
    private double speed = 7.0; // 패들 이동 속도
    private InputManager input; // 키보드 입력을 확인하기 위해 필요

    public Paddle(double x, double y, InputManager input) {
        super(x, y, 100, 20); // 너비 100, 높이 20
        this.input = input;
    }

    @Override
    public void update() {
        // 키보드 입력에 따라 위치 변경
        if (input.left) {
            position.x -= speed;
        }
        if (input.right) {
            position.x += speed;
        }

        // 화면 밖으로 나가지 않게 막기 (경계 처리)
        if (position.x < 0) position.x = 0;
        if (position.x > GamePanel.WIDTH - width) position.x = GamePanel.WIDTH - width;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.fillRect((int)position.x, (int)position.y, (int)width, (int)height);
        
        // 테두리 그리기 (디자인)
        g.setColor(Color.WHITE);
        g.drawRect((int)position.x, (int)position.y, (int)width, (int)height);
    }

    @Override
    public void onCollision(Collidable other) {
        // 나중에 구현 (공이랑 부딪히면 소리 재생 등)
    }
}