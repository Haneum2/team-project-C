package kr.ac.mnu.c_team.breakout.entity;

/**
 * 파괴되면 주변 벽돌까지 함께 제거되는 폭발 벽돌.
 * 실제 주변 벽돌 제거 로직은 GamePanel 또는 Manager에서 처리하고,
 * 여기서는 "폭발했다"는 상태만 표시하도록 설계할 수 있다.
 */
public class ExplosiveBrick extends Brick {

    private boolean exploded = false;

    public ExplosiveBrick(double x, double y, double width, double height) {
        super(x, y, width, height);
    }

    @Override
    public void onCollision(Collidable other) {
        if (exploded) return;

        exploded = true;
        active = false;

        // TODO: 실제 폭발 시 주변 벽돌 처리 로직은
        // GamePanel 또는 StageManager에서 이 상태를 보고 처리하게 만들면 됨.
    }

    public boolean hasExploded() {
        return exploded;
    }
}