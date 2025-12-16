package kr.ac.mnu.c_team.breakout.entity;

import java.awt.Color;

/**
 * 여러 번 맞아야 깨지는 단단한 벽돌.
 */
public class HardBrick extends Brick {

    public HardBrick(double x, double y, double width, double height) {
        super(x, y, width, height);
        this.hitPoints = 3;          // 예: 3번 맞아야 깨짐
        this.color = Color.RED;
    }
}