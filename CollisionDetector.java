package kr.ac.mnu.c_team.breakout.engine;

import java.awt.Rectangle;
import java.util.Iterator;
import java.util.List;

import kr.ac.mnu.c_team.breakout.entity.Ball;
import kr.ac.mnu.c_team.breakout.entity.Brick;
import kr.ac.mnu.c_team.breakout.entity.GameObject;
import kr.ac.mnu.c_team.breakout.entity.Paddle;
import kr.ac.mnu.c_team.breakout.entity.Collidable;
import kr.ac.mnu.c_team.breakout.entity.PowerUp; // PowerUp 임포트

public final class CollisionDetector {

    private CollisionDetector() {}

    public static boolean isColliding(Collidable a, Collidable b) {
        if (a == null || b == null) return false;
        Rectangle ra = a.getBounds();
        Rectangle rb = b.getBounds();
        return ra.intersects(rb);
    }

    public static void handleWallCollision(Ball ball,
                                           int minX, int minY,
                                           int maxX, int maxY) {

        if (ball == null) return;

        Rectangle b = ball.getBounds();
        Vector2D v = ball.getVelocity();

        // 왼쪽 / 오른쪽 벽
        if (b.x <= minX && v.x < 0) {
            v.x = -v.x;
            ball.getPosition().x = minX;
        } else if (b.x + b.width >= maxX && v.x > 0) {
            v.x = -v.x;
            ball.getPosition().x = maxX - b.width;
        }

        // 위쪽 벽
        if (b.y <= minY && v.y < 0) {
            v.y = -v.y;
            ball.getPosition().y = minY;
        }
    }

    public static void handlePaddleCollision(Ball ball, Paddle paddle) {
        if (ball == null || paddle == null) return;
        if (!isColliding(ball, paddle)) return;

        Rectangle b = ball.getBounds();
        Rectangle p = paddle.getBounds();
        Vector2D v = ball.getVelocity();

        if (v.y > 0) {
            double paddleCenter = p.getCenterX();
            double ballCenter = b.getCenterX();
            double offset = (ballCenter - paddleCenter) / (p.width / 2.0);

            double speed = v.magnitude();
            v.x = speed * offset;
            v.y = -Math.abs(v.y);

            ball.getPosition().y = p.y - b.height - 1;
        }
    }

    public static void handleBrickCollisions(Ball ball, List<? extends Brick> bricks) {
        if (ball == null || bricks == null) return;

        for (Brick brick : bricks) {
            // [수정] GameObject에서 상속된 isActive() 사용
            if (brick == null || !brick.isActive()) continue; 

            if (isColliding(ball, brick)) {
                resolveBallVsRect(ball, brick);
                brick.onCollision(ball);
                break;
            }
        }
    }

    /**
     * [추가] 패들과 파워업 리스트 간의 충돌을 검사하고, 충돌 시 해당 파워업을 패들에 적용합니다.
     */
    public static void handlePowerUpCollisions(Paddle paddle, List<PowerUp> powerUps) {
        if (paddle == null || powerUps == null) return;

        Iterator<PowerUp> iter = powerUps.iterator();
        while (iter.hasNext()) {
            PowerUp powerUp = iter.next();

            // 이미 비활성화된 아이템은 제거 (PowerUp.apply()에서 active = false로 설정)
            if (!powerUp.isActive()) { 
                iter.remove();
                continue;
            }

            if (isColliding(paddle, powerUp)) {
                powerUp.apply(paddle);
                iter.remove(); 
            }
        }
    }

    public static void resolveBallVsRect(Ball ball, GameObject rect) {
        if (ball == null || rect == null) return;
        if (!isColliding(ball, rect)) return;

        Rectangle b = ball.getBounds();
        Rectangle r = rect.getBounds();
        Vector2D v = ball.getVelocity();

        double overlapLeft   = b.getMaxX() - r.getMinX();
        double overlapRight  = r.getMaxX() - b.getMinX();
        double overlapTop    = b.getMaxY() - r.getMinY();
        double overlapBottom = r.getMaxY() - r.getMinY(); 

        double minOverlap = Math.min(
                Math.min(overlapLeft, overlapRight),
                Math.min(overlapTop, overlapBottom)
        );

        if (minOverlap == overlapLeft) {
            ball.getPosition().x -= overlapLeft;
            v.x = -Math.abs(v.x);
        } else if (minOverlap == overlapRight) {
            ball.getPosition().x += overlapRight;
            v.x = Math.abs(v.x);
        } else if (minOverlap == overlapTop) {
            ball.getPosition().y -= overlapTop;
            v.y = -Math.abs(v.y);
        } else {
            ball.getPosition().y += overlapBottom;
            v.y = Math.abs(v.y);
        }
    }

    public static Vector2D reflect(Vector2D velocity, Vector2D normal) {
        if (velocity == null || normal == null) return null;

        double dot = velocity.dot(normal);
        return new Vector2D(
                velocity.x - 2 * dot * normal.x,
                velocity.y - 2 * dot * normal.y
        );
    }
}