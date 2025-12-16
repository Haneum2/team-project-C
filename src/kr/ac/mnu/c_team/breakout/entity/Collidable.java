package kr.ac.mnu.c_team.breakout.entity;

import java.awt.Rectangle;

/**
 * 충돌 처리가 필요한 모든 게임 객체가 구현해야 하는 인터페이스.
 */
public interface Collidable {

    /**
     * 충돌 판정을 위한 영역(AABB)을 반환한다.
     *
     * @return 충돌 박스(Rectangle)
     */
    Rectangle getBounds();

    /**
     * 다른 객체와 충돌했을 때 호출되는 메서드.
     *
     * @param other 충돌한 다른 객체
     */
    void onCollision(Collidable other);
}