public class Point {
    Point(double x, double y, double mass) {
        this.x = x;
        this.y = y;
        this.mass = mass;
    }

    Point(double x, double y, double mass, int color) {
        this.x = x;
        this.y = y;
        this.mass = mass;
        this.color = color;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getMass() {
        return mass;
    }

    public int getColor() {
        return color;
    }

    double x;
    double y;
    double mass;
    int color;
}
