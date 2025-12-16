package kr.ac.mnu.c_team.breakout.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

// ★ 엔진 패키지 임포트 필수
import kr.ac.mnu.c_team.breakout.engine.CollisionDetector;
import kr.ac.mnu.c_team.breakout.entity.Ball;
import kr.ac.mnu.c_team.breakout.entity.Brick;
import kr.ac.mnu.c_team.breakout.entity.Paddle;
import kr.ac.mnu.c_team.breakout.manager.InputManager;
import kr.ac.mnu.c_team.breakout.manager.MapGenerator;

public class GamePanel extends JPanel implements Runnable {
    
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    
    public static final int STATE_PLAY = 0;
    public static final int STATE_GAME_OVER = 1;
    public static final int STATE_VICTORY = 2;
    
    private BufferedImage dbImage;
    private Graphics2D dbg;
    
    private Thread gameThread;
    private boolean running = false;
    private final int FPS = 60;
    
    private InputManager inputManager;
    private Paddle paddle;
    private Ball ball;
    private MapGenerator mapGenerator;
    
    private int gameState = STATE_PLAY;
    private int score = 0;
    
    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setFocusable(true);
        requestFocus();
        
        inputManager = new InputManager();
        addKeyListener(inputManager);
        
        initGameObjects();
    }
    
    private void initGameObjects() {
        paddle = new Paddle(WIDTH / 2 - 50, HEIGHT - 60, inputManager);
        ball = new Ball(WIDTH / 2 - 10, HEIGHT - 100);
        mapGenerator = new MapGenerator();
    }
    
    private void resetGame() {
        initGameObjects();
        score = 0;
        gameState = STATE_PLAY;
    }
    
    public void startGame() {
        if (gameThread == null) {
            running = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        
        while(running) {
            update();
            render();
            draw();
            
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;
                if(remainingTime < 0) remainingTime = 0;
                Thread.sleep((long)remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void update() {
        inputManager.update();
        
        if (gameState == STATE_PLAY) {
            paddle.update();
            ball.update();
            
            // ★ [팀원 코드 통합] CollisionDetector 사용!
            
            // 1. 벽 충돌 처리 (화면 경계)
            CollisionDetector.handleWallCollision(ball, 0, 0, WIDTH, HEIGHT);
            
            // 2. 패들 충돌 처리 (정교한 각도 계산 포함됨)
            CollisionDetector.handlePaddleCollision(ball, paddle);
            
            // 3. 벽돌 충돌 처리 (리스트 통째로 넘김)
            // handleBrickCollisions 내부에서 충돌 시 반사까지 처리해줌
            // 다만, 점수 계산을 위해 여기서는 개별 체크 로직을 유지하거나,
            // CollisionDetector를 수정해서 점수를 리턴받아야 함.
            // 일단 기존 방식과 CollisionDetector의 물리 반사를 섞어서 구현:
            
            for (Brick brick : mapGenerator.bricks) {
                if (!brick.isDestroyed && CollisionDetector.isColliding(ball, brick)) {
                    // 한흠 님의 물리 반사 로직 적용
                    CollisionDetector.resolveBallVsRect(ball, brick);
                    
                    // 벽돌 파괴 및 점수 로직
                    brick.hit();
                    score += brick.scoreValue;
                    break;
                }
            }
            
            // 게임 오버 체크
            if (ball.getPosition().y > HEIGHT) {
                gameState = STATE_GAME_OVER;
            }
            
            // 승리 체크
            long remainingBricks = mapGenerator.bricks.stream().filter(b -> !b.isDestroyed).count();
            if (remainingBricks == 0) {
                gameState = STATE_VICTORY;
            }
        } else {
            if (inputManager.enter) {
                resetGame();
            }
        }
    }
    
    private void render() {
        if (dbImage == null) {
            dbImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
            dbg = (Graphics2D) dbImage.getGraphics();
            dbg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        
        dbg.setColor(Color.BLACK);
        dbg.fillRect(0, 0, WIDTH, HEIGHT);
        
        mapGenerator.draw(dbg);
        paddle.draw(dbg);
        ball.draw(dbg);
        
        drawScore(dbg);
        
        if (gameState == STATE_GAME_OVER) {
            drawGameOver(dbg);
        } else if (gameState == STATE_VICTORY) {
            drawVictory(dbg);
        }
    }
    
    private void drawScore(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Consolas", Font.BOLD, 20));
        g2.drawString("SCORE: " + score, 20, 30);
    }
    
    private void drawGameOver(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, WIDTH, HEIGHT);
        
        g2.setColor(Color.RED);
        g2.setFont(new Font("Arial", Font.BOLD, 50));
        String text = "GAME OVER";
        int textWidth = g2.getFontMetrics().stringWidth(text);
        g2.drawString(text, WIDTH/2 - textWidth/2, HEIGHT/2);
        
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        String subText = "Press ENTER to Restart";
        int subWidth = g2.getFontMetrics().stringWidth(subText);
        g2.drawString(subText, WIDTH/2 - subWidth/2, HEIGHT/2 + 50);
        g2.drawString("Final Score: " + score, WIDTH/2 - 60, HEIGHT/2 + 80);
    }
    
    private void drawVictory(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, WIDTH, HEIGHT);
        g2.setColor(Color.GREEN);
        g2.setFont(new Font("Arial", Font.BOLD, 50));
        String text = "STAGE CLEAR!";
        int textWidth = g2.getFontMetrics().stringWidth(text);
        g2.drawString(text, WIDTH/2 - textWidth/2, HEIGHT/2);
        
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        String subText = "Press ENTER to Play Again";
        int subWidth = g2.getFontMetrics().stringWidth(subText);
        g2.drawString(subText, WIDTH/2 - subWidth/2, HEIGHT/2 + 50);
    }
    
    private void draw() {
        Graphics g = getGraphics();
        if (g != null) {
            g.drawImage(dbImage, 0, 0, null);
            g.dispose();
        }
    }
}