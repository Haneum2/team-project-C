package kr.ac.mnu.c_team.breakout.manager;

import java.awt.Graphics2D;
import java.util.ArrayList;
import kr.ac.mnu.c_team.breakout.entity.Brick;
import kr.ac.mnu.c_team.breakout.entity.NormalBrick;
import kr.ac.mnu.c_team.breakout.view.GamePanel;

public class MapGenerator {
    
    // 벽돌들을 담을 리스트 (배열보다 관리하기 편함)
    public ArrayList<Brick> bricks;
    
    // 벽돌 크기 설정
    private int brickWidth = 80;
    private int brickHeight = 30;
    private int padding = 5; // 벽돌 사이 간격

    public MapGenerator() {
        bricks = new ArrayList<>();
        createLevel1();
    }
    
    // 스테이지 1: 단순한 4줄 배치
    public void createLevel1() {
        int rows = 4;
        int cols = 8;
        
        // 화면 중앙 정렬을 위한 오프셋 계산
        int startX = (GamePanel.WIDTH - (cols * (brickWidth + padding))) / 2;
        int startY = 50; // 상단 여백

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double x = startX + j * (brickWidth + padding);
                double y = startY + i * (brickHeight + padding);
                
                // 생성해서 리스트에 추가
                bricks.add(new NormalBrick(x, y, brickWidth, brickHeight));
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