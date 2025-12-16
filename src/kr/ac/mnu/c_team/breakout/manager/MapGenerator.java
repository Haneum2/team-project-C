package kr.ac.mnu.c_team.breakout.manager;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import kr.ac.mnu.c_team.breakout.entity.Brick;
import kr.ac.mnu.c_team.breakout.entity.NormalBrick;
import kr.ac.mnu.c_team.breakout.view.GamePanel;

public class MapGenerator {
    
    public ArrayList<Brick> bricks;
    private int brickWidth = 80;
    private int brickHeight = 30;
    private int padding = 5;

    public MapGenerator() {
        bricks = new ArrayList<>();
        // 생성 시에는 비워두고 GamePanel에서 loadLevel 호출
    }
    
    // ★ 레벨에 따라 다른 맵 생성
    public void loadLevel(int level) {
        bricks.clear(); // 기존 벽돌 제거
        
        switch(level) {
            case 1: createLevel1(); break;
            case 2: createLevel2(); break;
            case 3: createLevel3(); break;
            default: createLevel1(); break;
        }
    }
    
    // Level 1: 기본 4줄 배치
    private void createLevel1() {
        int rows = 4;
        int cols = 8;
        int startX = (GamePanel.WIDTH - (cols * (brickWidth + padding))) / 2;
        int startY = 60;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                bricks.add(new NormalBrick(startX + j * (brickWidth + padding),
                                           startY + i * (brickHeight + padding),
                                           brickWidth, brickHeight));
            }
        }
    }
    
    // Level 2: 체크무늬 배치 (구멍 뚫린 맵)
    private void createLevel2() {
        int rows = 6;
        int cols = 8;
        int startX = (GamePanel.WIDTH - (cols * (brickWidth + padding))) / 2;
        int startY = 60;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // (i + j)가 짝수일 때만 벽돌 생성
                if ((i + j) % 2 == 0) {
                    bricks.add(new NormalBrick(startX + j * (brickWidth + padding),
                                               startY + i * (brickHeight + padding),
                                               brickWidth, brickHeight));
                }
            }
        }
    }
    
    // Level 3: 랜덤 배치 & 피라미드
    private void createLevel3() {
        int rows = 7;
        int startY = 50;
        
        for (int i = 0; i < rows; i++) {
            // 줄마다 벽돌 개수가 달라짐 (피라미드 형태)
            int colsInRow = i + 2; 
            int totalWidth = colsInRow * (brickWidth + padding);
            int startX = (GamePanel.WIDTH - totalWidth) / 2;
            
            for (int j = 0; j < colsInRow; j++) {
                bricks.add(new NormalBrick(startX + j * (brickWidth + padding),
                                           startY + i * (brickHeight + padding),
                                           brickWidth, brickHeight));
            }
        }
    }
    
    public void draw(Graphics2D g) {
        for (Brick b : bricks) {
            if (!b.isDestroyed) {
                b.draw(g);
            }
        }
    }
}