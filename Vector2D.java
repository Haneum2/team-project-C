package kr.ac.mnu.c_team.breakout.engine;

public class Vector2D {

    public double x;
    public double y;

    /** 생성자 */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /** 벡터 덧셈 */
    public void add(Vector2D v) {
        this.x += v.x;
        this.y += v.y;
    }

    /** 벡터 뺄셈 */
    public void subtract(Vector2D v) {
        this.x -= v.x;
        this.y -= v.y;
    }

    /** 벡터 크기(길이) 반환 */
    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    /** 벡터 정규화 (단위 벡터로 변환) */
    public void normalize() {
        double mag = magnitude();
        if (mag != 0) {
            x /= mag;
            y /= mag;
        }
    }

    /** 벡터 내적 (dot product) */
    public double dot(Vector2D v) {
        return this.x * v.x + this.y * v.y;
    }
}